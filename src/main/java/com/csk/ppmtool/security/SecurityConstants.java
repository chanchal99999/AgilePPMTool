package com.csk.ppmtool.security;

public class SecurityConstants {

	public static final String SIGN_UP_URL="/api/users/**";
	public static final String H2_URL="h2-console/**";
	public static final String SECRECT="SecrectKeyToGenJTs";
	public static final String TOKEN_PREFIX="Bearer ";
	public static final String HEADER_STRING="Authorization";
	public static final long EXPIRATION_TIME= 30_000;//30 seconds 
	
}
