package gov.va.vba.service;

import gov.va.vba.persistence.entity.ModelRatingResults;
import gov.va.vba.persistence.models.data.ClaimDetails;
import gov.va.vba.persistence.models.data.DecisionDetails;
import gov.va.vba.service.exception.BusinessException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ProSphere User on 1/4/2017.
 */
public interface EarService {

    ModelRatingResults processClaims(int veteranId, List<ClaimDetails> c, String currentLogin) throws BusinessException;

    int getClaimentAge(int veteranId, ClaimDetails kneeClaim);

    BigDecimal getPriorCdd(List<DecisionDetails> decisions);

}
