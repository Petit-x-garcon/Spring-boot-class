package com.sambat.demo.Service.mail;

import com.sambat.demo.Common.config.ApplicationConfiguration;
import com.sambat.demo.Common.wrapper.WebClientWrapper;
import com.sambat.demo.Dto.external.TelegramResponseDto;
import com.sambat.demo.Entity.OrderEntity;
import com.sambat.demo.Entity.OrderItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class NotificationService {
    @Autowired
    private ApplicationConfiguration appConfig;
    @Autowired
    private WebClientWrapper webClientWrapper;

    @Async
    public void sendMail(OrderEntity order){
        String threadName = Thread.currentThread().getName();

        log.info("[ASYNC-NOTIFICATION] Start sending notification to Telegram for Order: {} | Thread: {}", order.getId(), threadName);

        try {

            String formatMessage = this.generateTelegramNotificationTemplate(order);
            this.sendMessage(formatMessage);

            log.info("[ASYNC-NOTIFICATION] Sent notification to Telegram successfully for Order: {} | Thread: {}",order.getId(),threadName);
        } catch (Exception e) {
            log.error("[ASYNC-NOTIFICATION] Failed to send notification for Order: {} | Error: {}", order.getId(), e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void sendMessage(String message){
        String baseUrl = appConfig.getTelegram().getBaseUrl();
        String chatId = appConfig.getTelegram().getChatId();

        Map<String, String>  payload = new HashMap<>();
        payload.put("chat_id", chatId);
        payload.put("text", message);
        payload.put("parse_mode", "HTML");

        webClientWrapper.postTelegramMessage(baseUrl, payload, TelegramResponseDto.class);
    }


    private String generateTelegramNotificationTemplate(OrderEntity order){
        StringBuilder message = new StringBuilder();
        int itemCount = order.getOrderItems() != null ? order.getOrderItems().size() : 0;

        message.append("<b>🔔 New Order Received</b>\n");
        message.append("Order ID: #").append(order.getId()).append("\n");
        message.append("Status: ").append(order.getStatus()).append("\n");
        message.append("Ordered at: ").append(order.getCreatedAt()).append("\n");
        message.append("Items: <i>").append(itemCount).append("</i>\n");

        if(itemCount != 0) {
            message.append("\n<b>Order Details: </b>\n");

            for(OrderItemEntity item : order.getOrderItems()) {
                message
                        .append("  - Product ID: ").append(item.getProductId())
                        .append("  (Qty= ").append(item.getQuantity()).append(")\n");
            }
        } else {
            message.append("<i>No items</i>\n\n");
        }

        message.append("<b>⚡ Action required: check the order process</b>");

        return message.toString();
    }

}
