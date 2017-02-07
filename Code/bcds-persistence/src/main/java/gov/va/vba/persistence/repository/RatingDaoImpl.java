package gov.va.vba.persistence.repository;

import gov.va.vba.persistence.constants.QueryConstants;
import gov.va.vba.persistence.entity.DDMModelPatternIndex;
import gov.va.vba.persistence.entity.EditModelPatternResults;
import gov.va.vba.persistence.mapper.LongRowMapper;
import gov.va.vba.persistence.models.data.ClaimDetails;
import gov.va.vba.persistence.models.data.ContentionDetails;
import gov.va.vba.persistence.models.data.DecisionDetails;
import gov.va.vba.persistence.models.data.DiagnosisCount;
import gov.va.vba.persistence.models.data.ModelRatingAggregateResult;
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

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
    
    @Override
    public Long getClaimCountToProcess(Date fromDate, Date toDate, String modelType, Long regionalOfficeNumber) {
    	String formatFromDate="";
    	String formatToDate="";
    	if(fromDate != null && toDate != null){
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    		formatFromDate= sdf.format(fromDate);
            formatToDate= sdf.format(toDate);
    	}
    	
    	String SQL = "SELECT COUNT (*)  FROM (SELECT DISTINCT C.PTCPNT_VET_ID AS veteranId, BNFT_CLAIM_ID AS claimId, DATE_OF_CLAIM AS claimDate, CLAIM_RO_NUMBER AS claimRONumber, "
    			+ "CLAIM_RO_NAME AS claimROName, CNTNTN_ID AS contentionId, CNTNTN_CLMANT_TXT AS contentionClaimantText, CNTNTN_CLSFCN_ID AS contentionClassificationId,  "
    			+ "D.MODEL_TYPE AS modelType FROM BCDSS.AH4929_RATING_CORP_CLAIM C, BCDSS.AH4929_PERSON P, BCDSS.DDM_CNTNT D WHERE C.PTCPNT_VET_ID = P.PTCPNT_VET_ID AND "
    			+ "CNTNTN_CLSFCN_ID in ('230','270','2200','2210','3140','3150','3690','3700','3710','3720','3730','3780','3790','3800','4130','4210','4700','4920','5000','5010','5710','6850','8919') "
    			+ "AND D.MODEL_TYPE in (select D.MODEL_TYPE from BCDSS.DDM_CNTNT  where D.CNTNT_CD = C.CNTNTN_CLSFCN_ID) AND ROWNUM <= 150 ";
    	
    	if (fromDate != null) {
    		SQL += " AND c.DATE_OF_CLAIM >= TO_DATE('"+formatFromDate+"','yyyy-MM-dd')";
    	}
    	if (toDate != null) {
    		SQL +=  " AND c.DATE_OF_CLAIM <= TO_DATE('"+formatToDate+"','yyyy-MM-dd')";
    	}
    	if (regionalOfficeNumber != 0) {
    		SQL += " AND c.CLAIM_RO_NUMBER = " + regionalOfficeNumber;
    	}
    	if (modelType != null && modelType != "") {
    		SQL += " AND LOWER(D.MODEL_TYPE) like '%"+modelType.toLowerCase()+"%'";
    	}
    	
    	SQL += " )";
    	
    	LOG.info("Bulk Claims Query -------- " + SQL);
	    Long claimCount = jdbcTemplate.queryForObject(SQL, new LongRowMapper());
        LOG.info("****************************************************************");
        LOG.info("CLAIMS COUNT ::::::: {} ", claimCount);
        LOG.info("****************************************************************");
        return claimCount;
    }
    
    @Override
    public int saveBulkProcessRequest(Date fromDate, Date toDate, String modelType, Long regionalOfficeNumber, String userId, Long recordCount) {
    	final String insertSql = "INSERT INTO BCDSS.BULK_PROCESS_REQUEST (" +
								 "REQUEST_DATE, FROM_DATE, TO_DATE, MODEL_TYPE, RO_NUMBER, CRTD_BY, REQUEST_STATUS, RECORD_COUNT) values (?,?,?,?,?,?,?,?)";
    	String formatFromDate="";
    	String formatToDate="";
    	
    	if(fromDate != null && toDate != null){
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    		formatFromDate= sdf.format(fromDate);
            formatToDate= sdf.format(toDate);
    	}else{
    		formatFromDate = null;
    		formatToDate = null;
    	}
    	
    	if (modelType == null || modelType == "") {
    		modelType = null;
    	}
    	
    	if (regionalOfficeNumber == 0) {
    		regionalOfficeNumber = null;
    	}
    	
    	LOG.info("Save Bulk Claims Params Query -------- " + insertSql);
    	Object[] params = new Object[] { new Date(), formatFromDate, formatToDate, modelType, regionalOfficeNumber, userId, "Pending", recordCount };
    	int[] types = new int[] { Types.DATE, Types.DATE, Types.DATE, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.NUMERIC };
        int row = jdbcTemplate.update(insertSql, params, types);
        LOG.info("****************************************************************");
        LOG.info("BULK PROCESS CLAIMS REQUEST PARAMS SAVED ::::::: {} ", row);
        LOG.info("****************************************************************");
        return row;
    }
    
    @Override
    public List<EditModelPatternResults> getDDMPatternInfo(long patternId) {
        List<EditModelPatternResults> patterns = jdbcTemplate.query(QueryConstants.DDM_PATTERN_DETAILS, new Object[]{patternId, patternId}, new BeanPropertyRowMapper<>(EditModelPatternResults.class));
        LOG.info("****************************************************************");
        for (EditModelPatternResults pattern : patterns) {
            LOG.info(pattern.toString());
        }
        LOG.info("****************************************************************");
        return patterns;
    }
    
    public int createEditModelPattern(Long patternId, Double accuracy, Long cdd, Long patternIndexNumber, String createdBy, Date createdDate,
    									int ctlgId, String modelType) {
    	
    	final String insertSql = "INSERT INTO BCDSS.DDM_MODEL_PATTERN_INDX (PATTERN_ID, ACCURACY, CDD, PATTERN_INDX_NUMBER, CRTD_BY, CRTD_DTM, CTLG_ID, MODEL_TYPE) VALUES (?,?,?,?,?,?,?,?)";
    	
    	LOG.info("Edit Model Insert Query -------- " + insertSql);
    	Object[] params = new Object[] { patternId, accuracy, cdd, patternIndexNumber, createdBy, new Date(), ctlgId, modelType };
    	int[] types = new int[] { Types.NUMERIC, Types.DOUBLE, Types.NUMERIC, Types.NUMERIC, Types.VARCHAR, Types.DATE, Types.NUMERIC, Types.VARCHAR };
        int row = jdbcTemplate.update(insertSql, params, types);
        LOG.info("****************************************************************");
        LOG.info("EDIT MODEL WITH NEW CATEGORY ID SAVED ::::::: {} ", row);
        LOG.info("****************************************************************");
        return row;
    }
    
    @Override
    public List<ModelRatingAggregateResult> getModelRatingAggregateCounts(){
    	StringBuilder SQL = new StringBuilder();
    	SQL.append("SELECT  DISTINCT R.MODEL_TYPE, COUNT(DISTINCT S.CRTD_BY) AS UserCount, COUNT(DISTINCT R.CLAIM_ID) AS ClaimsCount, ");
    	SQL.append(" COUNT(DISTINCT R.PATTERN_ID) AS PatternsCount,  COUNT(DISTINCT R.PROCESS_DATE) AS SessionsCount, ");
    	SQL.append(" SUM(DISTINCT I.ACCURACY)/COUNT(DISTINCT R.PATTERN_ID) AS AvgStatedAccuracy ");
    	SQL.append(" FROM BCDSS.MODEL_RATING_RESULTS R, BCDSS.MODEL_RATING_RESULTS_STATUS S, BCDSS.DDM_MODEL_PATTERN_INDX I WHERE "); 
    	SQL.append(" R.PATTERN_ID IS NOT NULL AND R.PROCESS_ID = S.PROCESS_ID AND R.PATTERN_ID = I.PATTERN_ID GROUP BY R.MODEL_TYPE");
    	
    	LOG.info("Aggregate Counts Query -------- " + SQL.toString());
    	List<ModelRatingAggregateResult> results = jdbcTemplate.query(SQL.toString(), new BeanPropertyRowMapper<>(ModelRatingAggregateResult.class));
    	
    	LOG.info("****************************************************************");
        for (ModelRatingAggregateResult result : results) {
            LOG.info("result patterns count::" + result.getPatternsCount());
        }
        LOG.info("****************************************************************");
        
        return results;
    }
}
