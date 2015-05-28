/**
 * 
 */
package com.tui.sonar.report.tap.result;

import com.tui.sonar.report.dto.TUIQualityResults;

/**
 * @author bsinh1
 *
 */
public class TUITapResult {
	
	private String overYSlowAllScore = null;
	private int countOfYSlowcomplianceIssues = 0;
	
	public String getOverAllScore() {
		return overYSlowAllScore;
	}
	public void setOverAllScore(String overAllResults) {
		this.overYSlowAllScore = overAllResults;
	}
	public int getCountOfcomplianceIssues() {
		return countOfYSlowcomplianceIssues;
	}
	public void setCountOfcomplianceIssues(int countOfcomplianceIssues) {
		this.countOfYSlowcomplianceIssues = countOfcomplianceIssues;
	}
}
