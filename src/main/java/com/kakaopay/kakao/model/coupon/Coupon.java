package com.kakaopay.kakao.model.coupon;

import com.kakaopay.kakao.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

// 쿠폰 코드 엔티티
@Entity
@Table(name="coupons")
public class Coupon  extends DateAudit {

    // 쿠폰 코드 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coupon_id")
    private Long id;

    // 쿠폰 코드
    @NotNull
    @Column(name = "coupon_code")
    private String couponCode;

    // 쿠폰 코드 해시값
    @NotNull
    @Column(name = "coupon_hash")
    private Long couponHash;

    // 쿠폰 사용 여부
    @NotNull
    @Column(name = "coupon_utilization_status")
    private boolean utilizationStatus;

    // 쿠폰 만료 날짜
    @Column(name = "coupon_expiration_date")
    private String expirationDate;

    public Coupon(){

    }

    public Coupon(String couponCode,Long couponHash,String expirationDate) {
    this.couponCode=couponCode;
    this.couponHash=couponHash;
    this.expirationDate=expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCouponHash() {
        return couponHash;
    }

    public void setCouponHash(Long couponHash) {
        this.couponHash = couponHash;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public boolean isUtilizationStatus() {
        return utilizationStatus;
    }

    public void setUtilizationStatus(boolean utilizationStatus) {
        this.utilizationStatus = utilizationStatus;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
