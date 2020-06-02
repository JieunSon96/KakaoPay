package com.kakaopay.kakao.model.coupon;

import com.kakaopay.kakao.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

// 유저 쿠폰 엔티티
@Entity
@Table(name="user_coupons")
public class UserCoupon extends DateAudit {

    // 유저 쿠폰 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_coupon_id")
    private Long id;

    // 쿠폰 아이디 (Coupon 테이블 PK)
    @NotNull
    @Column(name = "coupon_id")
    private Long couponId;

    // 유저 아이디
    @NotNull
    @Column(name = "user_id")
    private Long userId;

    // 쿠폰 사용 여부
    @NotNull
    @Column(name="utilization_status")
    private Boolean utilized;

    // 쿠폰 만료 날짜
    @NotNull
    @Column(name = "expiration_date")
    private String expirationDate;


    public UserCoupon() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getUtilized() {
        return utilized;
    }

    public void setUtilized(Boolean utilized) {
        this.utilized = utilized;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
