package com.ejeek.back.action.service;

import com.ejeek.back.action.dto.DietLogDto;
import com.ejeek.back.action.entity.DietLog;
import com.ejeek.back.action.mapper.DietLogMapper;
import com.ejeek.back.action.repository.DietLogRepository;
import com.ejeek.back.image.Image;
import com.ejeek.back.image.ImageService;
import com.ejeek.back.member.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DietLogServiceImpl implements LogService{

    private final DietLogRepository dietLogRepository;
    private final DietLogMapper dietLogMapper;
    private final ImageService imageService;

    @Override
    @Transactional
    public Object createLog(Map<String, String> requestBody, Member member, MultipartFile multipartFile) {
        DietLogDto.CreateRequest request = dietLogMapper.fromRequestMap(requestBody);

        DietLog dietLog = dietLogMapper.toEntity(request, member);
        DietLog savedDietLog = dietLogRepository.save(dietLog);

        Optional.ofNullable(multipartFile).ifPresent(file -> {
            Image image = imageService.createImage(file, savedDietLog);
            savedDietLog.updateImage(image);
        });
        return dietLogMapper.toResponse(savedDietLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getLogById(Long id, Member member) {
        DietLog dietLog = dietLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DietLog not found with id " + id));

        if (!dietLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to access this log");
        }

        return dietLogMapper.toResponse(dietLog);
    }

    @Override
    @Transactional
    public Object updateLog(Long id, Map<String, String> requestBody, Member member, MultipartFile multipartFile) {
        DietLog dietLog = dietLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DietLog not found with id " + id));

        if (!dietLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to update this log");
        }

        DietLogDto.UpdateRequest request = dietLogMapper.fromUpdateRequestMap(requestBody);
        dietLog.updateDietLog(request);

        Optional.ofNullable(multipartFile).ifPresent(file -> {
            Image image = imageService.updateImage(file, dietLog);
            dietLog.updateImage(image);
        });

        DietLog updatedDietLog = dietLogRepository.save(dietLog);

        return dietLogMapper.toResponse(updatedDietLog);
    }

    @Override
    @Transactional
    public void deleteLog(Long id, Member member) {
        DietLog dietLog = dietLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DietLog not found with id " + id));

        if (!dietLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this log");
        }

        dietLogRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DietLogDto.Response> getAllLogs(Member member, LocalDate date) {
        List<DietLog> dietLogs = dietLogRepository.findByMemberAndDate(member, date);
        return dietLogs.stream()
                .map(dietLogMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public double getDailyAverageScore(Member member, LocalDate date) {
        List<DietLogDto.Response> logs = getAllLogs(member, date);
        return logs.stream()
                .mapToDouble(DietLogDto.Response::getScore)  // DietLogDto에 'score' 필드가 있다고 가정
                .average()
                .orElse(0);
    }
}
