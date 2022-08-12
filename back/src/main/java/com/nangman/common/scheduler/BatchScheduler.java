package com.nangman.common.scheduler;

import com.nangman.api.service.BusService;
import com.nangman.api.service.UserService;
import com.nangman.db.entity.Nickname;
import com.nangman.db.entity.User;
import com.nangman.db.repository.NicknameRepository;
import com.nangman.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class BatchScheduler {

    private final UserService userService;
    private final BusService busService;

    //10초마다 실행
    @Scheduled(cron = "0 0 4 * * *")
    public void nicknameSchedule() {
        userService.updateUserNickname();
    }

    @Scheduled(cron = "10 * * * * *")
    public void busLoggingSchedule() {
        System.out.println("===========시발=============");
        busService.followBuses();
        System.out.println("===========굿~~=============");
    }

}