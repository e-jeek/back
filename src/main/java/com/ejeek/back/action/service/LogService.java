package com.ejeek.back.action.service;

import com.ejeek.back.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LogService {
    Object createLog(Map<String, String> requestBody, Member member, MultipartFile file);

    Object getLogById(Long id, Member member);

    Object updateLog(Long id, Map<String, String> requestBody, Member member, MultipartFile multipartFile);

    void deleteLog(Long id, Member member);

    List<?> getAllLogs(Member member, LocalDate date);

    double getDailyAverageScore(Member member, LocalDate date);
}
