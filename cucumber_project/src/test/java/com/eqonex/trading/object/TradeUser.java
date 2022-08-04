package com.eqonex.trading.object;

import org.jboss.aerogear.security.otp.Totp;

public class TradeUser {
    private final Integer userId;
    private final String userEmail;
    private final String userPassWord;
    private final String requestToken;
    private final String requestSecret;
    private final String mFASecret;

    public TradeUser(Integer userId, String userEmail, String userPassWord, String mFASecret, String requestToken, String requestSecret){
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassWord = userPassWord;
        this.mFASecret = mFASecret;
        this.requestToken = requestToken;
        this.requestSecret = requestSecret;
    }

    public Integer getUserId(){
        return this.userId;
    }

    public String getUserEmail(){
        return this.userEmail;
    }

    public String getUserPassWord(){
        return this.userPassWord;
    }

    public String getmFASecret(){
        return this.mFASecret;
    }

    public String getRequestSecret(){
        return this.requestSecret;
    }

    public String getRequestToken(){
        return this.requestToken;
    }

    public String getOtpCode(){
        String secret = getmFASecret();
        return generate(secret);
    }

    public static String generate(String secret){
        Totp generator = new Totp(secret);
        return generator.now();
    }
}
