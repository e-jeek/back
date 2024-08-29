package com.ejeek.back.challenge.repository;

import com.ejeek.back.challenge.entity.ChallengeConfirm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ChallengeConfirmRepository extends JpaRepository<ChallengeConfirm, Long> {

    @Query(value = "SELECT * FROM challenge_confirm c WHERE c.challenge_id = :challengeId AND DATE(c.created_at) = :date",
                    nativeQuery = true)
    Slice<ChallengeConfirm> findAllByChallengeIdAndCreatedAt(Long challengeId, LocalDate date, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM challenge_confirm c WHERE c.challenge_id = :challengeId AND c.member_id = :memberId AND DATE(c.created_at) = :date",
                    nativeQuery = true)
    Long countByChallengeAndMemberAndCreatedAt(Long challengeId, Long memberId, LocalDate date);
}
