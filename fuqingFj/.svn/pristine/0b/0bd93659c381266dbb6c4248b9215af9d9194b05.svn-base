package com.greathack.homlin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdCardNumberVerify {
    //通过身份证查询年龄
    public static int getAgeByCertId(String certId) {
        String birthday = "";
        if (certId.length() == 18) {
            birthday = certId.substring(6, 10) + "/"
                    + certId.substring(10, 12) + "/"
                    + certId.substring(12, 14);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        Date birth = new Date();
        try {
            birth = sdf.parse(birthday);
        } catch (ParseException e) {
        }
        long intervalMilli = now.getTime() - birth.getTime();
        return (int) (intervalMilli/(24 * 60 * 60 * 1000))/365;
    }

    //通过身份证查询性别
    public static int getSexByCertId(String certId) {
        if (Integer.parseInt(certId.substring(16).substring(0, 1)) % 2 == 0) {
            //女
            return 2;

        } else {
            //男
            return 1;
        }

    }
}
