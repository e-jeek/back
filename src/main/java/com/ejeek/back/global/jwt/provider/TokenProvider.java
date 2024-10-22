package com.ejeek.back.global.jwt.provider;

import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import com.ejeek.back.global.jwt.dto.TokenDto;
import com.ejeek.back.member.dto.MemberDto;
import com.ejeek.back.member.entity.Member;
import com.ejeek.back.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

//JWT를 생성하고 검증하는 컴포넌트
@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private final MemberRepository memberRepository;
    private static final String BEARER_TYPE = "Bearer";
    private static final String AUTHORITIES_KEY = "auth";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-validity-in-seconds}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityInMilliseconds;

    private Key key;

    // 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto.Response generateToken(MemberDto.SimpleResponse member) {
        // member의 role
        String authorities = member.getRole().toString();

        // 토큰의 expire 시간을 설정
        long now = (new Date()).getTime();
        Date accessValidity = new Date(now + this.accessTokenValidityInMilliseconds);
        Date refreshValidity = new Date(now + this.refreshTokenValidityInMilliseconds);

        String accessToken = Jwts.builder()
                .setSubject(member.getEmail()) // member.getEmail()
                .claim("auth", authorities)
                .setExpiration(accessValidity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(refreshValidity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.Response.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());


        Member member = memberRepository.findByEmail(claims.getSubject()).orElseThrow(() ->
                new CustomException(ExceptionCode.MEMBER_NOT_EXIST));

        return new UsernamePasswordAuthenticationToken(member, token, authorities);
    }

    // 토큰의 유효성 검증을 수행
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {

            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {

            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {

            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {

            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
    public static String resolveTokenFromReqeust(HttpServletRequest request) {
        String authHeaderValue = request.getHeader(AUTHORIZATION);
        if (authHeaderValue != null) {
            return resolveToken(authHeaderValue);
        }
        return null;
    }

    // Authorization Header의 Value 에서 Bearer 뒤의 토큰 값을 꺼내오기 위한 메소드
    public static String resolveToken(String authHeaderValue) {
        if (authHeaderValue.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            return authHeaderValue.substring(BEARER_TYPE.length()).trim();
        }
        return null;
    }
}
