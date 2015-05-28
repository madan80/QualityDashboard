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
public interface ZapperService {
	public Map<String,Object> getZapperMetricsMap() throws IOException;

}
