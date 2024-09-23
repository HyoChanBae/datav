package com.mstr.util;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;
 
public class SystemMessage{
	private static MessageSourceAccessor msAcc = null;

    @SuppressWarnings("static-access")
	public void setMessageSourceAccessor(MessageSourceAccessor msAcc) {
        this.msAcc = msAcc;
    }
    
    public MessageSourceAccessor getMessageSourceAccessor() {
        return msAcc;
    }
    
    public static String getMessage(String key, Object[] objs){
        return msAcc.getMessage(key, objs, Locale.KOREAN);
    }

    public static String getMessage(String key){
        return msAcc.getMessage(key);
    }
    
    public static String getMessage(String key, String defaultMessage){
        String message = "";
        try {
            message = msAcc.getMessage(key);
        } catch(Exception e) {
            message = defaultMessage;
        }
        return message;
    }    

}
