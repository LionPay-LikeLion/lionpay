package com.jobdam.user.controller;

import com.jobdam.common.util.CustomUserDetails;
import com.jobdam.user.dto.OAuthRegisterRequestDto;
import com.jobdam.user.dto.UserProfileDto;
import com.jobdam.user.dto.UserSearchResponseDto;
import com.jobdam.user.service.UserService;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.jobdam.user.dto.ChangePasswordRequestDto;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Integer userId = userDetails.getUserId();
        UserProfileDto userProfile = userService.getUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/profile/image")
    public ResponseEntity<String> updateProfileImage(
            @RequestParam("image") MultipartFile image,
            @AuthenticationPrincipal CustomUserDetails user // JWT 인증된 유저 정보
    ) {
        userService.updateProfileImage(user.getUserId(), image);
        return ResponseEntity.ok("프로필 이미지가 변경되었습니다.");
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdrawUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Integer userId = userDetails.getUserId();
        userService.withdrawUser(userId);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ChangePasswordRequestDto requestDto
    ) {
        userService.changePassword(userDetails.getUserId(), requestDto);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserSearchResponseDto>> searchUsers(
            @RequestParam String keyword,
            @AuthenticationPrincipal CustomUserDetails user) {
        List<UserSearchResponseDto> response = userService.searchUsersByNickname(keyword, user.getUserId());
        return ResponseEntity.ok(response);
    }







}

