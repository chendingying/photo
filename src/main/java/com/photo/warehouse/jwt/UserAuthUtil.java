package com.photo.warehouse.jwt;//package com.reported.rarrier.jwt;
//
//
//import com.reported.rarrier.conf.UserAuthConfig;
//import com.reported.rarrier.exception.auth.UserTokenException;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.SignatureException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by cdy on 2018/11/14.
// */
//@Configuration
//public class UserAuthUtil {
//    @Autowired
//    private UserAuthConfig userAuthConfig;
//
//    public IJWTInfo getInfoFromToken(String token)  {
//        try {
//            return JWTHelper.getInfoFromToken(token, userAuthConfig.getPubKeyByte());
//        }catch (ExpiredJwtException ex){
//            throw new UserTokenException("User token expired!");
//        }catch (SignatureException ex){
//            throw new UserTokenException("User token signature error!");
//        }catch (Exception ex){
//            throw new UserTokenException("User token is null or empty!");
//        }
//    }
//}
