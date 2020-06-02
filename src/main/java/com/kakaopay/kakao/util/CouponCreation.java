package com.kakaopay.kakao.util;

import java.util.Random;
import java.util.zip.CRC32;

public class CouponCreation {
    Random random = new Random();
    StringBuffer sb= new StringBuffer();


    // 랜덤 쿠폰 코드 생성
    public String makeCoupon() {

        // 쿠폰 코드와 버퍼 초기화
        String coupon=null;
        sb.setLength(0);

        // 랜덤 코드 생성
        for(int i=0;i<20;i++){
            if(random.nextBoolean()){
                coupon=sb.append((char)(int)(random.nextInt(26)+97)).toString();
            }else{
                 coupon=sb.append((random.nextInt(10))).toString();
            }
        }

        return coupon;
    }
   // 랜덤 쿠폰 코드를 이용 하여 해시값 생성
    public long makeHash(byte[] b) {

        CRC32 crc32 = new CRC32();
        crc32.update(b);
        return crc32.getValue();
    }
}
