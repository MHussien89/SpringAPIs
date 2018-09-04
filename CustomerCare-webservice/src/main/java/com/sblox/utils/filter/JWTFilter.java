package com.sblox.utils.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.cloud.context.config.annotation.RefreshScope;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sblox.common.model.ErrorResponse;
import com.sblox.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RefreshScope
public class JWTFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		String url = ((HttpServletRequest) servletRequest).getRequestURL().toString();
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		String header = httpRequest.getHeader(SecurityConstants.HEADER_STRING);

		if ((url.endsWith("login") || url.endsWith("register") || url.endsWith("timezone"))) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
		if (authentication == null) {

			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			((HttpServletResponse) servletResponse).setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorMessage(HttpStatus.UNAUTHORIZED.name());
			errorResponse.setErrorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			// errorResponse.setDescription(e.getMessage());
			// errorResponse.setTransactionId(trxId);
			// errorResponse.setError(HttpStatus.UNAUTHORIZED.name());
			// errorResponse.setMessage(HttpStatus.UNAUTHORIZED.name());
			byte[] body = new ObjectMapper().writeValueAsBytes(errorResponse);
			((HttpServletResponse) servletResponse).getOutputStream().write(body);
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("ID: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		filterChain.doFilter(servletRequest, servletResponse);

		// JwtHelper.
		// if error

		// else
		// filterChain.doFilter(servletRequest, servletResponse);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		// String token = request.getHeader(SecurityConstants.HEADER_STRING);
		try {

			if (token != null) {
				// parse the token.
				Claims claims = Jwts.parser()
						.setSigningKey(DatatypeConverter.parseBase64Binary(SecurityConstants.SECRET))
						.parseClaimsJws(token).getBody();
				String user = claims.getId();

				if (user != null) {
					return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
				}
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

}
