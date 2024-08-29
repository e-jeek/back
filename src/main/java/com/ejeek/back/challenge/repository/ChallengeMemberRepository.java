package com.ejeek.back.challenge.repository;

import com.ejeek.back.challenge.entity.Challenge;
import com.ejeek.back.challenge.entity.ChallengeMember;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeMemberRepository extends JpaRepository<ChallengeMember, Long> {

    boolean existsByChallenge(Challenge challenge);

    Optional<ChallengeMember> findByChallengeIdAndMemberId(Long challengeId, Long memberId);

    Slice<ChallengeMember> findByChallengeId(Long challengeId, Pageable pageable);
}
