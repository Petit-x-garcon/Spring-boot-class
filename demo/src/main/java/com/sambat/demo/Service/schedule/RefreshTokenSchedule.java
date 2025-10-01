package com.sambat.demo.Service.schedule;

import com.sambat.demo.Service.security.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenSchedule {
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Scheduled(cron = "0 0 0/3 * * ?") // every 3 hours from hour 00:00
    public void deleteToken(){
        refreshTokenService.deleteToken();
    }
}
