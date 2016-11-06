package main.java.gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.DDMDiagnosis;

/**
 * Spring Data JPA repository for the DDM Diagnosis entity.
 */
public interface DDMDiagnosisRepository extends JpaRepository<DDMDiagnosis, Long> {

	@Query(value = "SELECT DIAG_ID, CTLG_ID, DIAG_CD, DIAG_CD_DESC, CRTD_BY, CRTD_DTM, MODEL_TYPE FROM DDM_DIAG", nativeQuery = true)
	public List<DDMDiagnosis> DDMDiagnosis();
}
