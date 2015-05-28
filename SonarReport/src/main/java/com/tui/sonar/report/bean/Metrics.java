package com.tui.sonar.report.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Metrics {
	@JsonProperty("msr")
	private Msr[] msr;
	@JsonProperty("id")
	private String id;
	@JsonProperty("creationDate")
	private String creationDate;
	@JsonProperty("lname")
	private String lname;
	@JsonProperty("scope")
	private String scope;
	@JsonProperty("description")
	private String description;
	@JsonProperty("name")
	private String name;
	@JsonProperty("qualifier")
	private String qualifier;
	@JsonProperty("date")
	private String date;
	@JsonProperty("key")
	private String key;
	@JsonProperty("version")
	private String version;

	public Msr[] getMsr() {
		return msr;
	}

	public void setMsr(Msr[] msr) {
		this.msr = msr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "ClassPojo [msr = " + msr + ", id = " + id + ", creationDate = "
				+ creationDate + ", lname = " + lname + ", scope = " + scope
				+ ", description = " + description + ", name = " + name
				+ ", qualifier = " + qualifier + ", date = " + date
				+ ", key = " + key + ", version = " + version + "]";
	}
}