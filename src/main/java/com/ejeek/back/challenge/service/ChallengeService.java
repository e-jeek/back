package com.ejeek.back.challenge.service;

import com.ejeek.back.challenge.dto.ChallengeDto;
import com.ejeek.back.challenge.entity.Challenge;
import com.ejeek.back.challenge.entity.ChallengeConfirm;
import com.ejeek.back.challenge.dto.ChallengeConfirmDto;
import com.ejeek.back.challenge.mapper.ChallengeMapper;
import com.ejeek.back.challenge.repository.ChallengeConfirmRepository;
import com.ejeek.back.challenge.entity.ChallengeMember;
import com.ejeek.back.challenge.dto.ChallengeMemberDto;
import com.ejeek.back.challenge.repository.ChallengeMemberRepository;
import com.ejeek.back.challenge.repository.ChallengeRepository;
import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import com.ejeek.back.hashtag.Hashtag;
import com.ejeek.back.hashtag.HashtagService;
import com.ejeek.back.image.Image;
import com.ejeek.back.image.ImageService;
import com.ejeek.back.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeMapper challengeMapper;
    private final ChallengeRepository challengeRepository;
    private final ChallengeConfirmRepository challengeConfirmRepository;
    private final ChallengeMemberRepository challengeMemberRepository;
    private final ImageService imageService;
    private final HashtagService hashtagService;
    private final PasswordEncoder passwordEncoder;

    public ChallengeDto.Response createChallenge(Member member, ChallengeDto.Request request, MultipartFile multipartFile) {
        Challenge challenge = challengeMapper.toChallenge(request, member);
        challenge.encryptSecretKey(passwordEncoder);
        Challenge save = challengeRepository.save(challenge);

        Optional.ofNullable(multipartFile).ifPresent(file -> {
            Image image = imageService.createImage(file, save);
            save.updateImgUrl(image);
        });

        List<Hashtag> hashtags = hashtagService.createHashtags(request.getHashtags(), save);
        save.updateHashtags(hashtags);
        return challengeMapper.toChallengeResponse(save);
    }

    public ChallengeDto.Response modifyChallenge(Long challengeId, Member member, ChallengeDto.Request request,
                    MultipartFile multipartFile) {
        Challenge findChallenge = ensureChallengeIsEditable(challengeId, member);
        findChallenge.updateChallengeByDto(request);
        findChallenge.encryptSecretKey(passwordEncoder);

        Optional.ofNullable(multipartFile).ifPresent(file -> {
            Image image = imageService.updateImage(file, findChallenge);
            findChallenge.updateImgUrl(image);
        });

        List<Hashtag> hashtags = hashtagService.createHashtags(request.getHashtags(), findChallenge);
        findChallenge.updateHashtags(hashtags);
        return challengeMapper.toChallengeResponse(findChallenge);
    }

    @Transactional(readOnly = true)
    public ChallengeDto.Response getChallenge(Long challengeId) {
        Challenge findChallenge = findVerifiedChallenge(challengeId);
        return challengeMapper.toChallengeResponse(findChallenge);
    }

    /*
     * TODO update 정렬 필터 검색 기능
     * 정렬 : 생성된 시간 순, 조회수 많은 순, 참여자 많은 순
     * 필터 : 카테고리별(운동, 기상, 식단)로 필터링 가능하도록 한다, (종료/진행중)
     * 검색 : 해시태그로 검색 가능하도록 한다
     *
     */
    @Transactional(readOnly = true)
    public Slice<ChallengeDto.Response> getChallenges(Pageable pageable) {
        Slice<Challenge> challenges = challengeRepository.findAll(pageable);
        List<ChallengeDto.Response> responseList = challengeMapper.toChallengeResponseList(challenges.getContent());
        return new SliceImpl<>(responseList, challenges.getPageable(), challenges.hasNext());
    }

    public void deleteChallenge(Long challengeId, Member member) {
        Challenge findChallenge = ensureChallengeIsEditable(challengeId, member);
        challengeRepository.delete(findChallenge);
    }

    public ChallengeMemberDto.Response participateChallenge(Long challengeId, Member member) {
        Challenge findChallenge = findVerifiedChallenge(challengeId);
        ChallengeMember participation = challengeMapper.toChallengeMember(findChallenge, member);
        ChallengeMember save = challengeMemberRepository.save(participation);
        return challengeMapper.toChallengeMemberResponse(save);
    }

    public void withdrawChallenge(Long challengeId, Member member) {
        ChallengeMember participation = findVerifiedChallengeMember(challengeId, member);
        challengeMemberRepository.delete(participation);
    }

    public ChallengeConfirmDto.Response submitConfirmation(Long challengeId, Member member, ChallengeConfirmDto.Request request,
                    MultipartFile file) {
        // TODO 같은 날짜에 동일한 사람이 인증 불가
        Challenge findChallenge = findVerifiedChallenge(challengeId);
        ChallengeConfirm challengeConfirm = challengeMapper.toChallengeConfirm(request, member, findChallenge);
        ChallengeConfirm save = challengeConfirmRepository.save(challengeConfirm);

        Image image = imageService.updateImage(file, save);
        save.updateImage(image);

        return challengeMapper.toChallengeConfirmResponse(save);
    }

    public ChallengeConfirmDto.Response approveConfirmation(Member member, Long challengeId, Long confirmId, Boolean confirmed) {
        Challenge findChallenge = findVerifiedChallenge(challengeId);
        verifySameMember(member, findChallenge.getMember());
        ChallengeConfirm challengeConfirm = findVerifiedChallengeConfirm(confirmId);
        challengeConfirm.updateConfirm(confirmed);
        return challengeMapper.toChallengeConfirmResponse(challengeConfirm);
    }

    public Slice<ChallengeConfirmDto.Response> getConfirmationsByDate(Member member, Long challengeId, LocalDate date,
                    Pageable pageable) {
        Challenge findChallenge = findVerifiedChallenge(challengeId);
        verifySameMember(member, findChallenge.getMember());
        Slice<ChallengeConfirm> challengeConfirms =
                        challengeConfirmRepository.findAllByChallengeIdAndCreatedAt(challengeId, date, pageable);
        List<ChallengeConfirmDto.Response> responseList =
                        challengeMapper.toChallengeConfirmResponseList(challengeConfirms.getContent());
        return new SliceImpl<>(responseList, challengeConfirms.getPageable(), challengeConfirms.hasNext());
    }

    public Slice<ChallengeMemberDto.Response> getParticipants(Long challengeId, Pageable pageable) {
        Slice<ChallengeMember> challengeMembers = challengeMemberRepository.findByChallengeId(challengeId, pageable);
        List<ChallengeMemberDto.Response> responseList =
                        challengeMapper.toChallengeMemberResponseList(challengeMembers.getContent());
        return new SliceImpl<>(responseList, challengeMembers.getPageable(), challengeMembers.hasNext());
    }

    private Challenge ensureChallengeIsEditable(Long challengeId, Member member) {
        Challenge findChallenge = findVerifiedChallenge(challengeId);
        verifySameMember(findChallenge.getMember(), member);
        checkParticipantsPresence(findChallenge);
        findChallenge.checkEditableOrDeletable();
        return findChallenge;
    }

    private Challenge findVerifiedChallenge(Long challengeId) {
        return challengeRepository.findById(challengeId)
                        .orElseThrow(() -> new CustomException(ExceptionCode.CHALLENGE_NOT_FOUND));
    }

    private ChallengeMember findVerifiedChallengeMember(Long challengeId, Member member) {
        return challengeMemberRepository.findByChallengeIdAndMemberId(challengeId, member.getId())
                        .orElseThrow(() -> new CustomException(ExceptionCode.CHALLENGE_MEMBER_NOT_FOUND));
    }

    private ChallengeConfirm findVerifiedChallengeConfirm(Long confirmId) {
        return challengeConfirmRepository.findById(confirmId)
                        .orElseThrow(() -> new CustomException(ExceptionCode.CHALLENGE_MEMBER_NOT_FOUND));
    }

    private void verifySameMember(Member member, Member loginMember) {
        if (!member.getEmail().equals(loginMember.getEmail())) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_SAME);
        }
    }

    private void checkParticipantsPresence(Challenge challenge) {
        boolean isExist = challengeMemberRepository.existsByChallenge(challenge);
        if (isExist) {
            throw new CustomException(ExceptionCode.PARTICIPANT_EXIST);
        }
    }
}
