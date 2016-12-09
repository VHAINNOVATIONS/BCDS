package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.entity.ModelRatingResults;
import gov.va.vba.persistence.entity.ModelRatingResultsCntnt;
import gov.va.vba.persistence.entity.ModelRatingResultsCntntId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ProSphere User on 12/7/2016.
 */
@Repository
public interface ModelRatingResultsCntnRepository extends JpaRepository<ModelRatingResultsCntnt, ModelRatingResultsCntntId> {
}
