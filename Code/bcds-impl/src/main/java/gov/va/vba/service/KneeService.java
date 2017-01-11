package gov.va.vba.service;

import gov.va.vba.persistence.entity.ModelRatingResults;
import gov.va.vba.persistence.models.data.ClaimDetails;

import java.util.List;

/**
 * Created by ProSphere User on 1/11/2017.
 */
public interface KneeService {
    ModelRatingResults processClaims(int veteranId, List<ClaimDetails> inputClaim, String currentLogin);
}
