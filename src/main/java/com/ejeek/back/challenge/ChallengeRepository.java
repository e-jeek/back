package com.ejeek.back.challenge;

import com.ejeek.back.enums.ChallengeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    List<Challenge> findByStatusAndStartDateBefore(ChallengeStatus status, LocalDateTime now);

    List<Challenge> findByStatusAndEndDateBefore(ChallengeStatus status, LocalDateTime now);

}
