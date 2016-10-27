package main.java.gov.va.vba.persistence.models.data;

import java.lang.String;
import java.util.Date;

public class Veterans {

	private long ptcpntVetId;
	private Date dateOfBirth;
	private String gender;
	private Date dateOfDec;
	private String stateCd;

	public Veterans() {
	}

	public Veterans(long ptcpntVetId, Date dateOfBirth) {
		this.ptcpntVetId = ptcpntVetId;
		this.dateOfBirth = dateOfBirth;
	}

	public Veterans(long ptcpntVetId, Date dateOfBirth, String gender, Date dateOfDec, String stateCd) {
		this.ptcpntVetId = ptcpntVetId;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.dateOfDec = dateOfDec;
		this.stateCd = stateCd;
	}

	public long getPtcpntVetId() {
		return this.ptcpntVetId;
	}

	public void setPtcpntVetId(long ptcpntVetId) {
		this.ptcpntVetId = ptcpntVetId;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Veterans))
			return false;
		Veterans castOther = (Veterans) other;

		return (this.getPtcpntVetId() == castOther.getPtcpntVetId())
				&& ((this.getDateOfBirth() == castOther.getDateOfBirth())
						|| (this.getDateOfBirth() != null && castOther.getDateOfBirth() != null
								&& this.getDateOfBirth().equals(castOther.getDateOfBirth())))
				&& ((this.getGender() == castOther.getGender())
						|| (this.getGender() != null && castOther.getGender() != null
								&& this.getGender().equals(castOther.getGender())))
				&& ((this.getDateOfDec() == castOther.getDateOfDec())
						|| (this.getDateOfDec() != null && castOther.getDateOfDec() != null
								&& this.getDateOfDec().equals(castOther.getDateOfDec())))
				&& ((this.getStateCd() == castOther.getStateCd()) || (this.getStateCd() != null
						&& castOther.getStateCd() != null && this.getStateCd().equals(castOther.getStateCd())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getPtcpntVetId();
		result = 37 * result + (getDateOfBirth() == null ? 0 : this.getDateOfBirth().hashCode());
		result = 37 * result + (getGender() == null ? 0 : this.getGender().hashCode());
		result = 37 * result + (getDateOfDec() == null ? 0 : this.getDateOfDec().hashCode());
		result = 37 * result + (getStateCd() == null ? 0 : this.getStateCd().hashCode());
		return result;
	}

}
