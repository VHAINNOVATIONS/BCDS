package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.entity.RatingDecision;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by ProSphere User on 11/6/2016.
 */
public interface RatingDecisionRepository extends CrudRepository<RatingDecision, BigDecimal>{

    //@Query(value = "SELECT r FROM RatingDecision r WHERE veteranId = ?1")
    List<RatingDecision> findByVeteranIdAndProfileDateLessThanAndPercentNumberNotNullAndDiagnosisCodeIn(BigDecimal veteranId, Date claimDate, List<String> diagnosisCodes);

}
