package com.kakaopay.kakao.service;

import com.kakaopay.kakao.model.coupon.Coupon;
import com.kakaopay.kakao.model.coupon.CouponMessage;
import com.kakaopay.kakao.model.coupon.UserCoupon;
import com.kakaopay.kakao.model.user.User;
import com.kakaopay.kakao.repository.CouponMessageRepository;
import com.kakaopay.kakao.repository.CouponRepository;
import com.kakaopay.kakao.repository.UserCouponRepository;
import com.kakaopay.kakao.security.UserPrincipal;
import com.kakaopay.kakao.util.CouponCreation;
import com.kakaopay.kakao.vo.UserCouponResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    @Autowired
    CouponRepository couponRepository;

    @Autowired
    UserCouponRepository userCouponRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    CouponMessageRepository couponMessageRepository;

    // 쿠폰 생성 서비스(100개씩 batch 생성)
    // 데이터베이스 상에서 배치를 돌려 10억개 이상 초기에 생성 해놔야 함
    // 이 서비스는 관리자가 쿠폰 생성 버튼 눌러 동작 시에 쿠폰 1천개 생성
    @Transactional
    public void createCoupon() {
        List<Coupon> coupons = new ArrayList<>();
        int targetCouponCount = 10000;
        int totalCount = 0;
        int count = 0;
        CouponCreation couponCreation = new CouponCreation();

        while (true) {
            if (totalCount == targetCouponCount) {
                System.out.println("finished all bulk insert for creating the coupons!!!!");
                break;
            }

            if (count == 100) {
                couponRepository.saveAll(coupons);
                coupons.clear();
                count = 0;
            }

            // 쿠폰 번호와 해당 쿠폰 번호를 해시화 한 값 함께 저장
            while (true) {
                String couponNum = couponCreation.makeCoupon();
                long hashNum = couponCreation.makeHash(couponNum.getBytes());

                if (couponRepository.findByCouponHash(hashNum).isEmpty()) {
                    Coupon coupon = new Coupon(couponNum, hashNum, utilService.getExpirationDate(30));
                    coupons.add(coupon);
                    count++;
                    totalCount++;
                    break;
                }


            }
        }

    }

    // 쿠폰 지급 서비스(사용 되지 않은 쿠폰 중에 첫번째 꺼부터 지급)
    @Transactional
    public String payCoupon(Optional<User> user) {
        Coupon coupon;
        UserCoupon userCoupon = new UserCoupon();
        Optional<Coupon> savedCoupon;
        try {
            coupon = couponRepository.findFirstByUtilizationStatusFalseOrderByIdAsc();

            if (coupon.getId() == null) {
                return "ResourceNotFoundException";
            }

            coupon.setUtilizationStatus(true);
            coupon = couponRepository.save(coupon);

            userCoupon.setUserId(user.get().getId());
            userCoupon.setCouponId(coupon.getId());
            userCoupon.setExpirationDate(coupon.getExpirationDate()); // 생성 된 쿠폰의 발급 기한을 넣어줌
            userCoupon.setUtilized(false);
            userCouponRepository.save(userCoupon);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            savedCoupon = couponRepository.findById(userCoupon.getCouponId()).isPresent() ?
                    Optional.of(couponRepository.findById(userCoupon.getCouponId())).get() : null;
        }
        return savedCoupon.get().getCouponCode();
    }

    // 쿠폰 조회 서비스
    public List<UserCouponResponse> viewCoupons(Optional<User> user) {
        List<UserCouponResponse> userCoupons = new ArrayList<>();
        try {
            userCoupons = userCouponRepository.findAllByUserId(user.get().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userCoupons;
    }

    // 지급된 쿠폰 사용 서비스
    public String useCoupon(Long couponId) {
        UserCoupon userCoupon = new UserCoupon();
        try {
            userCoupon = userCouponRepository.findByCouponId(couponId);

            if (userCoupon.getCouponId() == null) {
                return "ResourceNotFoundException";
            } else {
                userCoupon.setUtilized(true);
                userCouponRepository.save(userCoupon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Coupon successfully used!";
    }

    // 지급된 쿠폰 취소 서비스
    public String cancelCoupon(Long couponId) {
        UserCoupon userCoupon;
        try {
            userCoupon = userCouponRepository.findByCouponId(couponId);

            if (userCoupon.getCouponId() == null) {
                return "ResourceNotFoundException";
            } else {
                userCoupon.setUtilized(false);
                userCouponRepository.save(userCoupon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Coupon successfully canceled!";
    }

    // 당일 만료 되는 전체 쿠폰 목록 조회 서비스
    public List<Coupon> viewExpirationCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        try {
            coupons = couponRepository.findAllByExpirationDateOrderByIdAsc(utilService.getPresentTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coupons;
    }

    // 사용 되지 않고 만료 날짜가 3일 남은 쿠폰 조회 서비스
    public List<UserCoupon> getAllCouponsByExpirationDate(String expirationDate){
        List<UserCoupon> userCoupons= new ArrayList<>();
        try{
            userCoupons=userCouponRepository.findAllByExpirationDateAndUtilizedIsFalseOrderByCouponId(expirationDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userCoupons;
    }

    // 쿠폰 메세지 발송 서비스
    public List<CouponMessage> confirmCouponAlert(UserPrincipal userPrincipal){

        List<CouponMessage> couponMessages=new ArrayList<>();
        try{
            couponMessages=couponMessageRepository.findAllByUserIdOrderByCouponId(userPrincipal.getId());
        }catch (Exception e){
            e.printStackTrace();
        }

        return couponMessages;
    }

    // 유저의 쿠폰 메세지 확인 처리 서비스
    public void changeCouponMessageCheckStatus(List<CouponMessage> couponMessages){
       try{
           couponMessageRepository.saveAll(couponMessages);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    // 메세지 저장 여부 확인 서비스
    public CouponMessage confirmMessageStoredStatus(Long couponId){
        CouponMessage couponMessage=new CouponMessage();

        try{
        couponMessage=couponMessageRepository.findByCouponId(couponId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return couponMessage;
    }
}
