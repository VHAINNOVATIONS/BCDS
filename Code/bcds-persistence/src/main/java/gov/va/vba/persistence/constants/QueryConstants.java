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
            "ELSE 'OTHER' END AS GROUP_NAME, COUNT(*) FROM Claim C " +
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
}
