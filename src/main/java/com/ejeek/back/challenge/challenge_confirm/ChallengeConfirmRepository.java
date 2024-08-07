package com.ejeek.back.challenge.challenge_confirm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeConfirmRepository extends JpaRepository<ChallengeConfirm, Long> {
}
