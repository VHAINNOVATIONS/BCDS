package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.constants.QueryConstants;
import gov.va.vba.persistence.entity.DDMModelPatternIndex;
import gov.va.vba.persistence.mapper.LongRowMapper;
import gov.va.vba.persistence.models.data.ClaimDetails;
import gov.va.vba.persistence.models.data.ContentionDetails;
import gov.va.vba.persistence.models.data.DecisionDetails;
import gov.va.vba.persistence.models.data.DiagnosisCount;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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
    public List<ClaimDetails> getPreviousClaims(long veteranId, long claimId) {
        List<ClaimDetails> claims = jdbcTemplate.query(QueryConstants.PREVIOUS_CLAIMS_QUERY, new Object[]{veteranId, veteranId, claimId}, new BeanPropertyRowMapper<>(ClaimDetails.class));
        LOG.info("****************************************************************");
        for (ClaimDetails claim : claims) {
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
    public List<DiagnosisCount> getEarDiagnosisCount(long veteranId, Date claimDate) {
        List<DiagnosisCount> count = jdbcTemplate.query(QueryConstants.EAR_DIAGNOSIS_COUNT, new Object[]{veteranId, claimDate}, new BeanPropertyRowMapper<>(DiagnosisCount.class));
        LOG.info("****************************************************************");
        for (DiagnosisCount c : count) {
            LOG.info(c.toString());
        }
        LOG.info("****************************************************************");
        return count;
    }

    @Override
    public List<Long> getKneeCntntPattrens(Map<Long, Integer> contentionCount, List<Long> pattrens, String modelType) {
        StringBuilder pattrenQuery = new StringBuilder();
        pattrenQuery.append("SELECT DISTINCT PATTERN_ID FROM DDM_MODEL_CNTNT WHERE UPPER(MODEL_TYPE)='" + modelType + "' AND (  ");
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
    public List<Long> getKneeDiagPattrens(List<DiagnosisCount> diagCount, List<Long> pattrens, String modelType) {
        StringBuilder pattrenQuery = new StringBuilder();
        pattrenQuery.append("SELECT DISTINCT PATTERN_ID FROM DDM_MODEL_DIAG WHERE UPPER(MODEL_TYPE)='" + modelType + "' AND (  ");
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

    @Override
    public List<Long> getProcessIDSeq() {
        StringBuilder processQuery = new StringBuilder();
        processQuery.append("SELECT COALESCE(MAX(PROCESS_ID),0) FROM BCDSS.MODEL_RATING_RESULTS");
        LOG.info("QUERY -------- " + processQuery);
        List<Long> maxProcessId = jdbcTemplate.query(processQuery.toString(), new LongRowMapper());
        LOG.info("****************************************************************");
        for (Long pId : maxProcessId) {
            LOG.info(pId.toString());
        }
        LOG.info("****************************************************************");
        return maxProcessId;
    }

    @Override
    public List<ClaimDetails> getClaims() {
        List<ClaimDetails> claims = jdbcTemplate.query(QueryConstants.CLAIMS_QUERY, new BeanPropertyRowMapper<>(ClaimDetails.class));
        LOG.info("****************************************************************");
        for (ClaimDetails claim : claims) {
            LOG.info(claim.toString());
        }
        LOG.info("****************************************************************");
        return claims;
    }

    @Override
    public List<ClaimDetails> getClaims(long veteranId, long claimId) {
        List<ClaimDetails> claims = jdbcTemplate.query(QueryConstants.CLAIMS_BY_VETERAN_ID_AND_CLAIM_ID, new Object[]{veteranId, claimId}, new BeanPropertyRowMapper<>(ClaimDetails.class));
        LOG.info("****************************************************************");
        for (ClaimDetails claim : claims) {
            LOG.info(claim.toString());
        }
        LOG.info("****************************************************************");
        return claims;
    }

    @Override
    public ContentionDetails getContention(long contentionCode) {
        ContentionDetails contentionDetails = jdbcTemplate.queryForObject(QueryConstants.CNTNT_DETAILS_QUERY, new Object[]{contentionCode}, new BeanPropertyRowMapper<>(ContentionDetails.class));
        LOG.info("****************************************************************");
        LOG.info(contentionDetails.toString());
        LOG.info("****************************************************************");
        return contentionDetails;
    }

    @Override
    public List<Long> getPattern(String modelType, long priorCdd, Map<Long, Long> contentionsCount, List<DiagnosisCount> diagnosisCount) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT P.PATTERN_ID FROM BCDSS.DDM_MODEL_PATTERN P, BCDSS.DDM_MODEL_CNTNT C, BCDSS.DDM_MODEL_DIAG D ");
        query.append("WHERE P.PATTERN_ID = C.PATTERN_ID AND P.PATTERN_ID = D.PATTERN_ID AND P.MODEL_TYPE = '");
        query.append(modelType);
        query.append("' AND P.PRIOR_CDD = ");
        query.append(priorCdd);
        if(MapUtils.isNotEmpty(contentionsCount)) {
            query.append(" AND (");
            for (Map.Entry<Long, Long> contention : contentionsCount.entrySet()) {
                query.append(" AND (").append("C.CNTNT_CD=").append(contention.getKey()).append(" AND C.COUNT=").append(contention.getValue()).append(")");
            }
            query.append(")");
        }
        if(CollectionUtils.isNotEmpty(diagnosisCount)) {
            query.append(" AND (");
            for (DiagnosisCount diagnosis : diagnosisCount) {
                query.append(" AND (").append("D.DIAG_CD=").append(diagnosis.getDecisionCode()).append(" AND D.COUNT=").append(diagnosis.getCount()).append(")");
            }
            query.append(")");
        }

        LOG.info("************************PATTERN QUERY****************************************");
        LOG.info(query.toString());
        LOG.info("****************************************************************");

                //"((C.CNTNT_CD=3140 AND C.COUNT=1)  AND (D.DIAG_CD=6100 AND D.COUNT=5)) AND P.PRIOR_CDD = " + priorCdd;
        List<Long> pattrenIds = jdbcTemplate.query(query.toString(), new LongRowMapper());

        LOG.info("****************************************************************");
        LOG.info("PATTERN IDS "+pattrenIds);
        LOG.info("****************************************************************");
        return pattrenIds;
    }

    @Override
    public List<ClaimDetails> getClaimsByAllFilters(String contentionType, Long regionalOfficeNumber, Date fromDate, Date toDate) {
    	String formatFromDate="";
    	String formatToDate="";
    	//Formatting Date components to pass to the Query
    	if(null!=fromDate && null!=toDate){
    		formatFromDate= new SimpleDateFormat("yyyy-MM-dd").format(fromDate);
            formatToDate= new SimpleDateFormat("yyyy-MM-dd").format(toDate);
    	}
    	
    	String selectClause = "SELECT DISTINCT C.PTCPNT_VET_ID AS veteranId, BNFT_CLAIM_ID AS claimId, " +
            "DATE_OF_CLAIM AS claimDate, CLAIM_RO_NUMBER AS claimRONumber, CLAIM_RO_NAME AS claimROName, " +
            "CNTNTN_ID AS contentionId, CNTNTN_CLMANT_TXT AS contentionClaimantText, CNTNTN_CLSFCN_ID AS contentionClassificationId,  D.MODEL_TYPE AS modelType " +
            "FROM BCDSS.AH4929_RATING_CORP_CLAIM C, BCDSS.AH4929_PERSON P, BCDSS.DDM_CNTNT D WHERE C.PTCPNT_VET_ID = P.PTCPNT_VET_ID " +
            "AND CNTNTN_CLSFCN_ID in ('230','270','2200','2210','3140','3150','3690','3700','3710','3720','3730','3780','3790','3800','4130','4210','4700','4920','5000','5010','5710','6850','8919') " +
            " AND D.MODEL_TYPE in (select D.MODEL_TYPE from BCDSS.DDM_CNTNT  where D.CNTNT_CD = C.CNTNTN_CLSFCN_ID) AND ROWNUM <= 150 ";

	    	String where = "";
	    	if (contentionType != null) {
	    	   where += " AND LOWER(c.CNTNTN_CLMANT_TXT) like '"+contentionType+"'";
	    	}
	    	if (regionalOfficeNumber != 0) {
	    	   where += " AND c.CLAIM_RO_NUMBER = " + regionalOfficeNumber;
	    	}
	    	if (fromDate != null ) {
	    	   where += " AND c.DATE_OF_CLAIM >= TO_DATE('"+formatFromDate+"','yyyy-MM-dd')";
	    	}
	    	if (toDate != null ) {
	    		where +=  " AND c.DATE_OF_CLAIM <= TO_DATE('"+formatToDate+"','yyyy-MM-dd')";
		    	}
	    	String SQL = selectClause  +   where + " ORDER BY C.PTCPNT_VET_ID, DATE_OF_CLAIM";				
	    	LOG.info("Filter QUERY -------- " + SQL);
	    	
        List<ClaimDetails> claims = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(ClaimDetails.class));
        LOG.info("****************************************************************");
        for (ClaimDetails claim : claims) {
            LOG.info(claim.toString());
        }
        LOG.info("****************************************************************");
        return claims;
    }


}
