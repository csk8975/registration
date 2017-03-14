package com.csk.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormCheck {
    
    public static boolean isEmailValid(String email) {
        boolean result = false;
        Pattern pattern = Pattern.compile("^([a-z0-9A-Z\\._-])+@([a-zA-Z0-9_-])+(\\.[a-zA-Z0-9_-]+)+$");
        //Pattern pattern = Pattern.compile("^[^\\_][\\w\\-\\.]+@([\\w]+\\.)+[\\w]{2,3}[^\\_]$");
        Matcher matcher = pattern.matcher(email);
        if(matcher.find())
            result = true;
        return result;
    }
    
}
