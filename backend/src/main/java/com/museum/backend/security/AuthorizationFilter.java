package com.museum.backend.security;

import com.museum.backend.exceptions.NotFoundException;
import com.museum.backend.exceptions.UnauthorizedException;
import com.museum.backend.models.dto.JwtUserDTO;
import com.museum.backend.models.entities.UserEntity;
import com.museum.backend.models.enums.Role;
import com.museum.backend.repositories.UserEntityRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.function.Function;

@Component
public class AuthorizationFilter  extends OncePerRequestFilter {

    @Value("${authorization.token.header.name}")
    private String authorizationHeaderName;
    @Value("${authorization.token.header.prefix}")
    private String authorizationHeaderPrefix;
    @Value("${authorization.token.secret}")
    private String authorizationSecret;

    private final UserEntityRepository userEntityRepository;

    public AuthorizationFilter(UserEntityRepository userEntityRepository)
    {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader(authorizationHeaderName);
        if (authorizationHeader == null || !authorizationHeader.startsWith(authorizationHeaderPrefix)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = authorizationHeader.replace(authorizationHeaderPrefix, "");
        try {
            Claims claims = getAllClaimsFromToken(token);

            UserEntity userEntity = userEntityRepository.findById(Integer.valueOf(claims.getId())).orElseThrow(NotFoundException::new);
            if(!userEntity.getToken().equals(token)) {
                throw new UnauthorizedException();
            }

            JwtUserDTO jwtUser = new JwtUserDTO(Integer.valueOf(claims.getId()), claims.getSubject(), null, Role.valueOf(claims.get("role", String.class)));
            Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.error("JWT Authentication failed from: " + httpServletRequest.getRemoteHost());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

        private Date getExpirationDateFromToken(String token) {
            return getClaimFromToken(token, Claims::getExpiration);
        }

        private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
        }

        private Claims getAllClaimsFromToken(String token) {
            return Jwts.parser().setSigningKey(authorizationSecret).parseClaimsJws(token).getBody();
        }

        public Boolean isTokenExpired(String token) {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        }
}
