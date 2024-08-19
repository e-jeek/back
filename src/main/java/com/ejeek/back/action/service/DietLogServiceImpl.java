package com.ejeek.back.action.service;

import com.ejeek.back.action.dto.DietLogDto;
import com.ejeek.back.action.entity.DietLog;
import com.ejeek.back.action.mapper.DietLogMapper;
import com.ejeek.back.action.repository.DietLogRepository;
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
public class DietLogServiceImpl implements DietLogService{

    private final DietLogRepository dietLogRepository;
    private final DietLogMapper dietLogMapper;

    @Override
    @Transactional
    public DietLogDto.Response createDietLog(DietLogDto.CreateRequest request, Member member) {
        DietLog dietLog = dietLogMapper.toEntity(request);
        dietLog.setMember(member);

        DietLog savedDietLog = dietLogRepository.save(dietLog);
        return dietLogMapper.toResponse(savedDietLog);
    }

    @Override
    @Transactional
    public DietLogDto.Response updateDietLog(Long id, DietLogDto.UpdateRequest request, Member member) {
        DietLog dietLog = dietLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DietLog not found with id " + id));

        if (!dietLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to update this log");
        }

        dietLogMapper.updateFromDto(request, dietLog);
        DietLog updatedDietLog = dietLogRepository.save(dietLog);
        return dietLogMapper.toResponse(updatedDietLog);
    }

    @Override
    @Transactional
    public void deleteDietLog(Long id, Member member) {
        DietLog dietLog = dietLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DietLog not found with id " + id));

        if (!dietLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this log");
        }

        dietLogRepository.deleteById(id);
    }

    @Override
    public List<DietLogDto.Response> getAllDietLog(Member member) {
        List<DietLog> dietLogs = dietLogRepository.findByMember(member, Sort.by(Sort.Direction.DESC, "createdDate"));
        return dietLogs.stream()
                .map(dietLogMapper::toResponse)
                .collect(Collectors.toList());
    }
}
