package com.ejeek.back.action.service;

import com.ejeek.back.action.dto.WakeupLogDto;
import com.ejeek.back.action.entity.WakeupLog;
import com.ejeek.back.action.mapper.WakeupLogMapper;
import com.ejeek.back.action.repository.WakeupLogRepository;
import com.ejeek.back.member.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WakeupLogServiceImpl implements WakeupLogService {

    private final WakeupLogRepository wakeupLogRepository;
    private final WakeupLogMapper wakeupLogMapper;

    @Override
    @Transactional
    public WakeupLogDto.Response createWakeupLog(WakeupLogDto.CreateRequest request, Member member) {
        WakeupLog wakeupLog = wakeupLogMapper.toEntity(request);
        wakeupLog.setMember(member);

        WakeupLog savedWakeupLog = wakeupLogRepository.save(wakeupLog);
        return wakeupLogMapper.toResponse(savedWakeupLog);
    }

    @Override
    @Transactional
    public WakeupLogDto.Response updateWakeupLog(Long id, WakeupLogDto.UpdateRequest request, Member member) {
        WakeupLog wakeupLog = wakeupLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WakeupLog not found with id " + id));

        if (!wakeupLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to update this log");
        }

        wakeupLogMapper.updateFromDto(request, wakeupLog);
        WakeupLog updatedWakeupLog = wakeupLogRepository.save(wakeupLog);
        return wakeupLogMapper.toResponse(updatedWakeupLog);
    }

    @Override
    @Transactional
    public void deleteWakeupLog(Long id, Member member) {
        WakeupLog wakeupLog = wakeupLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WakeupLog not found with id " + id));

        if (!wakeupLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this log");
        }

        wakeupLogRepository.deleteById(id);
    }

    @Override
    public List<WakeupLogDto.Response> getAllWakeupLog(Member member) {
        List<WakeupLog> wakeupLogs = wakeupLogRepository.findByMember(member);
        return wakeupLogs.stream()
                .map(wakeupLogMapper::toResponse)
                .collect(Collectors.toList());
    }
}
