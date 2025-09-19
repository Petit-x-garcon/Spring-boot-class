package com.sambat.demo.Service.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenSchedule {
    @Scheduled(fixedRate = 2000)
    public void refeshTokenSchedule(){
        System.out.println("fixed schedule!");
    }

    @Scheduled(fixedDelay = 2000)
    public void refreshTokenDelay(){
        System.out.println("delay schedule!");
    }
}
