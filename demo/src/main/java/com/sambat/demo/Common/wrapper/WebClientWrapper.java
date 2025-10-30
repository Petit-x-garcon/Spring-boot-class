package com.sambat.demo.Common.wrapper;

import com.sambat.demo.Dto.external.JsonPlaceholderCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class WebClientWrapper {
    @Autowired
    private WebClient webClient;

    public <T> Mono<T> getAsyncComment(String url, Class<T> clazz){
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleErrorResponse)
                .bodyToMono(clazz)
                .timeout(Duration.ofMillis(5000))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> throwable instanceof WebClientResponseException
                        && ((WebClientResponseException) throwable).getStatusCode().is5xxServerError()
                        ));
    }

    public <T> T getSyncComment(String url, Class<T> clazz){
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleErrorResponse)
                .bodyToMono(clazz)
                .timeout(Duration.ofMillis(5000))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> throwable instanceof WebClientResponseException
                                && ((WebClientResponseException) throwable).getStatusCode().is5xxServerError()
                        ))
                .block();
    }

    public <T> T postSyncComment(String url, Class<T> clazz, JsonPlaceholderCommentDto payload){
        return webClient
                .post()
                .uri(url)
                .bodyValue(payload)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleErrorResponse)
                .bodyToMono(clazz)
                .timeout(Duration.ofMillis(5000))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> throwable instanceof WebClientResponseException
                                && ((WebClientResponseException) throwable).getStatusCode().is5xxServerError()
                        ))
                .block();
    }

    public <T> void postTelegramMessage(String url, Object payload, Class<T> clazz){
        webClient.post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleErrorResponse)
                .bodyToMono(clazz)
                .timeout(Duration.ofMillis(5000))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> throwable instanceof WebClientResponseException
                                && ((WebClientResponseException) throwable).getStatusCode().is5xxServerError()
                        ))
                .block();
    }

    private Mono<Throwable> handleErrorResponse(ClientResponse response){
        return response.bodyToMono(String.class)
                .flatMap(body -> {
                    String error = response.statusCode().is4xxClientError() ?
                            "Client error " + response.statusCode() :
                            "Server error " + response.statusCode();

                    return Mono.error(
                            new WebClientResponseException(
                                    error,
                                    response.statusCode().value(),
                                    response.statusCode().toString(),
                                    null,
                                    body.getBytes(),
                                    null
                            )
                    );
                });
    }
}
