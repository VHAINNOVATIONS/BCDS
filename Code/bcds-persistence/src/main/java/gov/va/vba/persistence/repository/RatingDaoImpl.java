package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.constants.QueryConstants;
import gov.va.vba.persistence.models.data.DecisionDetails;
import gov.va.vba.persistence.models.data.DiagnosisCount;
import gov.va.vba.persistence.models.data.KneeClaim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by ProSphere User on 12/16/2016.
 */
@Repository
@Transactional
public class RatingDaoImpl implements RatingDao {

    private static final Logger LOG = LoggerFactory.getLogger(RatingDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<KneeClaim> getPreviousClaims(long veteranId, long claimId) {
        List<KneeClaim> claims = jdbcTemplate.query(QueryConstants.PREVIOUS_CLAIMS_QUERY, new Object[]{veteranId, veteranId, claimId}, new BeanPropertyRowMapper<>(KneeClaim.class));
        LOG.info("****************************************************************");
        for (KneeClaim claim : claims) {
            LOG.info(claim.toString());
        }
        LOG.info("****************************************************************");
        return claims;
    }

    @Override
    public List<DecisionDetails> getDecisionsPercentByClaimDate(long veteranId, Date claimDate) {
        List<DecisionDetails> decisions = jdbcTemplate.query(QueryConstants.DECISIONS_PERCENT_QUERY, new Object[]{veteranId, claimDate, veteranId}, new BeanPropertyRowMapper<>(DecisionDetails.class));
        LOG.info("****************************************************************");
        for (DecisionDetails decision : decisions) {
            LOG.info(decision.toString());
        }
        LOG.info("****************************************************************");
        return decisions;
    }

    @Override
    public List<DiagnosisCount> getDiagnosisCount(long veteranId, Date claimDate) {
        List<DiagnosisCount> count = jdbcTemplate.query(QueryConstants.DIAGNOSIS_COUNT, new Object[]{veteranId, claimDate}, new BeanPropertyRowMapper<>(DiagnosisCount.class));
        LOG.info("****************************************************************");
        for (DiagnosisCount c : count) {
            LOG.info(c.toString());
        }
        LOG.info("****************************************************************");
        return count;
    }

}
