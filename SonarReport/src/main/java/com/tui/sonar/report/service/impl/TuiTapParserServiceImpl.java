/**
 * 
 */
package com.tui.sonar.report.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.tap4j.model.TestResult;
import org.tap4j.model.TestSet;
import org.tap4j.parser.Tap13Parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tui.sonar.report.service.TuiTapParserService;
import com.tui.sonar.report.tap.result.TUITapResult;

/**
 * @author machou
 *
 */
public class TuiTapParserServiceImpl implements TuiTapParserService{
	@Value("${yslow.file.path}")
	String yslowFile;
	
	@SuppressWarnings("unchecked")
	public Map<String,String> getParsingResults() {
		System.out.println("yslowFile: "+yslowFile);
		TestSet testSet = new Tap13Parser().parseFile(new File(yslowFile));
		TUITapResult tUITapResult = null;
		if (testSet != null ) {
			tUITapResult  = parseTapResults(testSet.getTestResults());
		}
		Gson gson = new GsonBuilder().create();
		String jsonResult = gson.toJson(tUITapResult);
		return gson.fromJson(jsonResult, Map.class);
	}
	
	private TUITapResult parseTapResults(List<TestResult> tapResults) {
		TUITapResult tUITapResult = new TUITapResult();
		int countOfcomplianceIssues = 0;
		for( TestResult testResult : tapResults) {
			String dataDesc = testResult.getDescription();
			
			if(dataDesc != null) {
				String rateValue = dataDesc.substring(0, 1);
				
				if(!"A".equalsIgnoreCase(rateValue) && !"B".equalsIgnoreCase(rateValue) &&
						!"C".equalsIgnoreCase(rateValue) && !"N".equalsIgnoreCase(rateValue)) { 
					++countOfcomplianceIssues;
				}
				int indexVal = dataDesc.indexOf("overall score");
				if(indexVal > -1) {
					String overallScore = dataDesc.substring(0, indexVal);
					if( overallScore != null ) {
						tUITapResult.setOverAllScore(overallScore.trim());
					} else {
						tUITapResult.setOverAllScore("NA");
					}
				}
				tUITapResult.setCountOfcomplianceIssues(countOfcomplianceIssues);
			}
		}
		return tUITapResult;
	}


}
