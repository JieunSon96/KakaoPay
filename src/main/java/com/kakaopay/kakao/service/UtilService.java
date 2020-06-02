package com.kakaopay.kakao.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class UtilService {

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date today=new Date();

    // 현재 날짜 구하기
    public String getPresentTime() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String presentTime = format.format(today);

        return presentTime;
    }

    // 만료 날짜 구하기
    public String getExpirationDate(int date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, date);

        return format.format(cal.getTime());
    }


}
