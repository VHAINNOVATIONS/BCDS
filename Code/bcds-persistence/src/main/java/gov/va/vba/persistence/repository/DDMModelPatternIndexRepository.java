package gov.va.vba.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import gov.va.vba.persistence.entity.DDMModelPatternIndex;

/**
 * Spring Data JPA repository for the DDM Model Pattern entity.
 */
@Repository
public interface DDMModelPatternIndexRepository extends JpaRepository<DDMModelPatternIndex, Long> {

}
