package com.ejeek.back.challenge;

import com.ejeek.back.challenge.challenge_member.ChallengeMemberRepository;
import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import com.ejeek.back.image.ImageService;
import com.ejeek.back.member.Member;
import com.ejeek.back.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeMapper challengeMapper;
    private final ChallengeRepository challengeRepository;
    private final ChallengeMemberRepository challengeMemberRepository;
    private final MemberMapper memberMapper;
    private final ImageService imageService;

    public ChallengeDto.Response createChallenge(Member member, ChallengeDto.Request request, MultipartFile file) {
        Challenge challenge = challengeMapper.toChallenge(request, member);
        Challenge save = challengeRepository.save(challenge);
        imageService.createImage(file, save);
        return challengeMapper.toResponse(challenge, memberMapper.toResponse(member));
    }

    public ChallengeDto.Response modifyChallenge(Long challengeId, Member loginMember, ChallengeDto.Request request,
                    MultipartFile file) {
        Challenge findChallenge = findVerifiedChallenge(challengeId);
        verifySameMember(findChallenge.getMember(), loginMember);
        checkParticipantsPresence(findChallenge);
        findChallenge.checkEditableOrDeletable();
        // TODO update 하면 됨
        return null;
    }

    @Transactional(readOnly = true)
    public ChallengeDto.Response getChallenge(Long challengeId) {
        Challenge findChallenge = findVerifiedChallenge(challengeId);
        return challengeMapper.toResponse(findChallenge, memberMapper.toResponse(findChallenge.getMember()));
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
        return challengeMapper.toReponseSlice(challenges);
    }

    public void deleteChallenge(Long challengeId, Member loginMember) {
        Challenge findChallenge = findVerifiedChallenge(challengeId);
        verifySameMember(findChallenge.getMember(), loginMember);
        checkParticipantsPresence(findChallenge);
        findChallenge.checkEditableOrDeletable();
        // TODO delete 하면 됨
    }

    private Challenge findVerifiedChallenge(Long challengeId) {
        return challengeRepository.findById(challengeId)
                        .orElseThrow(() -> new CustomException(ExceptionCode.CHALLENGE_NOT_FOUND));
    }

    private void verifySameMember(Member creator, Member loginMember) {
        if (!creator.getEmail().equals(loginMember.getEmail())) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_SAME);
        }
    }

    private void checkParticipantsPresence(Challenge challenge) {
        boolean isExist = challengeMemberRepository.existsByChallenge(challenge);
        if (!isExist) {
            throw new CustomException(ExceptionCode.PARTICIPANT_EXIST);
        }
    }
}
