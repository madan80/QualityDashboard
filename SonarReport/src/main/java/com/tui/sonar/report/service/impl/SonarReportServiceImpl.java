/**
 * 
 */
package com.tui.sonar.report.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.tui.sonar.report.bean.Metrics;
import com.tui.sonar.report.service.SonarReportService;



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
	public Map<String, List<Map<String,String>>> getSonarMetricsForProject() throws IOException{
		Map<String, List<Map<String,String>>> map = new HashMap<>();
		File file = new File(propertiesFileLocation);
		String[] extensions = new String[]{"properties"};
		List<File> lstFiles = (List<File>) FileUtils.listFiles(file, extensions, true);
		
		    for(File propertyFile:lstFiles){
				String resourceName = propertyFile.getName().split("\\.properties")[0];
				String url = createRestUrl(propertyFile,resourceName);
				
				
				HttpClient httpClient = HttpClientBuilder.create().build();
				
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
				requestFactory.setReadTimeout(20000);
				requestFactory.setConnectTimeout(30000);
				
				RestTemplate restTemplate = new RestTemplate(requestFactory);
				List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
				
				//Add the Jackson Message converter
				messageConverters.add(new MappingJackson2HttpMessageConverter());
				messageConverters.add(new StringHttpMessageConverter()) ;
				
				//Add the message converters to the restTemplate
				restTemplate.setMessageConverters(messageConverters); 
				
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
