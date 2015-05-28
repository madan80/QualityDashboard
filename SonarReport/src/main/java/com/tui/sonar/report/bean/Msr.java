package com.tui.sonar.report.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Msr {
	@JsonProperty("val")
	private String val;
	@JsonProperty("frmt_val")
	private String frmt_val;
	@JsonProperty("key")
	private String key;

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getFrmt_val() {
		return frmt_val;
	}

	public void setFrmt_val(String frmt_val) {
		this.frmt_val = frmt_val;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "ClassPojo [val = " + val + ", frmt_val = " + frmt_val
				+ ", key = " + key + "]";
	}
}