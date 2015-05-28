package com.tui.sonar.report.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface JenkinsReportService {
	
	public Map<String, List<Map<String,String>>> getJenkinsReportForJS() throws IOException;

}
