package com.sblox.utils;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sblox.common.model.UserData;
import com.sblox.security.SecurityConstants;
import com.sblox.webservice.AuthenticationController;

import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date;

public class JwtHelper {

	private static Logger logger = Logger.getLogger(JwtHelper.class);

	// Sample method to construct a JWT
	public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstants.SECRET);// apiKey.getSecret());
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
				.signWith(signatureAlgorithm, signingKey);

		// if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		// ObjectMapper mapper = new ObjectMapper();
		// String jsonInString;
		// try {
		// jsonInString = mapper.writeValueAsString(userData);
		// builder.setSubject(jsonInString);
		// } catch (JsonProcessingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	// Sample method to validate and read the JWT
	public static void parseJWT(String jwt) {

		// This line will throw an exception if it is not a signed JWS (as expected)
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SecurityConstants.SECRET))
					.parseClaimsJws(jwt).getBody();
		} catch (Exception ex) {
			logger.error("Invalid Token", ex);
		}
		// System.out.println("ID: " + claims.getId());
		// System.out.println("Subject: " + claims.getSubject());
		// System.out.println("Issuer: " + claims.getIssuer());
		// System.out.println("Expiration: " + claims.getExpiration());
	}

	// public static String resolveToken(String bearerToken) {
	//
	// if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
	// String jwt = bearerToken.substring(7, bearerToken.length());
	// return jwt;
	// }
	// return null;
	// }

	public static void main(String arg[]) {
		String test = createJWT("id", "issuer", "45:OWNER", 900000);
		System.out.println("JwtHelper.main() " + test);
		parseJWT(test);
	}

}
