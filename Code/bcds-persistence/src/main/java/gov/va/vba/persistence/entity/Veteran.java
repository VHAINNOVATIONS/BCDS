package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema = "BCDSS", name = "AH4929_PERSON")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Veteran implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long veteranId;
	private String BirthYear;
	private String veteranGender;
	private String dateOfDec;
	private String stateCd;
	private Set<Claim> claims = new HashSet<>();

	@Id
	@Column(name = "PTCPNT_VET_ID")
	public Long getVeteranId() {
		return veteranId;
	}

	public void setVeteranId(Long veteranId) {
		this.veteranId = veteranId;
	}

	@Column(name = "DOB")
	public String getBirthYear() {
		return BirthYear;
	}

	public void setBirthYear(String BirthYear) {
		this.BirthYear = BirthYear;
	}

	@Column(name = "GENDER")
	public String getVeteranGender() {
		return veteranGender;
	}

	public void setVeteranGender(String veteranGender) {
		this.veteranGender = veteranGender;
	}

	@Column(name = "DOD")
	public String getDateOfDec() {
		return dateOfDec;
	}

	public void setDateOfDec(String dateOfDec) {
		this.dateOfDec = dateOfDec;
	}

	@Column(name = "STATE_CODE")
	public String getStateCd() {
		return stateCd;
	}

	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "veteran")
	public Set<Claim> getClaims() {
		return claims;
	}

	public void setClaims(Set<Claim> claims) {
		this.claims = claims;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((veteranId == null) ? 0 : veteranId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veteran other = (Veteran) obj;
		if (veteranId == null) {
			if (other.veteranId != null)
				return false;
		} else if (!veteranId.equals(other.veteranId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
