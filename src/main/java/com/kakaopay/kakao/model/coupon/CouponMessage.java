package com.kakaopay.kakao.model.coupon;

import com.kakaopay.kakao.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

// 쿠폰 알람 메세지 엔티티
@Entity
@Table(name = "coupon_message")
public class CouponMessage extends DateAudit {
    // 쿠폰 메시지 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coupon_message_id")
    private Long id;

    // 유저 아이디
    @NotNull
    @Column(name = "user_id")
    private Long userId;

    // 메세지 확인 여부
    @NotNull
    @Column(name = "coupon_message_checked")
    private boolean messageChecked;

    // 메세지 확인 여부
    @NotNull
    @Column(name = "coupon_id")
    private Long couponId;

    public CouponMessage() {

    }

    public CouponMessage(Long userId, Long couponId,boolean messageChecked) {
        this.userId = userId;
        this.couponId= couponId;
        this.messageChecked = messageChecked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isMessageChecked() {
        return messageChecked;
    }

    public void setMessageChecked(boolean messageChecked) {
        this.messageChecked = messageChecked;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
}
