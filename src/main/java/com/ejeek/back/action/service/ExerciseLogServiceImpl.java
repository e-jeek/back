package com.ejeek.back.action.service;

import com.ejeek.back.action.dto.ExerciseLogDto;
import com.ejeek.back.action.entity.ExerciseLog;
import com.ejeek.back.action.mapper.ExerciseLogMapper;
import com.ejeek.back.action.repository.ExerciseLogRepository;
import com.ejeek.back.member.Member;
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
public class ExerciseLogServiceImpl implements ExerciseLogService {

    private final ExerciseLogRepository exerciseLogRepository;
    private final ExerciseLogMapper exerciseLogMapper;

    @Override
    @Transactional
    public ExerciseLogDto.Response createExerciseLog(ExerciseLogDto.CreateRequest request, Member member) {
        ExerciseLog exerciseLog = exerciseLogMapper.toEntity(request);
        exerciseLog.setMember(member);

        ExerciseLog savedExerciseLog = exerciseLogRepository.save(exerciseLog);
        return exerciseLogMapper.toResponse(savedExerciseLog);
    }

    @Override
    @Transactional
    public ExerciseLogDto.Response updateExerciseLog(Long id, ExerciseLogDto.UpdateRequest request, Member member) {
        ExerciseLog exerciseLog = exerciseLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ExerciseLog not found with id " + id));

        if (!exerciseLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to update this log");
        }

        exerciseLogMapper.updateFromDto(request, exerciseLog);
        ExerciseLog updatedExerciseLog = exerciseLogRepository.save(exerciseLog);
        return exerciseLogMapper.toResponse(updatedExerciseLog);
    }

    @Override
    @Transactional
    public void deleteExerciseLog(Long id, Member member) {
        ExerciseLog exerciseLog = exerciseLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ExerciseLog not found with id " + id));

        if (!exerciseLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this log");
        }

        exerciseLogRepository.deleteById(id);
    }

    @Override
    public List<ExerciseLogDto.Response> getAllExerciseLog(Member member) {
        List<ExerciseLog> exerciseLogs = exerciseLogRepository.findByMember(member, Sort.by(Sort.Direction.DESC, "createdDate"));
        return exerciseLogs.stream()
                .map(exerciseLogMapper::toResponse)
                .collect(Collectors.toList());
    }
}
