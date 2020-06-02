package com.kakaopay.kakao.repository;

import com.kakaopay.kakao.model.coupon.CouponMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponMessageRepository extends JpaRepository<CouponMessage, Long> {
    List<CouponMessage> findAllByUserIdOrderByCouponId(Long userId);
    CouponMessage findByCouponId(Long couponId);
}
