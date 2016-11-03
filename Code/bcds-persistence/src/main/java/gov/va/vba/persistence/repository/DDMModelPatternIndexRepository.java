package gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.va.vba.persistence.entity.DDMModelPatternIndex;

/**
 * Spring Data JPA repository for the DDM Model Pattern Index entity.
 */
public interface DDMModelPatternIndexRepository extends JpaRepository<DDMModelPatternIndex, Long> {

	@Query(value = "SELECT PATTERN_ID, ACCURACY, CDD, PATTERN_INDEX_NUMBER, CRTD_BY, CRTD_DTM, CTLG_ID, MODEL_TYPE FROM DDM_MODEL_PATTERN_INDX", nativeQuery = true)
	public List<DDMModelPatternIndex> modelPatternIndex();
}
