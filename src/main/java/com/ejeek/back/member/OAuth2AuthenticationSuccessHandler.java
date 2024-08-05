package com.ejeek.back.member;

import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("onAuthenticationSuccess");
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User customUser = (DefaultOAuth2User) authToken.getPrincipal();

        String type = (String) request.getSession().getAttribute("type");
        log.info("type: {}", type);

        if (Objects.equals(type, "LOGIN")) {
            // ACCESS/REFRESH TOKEN 반환
            // 예시는 참고만
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // tokenProvider로부터 토큰 발급하기
            // TokenDto tokenDto = new TokenDto();

            response.setStatus(200);
            response.addHeader("ACCESS_TOKEN", "tokenDto.getAccessToken()");
            response.addHeader("REFRESH_TOKEN", "tokenDto.getRefreshToken()");

        } else if (Objects.equals(type, "SIGNUP")) {
            // 회원가입 처리


        } else if (Objects.equals(type, "VALIDATE")) {
            log.info(customUser.getName());
            log.info(customUser.getAttributes().keySet().toString());

            // 이미 가입한 유저가 있으면 409
            // 가입한 유저가 없으면 200
            String email = (String) customUser.getAttributes().get("email");
            Optional<Member> member = memberRepository.findByEmail(email);

            if (member.isEmpty()) {
                throw new CustomException(ExceptionCode.MEMBER_EMAIL_EXIST);
            }
            response.setStatus(200);

        }
    }
}
