package com.ejeek.back.challenge.challenge_member;

import com.ejeek.back.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeMemberRepository extends JpaRepository<ChallengeMember, Long> {

    boolean existsByChallenge(Challenge challenge);
}
