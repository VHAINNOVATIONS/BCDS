package gov.va.vba.persistence.constants;

public class QueryConstants {

    public static String QUERY = "";

    public static final String CONTENTIONS_COUNT = "SELECT CASE " +
            "WHEN C.contentionClsfcnId = 230 THEN 'FEATURE_230' " +
            "WHEN C.contentionClsfcnId = 270 THEN 'FEATURE_270' " +
            "WHEN C.contentionClsfcnId = 3690 THEN 'FEATURE_3690' " +
            "WHEN C.contentionClsfcnId = 3700 THEN 'FEATURE_3700' " +
            "WHEN C.contentionClsfcnId = 3710 THEN 'FEATURE_3710' " +
            "WHEN C.contentionClsfcnId = 3720 THEN 'FEATURE_3720' " +
            "WHEN C.contentionClsfcnId = 3730 THEN 'FEATURE_3730' " +
            "WHEN C.contentionClsfcnId = 3780 THEN 'FEATURE_3780' " +
            "WHEN C.contentionClsfcnId = 3790 THEN 'FEATURE_3790' " +
            "WHEN C.contentionClsfcnId = 3800 THEN 'FEATURE_3800' " +
            "WHEN C.contentionClsfcnId = 8919 THEN 'FEATURE_8919' " +
            "ELSE 'OTHER_0' END AS GROUP_NAME, COUNT(*) FROM Claim C " +
            "WHERE C.claimId = ?1 AND C.veteran.veteranId = ?2 GROUP BY C.contentionClsfcnId";

    public static final String DECISION_DETAILS = "SELECT PTCPNT_VET_ID, PRFIL_DT, BEGIN_DT, END_DT, PRMLGN_DT, DGNSTC_TXT, DSBLTY_ID, DIAGNOSIS_CODE, HYPNTD_DGNSTC_TYPE_CD, PRCNT_NBR " +
            "FROM BCDSS.AH4929_RATING_DECISION " +
            "WHERE PTCPNT_VET_ID =?1 " +
            "AND BEGIN_DT IS NOT NULL " +
            "AND BEGIN_DT < PRMLGN_DT " +
            "AND (END_DT IS NULL OR END_DT >= PRMLGN_DT) " +
            "AND SYSTEM_TYPE_CD = 'C' " +
            "AND DSBLTY_DECN_TYPE_CD = 'SVCCONNCTED' " +
            "GROUP BY BEGIN_DT, END_DT, PRMLGN_DT, DGNSTC_TXT, DSBLTY_ID, DIAGNOSIS_CODE, HYPNTD_DGNSTC_TYPE_CD, PRCNT_NBR,  PTCPNT_VET_ID, PRFIL_DT " +
            "ORDER BY PTCPNT_VET_ID,PRFIL_DT,DSBLTY_ID,BEGIN_DT,DIAGNOSIS_CODE,PRCNT_NBR";

    public static final String CALCULATE_CDD = "SELECT (SUM(D.percentNumber)*(1 - (SUM(D.percentNumber)/ 100))) AS SUM_PERCENT FROM RatingDecision D " +
            "WHERE D.veteran.veteranId=?1 AND D.profileDate=?2 " +
            "AND D.dsbltyDecnTypeCd='SVCCONNCTED' AND " +
            "D.diagnosisCode IN (5055, 5161, 5162, 5163, 5164, 5165, 5256, 5257, 5258, 5259, 5260, 5261, 5262, 5263, 5264, 5313, 5314, 5315)";

    public static final String DIAGNOSIS_COUNT = "SELECT DIAGNOSIS_CODE AS decisionCode, COUNT(DIAGNOSIS_CODE) AS count FROM (SELECT DISTINCT RD.DIAGNOSIS_CODE, " +
            "PRFIL_DT FROM BCDSS.AH4929_RATING_DECISION RD WHERE RD.PTCPNT_VET_ID = ? " +
            "AND PRFIL_DT < ? " +
            "AND DIAGNOSIS_CODE IN  ('5055','5161','5162','5163','5164','5165','5256','5257','5258','5259','5260','5261','5262','5263','5264','5313','5314','5315') " +
            "AND RD.BEGIN_DT IS NOT NULL AND RD.BEGIN_DT < RD.PRMLGN_DT AND (RD.END_DT IS NULL OR RD.END_DT >= RD.PRMLGN_DT) " +
            "AND RD.SYSTEM_TYPE_CD = 'C' AND RD.DSBLTY_DECN_TYPE_CD IN ('SVCCONNCTED','1151GRANTED') " +
            "GROUP BY RD.DIAGNOSIS_CODE, PRFIL_DT ORDER BY DIAGNOSIS_CODE, PRFIL_DT) GROUP BY DIAGNOSIS_CODE";

    public static final String PROFILE_DATE_QUERY = "SELECT DISTINCT PTCPNT_VET_ID AS veteranId, BNFT_CLAIM_ID AS claimId, CNTNTN_CLSFCN_ID AS contentionClassificationId," +
            "PRFIL_DT AS profileDate, DATE_OF_CLAIM AS claimDate, CLAIM_RO_NAME AS claimROName, CNTNTN_CLMANT_TXT AS contentionClaimantText " +
            "FROM BCDSS.AH4929_RATING_CORP_CLAIM WHERE PTCPNT_VET_ID = ?1 AND  BNFT_CLAIM_ID = ?2 " +
            "AND CNTNTN_CLSFCN_ID IN (230,270,3690,3700,3710,8919,3720,3730,3780,3790,3800)";

    public static final String PREVIOUS_CLAIMS_QUERY = "SELECT DISTINCT PTCPNT_VET_ID AS veteranId, " +
            "BNFT_CLAIM_ID AS claimId, DATE_OF_CLAIM AS claimDate,  CLAIM_RO_NAME AS claimROName, CLAIM_RO_NUMBER AS claimRONumber," +
            "CNTNTN_CLSFCN_ID AS contentionClassificationId " +
            "FROM BCDSS.AH4929_RATING_CORP_CLAIM " +
            "WHERE PTCPNT_VET_ID = ? and PRFIL_DT <= (SELECT MAX(PRFIL_DT) FROM BCDSS.AH4929_RATING_CORP_CLAIM WHERE PTCPNT_VET_ID = ? AND " +
            "BNFT_CLAIM_ID = ? AND CNTNTN_CLSFCN_ID IN (230,270,3690,3700,3710,8919,3720,3730,3780,3790,3800)) " +
            "AND CNTNTN_CLSFCN_ID IN (230,270,3690,3700,3710,8919,3720,3730,3780,3790,3800)";

    public static final String DECISIONS_PERCENT_QUERY = "SELECT DIAGNOSIS_CODE AS decisionCode, MAX(BEGIN_DT) AS beginDate, " +
            "PRCNT_NBR AS percentNumber FROM BCDSS.AH4929_RATING_DECISION " +
            "WHERE  PTCPNT_VET_ID = ? AND PRFIL_DT LIKE (SELECT MIN(PRFIL_DT) FROM BCDSS.AH4929_RATING_DECISION WHERE  PRFIL_DT >= ? AND PTCPNT_VET_ID = ? " +
            "AND DIAGNOSIS_CODE IN  ('5055','5161','5162','5163','5164','5165','5256','5257','5258','5259','5260','5261','5262','5263','5264','5313','5314','5315') AND DSBLTY_DECN_TYPE_CD IN ('SVCCONNCTED','1151GRANTED')) " +
            "AND DIAGNOSIS_CODE IN  ('5055','5161','5162','5163','5164','5165','5256','5257','5258','5259','5260','5261','5262','5263','5264','5313','5314','5315') AND DSBLTY_DECN_TYPE_CD IN ('SVCCONNCTED','1151GRANTED')  GROUP BY DIAGNOSIS_CODE,BEGIN_DT,PRFIL_DT,PRCNT_NBR ORDER BY BEGIN_DT DESC ";
}
