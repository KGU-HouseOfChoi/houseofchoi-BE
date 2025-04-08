package com.noraknorak.auth.infrastructure;

import com.noraknorak.auth.exception.AuthenticationErrorCode;
import com.noraknorak.user.domain.Role;
import com.noraknorak.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import static org.mockito.Mockito.lenient;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    @Mock
    private JwtProperties jwtProperties;

    @Mock
    private JwtProperties.TokenConfig tokenConfig;

    @Mock
    private User user;

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        lenient().when(jwtProperties.getSecretKey()).thenReturn("test-secret-key-1234567890-1234567890");
        lenient().when(jwtProperties.getIssuer()).thenReturn("noraknorak");
        lenient().when(jwtProperties.getAccess()).thenReturn(tokenConfig);
        lenient().when(jwtProperties.getRefresh()).thenReturn(tokenConfig);
        lenient().when(tokenConfig.getExpiration()).thenReturn(3600L);
        lenient().when(jwtProperties.getAccessExpiration()).thenReturn(3600L);
        lenient().when(jwtProperties.getRefreshExpiration()).thenReturn(86400L);
        lenient().when(tokenConfig.getPrefix()).thenReturn("Bearer ");

        lenient().when(user.getId()).thenReturn(1L);
        lenient().when(user.getRole()).thenReturn(Role.SENIOR);
        jwtTokenProvider = new JwtTokenProvider(jwtProperties);
    }

    @Test
    @DisplayName("액세스 토큰 발급 성공")
    void 액세스_토큰_발급_성공(){
        // when
        String accessToken = jwtTokenProvider.provideAccessToken(user);
        Claims claims = jwtTokenProvider.getPayload(accessToken);

        // then
        assertEquals("access", claims.get("type", String.class));
        assertEquals("noraknorak", claims.getIssuer());
        assertEquals(Long.toString(user.getId()), claims.get("sub", String.class));
    }

    @Test
    @DisplayName("만료된 토큰 검증시 예외 발생")
    void 토큰_만료시_테스트(){
        // given
        lenient().when(jwtProperties.getAccess().getExpiration()).thenReturn(-1L);

        // when
        String accessToken = jwtTokenProvider.provideAccessToken(user);

        // then
        assertThrows(AuthenticationErrorCode.EXPIRED_TOKEN.toException().getClass()
                , () -> jwtTokenProvider.getPayload(accessToken)
        );
    }

    @Test
    @DisplayName("잘못된 서명 검증시 예외 처리")
    void 잘못된_서명(){
        // given
        String invalidToken = Jwts.builder()
                .claims(Map.of("type", "access"))
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor("wrong-test-secret-key-1234567890-1234567890".getBytes()))
                .compact();

        // when & then
        assertThrows(
                AuthenticationErrorCode.INVALID_SIGNATURE.toException().getClass(),
                () -> jwtTokenProvider.getPayload(invalidToken)
        );
    }

    @Test
    @DisplayName("잘못된 토큰 형식 검증시 예외 발생")
    void 잘못된_토큰(){
        // given
        String wrongToken = "invalid.token.wrong.content";

        // when & then
        assertThrows(
                AuthenticationErrorCode.NOT_MATCH_TOKEN_FORMAT.toException().getClass(),
                () -> jwtTokenProvider.getPayload(wrongToken)
        );
    }

    @Test
    @DisplayName("그외 다른 예외 발생 테스트")
    void 예외_발생_테스트(){
        // given
        String wrongToken = "invalid.token.wrong.content";

        // when
        assertThrows(
                AuthenticationErrorCode.NOT_DEFINE_TOKEN.toException().getClass()
                , () -> jwtTokenProvider.getPayload(wrongToken)
        );
    }
}
