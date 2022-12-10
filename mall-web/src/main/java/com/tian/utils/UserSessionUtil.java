package com.tian.utils;

import com.tian.constants.Constants;
import com.tian.vo.MallUserVO;

import javax.servlet.http.HttpSession;

public class UserSessionUtil {

    public static MallUserVO userSession(HttpSession httpSession) {
        Object sessionObj = httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        if(sessionObj == null){
            return null;
        }
        return (MallUserVO)sessionObj;
    }

}
