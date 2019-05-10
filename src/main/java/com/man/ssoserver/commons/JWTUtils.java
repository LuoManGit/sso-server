package com.man.ssoserver.commons;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.man.ssoserver.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    public static String createAccessToken(String userid, String secret, int timeFiled, int timeInterval) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            return JWT.create()
                    .withHeader(map)
                    .withClaim(Constants.USER_ID, userid)
                    .withIssuedAt(new Date())
                    .withExpiresAt(getExperisDate(timeFiled, timeInterval))
                    .sign(Algorithm.HMAC256(secret));
        } catch (JWTCreationException exception) {
            logger.info("JWTUtils.createAccessToken is error---->" + exception.getMessage());
            exception.printStackTrace();
            return null;
        }
    }

    private static Map<String, Claim> getClaims(String token, String secret) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return jwt.getClaims();
        } catch (TokenExpiredException e) {
            logger.info("JWTUtils.getClaims is error---->" + e.getMessage());
            return null;
        } catch (JWTDecodeException e) {
            logger.info("JWTUtils.getClaims is error---->" + e.getMessage());
            return null;
        } catch (Exception e) {
            logger.info("JWTUtils.getClaims is error---->" + e.getMessage());
            return null;
        }
    }


    public static String getString(String token, String secret){
        Map<String, Claim> claimMap = getClaims(token, secret);
        if (claimMap == null)
            return null;
        Claim claim = claimMap.get(Constants.USER_ID);
        if (claim == null)
            return null;
        return claim.asString();
    }

    public static Date getExperisDate(int timeFiled, int timeInterval) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(timeFiled, timeInterval);
        return nowTime.getTime();
    }


}