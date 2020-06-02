package com.kakaopay.kakao.repository;

import com.kakaopay.kakao.model.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCouponHash(Long couponHash);
    Coupon findFirstByUtilizationStatusFalseOrderByIdAsc();
    List<Coupon> findAllByExpirationDateOrderByIdAsc(String expirationDate);
}
