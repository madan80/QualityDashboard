/**
 * 
 */
package com.tui.sonar.report.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author machou
 *
 */
public interface SonarQualityGateService {
	
	public List<Map<String,String>> getQualityGateMetrics() throws IOException;

}
