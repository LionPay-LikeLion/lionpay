package com.jobdam.user.service;

import com.jobdam.common.util.JwtProvider;
import com.jobdam.user.dto.LoginRequestDto;
import com.jobdam.user.dto.RegisterRequestDto;
import com.jobdam.user.entity.User;
import com.jobdam.user.mapper.UserMapper;
import com.jobdam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void register(RegisterRequestDto request) {
        if (existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        } if (existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        String encodedPw = passwordEncoder.encode(request.getPassword());
        System.out.println("비밀번호 인코딩 완료");
        User user = userMapper.toEntity(request, encodedPw);
        System.out.println("유저 정보 매핑 완료");
        userRepository.save(user);
        System.out.println("유저 정보 저장 요청 완료");
    }

    public boolean checkNicknameExists(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean checkEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public String login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        System.out.println(">>>>> [Service] 로그인 처리 중, 이메일: " + request.getEmail());

        String token = jwtProvider.createToken(
                user.getUserId(),
                user.getEmail(),
                user.getRoleCode().getCode()
        );
        System.out.println(">>>>> [Service] 생성된 JWT 토큰: " + token);

        return token;
    }

}
