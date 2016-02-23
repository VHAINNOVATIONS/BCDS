package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.entity.reference.Lookup;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Lookup entity.
 */
public interface LookupRepository extends JpaRepository<Lookup,Long> {

    @Query(value = "select distinct discriminator from RULES_MANAGER.Lookup l", nativeQuery = true)
    List<String> findAllTypes();

    @Query(value = "select * from RULES_MANAGER.Lookup where discriminator = ?1 and reference_id = ?2 order by name", nativeQuery = true)
    List<Lookup> findByTypeAndReferenceId(String type, Long referenceId);

}
