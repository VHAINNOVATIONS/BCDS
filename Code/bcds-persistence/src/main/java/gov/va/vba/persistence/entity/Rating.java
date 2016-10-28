package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema="BCDSS", name = "AH4929_RATING_DECISION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rating implements Serializable {

	private Long veteranId;
	private String diagnosisCode;
	private String NBRPrcnt;

	@Id
	@Column(name="PTCPNT_VET_ID")
	public Long getVeteranId() {
		return veteranId;
	}
	public void setVeteranId(Long veteranId) {
		this.veteranId = veteranId;
	}

	@Column(name="DIAGNOSIS_CODE")
	public String getDiagnosisCode() {
		return diagnosisCode;
	}
	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	@Column(name="PRCNT_NBR")
	public String getNBRPrcnt() {
		return NBRPrcnt;
	}
	public void setNBRPrcnt(String NBRPrcnt) {
		this.NBRPrcnt = NBRPrcnt;
	}
}
