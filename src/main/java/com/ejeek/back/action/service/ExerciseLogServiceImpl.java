package com.ejeek.back.action.service;

import com.ejeek.back.action.dto.ExerciseLogDto;
import com.ejeek.back.action.entity.ExerciseLog;
import com.ejeek.back.action.mapper.ExerciseLogMapper;
import com.ejeek.back.action.repository.ExerciseLogRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseLogServiceImpl implements ExerciseLogService {

    private final ExerciseLogRepository exerciseLogRepository;
    private final ExerciseLogMapper exerciseLogMapper;
    private final ImageService imageService;

    @Override
    @Transactional
    public ExerciseLogDto.Response createExerciseLog(ExerciseLogDto.CreateRequest request, Member member, MultipartFile multipartFile) {
        ExerciseLog exerciseLog = exerciseLogMapper.toEntity(request, member);

        ExerciseLog savedExerciseLog = exerciseLogRepository.save(exerciseLog);

        Optional.ofNullable(multipartFile).ifPresent(file -> {
            Image image = imageService.createImage(file, savedExerciseLog);
            savedExerciseLog.updateImage(image);
        });

        return exerciseLogMapper.toResponse(savedExerciseLog);
    }

    @Override
    @Transactional(readOnly = true)
    public ExerciseLogDto.Response getExerciseLogById(Long id, Member member) {
        ExerciseLog exercise = exerciseLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found with id " + id));

        if (!exercise.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to access this exercise");
        }

        return exerciseLogMapper.toResponse(exercise);
    }

    @Override
    @Transactional
    public ExerciseLogDto.Response updateExerciseLog(Long id, ExerciseLogDto.UpdateRequest request, Member member, MultipartFile multipartFile) {
        ExerciseLog exerciseLog = exerciseLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ExerciseLog not found with id " + id));

        if (!exerciseLog.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("You do not have permission to update this log");
        }

        exerciseLog.updateExerciseLog(request);

        Optional.ofNullable(multipartFile).ifPresent(file -> {
            Image image = imageService.updateImage(file, exerciseLog);
            exerciseLog.updateImage(image);
        });

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
    @Transactional(readOnly = true)
    public List<ExerciseLogDto.Response> getAllExerciseLog(Member member, LocalDate date) {
        List<ExerciseLog> exerciseLogs = exerciseLogRepository.findByMemberAndDate(member, date);
        return exerciseLogs.stream()
                .map(exerciseLogMapper::toResponse)
                .collect(Collectors.toList());
    }
}
