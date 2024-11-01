package com.ejeek.back.comment.service;

import com.ejeek.back.comment.dto.CommentDto;
import com.ejeek.back.comment.entity.Comment;
import com.ejeek.back.comment.mapper.CommentMapper;
import com.ejeek.back.comment.repository.CommentRepository;
import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import com.ejeek.back.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentDto.CommentResponse createComment(Member member, Feed feed, CommentDto.CommentRequest request ) {
        Comment comment = commentMapper.toCommentEntity(request, member, feed);
        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toCommentDto(savedComment);
    }

    @Transactional(readOnly = true)
    public CommentDto.CommentResponse getComment(Long commentId) {
        Comment comment = verifyComment(commentId);

        return commentMapper.toCommentDto(comment);
    }





    private Comment verifyComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() ->new CustomException(ExceptionCode.MEMBER_NOT_SAME)); // 변경해야함
    }
}
