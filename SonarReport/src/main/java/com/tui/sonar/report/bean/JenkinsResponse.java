/**
 * 
 */
package com.tui.sonar.report.bean;

/**
 * @author machou
 *
 */
public class JenkinsResponse {
	 private Results results;

	    public Results getResults ()
	    {
	        return results;
	    }

	    public void setResults (Results results)
	    {
	        this.results = results;
	    }

	    @Override
	    public String toString()
	    {
	        return "JenkinsResponse [results = "+results+"]";
	    }

}
