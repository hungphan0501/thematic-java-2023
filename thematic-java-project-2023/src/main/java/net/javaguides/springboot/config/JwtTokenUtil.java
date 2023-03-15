//package net.javaguides.springboot.config;
////
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.security.Keys;
////import org.springframework.security.core.userdetails.UserDetails;
////
////import java.util.Date;
////import java.util.HashMap;
////import java.util.Map;
////
////public class JwtTokenUtil {
////
////    private String SECRET_KEY = "secret";
////    private long VALIDITY_IN_MILLISECONDS = 3600000;
////
////    public String generateToken(UserDetails userDetails) {
////        Map<String, Object> claims = new HashMap<>();
////        return createToken(claims, userDetails.getUsername());
////    }
////
////    private String createToken(Map<String, Object> claims, String subject) {
////        long now = System.currentTimeMillis();
////        Date issuedAt = new Date(now);
////        Date expiration = new Date(now + VALIDITY_IN_MILLISECONDS);
////
////        return Jwts.builder().setClaims(claims)
////                .setSubject(subject)
////                .setIssuedAt(issuedAt)
////                .setExpiration(expiration)
////                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
////                .compact();
////    }
////
////    public boolean validateToken(String token, UserDetails userDetails) {
////        String username = extractUsername(token);
////        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
////    }
////
////    String extractUsername(String token) {
////        return Jwts.parser()
////                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
////                .parseClaimsJws(token)
////                .getBody()
////                .getSubject();
////    }
////
////    private boolean isTokenExpired(String token) {
////        return Jwts.parser()
////                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))//.build() here - find later
////                .parseClaimsJws(token)
////                .getBody()
////                .getExpiration()
////                .before(new Date());
////
////    }
////}
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Component
//public class JwtTokenUtil implements Serializable {
//
//    private static final long serialVersionUID = -2550185165626007488L;
//    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    //retrieve username from jwt token
//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//
//    //retrieve expiration date from jwt token
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
//
//    //for retrieving any information from token we will need the secret key
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//    }
//
//    //check if the token has expired
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    //generate token for user
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return doGenerateToken(claims, userDetails.getUsername());
//    }
//
//    //while creating the token -
//    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
//    //2. Sign the JWT using the HS512 algorithm and secret key.
//    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
//    //   compaction of the JWT to a URL-safe string
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(SignatureAlgorithm.HS512, secret).compact();
//    }
//
//    //validate token
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//}