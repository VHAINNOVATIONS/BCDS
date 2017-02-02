package gov.va.vba.persistence.models.data;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.Set;

public class ModelRatingAggregateResult implements Serializable {
	private String modelType;
	private Long userCount;
	private Long claimsCount;
	private Long patternsCount;
	private Long sessionsCount;
	private Long avgStatedAccuracy;
	
	public Long getUserCount() {
		return userCount;
	}
	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}
	
	public Long getSessionsCount() {
		return sessionsCount;
	}
	public void setSessionsCount(Long sessionsCount) {
		this.sessionsCount = sessionsCount;
	}
	
	public Long getClaimsCount() {
		return claimsCount;
	}
	public void setClaimsCount(Long claimsCount) {
		this.claimsCount = claimsCount;
	}
	
	public Long getPatternsCount() {
		return patternsCount;
	}
	public void setPatternsCount(Long patternsCount) {
		this.patternsCount = patternsCount;
	}
	
	public Long getAvgStatedAccuracy() {
		return avgStatedAccuracy;
	}
	public void setAvgStatedAccuracy(Long avgStatedAccuracy) {
		this.avgStatedAccuracy = avgStatedAccuracy;
	}
	
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
}
