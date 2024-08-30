package com.ejeek.back.action.repository;

import com.ejeek.back.action.entity.DietLog;
import com.ejeek.back.member.entity.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DietLogRepository extends JpaRepository<DietLog, Long> {
    List<DietLog> findByMemberAndDate(Member member, LocalDate date);
}
