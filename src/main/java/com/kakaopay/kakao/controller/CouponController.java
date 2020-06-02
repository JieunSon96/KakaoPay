package com.kakaopay.kakao.controller;

import com.kakaopay.kakao.model.coupon.Coupon;
import com.kakaopay.kakao.model.coupon.CouponMessage;
import com.kakaopay.kakao.model.user.User;
import com.kakaopay.kakao.security.CurrentUser;
import com.kakaopay.kakao.security.UserPrincipal;
import com.kakaopay.kakao.service.CouponService;
import com.kakaopay.kakao.service.UserService;
import com.kakaopay.kakao.vo.ApiResponse;
import com.kakaopay.kakao.vo.UserCouponResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @Autowired
    UserService userService;

    //쿠폰 생성 API
    @PostMapping("/createCoupon")
    @PreAuthorize("hasRole('ADMIN')")
    public void createCoupon() {
        couponService.createCoupon();
    }

    //쿠폰 지급 API
    @PostMapping("/payCoupon")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<String> payCoupon(@CurrentUser UserPrincipal currentUser) {

        // 유저 정보 확인
        Optional<User> user = userService.findByEmail(currentUser.getEmail());
        if (user.isEmpty()) {
            return new ResponseEntity(new ApiResponse(false, "User is not existed!"),
                    HttpStatus.BAD_REQUEST);
        } else {
            String couponNum = couponService.payCoupon(user);
            if (couponNum.equals("ResourceNotFoundException")) {
                return new ResponseEntity<String>("Sorry, Coupon payment is not possible. Please Contact customer service.", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<String>(couponNum, HttpStatus.OK);
            }
        }
    }

    // 쿠폰 조회 API
    @GetMapping("/viewCoupon")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<List<UserCouponResponse>> viewCoupons(@CurrentUser UserPrincipal currentUser) {
        // 유저 정보 확인
        Optional<User> user = userService.findByEmail(currentUser.getEmail());
        List<UserCouponResponse> userCouponList = new ArrayList<>();
        if (user.isEmpty()) {
            return new ResponseEntity(new ApiResponse(false, "User is not existed!"),
                    HttpStatus.BAD_REQUEST);
        } else {
            userCouponList = couponService.viewCoupons(user);
        }

        return new ResponseEntity<List<UserCouponResponse>>(userCouponList, HttpStatus.OK);
    }

    // 지급된 쿠폰 한 개 사용, 취소 API
    @PostMapping("/useCoupon/{couponId}/{utilizationStatus}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> useCoupon(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "couponId") Long couponId, @PathVariable(value = "utilizationStatus") boolean utilizationStatus) {
        String result;

        // 유저 정보 확인
        Optional<User> user = userService.findByEmail(currentUser.getEmail());

        if (user.isEmpty()) {
            return new ResponseEntity(new ApiResponse(false, "User is not existed!"),
                    HttpStatus.BAD_REQUEST);
        } else {

            if (utilizationStatus) {
                // 쿠폰 사용
                result = couponService.useCoupon(couponId);
            } else {
                // 쿠폰 취소
                result = couponService.cancelCoupon(couponId);
            }

            if (result == "ResourceNotFoundException") {
                result = "Sorry, It is not possible to use this coupon. Please Contact customer service.";
            }
        }

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    // 당일 만료 되는 전체 쿠폰 목록 조회 API
    @GetMapping("/viewExpirationCoupons")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Coupon>> viewAllExpirationCoupons(){
        List<Coupon> coupons=couponService.viewExpirationCoupons();

        if(coupons.size()<1){
            new ResponseEntity(new ApiResponse(false, "Coupons are not existed!"),
                    HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
    }

    // 메세지 확인 API
    @PostMapping("/confirmCouponAlert")
    @Secured({"ROLE_USER"})
    public ResponseEntity<List<CouponMessage>> confirmCouponAlert(@CurrentUser UserPrincipal currentUser){
        // 유저 정보 확인
        Optional<User> user = userService.findByEmail(currentUser.getEmail());

        List<CouponMessage> couponMessages=new ArrayList<>();
        if (user.isEmpty()) {
            return new ResponseEntity(new ApiResponse(false, "User is not existed!"),
                    HttpStatus.BAD_REQUEST);
        }else{
             couponMessages=couponService.confirmCouponAlert(currentUser);
        }

        if(couponMessages.size()>0){
            for(int i=0; i<couponMessages.size();i++){
                System.out.println(couponMessages.get(i).getCouponId()+"의 쿠폰이 3일 후 만료됩니다.");
                couponMessages.get(i).setMessageChecked(true);
            }

            couponService.changeCouponMessageCheckStatus(couponMessages);
        }else{
            return new ResponseEntity(new ApiResponse(false, "Coupon Messages are not existed!"),
                    HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<CouponMessage>>(couponMessages, HttpStatus.OK);
    }
}