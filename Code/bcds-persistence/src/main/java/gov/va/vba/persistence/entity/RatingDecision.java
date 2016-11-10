package gov.va.vba.persistence.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ProSphere User on 11/6/2016.
 */
@Entity
@Table(schema = "BCDSS", name = "AH4929_RATING_DECISION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RatingDecision {

    private BigDecimal veteranId;
    private Date profileDate;
    private Date inactvDt;
    private String systemTypeCd;
    private BigDecimal ratingDecnId;
    private String poa;
    private Date prmlgnDt;
    private Date ratingDt;
    private String vbmsRInd;
    private String jrsdtnTxt;
    private Date ratingRcvdDt;
    private BigDecimal stationNumber;
    private String stationName;
    private String militarySvcPeriodCode;
    private String militarySvcPeriodDesc;
    private String ratingDecnTypeCd;
    private String ratingDecnTypeDesc;
    private String narrativeCodesheetInd;
    private String issueTxt;
    private String decnTxt;
    private String dsbltyDecnTypeCd;
    private String dsbltyDecnTypeDesc;
    private String dsbltyDecnBasisTypeCd;
    private String dsbltyDecnBasisTypeDesc;
    private String blatrlTypeCd;
    private String blatrlTypeDesc;
    private String diagnosisCode;
    private String diagnosisDescription;
    private BigDecimal dsbltyId;
    private Date dsbltyDt;
    private String dgnstcTxt;
    private BigDecimal relatdDsbltyId;
    private Date relatdDsbltyDt;
    private String relatdDgnstcTxt;
    private String fullDgnstcTxt;
    private String splmtlDecnTypeCd;
    private String hypntdDgnstcTypeCd;
    private String shedTypeCd;
    private String prgrphTypeCd;
    private String percentNumber;
    private String majorInd;
    private String combatInd;
    private String prevEvaltnInd;
    private Date beginDt;
    private Date endDt;
    private Date futureExamDt;
    private String staticInd;
    private String ancilyDecnTypeCd;
    private String ancilyDecnTypeDesc;
    private String cmptnyDecnTypeCd;
    private String cmptnyDecnTypeDesc;
    private String dentalSvcCnectdInd;
    private String indvdlUnemplDecnTypeCd;
    private String indvdlUnemplDecnTypeDesc;
    private String permntTotalDecnTypeCd;
    private String permntTotalDecnTypeDesc;
    private String scDeathDecnTypeCd;
    private String scDeathDecnTypeDesc;
    private String smpDecnTypeCd;
    private String smpDecnTypeDesc;
    private String speclPrvsnLawDecnTypeCd;
    private String speclPrvsnLawDecnTypeDesc;
    private String smcTypeCd;
    private String smcTypeDesc;
    private String hsptlSmcTypeCd;
    private String hsptlSmcTypeDesc;
    private String lossUseTypeCd;
    private String lossUseTypeDesc;
    private String antmclLossTypeCd;
    private String antmclLossTypeDesc;
    private String otherLossTypeCd;
    private String otherLossTypeDesc;
    private String smcPrgrphKeyCd;
    private String smcPrgrphTxt;

    @Column(name = "PTCPNT_VET_ID", nullable = false, precision = 22, scale = 0)
    public BigDecimal getVeteranId() {
        return this.veteranId;
    }

    public void setVeteranId(BigDecimal veteranId) {
        this.veteranId = veteranId;
    }

    @Column(name = "PRFIL_DT", nullable = false, length = 7)
    public Date getProfileDate() {
        return this.profileDate;
    }

    public void setProfileDate(Date profileDate) {
        this.profileDate = profileDate;
    }

    @Column(name = "INACTV_DT", length = 7)
    public Date getInactvDt() {
        return this.inactvDt;
    }

    public void setInactvDt(Date inactvDt) {
        this.inactvDt = inactvDt;
    }

    @Column(name = "SYSTEM_TYPE_CD", length = 12)
    public String getSystemTypeCd() {
        return this.systemTypeCd;
    }

    public void setSystemTypeCd(String systemTypeCd) {
        this.systemTypeCd = systemTypeCd;
    }

    @Id
    @Column(name = "RATING_DECN_ID", precision = 22, scale = 0)
    public BigDecimal getRatingDecnId() {
        return this.ratingDecnId;
    }

    public void setRatingDecnId(BigDecimal ratingDecnId) {
        this.ratingDecnId = ratingDecnId;
    }

    @Column(name = "POA", length = 200)
    public String getPoa() {
        return this.poa;
    }

    public void setPoa(String poa) {
        this.poa = poa;
    }

    @Column(name = "PRMLGN_DT", length = 7)
    public Date getPrmlgnDt() {
        return this.prmlgnDt;
    }

    public void setPrmlgnDt(Date prmlgnDt) {
        this.prmlgnDt = prmlgnDt;
    }

    @Column(name = "RATING_DT", length = 7)
    public Date getRatingDt() {
        return this.ratingDt;
    }

    public void setRatingDt(Date ratingDt) {
        this.ratingDt = ratingDt;
    }

    @Column(name = "VBMS_R_IND", length = 1)
    public String getVbmsRInd() {
        return this.vbmsRInd;
    }

    public void setVbmsRInd(String vbmsRInd) {
        this.vbmsRInd = vbmsRInd;
    }

    @Column(name = "JRSDTN_TXT", length = 50)
    public String getJrsdtnTxt() {
        return this.jrsdtnTxt;
    }

    public void setJrsdtnTxt(String jrsdtnTxt) {
        this.jrsdtnTxt = jrsdtnTxt;
    }

    @Column(name = "RATING_RCVD_DT", length = 7)
    public Date getRatingRcvdDt() {
        return this.ratingRcvdDt;
    }

    public void setRatingRcvdDt(Date ratingRcvdDt) {
        this.ratingRcvdDt = ratingRcvdDt;
    }

    @Column(name = "STATION_NUMBER", nullable = false, precision = 22, scale = 0)
    public BigDecimal getStationNumber() {
        return this.stationNumber;
    }

    public void setStationNumber(BigDecimal stationNumber) {
        this.stationNumber = stationNumber;
    }

    @Column(name = "STATION_NAME", nullable = false, length = 100)
    public String getStationName() {
        return this.stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Column(name = "MILITARY_SVC_PERIOD_CODE", length = 12)
    public String getMilitarySvcPeriodCode() {
        return this.militarySvcPeriodCode;
    }

    public void setMilitarySvcPeriodCode(String militarySvcPeriodCode) {
        this.militarySvcPeriodCode = militarySvcPeriodCode;
    }

    @Column(name = "MILITARY_SVC_PERIOD_DESC", length = 250)
    public String getMilitarySvcPeriodDesc() {
        return this.militarySvcPeriodDesc;
    }

    public void setMilitarySvcPeriodDesc(String militarySvcPeriodDesc) {
        this.militarySvcPeriodDesc = militarySvcPeriodDesc;
    }

    @Column(name = "RATING_DECN_TYPE_CD", length = 15)
    public String getRatingDecnTypeCd() {
        return this.ratingDecnTypeCd;
    }

    public void setRatingDecnTypeCd(String ratingDecnTypeCd) {
        this.ratingDecnTypeCd = ratingDecnTypeCd;
    }

    @Column(name = "RATING_DECN_TYPE_DESC", length = 50)
    public String getRatingDecnTypeDesc() {
        return this.ratingDecnTypeDesc;
    }

    public void setRatingDecnTypeDesc(String ratingDecnTypeDesc) {
        this.ratingDecnTypeDesc = ratingDecnTypeDesc;
    }

    @Column(name = "NARRATIVE_CODESHEET_IND", length = 1)
    public String getNarrativeCodesheetInd() {
        return this.narrativeCodesheetInd;
    }

    public void setNarrativeCodesheetInd(String narrativeCodesheetInd) {
        this.narrativeCodesheetInd = narrativeCodesheetInd;
    }

    @Column(name = "ISSUE_TXT", length = 2048)
    public String getIssueTxt() {
        return this.issueTxt;
    }

    public void setIssueTxt(String issueTxt) {
        this.issueTxt = issueTxt;
    }

    @Column(name = "DECN_TXT", length = 2048)
    public String getDecnTxt() {
        return this.decnTxt;
    }

    public void setDecnTxt(String decnTxt) {
        this.decnTxt = decnTxt;
    }

    @Column(name = "DSBLTY_DECN_TYPE_CD", length = 12)
    public String getDsbltyDecnTypeCd() {
        return this.dsbltyDecnTypeCd;
    }

    public void setDsbltyDecnTypeCd(String dsbltyDecnTypeCd) {
        this.dsbltyDecnTypeCd = dsbltyDecnTypeCd;
    }

    @Column(name = "DSBLTY_DECN_TYPE_DESC", length = 50)
    public String getDsbltyDecnTypeDesc() {
        return this.dsbltyDecnTypeDesc;
    }

    public void setDsbltyDecnTypeDesc(String dsbltyDecnTypeDesc) {
        this.dsbltyDecnTypeDesc = dsbltyDecnTypeDesc;
    }

    @Column(name = "DSBLTY_DECN_BASIS_TYPE_CD", length = 12)
    public String getDsbltyDecnBasisTypeCd() {
        return this.dsbltyDecnBasisTypeCd;
    }

    public void setDsbltyDecnBasisTypeCd(String dsbltyDecnBasisTypeCd) {
        this.dsbltyDecnBasisTypeCd = dsbltyDecnBasisTypeCd;
    }

    @Column(name = "DSBLTY_DECN_BASIS_TYPE_DESC", length = 50)
    public String getDsbltyDecnBasisTypeDesc() {
        return this.dsbltyDecnBasisTypeDesc;
    }

    public void setDsbltyDecnBasisTypeDesc(String dsbltyDecnBasisTypeDesc) {
        this.dsbltyDecnBasisTypeDesc = dsbltyDecnBasisTypeDesc;
    }

    @Column(name = "BLATRL_TYPE_CD", length = 12)
    public String getBlatrlTypeCd() {
        return this.blatrlTypeCd;
    }

    public void setBlatrlTypeCd(String blatrlTypeCd) {
        this.blatrlTypeCd = blatrlTypeCd;
    }

    @Column(name = "BLATRL_TYPE_DESC", length = 50)
    public String getBlatrlTypeDesc() {
        return this.blatrlTypeDesc;
    }

    public void setBlatrlTypeDesc(String blatrlTypeDesc) {
        this.blatrlTypeDesc = blatrlTypeDesc;
    }

    @Column(name = "DIAGNOSIS_CODE", length = 12)
    public String getDiagnosisCode() {
        return this.diagnosisCode;
    }

    public void setDiagnosisCode(String diagnosisCode) {
        this.diagnosisCode = diagnosisCode;
    }

    @Column(name = "DIAGNOSIS_DESCRIPTION", length = 250)
    public String getDiagnosisDescription() {
        return this.diagnosisDescription;
    }

    public void setDiagnosisDescription(String diagnosisDescription) {
        this.diagnosisDescription = diagnosisDescription;
    }

    @Column(name = "DSBLTY_ID", precision = 22, scale = 0)
    public BigDecimal getDsbltyId() {
        return this.dsbltyId;
    }

    public void setDsbltyId(BigDecimal dsbltyId) {
        this.dsbltyId = dsbltyId;
    }

    @Column(name = "DSBLTY_DT", length = 7)
    public Date getDsbltyDt() {
        return this.dsbltyDt;
    }

    public void setDsbltyDt(Date dsbltyDt) {
        this.dsbltyDt = dsbltyDt;
    }

    @Column(name = "DGNSTC_TXT", length = 300)
    public String getDgnstcTxt() {
        return this.dgnstcTxt;
    }

    public void setDgnstcTxt(String dgnstcTxt) {
        this.dgnstcTxt = dgnstcTxt;
    }

    @Column(name = "RELATD_DSBLTY_ID", precision = 22, scale = 0)
    public BigDecimal getRelatdDsbltyId() {
        return this.relatdDsbltyId;
    }

    public void setRelatdDsbltyId(BigDecimal relatdDsbltyId) {
        this.relatdDsbltyId = relatdDsbltyId;
    }

    @Column(name = "RELATD_DSBLTY_DT", length = 7)
    public Date getRelatdDsbltyDt() {
        return this.relatdDsbltyDt;
    }

    public void setRelatdDsbltyDt(Date relatdDsbltyDt) {
        this.relatdDsbltyDt = relatdDsbltyDt;
    }

    @Column(name = "RELATD_DGNSTC_TXT", length = 300)
    public String getRelatdDgnstcTxt() {
        return this.relatdDgnstcTxt;
    }

    public void setRelatdDgnstcTxt(String relatdDgnstcTxt) {
        this.relatdDgnstcTxt = relatdDgnstcTxt;
    }

    @Column(name = "FULL_DGNSTC_TXT", length = 617)
    public String getFullDgnstcTxt() {
        return this.fullDgnstcTxt;
    }

    public void setFullDgnstcTxt(String fullDgnstcTxt) {
        this.fullDgnstcTxt = fullDgnstcTxt;
    }

    @Column(name = "SPLMTL_DECN_TYPE_CD", length = 12)
    public String getSplmtlDecnTypeCd() {
        return this.splmtlDecnTypeCd;
    }

    public void setSplmtlDecnTypeCd(String splmtlDecnTypeCd) {
        this.splmtlDecnTypeCd = splmtlDecnTypeCd;
    }

    @Column(name = "HYPNTD_DGNSTC_TYPE_CD", length = 12)
    public String getHypntdDgnstcTypeCd() {
        return this.hypntdDgnstcTypeCd;
    }

    public void setHypntdDgnstcTypeCd(String hypntdDgnstcTypeCd) {
        this.hypntdDgnstcTypeCd = hypntdDgnstcTypeCd;
    }

    @Column(name = "SHED_TYPE_CD", length = 12)
    public String getShedTypeCd() {
        return this.shedTypeCd;
    }

    public void setShedTypeCd(String shedTypeCd) {
        this.shedTypeCd = shedTypeCd;
    }

    @Column(name = "PRGRPH_TYPE_CD", length = 12)
    public String getPrgrphTypeCd() {
        return this.prgrphTypeCd;
    }

    public void setPrgrphTypeCd(String prgrphTypeCd) {
        this.prgrphTypeCd = prgrphTypeCd;
    }

    @Column(name = "PRCNT_NBR", length = 10)
    public String getPercentNumber() {
        return this.percentNumber;
    }

    public void setPercentNumber(String percentNumber) {
        this.percentNumber = percentNumber;
    }

    @Column(name = "MAJOR_IND", length = 1)
    public String getMajorInd() {
        return this.majorInd;
    }

    public void setMajorInd(String majorInd) {
        this.majorInd = majorInd;
    }

    @Column(name = "COMBAT_IND", length = 1)
    public String getCombatInd() {
        return this.combatInd;
    }

    public void setCombatInd(String combatInd) {
        this.combatInd = combatInd;
    }

    @Column(name = "PREV_EVALTN_IND", length = 1)
    public String getPrevEvaltnInd() {
        return this.prevEvaltnInd;
    }

    public void setPrevEvaltnInd(String prevEvaltnInd) {
        this.prevEvaltnInd = prevEvaltnInd;
    }

    @Column(name = "BEGIN_DT", length = 7)
    public Date getBeginDt() {
        return this.beginDt;
    }

    public void setBeginDt(Date beginDt) {
        this.beginDt = beginDt;
    }

    @Column(name = "END_DT", length = 7)
    public Date getEndDt() {
        return this.endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    @Column(name = "FUTURE_EXAM_DT", length = 7)
    public Date getFutureExamDt() {
        return this.futureExamDt;
    }

    public void setFutureExamDt(Date futureExamDt) {
        this.futureExamDt = futureExamDt;
    }

    @Column(name = "STATIC_IND", length = 1)
    public String getStaticInd() {
        return this.staticInd;
    }

    public void setStaticInd(String staticInd) {
        this.staticInd = staticInd;
    }

    @Column(name = "ANCILY_DECN_TYPE_CD", length = 12)
    public String getAncilyDecnTypeCd() {
        return this.ancilyDecnTypeCd;
    }

    public void setAncilyDecnTypeCd(String ancilyDecnTypeCd) {
        this.ancilyDecnTypeCd = ancilyDecnTypeCd;
    }

    @Column(name = "ANCILY_DECN_TYPE_DESC", length = 50)
    public String getAncilyDecnTypeDesc() {
        return this.ancilyDecnTypeDesc;
    }

    public void setAncilyDecnTypeDesc(String ancilyDecnTypeDesc) {
        this.ancilyDecnTypeDesc = ancilyDecnTypeDesc;
    }

    @Column(name = "CMPTNY_DECN_TYPE_CD", length = 12)
    public String getCmptnyDecnTypeCd() {
        return this.cmptnyDecnTypeCd;
    }

    public void setCmptnyDecnTypeCd(String cmptnyDecnTypeCd) {
        this.cmptnyDecnTypeCd = cmptnyDecnTypeCd;
    }

    @Column(name = "CMPTNY_DECN_TYPE_DESC", length = 50)
    public String getCmptnyDecnTypeDesc() {
        return this.cmptnyDecnTypeDesc;
    }

    public void setCmptnyDecnTypeDesc(String cmptnyDecnTypeDesc) {
        this.cmptnyDecnTypeDesc = cmptnyDecnTypeDesc;
    }

    @Column(name = "DENTAL_SVC_CNECTD_IND", length = 1)
    public String getDentalSvcCnectdInd() {
        return this.dentalSvcCnectdInd;
    }

    public void setDentalSvcCnectdInd(String dentalSvcCnectdInd) {
        this.dentalSvcCnectdInd = dentalSvcCnectdInd;
    }

    @Column(name = "INDVDL_UNEMPL_DECN_TYPE_CD", length = 12)
    public String getIndvdlUnemplDecnTypeCd() {
        return this.indvdlUnemplDecnTypeCd;
    }

    public void setIndvdlUnemplDecnTypeCd(String indvdlUnemplDecnTypeCd) {
        this.indvdlUnemplDecnTypeCd = indvdlUnemplDecnTypeCd;
    }

    @Column(name = "INDVDL_UNEMPL_DECN_TYPE_DESC", length = 50)
    public String getIndvdlUnemplDecnTypeDesc() {
        return this.indvdlUnemplDecnTypeDesc;
    }

    public void setIndvdlUnemplDecnTypeDesc(String indvdlUnemplDecnTypeDesc) {
        this.indvdlUnemplDecnTypeDesc = indvdlUnemplDecnTypeDesc;
    }

    @Column(name = "PERMNT_TOTAL_DECN_TYPE_CD", length = 12)
    public String getPermntTotalDecnTypeCd() {
        return this.permntTotalDecnTypeCd;
    }

    public void setPermntTotalDecnTypeCd(String permntTotalDecnTypeCd) {
        this.permntTotalDecnTypeCd = permntTotalDecnTypeCd;
    }

    @Column(name = "PERMNT_TOTAL_DECN_TYPE_DESC", length = 50)
    public String getPermntTotalDecnTypeDesc() {
        return this.permntTotalDecnTypeDesc;
    }

    public void setPermntTotalDecnTypeDesc(String permntTotalDecnTypeDesc) {
        this.permntTotalDecnTypeDesc = permntTotalDecnTypeDesc;
    }

    @Column(name = "SC_DEATH_DECN_TYPE_CD", length = 12)
    public String getScDeathDecnTypeCd() {
        return this.scDeathDecnTypeCd;
    }

    public void setScDeathDecnTypeCd(String scDeathDecnTypeCd) {
        this.scDeathDecnTypeCd = scDeathDecnTypeCd;
    }

    @Column(name = "SC_DEATH_DECN_TYPE_DESC", length = 50)
    public String getScDeathDecnTypeDesc() {
        return this.scDeathDecnTypeDesc;
    }

    public void setScDeathDecnTypeDesc(String scDeathDecnTypeDesc) {
        this.scDeathDecnTypeDesc = scDeathDecnTypeDesc;
    }

    @Column(name = "SMP_DECN_TYPE_CD", length = 12)
    public String getSmpDecnTypeCd() {
        return this.smpDecnTypeCd;
    }

    public void setSmpDecnTypeCd(String smpDecnTypeCd) {
        this.smpDecnTypeCd = smpDecnTypeCd;
    }

    @Column(name = "SMP_DECN_TYPE_DESC", length = 50)
    public String getSmpDecnTypeDesc() {
        return this.smpDecnTypeDesc;
    }

    public void setSmpDecnTypeDesc(String smpDecnTypeDesc) {
        this.smpDecnTypeDesc = smpDecnTypeDesc;
    }

    @Column(name = "SPECL_PRVSN_LAW_DECN_TYPE_CD", length = 12)
    public String getSpeclPrvsnLawDecnTypeCd() {
        return this.speclPrvsnLawDecnTypeCd;
    }

    public void setSpeclPrvsnLawDecnTypeCd(String speclPrvsnLawDecnTypeCd) {
        this.speclPrvsnLawDecnTypeCd = speclPrvsnLawDecnTypeCd;
    }

    @Column(name = "SPECL_PRVSN_LAW_DECN_TYPE_DESC", length = 50)
    public String getSpeclPrvsnLawDecnTypeDesc() {
        return this.speclPrvsnLawDecnTypeDesc;
    }

    public void setSpeclPrvsnLawDecnTypeDesc(String speclPrvsnLawDecnTypeDesc) {
        this.speclPrvsnLawDecnTypeDesc = speclPrvsnLawDecnTypeDesc;
    }

    @Column(name = "SMC_TYPE_CD", length = 12)
    public String getSmcTypeCd() {
        return this.smcTypeCd;
    }

    public void setSmcTypeCd(String smcTypeCd) {
        this.smcTypeCd = smcTypeCd;
    }

    @Column(name = "SMC_TYPE_DESC", length = 50)
    public String getSmcTypeDesc() {
        return this.smcTypeDesc;
    }

    public void setSmcTypeDesc(String smcTypeDesc) {
        this.smcTypeDesc = smcTypeDesc;
    }

    @Column(name = "HSPTL_SMC_TYPE_CD", length = 12)
    public String getHsptlSmcTypeCd() {
        return this.hsptlSmcTypeCd;
    }

    public void setHsptlSmcTypeCd(String hsptlSmcTypeCd) {
        this.hsptlSmcTypeCd = hsptlSmcTypeCd;
    }

    @Column(name = "HSPTL_SMC_TYPE_DESC", length = 50)
    public String getHsptlSmcTypeDesc() {
        return this.hsptlSmcTypeDesc;
    }

    public void setHsptlSmcTypeDesc(String hsptlSmcTypeDesc) {
        this.hsptlSmcTypeDesc = hsptlSmcTypeDesc;
    }

    @Column(name = "LOSS_USE_TYPE_CD", length = 12)
    public String getLossUseTypeCd() {
        return this.lossUseTypeCd;
    }

    public void setLossUseTypeCd(String lossUseTypeCd) {
        this.lossUseTypeCd = lossUseTypeCd;
    }

    @Column(name = "LOSS_USE_TYPE_DESC", length = 50)
    public String getLossUseTypeDesc() {
        return this.lossUseTypeDesc;
    }

    public void setLossUseTypeDesc(String lossUseTypeDesc) {
        this.lossUseTypeDesc = lossUseTypeDesc;
    }

    @Column(name = "ANTMCL_LOSS_TYPE_CD", length = 12)
    public String getAntmclLossTypeCd() {
        return this.antmclLossTypeCd;
    }

    public void setAntmclLossTypeCd(String antmclLossTypeCd) {
        this.antmclLossTypeCd = antmclLossTypeCd;
    }

    @Column(name = "ANTMCL_LOSS_TYPE_DESC", length = 50)
    public String getAntmclLossTypeDesc() {
        return this.antmclLossTypeDesc;
    }

    public void setAntmclLossTypeDesc(String antmclLossTypeDesc) {
        this.antmclLossTypeDesc = antmclLossTypeDesc;
    }

    @Column(name = "OTHER_LOSS_TYPE_CD", length = 12)
    public String getOtherLossTypeCd() {
        return this.otherLossTypeCd;
    }

    public void setOtherLossTypeCd(String otherLossTypeCd) {
        this.otherLossTypeCd = otherLossTypeCd;
    }

    @Column(name = "OTHER_LOSS_TYPE_DESC", length = 50)
    public String getOtherLossTypeDesc() {
        return this.otherLossTypeDesc;
    }

    public void setOtherLossTypeDesc(String otherLossTypeDesc) {
        this.otherLossTypeDesc = otherLossTypeDesc;
    }

    @Column(name = "SMC_PRGRPH_KEY_CD", length = 12)
    public String getSmcPrgrphKeyCd() {
        return this.smcPrgrphKeyCd;
    }

    public void setSmcPrgrphKeyCd(String smcPrgrphKeyCd) {
        this.smcPrgrphKeyCd = smcPrgrphKeyCd;
    }

    @Column(name = "SMC_PRGRPH_TXT", length = 2000)
    public String getSmcPrgrphTxt() {
        return this.smcPrgrphTxt;
    }

    public void setSmcPrgrphTxt(String smcPrgrphTxt) {
        this.smcPrgrphTxt = smcPrgrphTxt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
