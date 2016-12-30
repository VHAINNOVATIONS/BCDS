package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.constants.QueryConstants;
import gov.va.vba.persistence.entity.DDMModelPatternIndex;
import gov.va.vba.persistence.mapper.LongRowMapper;
import gov.va.vba.persistence.models.data.DecisionDetails;
import gov.va.vba.persistence.models.data.DiagnosisCount;
import gov.va.vba.persistence.models.data.KneeClaim;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public List<DDMModelPatternIndex> getPatternAccuracy(Long patternId) {
        String sql = "SELECT PATTERN_ID AS patternId, ACCURACY, CDD, " +
                "PATTERN_INDX_NUMBER AS patternIndexNumber, CRTD_BY, CRTD_DTM,CTLG_ID, MODEL_TYPE " +
                "FROM BCDSS.DDM_MODEL_PATTERN_INDX WHERE PATTERN_ID = " + patternId;
        List<DDMModelPatternIndex> decisions = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DDMModelPatternIndex.class));
        LOG.info("****************************************************************");
        for (DDMModelPatternIndex decision : decisions) {
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

    @Override
    public List<Long> getKneeCntntPattrens(Map<Long, Integer> contentionCount, List<Long> pattrens) {
        StringBuilder pattrenQuery = new StringBuilder();
        pattrenQuery.append("SELECT DISTINCT PATTERN_ID FROM DDM_MODEL_CNTNT WHERE MODEL_TYPE='Knee' AND (  ");
        for (Map.Entry<Long, Integer> x : contentionCount.entrySet()) {
            pattrenQuery.append(" (").append("CNTNT_CD=").append(x.getKey()).append(" AND ")
                    .append("COUNT=").append(x.getValue()).append(") OR");
        }
        pattrenQuery.replace(pattrenQuery.length() - 2, pattrenQuery.length(), "");
        pattrenQuery.append(")");
        if (CollectionUtils.isNotEmpty(pattrens)) {
            pattrenQuery.append(" AND PATTERN_ID IN (").append(StringUtils.join(pattrens, ",")).append(")");
        }
        LOG.info("QUERY -------- " + pattrenQuery);
        List<Long> result = jdbcTemplate.query(pattrenQuery.toString(), new LongRowMapper());
        //List<DiagnosisCount> count = jdbcTemplate.query(QueryConstants.DIAGNOSIS_COUNT, new Object[]{veteranId, claimDate}, new BeanPropertyRowMapper<>(DiagnosisCount.class));
        LOG.info("****************************************************************");
        for (Long c : result) {
            LOG.info(c.toString());
        }
        LOG.info("****************************************************************");
        return result;
    }

    @Override
    public List<Long> getKneeDiagPattrens(List<DiagnosisCount> diagCount, List<Long> pattrens) {
        StringBuilder pattrenQuery = new StringBuilder();
        pattrenQuery.append("SELECT DISTINCT PATTERN_ID FROM DDM_MODEL_DIAG WHERE MODEL_TYPE='Knee' AND (  ");
        for (DiagnosisCount x : diagCount) {
            pattrenQuery.append(" (").append("DIAG_CD=").append(x.getDecisionCode()).append(" AND ")
                    .append("COUNT=").append(x.getCount()).append(") OR");
        }
        pattrenQuery.replace(pattrenQuery.length() - 2, pattrenQuery.length(), "");
        pattrenQuery.append(")");
        if (CollectionUtils.isNotEmpty(pattrens)) {
            pattrenQuery.append(" AND PATTERN_ID IN (").append(StringUtils.join(pattrens, ",")).append(")");
        }
        LOG.info("QUERY -------- " + pattrenQuery);
        List<Long> result = jdbcTemplate.query(pattrenQuery.toString(), new LongRowMapper());
        //List<DiagnosisCount> count = jdbcTemplate.query(QueryConstants.DIAGNOSIS_COUNT, new Object[]{veteranId, claimDate}, new BeanPropertyRowMapper<>(DiagnosisCount.class));
        LOG.info("****************************************************************");
        for (Long c : result) {
            LOG.info(c.toString());
        }
        LOG.info("****************************************************************");
        return result;
    }

    @Override
    public int getClaimaintAge(long veteranId, long claimId) {
        Integer count = jdbcTemplate.queryForObject(QueryConstants.CLAIMANT_AGE_QUERY, new Object[]{veteranId, claimId}, Integer.class);
        LOG.info("****************************************************************");
        LOG.info("CLAIMANT AGE ::::::: {} ", count);
        LOG.info("****************************************************************");
        return count;
    }

}
