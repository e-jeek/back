package com.ejeek.back.challenge;

import com.ejeek.back.enums.ChallengeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChallengeStatusScheduler {

    private final ChallengeRepository challengeRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateChallengeStatus() {
        LocalDateTime now = LocalDateTime.now();

        // INACTIVE -> ACTIVE
        List<Challenge> inactiveChallenges = challengeRepository.findByStatusAndStartDateBefore(ChallengeStatus.INACTIVE, now);
        for (Challenge challenge : inactiveChallenges) {
            challenge.updateStatus(ChallengeStatus.ACTIVE);
        }

        // ACTIVE -> FINISH
        List<Challenge> activeChallenges = challengeRepository.findByStatusAndEndDateBefore(ChallengeStatus.ACTIVE, now);
        for (Challenge challenge : activeChallenges) {
            challenge.updateStatus(ChallengeStatus.FINISH);
        }
    }
}
