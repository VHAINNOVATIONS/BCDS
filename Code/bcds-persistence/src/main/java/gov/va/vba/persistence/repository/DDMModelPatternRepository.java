package gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.DDMModelPattern;

/**
 * Spring Data JPA repository for the DDM Model Pattern entity.
 */
public interface DDMModelPatternRepository extends JpaRepository<DDMModelPattern, Long> {

	@Query(value = "SELECT PATTERN_ID, MODEL_TYPE, CLAIMANT_AGE, CLAIM_COUNT, CONTENTION_COUNT, PRIOR_CDD, CDD_AGE FROM DDM_MODEL_PATTERN", nativeQuery = true)
	public List<DDMModelPattern> modelPattern();
}
