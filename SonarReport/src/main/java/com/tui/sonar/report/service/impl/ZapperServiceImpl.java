/**
 * 
 */
package com.tui.sonar.report.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.tui.sonar.report.service.ZapperService;

/**
 * @author machou
 *
 */
public class ZapperServiceImpl implements ZapperService{
	/*@Value("${jenkin-user}")
	private String login;
	@Value("${jenkin-password}")
	private String password;*/
	@Value("${properties.file.location.zapper}")
	private String propertiesFileLocation;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> getZapperMetricsMap() throws IOException{
		Map<String, Object> zapperMetricsMap = new HashMap<>();
		File file = new File(propertiesFileLocation);
		String[] extensions = new String[] { "properties" };
		List<File> lstFiles = (List<File>) FileUtils.listFiles(file,
				extensions, true);

		for (File propertyFile : lstFiles) {
			String resourceName = propertyFile.getName().split("\\.properties")[0];
			String url = createRestUrl(propertyFile, resourceName);

			HttpClient httpClient = HttpClientBuilder.create().build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
					httpClient);
			requestFactory.setReadTimeout(20000);
			requestFactory.setConnectTimeout(30000);

			RestTemplate restTemplate = new RestTemplate(requestFactory);
			
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
			
			//Add the Jackson Message converter
			messageConverters.add(new MappingJackson2HttpMessageConverter());
			messageConverters.add(new StringHttpMessageConverter()) ;
			
			//Add the message converters to the restTemplate
			restTemplate.setMessageConverters(messageConverters); 
			
			ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) restTemplate.exchange(url, HttpMethod.GET,null, Object.class);
		    if(null!= responseEntity){
		    	List<Map<String,String>> lstZapperResponseMap =  (List<Map<String, String>>) responseEntity.getBody();
		    	zapperMetricsMap.put("url", lstZapperResponseMap.get(0).get("url"));
		    	for(Map<String, String> map : lstZapperResponseMap){
		    		String riskType = map.get("risk");
		    		if(zapperMetricsMap.containsKey(riskType)){
		    			int riskCount = (int) zapperMetricsMap.get(riskType);
		    			zapperMetricsMap.put(map.get("risk"), riskCount + 1);
		    		}else{
		    			zapperMetricsMap.put(map.get("risk"), 1);
		    		}
		    	}
		    	
		    }

		}
		return zapperMetricsMap;
	}
	
	private String createRestUrl(File resource, String resourceName)
			throws IOException {

		List<String> lstString = FileUtils.readLines(resource);
		return lstString.get(0).split("url=")[1];

	}

	

}
