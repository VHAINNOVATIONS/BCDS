package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.constants.QueryConstants;
import gov.va.vba.persistence.entity.ModelRatingResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


/**
 * Spring Data JPA repository for the DDM Model Pattern entity.
 */
public interface ModelRatingResultsRepository extends JpaRepository<ModelRatingResults, Long> {

	@Query(value = "SELECT PROCESS_ID, VET_ID, PATTERN_ID, PROCESS_DATE, CLAIM_ID, CLAIMANT_AGE, DOB, END_PRODUCT_CODE, RO_NUMBER, CLAIM_DATE, PROFILE_DATE, PROMULGATION_DATE, RECENT_DATE, MODEL_TYPE, MODEL_CONTENTION_COUNT, CONTENTION_COUNT, PRIOR_CDD, QUANT_PRIOR_CDD, CURR_CDD, QUANT_CDD, CLAIM_AGE, CDD_AGE, CLAIM_COUNT FROM BCDSS.MODEL_RATING_RESULTS", nativeQuery = true)
	public List<ModelRatingResults> findAll();
	
	@Query(value = "SELECT PROCESS_ID, VET_ID, PATTERN_ID, PROCESS_DATE, CLAIM_ID, CLAIMANT_AGE, DOB, END_PRODUCT_CODE, RO_NUMBER, CLAIM_DATE, PROFILE_DATE, PROMULGATION_DATE, RECENT_DATE, MODEL_TYPE, MODEL_CONTENTION_COUNT, CONTENTION_COUNT, PRIOR_CDD, QUANT_PRIOR_CDD, CURR_CDD, QUANT_CDD, CLAIM_AGE, CDD_AGE, CLAIM_COUNT FROM BCDSS.MODEL_RATING_RESULTS WHERE ROWNUM <= 50 ORDER BY PROCESS_ID", nativeQuery = true)
	public List<ModelRatingResults> findTop50();

	@Query(value = "SELECT r FROM ModelRatingResults r WHERE r.processId = ?1")
	List<ModelRatingResults> findOneResult(Long processId);
	
	@Query(value = "SELECT r FROM ModelRatingResults r WHERE (r.processId = ?1) AND (r.processDate >= ?2 AND r.processDate <= ?3) AND (?4 is null or r.modelType = ?4)")
	List<ModelRatingResults> findResultByRangeOnProcssedDate(Long processId, Date fromDate, Date toDate, String modelType);

//	@Query(value = "SELECT c FROM ModelRatingResults c WHERE c.modelType = ?1 AND c.claimantAge = ?2 AND c.claimCount = ?3 AND c.contentionCount = ?4 AND c.priorCDD = ?5 AND c.CDDAge = ?6")
//	List<ModelRatingResults> findPatternId(String modelType, Long claimantAge, Long claimCount, Long contentionCount, Long priorCDD, Long CDDAge);

}
