package com.jobdam.community.entity;

import jakarta.persistence.*;
import lombok.*;
import com.jobdam.user.entity.User;
import com.jobdam.code.entity.SubscriptionLevelCode;


@Getter @Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Integer communityId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "subscription_level_code_id", nullable = false)
    private Integer subscriptionLevelCodeId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "enter_point")
    private Integer enterPoint;

    @Column(name = "current_member")
    private Integer currentMember;

    @Column(name = "max_member", columnDefinition = "INT DEFAULT 30")
    private Integer maxMember;

    @Column(name = "profile_image_url", length = 255)
    private String profileImageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_level_code_id", insertable = false, updatable = false)
    private SubscriptionLevelCode subscriptionLevelCode;

}
