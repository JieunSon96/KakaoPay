package com.kakaopay.kakao.util;

import com.kakaopay.kakao.model.coupon.CouponMessage;
import com.kakaopay.kakao.model.coupon.UserCoupon;
import com.kakaopay.kakao.repository.CouponMessageRepository;
import com.kakaopay.kakao.service.CouponService;
import com.kakaopay.kakao.service.UtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CouponScheduler {

    @Autowired
    UtilService utilService;

    @Autowired
    CouponService couponService;

    @Autowired
    CouponMessageRepository couponMessageRepository;

    private static Logger logger = LoggerFactory.getLogger(CouponScheduler.class);

    // 매일 오전 9시 30분 0초에 실행됨
    @Scheduled(cron = "0 30 9 * * *")
    @Transactional
    public void sendCouponExpirationAlert() {
        List<UserCoupon> userCoupons = new ArrayList<>();

        // 3일 후에 만료 되는 쿠폰 조회
        String expirationDate = utilService.getExpirationDate(3);
        userCoupons = couponService.getAllCouponsByExpirationDate(expirationDate);

        List<CouponMessage> couponMessages = new ArrayList<>();

        for (int i = 0; i < userCoupons.size(); i++) {
            // 메세지 저장 여부 조회
            if (couponService.confirmMessageStoredStatus(userCoupons.get(i).getCouponId()) == null) {
                CouponMessage couponMessage = new CouponMessage(userCoupons.get(i).getUserId(), userCoupons.get(i).getCouponId(), false);
                couponMessages.add(couponMessage);
            }
        }

        couponMessageRepository.saveAll(couponMessages);
        logger.info("Present Time (Coupon Message Scheduler Worked Successfully!):{}", new Date());
    }

}
