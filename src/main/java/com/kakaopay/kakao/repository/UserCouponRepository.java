package com.kakaopay.kakao.repository;

import com.kakaopay.kakao.model.coupon.UserCoupon;
import com.kakaopay.kakao.vo.UserCouponResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    // 유저가 발급 받은 쿠폰 전체 가져 오기 (이미 사용한 쿠폰도 함께 가져 오기)
    @Query(value="SELECT row_number() over (order by user_coupon_id) AS rowNum, uc.user_id AS userId,uc.coupon_id AS couponId,c.coupon_code AS couponCode,uc.expiration_date AS expirationDate FROM user_coupons AS uc INNER JOIN coupons AS c ON uc.coupon_id=c.coupon_id AND uc.user_id=:userId",nativeQuery=true)
    List<UserCouponResponse> findAllByUserId(@Param("userId") Long userId);

    UserCoupon findByCouponId(Long couponId);

    List<UserCoupon> findAllByExpirationDateAndUtilizedIsFalseOrderByCouponId(String expirationDate);

}
