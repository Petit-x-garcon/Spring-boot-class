package com.sambat.demo.Common.Logging;

import com.sambat.demo.Common.Constant.LogConstant;
import org.springframework.stereotype.Component;

@Component
public class LogFormatter {
    private static final String LOG_FORMAT = "type:%s | id:%s | target=%s | method=%s | httpMethod=%s " +
            "| requestPath=%s | startTime=%s | endTime=%s | executionTime=%s ms";

    public String requestLog(String requestId, String target, String method, String httpMethod, String requestPath,
                             Long startTime){
        return String.format(LOG_FORMAT,
                LogConstant.REQUEST,
                requestId,
                target,
                method,
                httpMethod,
                requestPath,
                startTime,
                0,0);
    }

    public String responseLog(String requestId, String target, String method, String httpMethod, String requestPath,
                             Long startTime, Long endTime){
        return String.format(LOG_FORMAT,
                LogConstant.RESPONSE,
                requestId,
                target,
                method,
                httpMethod,
                requestPath,
                startTime,
                endTime,
                endTime - startTime);
    }

    public String errorLog(String requestId, String target, String method, String httpMethod, String requestPath,
                             Long startTime, Long endTime){
        return String.format(LOG_FORMAT,
                LogConstant.ERROR,
                requestId,
                target,
                method,
                httpMethod,
                requestPath,
                startTime,
                endTime,
                endTime - startTime);
    }
}
