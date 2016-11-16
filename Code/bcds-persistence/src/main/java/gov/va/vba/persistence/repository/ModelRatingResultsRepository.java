package gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.ModelRatingResults;

/**
 * Spring Data JPA repository for the DDM Model Pattern entity.
 */
public interface ModelRatingResultsRepository extends JpaRepository<ModelRatingResults, Long> {

	@Query(value = "SELECT PROCESS_ID, VET_ID, PATTERN_ID, PROCESS_DATE, CLAIM_ID, CLAIMANT_AGE, DOB, END_PRODUCT_CODE, RO_NUMBER, CLAIM_DATE, PROFILE_DATE, PROMULGATION_DATE, RECENT_DATE, MODEL_TYPE, MODEL_CONTENTION_COUNT, CONTENTION_COUNT, PRIOR_CDD, QUANT_PRIOR_CDD, CURR_CDD, QUANT_CDD, CLAIM_AGE, CDD_AGE, CLAIM_COUNT FROM MODEL_RATING_RESULTS", nativeQuery = true)
	public List<ModelRatingResults> findAll();
	
	@Query(value = "SELECT PROCESS_ID, VET_ID, PATTERN_ID, PROCESS_DATE, CLAIM_ID, CLAIMANT_AGE, DOB, END_PRODUCT_CODE, RO_NUMBER, CLAIM_DATE, PROFILE_DATE, PROMULGATION_DATE, RECENT_DATE, MODEL_TYPE, MODEL_CONTENTION_COUNT, CONTENTION_COUNT, PRIOR_CDD, QUANT_PRIOR_CDD, CURR_CDD, QUANT_CDD, CLAIM_AGE, CDD_AGE, CLAIM_COUNT FROM MODEL_RATING_RESULTS WHERE ROWNUM <= 50 ORDER BY PROCESS_ID", nativeQuery = true)
	public List<ModelRatingResults> findTop50();

	@Query(value = "SELECT c FROM ModelRatingResults c WHERE c.processId = ?1")
	List<ModelRatingResults> findOneResult(Long processId);

//	@Query(value = "SELECT c FROM ModelRatingResults c WHERE c.modelType = ?1 AND c.claimantAge = ?2 AND c.claimCount = ?3 AND c.contentionCount = ?4 AND c.priorCDD = ?5 AND c.CDDAge = ?6")
//	List<ModelRatingResults> findPatternId(String modelType, Long claimantAge, Long claimCount, Long contentionCount, Long priorCDD, Long CDDAge);
}
