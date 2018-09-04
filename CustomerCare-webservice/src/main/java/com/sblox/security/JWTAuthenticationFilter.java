//package com.sblox.security;
//
//import com.auth0.jwt.JWT;
////import com.auth0.samples.authapi.springbootauthupdated.user.ApplicationUser;
//
////import io.jsonwebtoken.*;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
//
//import com.sblox.common.dto.UserDto;
//import com.sblox.common.exception.BaseException;
//import com.sblox.common.model.AuthenticationResponse;
//import com.sblox.common.model.UserCredentials;
//import com.sblox.security.SecurityConstants;
//import com.sblox.service.UserService;
//
//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
////	private AuthenticationManager authenticationManager;
////
//	@Autowired
//	private UserService userService;
////
////	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
////		this.authenticationManager = authenticationManager;
////	}
//
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
//			throws AuthenticationException {
//		try {
//			UserCredentials creds = new ObjectMapper().readValue(req.getInputStream(), UserCredentials.class);
//
//			AuthenticationResponse uthenticationResponse = userService.authenticateUser(creds);
//			List<GrantedAuthority> grantedAuths = new ArrayList<>();
//			grantedAuths.add(new SimpleGrantedAuthority(uthenticationResponse.getUserData().getRole()));
//			logger.debug("Finish LoginAuthenticationProvider.authenticate()");
//			return new UsernamePasswordAuthenticationToken(uthenticationResponse, creds.getPassword(), grantedAuths);
//
//			// return authenticationManager.authenticate(
//			// new UsernamePasswordAuthenticationToken(creds.getEmail(),
//			// creds.getPassword(), new ArrayList<>()));
//		} catch (IOException | BaseException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	@Override
//	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
//			Authentication auth) throws IOException, ServletException {
//
//		String token = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername())
//				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
//				.sign(HMAC512(SecurityConstants.SECRET.getBytes()));
//		res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
//	}
//
//}
