package gov.va.vba.persistence.models.data;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.Set;

public class Veterans implements Serializable {

	private long veteranId;
	private Date dateOfBirth;
	private String veteranGender;
	private Date dateOfDec;
	private String stateCd;

	private Set<Claims> claims;

	public Veterans() {
	}

	public Veterans(long veteranId, Date dateOfBirth) {
		this.veteranId = veteranId;
		this.dateOfBirth = dateOfBirth;
	}

	public Veterans(long veteranId, Date dateOfBirth, String veteranGender, Date dateOfDec, String stateCd) {
		this.veteranId = veteranId;
		this.dateOfBirth = dateOfBirth;
		this.veteranGender = veteranGender;
		this.dateOfDec = dateOfDec;
		this.stateCd = stateCd;
	}

	public long getVeteranId() {
		return this.veteranId;
	}

	public void setVeteranId(long veteranId) {
		this.veteranId = veteranId;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getVeteranGender() {
		return this.veteranGender;
	}

	public void setVeteranGender(String veteranGender) {
		this.veteranGender = veteranGender;
	}

	public Date getDateOfDec() {
		return this.dateOfDec;
	}

	public void setDateOfDec(Date dateOfDec) {
		this.dateOfDec = dateOfDec;
	}

	public String getStateCd() {
		return this.stateCd;
	}

	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}

	public Set<Claims> getClaims() {
		return claims;
	}

	public void setClaims(Set<Claims> claims) {
		this.claims = claims;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Veterans))
			return false;
		Veterans castOther = (Veterans) other;

		return (this.getVeteranId() == castOther.getVeteranId())
				&& ((this.getDateOfBirth() == castOther.getDateOfBirth())
						|| (this.getDateOfBirth() != null && castOther.getDateOfBirth() != null
								&& this.getDateOfBirth().equals(castOther.getDateOfBirth())))
				&& ((this.getVeteranGender() == castOther.getVeteranGender())
						|| (this.getVeteranGender() != null && castOther.getVeteranGender() != null
								&& this.getVeteranGender().equals(castOther.getVeteranGender())))
				&& ((this.getDateOfDec() == castOther.getDateOfDec())
						|| (this.getDateOfDec() != null && castOther.getDateOfDec() != null
								&& this.getDateOfDec().equals(castOther.getDateOfDec())))
				&& ((this.getStateCd() == castOther.getStateCd()) || (this.getStateCd() != null
						&& castOther.getStateCd() != null && this.getStateCd().equals(castOther.getStateCd())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getVeteranId();
		result = 37 * result + (getDateOfBirth() == null ? 0 : this.getDateOfBirth().hashCode());
		result = 37 * result + (getVeteranGender() == null ? 0 : this.getVeteranGender().hashCode());
		result = 37 * result + (getDateOfDec() == null ? 0 : this.getDateOfDec().hashCode());
		result = 37 * result + (getStateCd() == null ? 0 : this.getStateCd().hashCode());
		return result;
	}

}
