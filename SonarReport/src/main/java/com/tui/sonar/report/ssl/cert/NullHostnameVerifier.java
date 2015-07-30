package com.tui.sonar.report.ssl.cert;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class NullHostnameVerifier implements HostnameVerifier {
	  

	@Override
	public boolean verify(String paramString, SSLSession paramSSLSession) {
		// TODO Auto-generated method stub
		return true;
	}
	}
