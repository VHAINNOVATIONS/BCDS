package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.constants.QueryConstants;
import gov.va.vba.persistence.models.data.ClaimDetails;
import gov.va.vba.persistence.models.data.DecisionDetails;
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
 * Created by ProSphere User on 1/4/2017.
 */
@Repository
@Transactional
public class EarDaoImpl implements EarDao {

    private static final Logger LOG = LoggerFactory.getLogger(EarDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ClaimDetails> getClaims(long veteranId, long claimId) {
        List<ClaimDetails> claims = jdbcTemplate.query(QueryConstants.EAR_PROFILE_DATE_QUERY, new Object[]{veteranId, claimId}, new BeanPropertyRowMapper<>(ClaimDetails.class));
        LOG.info("****************************************************************");
        for (ClaimDetails claim : claims) {
            LOG.info(claim.toString());
        }
        LOG.info("****************************************************************");
        return claims;
    }

    @Override
    public List<ClaimDetails> getPreviousClaims(long veteranId, Date claimDate) {
        List<ClaimDetails> claims = jdbcTemplate.query(QueryConstants.EAR_PREVIOUS_CLAIMS_QUERY, new Object[]{veteranId, claimDate}, new BeanPropertyRowMapper<>(ClaimDetails.class));
        LOG.info("****************************************************************");
        for (ClaimDetails claim : claims) {
            LOG.info(claim.toString());
        }
        LOG.info("****************************************************************");
        return claims;
    }

    @Override
    public List<DecisionDetails> getDecisionsPercentByClaimDate(long veteranId, Date claimDate) {
        List<DecisionDetails> decisions = jdbcTemplate.query(QueryConstants.EAR_DECISIONS_PERCENT_QUERY, new Object[]{veteranId, veteranId, claimDate}, new BeanPropertyRowMapper<>(DecisionDetails.class));
        LOG.info("****************************************************************");
        for (DecisionDetails decision : decisions) {
            LOG.info(decision.toString());
        }
        LOG.info("****************************************************************");
        return decisions;
    }

    @Override
    public Date getBeginDate(long veteranId, Date claimDate) {
        Date beginDate = jdbcTemplate.queryForObject(QueryConstants.EAR_PRIOR_CDD_BEGIN_DATE, new Object[]{veteranId, claimDate}, Date.class);
        LOG.info("****************************************************************");
        LOG.info("PRIOR CDD BEGIN DATE AGE ::::::: {} ", beginDate);
        LOG.info("****************************************************************");
        return beginDate;
    }

}
