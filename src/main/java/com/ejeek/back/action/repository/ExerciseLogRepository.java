package com.ejeek.back.action.repository;

import com.ejeek.back.action.entity.ExerciseLog;
import com.ejeek.back.member.entity.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {
    List<ExerciseLog> findByMember(Member member, Sort sort);
}
