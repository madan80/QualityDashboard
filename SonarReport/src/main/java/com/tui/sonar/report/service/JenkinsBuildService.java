/**
 * 
 */
package com.tui.sonar.report.service;

import java.io.IOException;
import java.util.Map;

/**
 * @author machou
 *
 */
public interface JenkinsBuildService {
	
	public Map<String, String> getBuildStatus() throws IOException;

}
