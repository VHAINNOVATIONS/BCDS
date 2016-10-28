package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema="BCDSS", name = "AH4929_PERSON")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Veteran implements Serializable {

	private Long veteranId;
	private Date dateOfBirth;
	private String veteranGender;
	private Date dateOfDec;
	private String stateCd;

	private Set<Claim> claim;

	@Id
	@Column(name="PTCPNT_VET_ID")
	public Long getVeteranId() {
		return veteranId;
	}
	public void setVeteranId(Long veteranId) {
		this.veteranId = veteranId;
	}

	@Column(name="DOB")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name="GENDER")
	public String getVeteranGender() {
		return veteranGender;
	}
	public void setVeteranGender(String veteranGender) {
		this.veteranGender = veteranGender;
	}

	@Column(name="DOD")
	public Date getDateOfDec() {
		return dateOfDec;
	}
	public void setDateOfDec(Date dateOfDec) {
		this.dateOfDec = dateOfDec;
	}

	@Column(name="STATE_CODE")
	public String getStateCd() {
		return stateCd;
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
}
