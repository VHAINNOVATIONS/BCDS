package main.java.gov.va.vba.persistence.models.data;
// Generated Oct 21, 2016 1:08:50 PM by Hibernate Tools 5.2.0.Beta1

import java.math.BigDecimal;

/**
 * SecAppRsrcPermId generated by hbm2java
 */
public class SecAppRsrcPermId implements java.io.Serializable {

	private BigDecimal permId;
	private BigDecimal appRsrcId;

	public SecAppRsrcPermId() {
	}

	public SecAppRsrcPermId(BigDecimal permId, BigDecimal appRsrcId) {
		this.permId = permId;
		this.appRsrcId = appRsrcId;
	}

	public BigDecimal getPermId() {
		return this.permId;
	}

	public void setPermId(BigDecimal permId) {
		this.permId = permId;
	}

	public BigDecimal getAppRsrcId() {
		return this.appRsrcId;
	}

	public void setAppRsrcId(BigDecimal appRsrcId) {
		this.appRsrcId = appRsrcId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SecAppRsrcPermId))
			return false;
		SecAppRsrcPermId castOther = (SecAppRsrcPermId) other;

		return ((this.getPermId() == castOther.getPermId()) || (this.getPermId() != null
				&& castOther.getPermId() != null && this.getPermId().equals(castOther.getPermId())))
				&& ((this.getAppRsrcId() == castOther.getAppRsrcId()) || (this.getAppRsrcId() != null
						&& castOther.getAppRsrcId() != null && this.getAppRsrcId().equals(castOther.getAppRsrcId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPermId() == null ? 0 : this.getPermId().hashCode());
		result = 37 * result + (getAppRsrcId() == null ? 0 : this.getAppRsrcId().hashCode());
		return result;
	}

}
