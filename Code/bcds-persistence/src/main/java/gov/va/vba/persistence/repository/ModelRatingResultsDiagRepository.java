package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.entity.ModelRatingResultsDiag;
import gov.va.vba.persistence.entity.ModelRatingResultsDiagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ProSphere User on 12/12/2016.
 */
@Repository
public interface ModelRatingResultsDiagRepository extends JpaRepository<ModelRatingResultsDiag, ModelRatingResultsDiagId> {
}
