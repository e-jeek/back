package com.ejeek.back.action.service;

import com.ejeek.back.action.dto.WakeupLogDto;
import com.ejeek.back.action.entity.WakeupLog;
import com.ejeek.back.action.mapper.WakeupLogMapper;
import com.ejeek.back.action.repository.WakeupLogRepository;
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
public class WakeupLogServiceImpl implements LogService {

    private final WakeupLogRepository wakeupLogRepository;
    private final WakeupLogMapper wakeupLogMapper;
    private final ImageService imageService;

    @Override
    @Transactional
    public Object createLog(Map<String, String> requestBody, Member member, MultipartFile multipartFile) {
        WakeupLogDto.CreateRequest request = wakeupLogMapper.fromRequestMap(requestBody);

        WakeupLog wakeupLog = wakeupLogMapper.toEntity(request, member);
        WakeupLog savedWakeupLog = wakeupLogRepository.save(wakeupLog);

        Optional.ofNullable(multipartFile).ifPresent(file -> {
            Image image = imageService.createImage(file, savedWakeupLog);
            savedWakeupLog.updateImage(image);
        });

        return wakeupLogMapper.toResponse(savedWakeupLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getLogById(Long id, Member member) {
        WakeupLog wakeupLog = wakeupLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WakeupLog not found with id " + id));

        if (!wakeupLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to access this wakeup log");
        }

        return wakeupLogMapper.toResponse(wakeupLog);
    }

    @Override
    @Transactional
    public Object updateLog(Long id, Map<String, String> requestBody, Member member, MultipartFile multipartFile) {
        WakeupLog wakeupLog = wakeupLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WakeupLog not found with id " + id));

        if (!wakeupLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to update this log");
        }

        WakeupLogDto.UpdateRequest request = wakeupLogMapper.fromUpdateRequestMap(requestBody);
        wakeupLog.updateWakeupLog(request);

        Optional.ofNullable(multipartFile).ifPresent(file -> {
            Image image = imageService.updateImage(file, wakeupLog);
            wakeupLog.updateImage(image);
        });

        WakeupLog updatedWakeupLog = wakeupLogRepository.save(wakeupLog);

        return wakeupLogMapper.toResponse(updatedWakeupLog);
    }

    @Override
    @Transactional
    public void deleteLog(Long id, Member member) {
        WakeupLog wakeupLog = wakeupLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WakeupLog not found with id " + id));

        if (!wakeupLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this log");
        }

        wakeupLogRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WakeupLogDto.Response> getAllLogs(Member member, LocalDate date) {
        List<WakeupLog> wakeupLogs = wakeupLogRepository.findByMemberAndDate(member, date);
        return wakeupLogs.stream()
                .map(wakeupLogMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public double getDailyAverageScore(Member member, LocalDate date) {
        List<WakeupLogDto.Response> logs = getAllLogs(member, date);
        return logs.stream()
                .mapToDouble(WakeupLogDto.Response::getScore)
                .average()
                .orElse(0);
    }
}
