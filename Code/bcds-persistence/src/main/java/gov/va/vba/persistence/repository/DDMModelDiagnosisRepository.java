package gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.DDMModelDiagnosis;

/**
 * Spring Data JPA repository for the DDM Model Diagnosis entity.
 */
public interface DDMModelDiagnosisRepository extends JpaRepository<DDMModelDiagnosis, Long> {

	@Query(value = "SELECT PATTERN_ID, DIAG_ID, COUNT, MODEL_TYPE FROM DDM_MODEL_DIAG", nativeQuery = true)
	public List<DDMModelDiagnosis> modelDiagnosis();
}
