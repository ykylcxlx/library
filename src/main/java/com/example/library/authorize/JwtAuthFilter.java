package com.example.library.authorize;

import com.example.library.etc.ResultGenerator;
import com.example.library.pojo.entity.User;
import com.example.library.pojo.model.UserJwtInfo;
import com.example.library.util.JwtTokenUtil;
import com.example.library.util.ResponseUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author WangYi
 * @create 2024/7/30
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    @Lazy
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (! StringUtils.hasText(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        UserJwtInfo userJwtInfo = jwtTokenUtil.getUserJwtInfo(jwtToken);
        UserDetails targetUser = userDetailsService.loadUserByUsername(userJwtInfo.getAccount());
        if (targetUser == null) {
            ResponseUtil.response(response, ResultGenerator.genFail(HttpStatus.UNAUTHORIZED));
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(targetUser, null,
                targetUser.getAuthorities().stream().map(item ->
                        new SimpleGrantedAuthority(item.getAuthority())).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
