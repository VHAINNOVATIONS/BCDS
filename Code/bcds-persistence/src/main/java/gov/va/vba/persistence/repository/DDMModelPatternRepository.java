package gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.DDMModelPattern;

/**
 * Spring Data JPA repository for the DDM Model Pattern entity.
 */
public interface DDMModelPatternRepository extends JpaRepository<DDMModelPattern, Long> {

	@Query(value = "SELECT PATTERN_ID, MODEL_TYPE, CLAIMANT_AGE, CLAIM_COUNT, CONTENTION_COUNT, PRIOR_CDD, CDD_AGE FROM DDM_MODEL_CNTNT", nativeQuery = true)
	public List<DDMModelPattern> findAll();
	
	@Query(value = "SELECT c FROM DDMModelPattern c WHERE c.modelType = ?1 AND c.claimantAge = ?2 AND c.claimCount = ?3 AND c.contentionCount = ?4 AND c.priorCDD = ?5 AND c.CDDAge = ?6")
	List<DDMModelPattern> findPatternId(String modelType, Long claimantAge, Long claimCount, Long contentionCount, Long priorCDD, Long CDDAge);
}
