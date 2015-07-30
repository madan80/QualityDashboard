/**
 * 
 */
package com.tui.sonar.report.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import com.tui.sonar.report.ssl.cert.*;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.tui.sonar.report.service.SonarReportService;
import com.tui.sonar.report.ssl.cert.SSLCertificateValidation;



/**
 * 
 *
 */

public class SonarReportServiceImpl implements SonarReportService {
	@Value("${login}")
	private String login;
	@Value("${password}")
	private String password;
	@Value("${host}")
	private String host;
	@Value("${property.file.location.sonar}")
	private String propertiesFileLocation;
	//private RestTemplate restTemplate= new RestTemplate();

	/* (non-Javadoc)
	 * @see com.tui.sonar.report.service.SonarReportService#getSonarMetricsForProject()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<Map<String,String>>> getSonarMetricsForProject() throws IOException, NoSuchAlgorithmException{
		Map<String, List<Map<String,String>>> map = new HashMap<>();
		File file = new File(propertiesFileLocation);
		String[] extensions = new String[]{"properties"};
		List<File> lstFiles = (List<File>) FileUtils.listFiles(file, extensions, true);
		/*SSLSocketFactory sf = new SSLSocketFactory(
				SSLContext.getInstance("SSL"),
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				Scheme sch = new Scheme("https", 443, (SchemeSocketFactory) sf);*/
				
		
		    for(File propertyFile:lstFiles){
				String resourceName = propertyFile.getName().split("\\.properties")[0];
				String url = createRestUrl(propertyFile,resourceName);
				
				
				
				HttpClient httpClient = HttpClientBuilder.create().build();
			//	httpClient.getConnectionManager().getSchemeRegistry().register(sch);
				
				//HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
				//SimpleClientHttpRequestFactory  requestFactory = new SimpleClientHttpRequestFactory {
				
				   HostnameVerifier verifier = new NullHostnameVerifier();
				   MySimpleClientHttpRequestFactory requestFactory = new MySimpleClientHttpRequestFactory(verifier);

					   

				requestFactory.setReadTimeout(20000);
				requestFactory.setConnectTimeout(30000);
				
				RestTemplate restTemplate = new RestTemplate(requestFactory);
				List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
				
				//Add the Jackson Message converter
				messageConverters.add(new MappingJackson2HttpMessageConverter());
				messageConverters.add(new StringHttpMessageConverter()) ;
				
				//Add the message converters to the restTemplate
				restTemplate.setMessageConverters(messageConverters); 
			//	SSLCertificateValidation.disable();
				ResponseEntity<ArrayList> responseEntity = (ResponseEntity<ArrayList>) restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders(login, password)), ArrayList.class);
			    if(null!= responseEntity){
			    	HashMap<String,Object> linkedHashMap =  ( (java.util.LinkedHashMap<String,Object>)responseEntity.getBody().get(0));
			    	List<Map<String,String>> lstMetrics =(List<Map<String, String>>) linkedHashMap.get("msr");
			    	map.put(resourceName, lstMetrics);
			    	
			    }
			}
			
		return map;
	}

	private String createRestUrl(File resource,String resourceName) throws IOException {
		
		List<String> lstString = FileUtils.readLines(resource);
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(host).append("resource=").append(resourceName).append("&").append(lstString.get(0));
		return strBuilder.toString();
	}
	
	private HttpHeaders createHeaders( final String login,final String password ){
		   return new HttpHeaders(){
	   	{
		         String auth = login + ":" + password;
		         byte[] encodedAuth = Base64.encodeBase64( 
		            auth.getBytes(Charset.forName("US-ASCII")) );
		         String authHeader = "Basic " + new String( encodedAuth );
		         set( "Authorization", authHeader );
		        
		      }
		   };
		}

}


