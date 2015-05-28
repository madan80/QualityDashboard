/**
 * 
 */
package com.tui.sonar.report.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tui.sonar.report.service.JenkinsBuildService;
import com.tui.sonar.report.service.JenkinsReportService;
import com.tui.sonar.report.service.SonarQualityGateService;
import com.tui.sonar.report.service.SonarReportService;
import com.tui.sonar.report.service.TuiTapParserService;
import com.tui.sonar.report.service.ZapperService;

/**
 * @author machou
 * 
 */
@Controller
@RequestMapping("/")
public class BaseController {
	@Autowired
	private SonarReportService sonarReportService;
	@Autowired
	private JenkinsReportService jenkinsReportService;
	@Autowired
	private TuiTapParserService tuiTapParser;
	@Autowired
	private SonarQualityGateService sonarQualityGateService;
	@Autowired
	private JenkinsBuildService jenkinsBuildService;
	@Autowired
	private ZapperService zapperService;
	@Autowired
	private Properties metricKeyMap;

	@SuppressWarnings("unused")
	@RequestMapping("/")
	public String sonarReport(ModelMap model) {
	//	Map<String, String> jsMetricMap = new HashMap<>();

		try {
			Map<String, List<Map<String, String>>> sonarMetricMap = sonarReportService
					.getSonarMetricsForProject();
			Map<String, List<Map<String, String>>> jenkinMetricMap = jenkinsReportService
					.getJenkinsReportForJS();
			
			Map<String, String> tapMetricMap = tuiTapParser.getParsingResults();
			List<Map<String,String>> lstQualityGateMetricsMap = sonarQualityGateService.getQualityGateMetrics();
			Map<String,String> buildStatusMap = jenkinsBuildService.getBuildStatus();
			Map<String, Object> zapperResponseMap = zapperService.getZapperMetricsMap(); 

			modifySonarMetricMapUsingJenkinsJsMetric(sonarMetricMap,
					jenkinMetricMap);
			
			modifySonarMetricMapWithTapMetric(sonarMetricMap,tapMetricMap);

			model.addAttribute("metricMap", sonarMetricMap);
			model.addAttribute("keyMap", metricKeyMap);
			model.addAttribute("lstQualityGateMetricsMap", lstQualityGateMetricsMap);
			model.addAttribute("buildStatusMap", buildStatusMap);
			model.addAttribute("zapperResponse", zapperResponseMap);

		} catch (IOException ioException) {
			// TODO Auto-generated catch block
			ioException.printStackTrace();
		}
		return "sonarReport";

	}

	private void modifySonarMetricMapWithTapMetric(Map<String, List<Map<String, String>>> sonarMetricMap,
			Map<String, String> tapMetrics) {
		
		List<Map<String, String>> lstSonarMetricMap = sonarMetricMap
				.get("WSH_develop_Sonar_Reporter");
		
		for(Entry<String, String> entry : tapMetrics.entrySet()){
			Map<String, String> mapTapMetrics = new HashMap<>();
			mapTapMetrics.put("key", entry.getKey());
			mapTapMetrics.put("frmt_val", String.valueOf(entry.getValue()));
			lstSonarMetricMap.add(mapTapMetrics);
		}
		
		sonarMetricMap.put("WSH_develop_Sonar_Reporter", lstSonarMetricMap);
		
	}

	private void modifySonarMetricMapUsingJenkinsJsMetric(
			Map<String, List<Map<String, String>>> sonarMetricMap,
			Map<String, List<Map<String, String>>> jenkinMetricMap) {

		Map<String, String> lineCoverageMap = new HashMap<>();
		Map<String, String> conditionCoverageMap = new HashMap<>();

		List<Map<String, String>> lstSonarMetricMap = sonarMetricMap
				.get("WSH_fb_bookflow_js_Sonar_Reporter");
		

		List<Map<String, String>> lstJenkinsMetricMap = jenkinMetricMap
				.get("WSH_fb_bookflow_js_Sonar_Reporter");
		
		populateLineAndConditionCoverageMap(lineCoverageMap,
				conditionCoverageMap, lstJenkinsMetricMap);
		
		modifySonarMetricMapForJsLineAndConditionCoverage(sonarMetricMap,
				lineCoverageMap, conditionCoverageMap, lstSonarMetricMap);
	}

	private void modifySonarMetricMapForJsLineAndConditionCoverage(
			Map<String, List<Map<String, String>>> sonarMetricMap,
			Map<String, String> lineCoverageMap,
			Map<String, String> conditionCoverageMap,
			List<Map<String, String>> lstSonarMetricMap) {
		List<Map<String, String>> lstModifiedMetricMap = new ArrayList<>();
		lstModifiedMetricMap.addAll(lstSonarMetricMap);

		for (Map<String, String> metricMap : lstSonarMetricMap) {
			if ("line_coverage".equalsIgnoreCase(metricMap.get("key"))) {
				lstModifiedMetricMap.remove(metricMap);
			} else if ("branch_coverage".equalsIgnoreCase(metricMap.get("key"))) {
				lstModifiedMetricMap.remove(metricMap);
			}
		}

		lstModifiedMetricMap.add(lineCoverageMap);
		lstModifiedMetricMap.add(conditionCoverageMap);
		sonarMetricMap.put("WSH_fb_bookflow_js_Sonar_Reporter",
				lstModifiedMetricMap);
	}

	private void populateLineAndConditionCoverageMap(
			Map<String, String> lineCoverageMap,
			Map<String, String> conditionCoverageMap,
			List<Map<String, String>> lstJenkinsMetricMap) {
		for (Map<String, String> map : lstJenkinsMetricMap) {
			if ("Lines".equalsIgnoreCase((String) map.get("name"))) {
				lineCoverageMap.put("key", "line_coverage");
				lineCoverageMap.put("frmt_val",
						String.format("%.1f%%", map.get("ratio")));
			} else if ("Conditionals"
					.equalsIgnoreCase((String) map.get("name"))) {
				conditionCoverageMap.put("key", "branch_coverage");
				conditionCoverageMap.put("frmt_val",
						String.format("%.1f%%", map.get("ratio")));
			}

		}
	}

}
