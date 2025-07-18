package com.jobdam.sns.mapper;

import com.jobdam.sns.dto.BookmarkRequestDto;
import com.jobdam.sns.dto.BookmarkResponseDto;
import com.jobdam.sns.entity.Bookmark;
import com.jobdam.sns.entity.SnsPost;
import com.jobdam.user.entity.User;

import java.time.LocalDateTime;

public class BookmarkMapper {

    public static Bookmark toEntity(BookmarkRequestDto dto, Integer userId) {
        Bookmark bookmark = new Bookmark();
        bookmark.setSnsPostId(dto.getSnsPostId());
        bookmark.setUserId(userId);
        bookmark.setCreatedAt(LocalDateTime.now()); // 북마크 시간 설정
        return bookmark;
    }

    public static BookmarkResponseDto toDto(Bookmark bookmark, SnsPost post, User author) {
        BookmarkResponseDto dto = new BookmarkResponseDto();
        dto.setBookmarkId(bookmark.getBookmarkId());
        dto.setSnsPostId(bookmark.getSnsPostId());
        dto.setBookmarkedAt(bookmark.getCreatedAt());

        if (post != null) {
            dto.setTitle(post.getTitle());
            dto.setThumbnailImageUrl(post.getImageUrl());
        }

        // 작성자 정보
        if (author != null) {
            dto.setUserId(author.getUserId());
            dto.setNickname(author.getNickname());
        }

        return dto;
    }
}
