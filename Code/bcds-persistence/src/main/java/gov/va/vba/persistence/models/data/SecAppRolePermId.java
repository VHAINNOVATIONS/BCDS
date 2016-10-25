package main.java.gov.va.vba.persistence.models.data;
// Generated Oct 21, 2016 1:08:50 PM by Hibernate Tools 5.2.0.Beta1

import java.math.BigDecimal;

/**
 * SecAppRolePermId generated by hbm2java
 */
public class SecAppRolePermId implements java.io.Serializable {

	private BigDecimal roleId;
	private BigDecimal permId;

	public SecAppRolePermId() {
	}

	public SecAppRolePermId(BigDecimal roleId, BigDecimal permId) {
		this.roleId = roleId;
		this.permId = permId;
	}

	public BigDecimal getRoleId() {
		return this.roleId;
	}

	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}

	public BigDecimal getPermId() {
		return this.permId;
	}

	public void setPermId(BigDecimal permId) {
		this.permId = permId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SecAppRolePermId))
			return false;
		SecAppRolePermId castOther = (SecAppRolePermId) other;

		return ((this.getRoleId() == castOther.getRoleId()) || (this.getRoleId() != null
				&& castOther.getRoleId() != null && this.getRoleId().equals(castOther.getRoleId())))
				&& ((this.getPermId() == castOther.getPermId()) || (this.getPermId() != null
						&& castOther.getPermId() != null && this.getPermId().equals(castOther.getPermId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		result = 37 * result + (getPermId() == null ? 0 : this.getPermId().hashCode());
		return result;
	}

}
