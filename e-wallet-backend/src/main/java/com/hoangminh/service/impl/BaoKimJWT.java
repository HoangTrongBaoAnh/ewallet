package com.hoangminh.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BaoKimJWT {
    private String API_KEY ="a18ff78e7a9e44f38de372e093d87ca1 ";
    private String API_SECRET="9623ac03057e433f95d86cf4f3bef5cc";
    private Long TOKEN_EXPIRE=600000L;
    public String generateToken() throws InvalidKeyException, NoSuchAlgorithmException, JsonProcessingException {
       ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Map header = new HashMap();
        header.put("typ","JWT");
        header.put("alg","HS256");
        String headerBase64 = Base64.getUrlEncoder().encodeToString(
                objectWriter.writeValueAsString(header)
                        .replace(" ","")
                        .replace("\n","")
                        .replace("\r","").getBytes(StandardCharsets.UTF_8));
        Map payload = new HashMap();
        Date now = new Date();
        Date expire = new Date(now.getTime()+TOKEN_EXPIRE);
        payload.put("iss",API_KEY);
        payload.put("iat",now);
        payload.put("aud","");
        payload.put("sub","");
        payload.put("exp",expire);
        payload.put("jti","15642168541522");
        String payloadBase64 = Base64.getUrlEncoder().encodeToString(
                objectWriter.writeValueAsString(payload)
                        .replace(" ","")
                    .replace("\n","")
                    .replace("\r","").getBytes(StandardCharsets.UTF_8));
        String dataSignature = headerBase64+"."+payloadBase64;
        String signature = hmacSHA256(dataSignature,API_SECRET);

        return dataSignature+"."+signature;
    }
    public String hmacSHA256(String data,String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
        sha256Hmac.init(secretKey);
        byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(signedBytes);
    }
}