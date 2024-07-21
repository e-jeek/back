package com.ejeek.back.service;

import com.ejeek.back.member.Member;
import com.ejeek.back.dto.MemberDto;
import com.ejeek.back.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    public MemberServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveOrUpdateMember() {
        MemberDto memberDTO = new MemberDto();
        memberDTO.setEmail("test@example.com");
        memberDTO.setName("John Doe");

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@example.com");
        member.setName("John Doe");

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        MemberDto savedMember = memberService.createMember(memberDTO);

        assertEquals(1L, savedMember.getId());
        assertEquals("test@example.com", savedMember.getEmail());
        assertEquals("John Doe", savedMember.getName());
    }
}