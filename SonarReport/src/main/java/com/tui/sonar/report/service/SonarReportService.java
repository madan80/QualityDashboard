/**
 * 
 */
package com.tui.sonar.report.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.tui.sonar.report.bean.Metrics;

/**
 * @author Madan80
 *
 */
public interface SonarReportService {
	public Map<String, List<Map<String,String>>> getSonarMetricsForProject() throws IOException;
	
}
