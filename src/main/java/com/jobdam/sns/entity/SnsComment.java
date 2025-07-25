package com.jobdam.sns.entity;

import com.jobdam.code.entity.BoardStatusCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.jobdam.user.entity.User;


@Getter @Setter
@Entity
@Table(name = "sns_comment")
public class SnsComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sns_comment_id")
    private Integer snsCommentId;

    @Column(name = "sns_post_id", nullable = false)
    private Integer snsPostId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "board_status_code_id", nullable = false)
    private Integer boardStatusCodeId;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    // user 객체
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sns_post_id", insertable = false, updatable = false)
    private SnsPost snsPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_status_code_id", insertable = false, updatable = false)
    private BoardStatusCode boardStatusCode;

}
