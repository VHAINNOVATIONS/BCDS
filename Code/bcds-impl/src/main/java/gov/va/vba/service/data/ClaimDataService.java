package gov.va.vba.service.data;

import gov.va.vba.bcdss.models.ClaimRating;
import gov.va.vba.bcdss.models.Rating;
import gov.va.vba.bcdss.models.RatingDecisions;
import gov.va.vba.bcdss.models.VeteranClaim;
import gov.va.vba.bcdss.models.VeteranClaimRating;
import gov.va.vba.domain.Claim;
import gov.va.vba.domain.CustomBCDSSException;
import gov.va.vba.domain.ModelRatingPattern;
import gov.va.vba.domain.ServerRequestStatus;
import gov.va.vba.domain.util.ModelPatternIndex;
import gov.va.vba.persistence.common.ModelType;
import gov.va.vba.persistence.entity.DDMModelPattern;
import gov.va.vba.persistence.entity.DDMModelPatternIndex;
import gov.va.vba.persistence.entity.EditModelPatternResults;
import gov.va.vba.persistence.entity.ModelRatingResults;
import gov.va.vba.persistence.entity.ModelRatingResultsCntnt;
import gov.va.vba.persistence.entity.ModelRatingResultsCntntId;
import gov.va.vba.persistence.entity.ModelRatingResultsDiag;
import gov.va.vba.persistence.entity.ModelRatingResultsDiagId;
import gov.va.vba.persistence.entity.ModelRatingResultsStatus;
import gov.va.vba.persistence.entity.ModelRatingResultsStatusId;
import gov.va.vba.persistence.entity.RatingDecision;
import gov.va.vba.persistence.entity.Veteran;
import gov.va.vba.persistence.entity.BulkProcessRequest;
import gov.va.vba.persistence.models.data.ClaimDetails;
import gov.va.vba.persistence.models.data.ContentionDetails;
import gov.va.vba.persistence.models.data.DecisionDetails;
import gov.va.vba.persistence.models.data.DiagnosisCount;
import gov.va.vba.persistence.repository.ClaimRepository;
import gov.va.vba.persistence.repository.DDMContentionRepository;
import gov.va.vba.persistence.repository.EarDao;
import gov.va.vba.persistence.repository.ModelRatingResultsCntnRepository;
import gov.va.vba.persistence.repository.ModelRatingResultsDiagRepository;
import gov.va.vba.persistence.repository.ModelRatingResultsRepository;
import gov.va.vba.persistence.repository.ModelRatingResultsStatusRepository;
import gov.va.vba.persistence.repository.RatingDao;
import gov.va.vba.persistence.repository.RatingDecisionRepository;
import gov.va.vba.persistence.repository.VeteranRepository;
import gov.va.vba.service.AppUtill;
import gov.va.vba.service.EarService;
import gov.va.vba.service.KneeService;
import gov.va.vba.service.common.Error;
import gov.va.vba.service.exception.BusinessException;
import gov.va.vba.service.orika.ClaimMapper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.maven.wagon.authentication.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClaimDataService extends AbsDataService<gov.va.vba.persistence.entity.Claim, Claim> {

    private static final Logger LOG = LoggerFactory.getLogger(ClaimDataService.class);
    private static final List<Long> KNEE_CONTENTION_IDS = Arrays.asList(230L, 270L, 3690L, 3700L, 3710L, 8919L, 3720L, 3730L, 3780L, 3790L, 3800L);
    private static final List<Long> EAR_CONTENTION_IDS = Arrays.asList(2200L, 2210L, 3140L, 3150L, 4130L, 4210L, 4700L, 4920L, 5000L, 5010L, 5710L, 6850L);
    private static final List<String> KNEE_DIAGNOSIS_CODES = Arrays.asList("5055", "5161", "5162", "5163", "5164", "5165", "5256", "5257", "5258", "5259", "5260", "5261", "5262", "5263", "5264", "5313", "5314", "5315");
    private static final List<String> EAR_DIAGNOSIS_CODES = Arrays.asList("6100", "6200", "6210", "6202", "6204", "6205", "6207", "6209", "6201", "6211", "6260");

    @Autowired
    private ClaimRepository claimRepository;
    
    @Autowired
    private RatingDecisionRepository ratingDecisionRepository;

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private ModelRatingResultsRepository modelRatingResultsRepository;

    @Autowired
    private ModelRatingResultsCntnRepository modelRatingResultsCntnRepository;

    @Autowired
    private ModelRatingResultsDiagRepository modelRatingResultsDiagRepository;

    @Autowired
    private ModelRatingResultsStatusRepository modelRatingResultsStatusRepository;

    @Autowired
    private EarService earService;

    @Autowired
    private KneeService kneeService;

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private EarDao earDao;

    @Autowired
    private DDMDataService ddmDataService;

    @Autowired
    private DDMModelCntntService ddmModelCntntService;

    @Autowired
    private DDMModelDiagService ddmModelDiagService;

    public ClaimDataService() {
        this.setClasses(gov.va.vba.persistence.entity.Claim.class, Claim.class);
    }

    public List<Claim> findAll() {
        List<Claim> output = new ArrayList<>();
        List<gov.va.vba.persistence.entity.Claim> input = ((ClaimRepository) repository).findAll();
        mapper.mapAsCollection(input, output, outputClass);
        return output;
    }

    public List<Claim> findFirstNumberedRow() {
        List<ClaimDetails> claims = ratingDao.getClaims();
        LOG.info("SIZE of input :::: " + claims.size());
        return mapKneeClaimsToClaims(claims);
    }

    public List<VeteranClaimRating> findByVeteranId(List<VeteranClaim> veteranClaims) throws CustomBCDSSException, BusinessException {
        List<VeteranClaimRating> veteranClaimRatings = new ArrayList<>();
        String currentLogin = null;
        //try{
        for (VeteranClaim vc : veteranClaims) {

            VeteranClaimRating veteranClaimRating = new VeteranClaimRating();
            veteranClaimRating.setVeteran(vc.getVeteran());
            int veteranId = vc.getVeteran().getVeteranId();
            if(null!=vc.getUserId()){
            	currentLogin = vc.getUserId();
            }
            List<gov.va.vba.bcdss.models.Claim> claims = vc.getClaim();
            List<ClaimRating> claimRatings = new ArrayList<>();
            for (gov.va.vba.bcdss.models.Claim c : claims) {
                int claimId = c.getClaimId();
                LOG.info("CONTENTION CODE IS ::: " + c.getContentionId());
                if(c.getContentionId() == 0) {
                    continue;
                }
                ContentionDetails contentionDetails = ratingDao.getContention(NumberUtils.toInt(c.getContentionClassificationId()));
                String modelType = contentionDetails.getModelType();
                ModelRatingResults results = null;
                List<ClaimDetails> claimDetails = new ArrayList<>();
                if (modelType.equalsIgnoreCase(ModelType.EAR.name())) {
                    claimDetails = earDao.getClaims(veteranId, claimId);
                    if(CollectionUtils.isEmpty(claimDetails)) {
                        throw new BusinessException(Error.ER_1001, String.valueOf(veteranId), String.valueOf(claimId));
                    }
                    results = earService.processClaims(veteranId, claimDetails, currentLogin);
                } else if (modelType.equalsIgnoreCase(ModelType.KNEE.name())) {
                    claimDetails = ratingDao.getPreviousClaims(veteranId, claimId);
                    if(CollectionUtils.isEmpty(claimDetails)) {
                        throw new BusinessException(Error.ER_1001, String.valueOf(veteranId), String.valueOf(claimId));
                    }
                    results = kneeService.processClaims(veteranId, claimDetails, currentLogin);
                }

                if (results != null) {
                    //Added code for Process Id Sequence generation
                    List<Long> maxprocessId = ratingDao.getProcessIDSeq();
                    LOG.debug("******************************");
                    long processIdSeq = maxprocessId.get(0);
                    LOG.info("MAX ProcessId -------- " + processIdSeq);

                    Set<Long> claimsList = new HashSet<>();
                    Map<Long, Integer> contentionCounts = new HashMap<>();
                    for (ClaimDetails details : claimDetails) {
                        claimsList.add(details.getClaimId());
                        if (contentionCounts.containsKey(details.getContentionClassificationId())) {
                            int value = contentionCounts.get(details.getContentionClassificationId());
                            contentionCounts.put(details.getContentionClassificationId(), value + 1);
                        } else {
                            contentionCounts.put(details.getContentionClassificationId(), 1);
                        }
                    }
                    LOG.info("Contention count :::::: " + contentionCounts);
                    

                    //setting ProcessId as Max Sequence generated+1
                    results.setProcessId(processIdSeq + 1);
                    results.setClaimCount((long) claimsList.size());
                    results.setContentionCount((long) contentionCounts.keySet().size());

                    ModelRatingResults savedResults = modelRatingResultsRepository.save(results);
                    BigDecimal processId = BigDecimal.valueOf(savedResults.getProcessId());

                    LOG.info("=================================================================");
                    LOG.info("RESULTS :::::::::::::::: " + savedResults);
                    LOG.info("=================================================================");
                    List<DiagnosisCount> diagnosisCount;
                    List<String> diagnosisCodes;
                    List<Long> contentions;
                    if(modelType.equalsIgnoreCase("EAR")) {
                        diagnosisCodes = EAR_DIAGNOSIS_CODES;
                        contentions = EAR_CONTENTION_IDS;
                        diagnosisCount = ratingDao.getEarDiagnosisCount((long) veteranId, savedResults.getClaimDate());
                    } else {
                        diagnosisCodes = KNEE_DIAGNOSIS_CODES;
                        contentions = KNEE_CONTENTION_IDS;
                        diagnosisCount = ratingDao.getDiagnosisCount((long) veteranId, savedResults.getClaimDate());
                    }
                    LOG.info("Diagnosis count :::::: " + diagnosisCount);

                    saveModelResultsCtnts(contentionCounts, savedResults, contentions);
                    saveModelResultsDiag(savedResults, diagnosisCount, diagnosisCodes);
                    saveResultStatus(results, currentLogin);
                    //ddmModelCntntService.getPatternId(results.getModelType(), )

                    /*EarDecision ed = new EarDecision();
                    KneeDecision kd = new KneeDecision();*/
                    RatingDecisions ratingDecisions = new RatingDecisions();
                    ratingDecisions.setProcessId(processId.intValue());
                   /* ratingDecisions.setEarRatings(ed);
                    ratingDecisions.setKneeRatings(kd);*/
                    gov.va.vba.bcdss.models.Rating rating = new Rating();
                    rating.setCddAge(savedResults.getClaimantAge().intValue());
                    rating.setClaimAge(savedResults.getClaimantAge().intValue());
                    rating.setClaimantAge(savedResults.getClaimantAge().intValue());
                    rating.setClaimCount(savedResults.getClaimCount().intValue());
                    //rating.setContationCount(contentionsCount);
                    rating.setCurrentCdd(savedResults.getCurrentCDD().intValue());
                    rating.setRaterEvaluation(savedResults.getCurrentCDD().intValue());
                    rating.setPriorCdd(results.getPriorCDD().intValue());
                    rating.setProcessId(processId.intValue());
                    //rating.setQuantCdd(calculatedCdd.intValue());
                    rating.setRatingDecisions(ratingDecisions);
                    rating.setModelType(savedResults.getModelType());
                    
                    rating.setProcessDate(savedResults.getClaimDate());
                    c.setClaimDate(savedResults.getClaimDate());
                        /*c.setProfileDate(claim.getProfileDate());*/
                    //c.setContentionClassificationId(String.valueOf(kneeClaim.getContentionClsfcnId()));
                    //c.setContentionId(Integer.parseInt(kneeClaim.getContentionId()));
                    //c.setProductTypeCode(kneeClaim.getEndPrdctTypeCode());
                    // c.setContentionBeginDate(kneeClaim.getContentionBeginDate());
                    ClaimRating cr = new ClaimRating();
                    cr.setClaim(c);
                    cr.setRating(rating);
                    claimRatings.add(cr);
                    
                    //TODO: last param
                    List<DDMModelPattern> patterns = ddmDataService.getPatternId(results.getModelType(), results.getClaimantAge(), results.getClaimCount(), (long) contentionCounts.keySet().size(), 0L);
                    List<Long> diagPattren = new ArrayList<>();
                    List<Long> cntntPattrens = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(patterns)) {
                        List<Long> patternsList = patterns.stream().map(DDMModelPattern::getPatternId).collect(Collectors.toList());
                        if(MapUtils.isNotEmpty(contentionCounts)){
                        	 LOG.info("Contention count :::::: " + contentionCounts);
                        	cntntPattrens = ddmModelCntntService.getKneePatternId(contentionCounts, patternsList, modelType.toUpperCase());
                        }
                        if (CollectionUtils.isNotEmpty(cntntPattrens)) {
                            //List<Long> diagPatternsList = patterns.stream().map(DDMModelPattern::getPatternId).collect(Collectors.toList());
                        	if (CollectionUtils.isNotEmpty(diagnosisCount)) {
                        		LOG.info("Diagnosis count :::::: " + diagnosisCount);
                        		diagPattren = ddmModelDiagService.getKneePatternId(diagnosisCount, cntntPattrens, modelType.toUpperCase());
                        	}
                            LOG.info("PATTREN SIZE :::::: " + diagPattren);
                            if (CollectionUtils.isNotEmpty(diagPattren)) {
                                Long pattrenId = diagPattren.get(0);
                                results.setPatternId(pattrenId);
                                LOG.info("PATTREN ID :: " + pattrenId);
                                results = modelRatingResultsRepository.save(results);
                            }
                        }
                    }
                   
                    if (results.getPatternId() != null) {
                        List<DDMModelPatternIndex> accuracy = ratingDao.getPatternAccuracy(results.getPatternId());
                        if (CollectionUtils.isNotEmpty(accuracy)) {
                            DDMModelPatternIndex ddmModelPatternIndex = accuracy.get(0);
                            LOG.info("PATTREN INDEX :: PATTERN {} AND ACCURACY {}", ddmModelPatternIndex.getPatternId(), ddmModelPatternIndex.getAccuracy());
                            if (ddmModelPatternIndex.getAccuracy() != null) {
                                rating.setAccuracy(ddmModelPatternIndex.getAccuracy().intValue());
                            }
                            if (ddmModelPatternIndex.getPatternIndexNumber() != null) {
                                rating.setRateOfUse(ddmModelPatternIndex.getPatternIndexNumber().intValue());
                            }
                            if (ddmModelPatternIndex.getCDD() != null) {
                                rating.setQuantCdd(ddmModelPatternIndex.getCDD().intValue());
                            }
                        }
                            rating.setPatternId(results.getPatternId().intValue());
                    }
                }
            }
            veteranClaimRating.getClaimRating().addAll(claimRatings);
            veteranClaimRatings.add(veteranClaimRating);
        }
       /*}catch(CustomBCDSSException e){
    	   LOG.info("Exception Caught :::::: " + e);
    	   e.getMessage();
    	   System.err.println("Application Exception. Please contact the administrator: " + e.getMessage());
       }*/
        return veteranClaimRatings;
    }

    /**
     * Description: This method is used to save contentions count
     *  @param counts  - claimId
     * @param results - results
     * @param contentions
     */
    private List<ModelRatingResultsCntnt> saveModelResultsCtnts(Map<Long, Integer> counts, ModelRatingResults results, List<Long> contentions) {
        //TODO:
        //List<Object[]> contentionsCount = claimRepository.aggregateContentions(claimId, veteranId);
        HashMap<Long, Long> countMap = new HashMap<>();
        for (Long i : contentions) {
            countMap.put(i, 0L);
        }
        for (Map.Entry<Long, Integer> x : counts.entrySet()) {
            if (countMap.containsKey(x.getKey())) {
                countMap.put(x.getKey(), (long) x.getValue());
            }
        }
        List<ModelRatingResultsCntnt> cntntList = new ArrayList<>();
        for (Map.Entry<Long, Long> x : countMap.entrySet()) {
            ModelRatingResultsCntntId cntntId = new ModelRatingResultsCntntId(x.getKey(), results.getProcessId());
            ModelRatingResultsCntnt cntnt = new ModelRatingResultsCntnt();
            cntnt.setId(cntntId);
            cntnt.setCount(BigDecimal.valueOf(x.getValue()));
            cntnt.setModelRatingResults(results);
            cntntList.add(cntnt);
        }
        modelRatingResultsCntnRepository.save(cntntList);
        LOG.info("SUCCESSFULLY SAVED CONTENTION COUNT INTO MODEL_RATING_RESULTS_CNTNT TABLE");
        return cntntList;
    }

    /**
     * Description:
     *  @param results
     * @param diagnosisCount
     * @param diagnosisCodes
     */
    private void saveModelResultsDiag(ModelRatingResults results, List<DiagnosisCount> diagnosisCount, List<String> diagnosisCodes) {
        HashMap<String, Long> diagCount = new HashMap<>();
        for (String i : diagnosisCodes) {
            diagCount.put(i, 0L);
        }
        for (DiagnosisCount x : diagnosisCount) {
            String diagCode = x.getDecisionCode();
            int count = x.getCount();
            if (diagCount.containsKey(diagCode)) {
                diagCount.put(diagCode, (long) count);
            }
        }
        List<ModelRatingResultsDiag> diagList = new ArrayList<>();
        for (Map.Entry<String, Long> x : diagCount.entrySet()) {
            ModelRatingResultsDiagId diagId = new ModelRatingResultsDiagId(Long.valueOf(x.getKey()), results.getProcessId());
            ModelRatingResultsDiag diag = new ModelRatingResultsDiag();
            diag.setId(diagId);
            diag.setCount(x.getValue());
            diag.setModelRatingResults(results);
            diagList.add(diag);
        }
        modelRatingResultsDiagRepository.save(diagList);
        LOG.info("SUCCESSFULLY SAVED DIGANOSIS COUNT INTO MODEL_RATING_RESULTS_DIAG TABLE");

    }

    private void saveResultStatus(ModelRatingResults results, String user) {
        ModelRatingResultsStatusId id = new ModelRatingResultsStatusId();
        id.setProcessId(results.getProcessId());
        id.setProcessStatus("PENDING");
        ModelRatingResultsStatus status = new ModelRatingResultsStatus();
        status.setId(id);
        status.setModelRatingResults(results);
        status.setCrtdDtm(new Date());
        status.setCrtdBy(user);
        modelRatingResultsStatusRepository.save(status);
        LOG.info("SAVED MODEL RATING RESULTS STATUS SUCCESSFULLY");
    }

    public List<Claim> findClaims(String contentionType, Long regionalOfficeNumber, Date fromDate, Date toDate) {
        LOG.debug("REST request to get advance filtered Claims");
        List<ClaimDetails> input = new ArrayList<>();
        input = ratingDao.getClaimsByAllFilters(contentionType, regionalOfficeNumber, fromDate, toDate);

        LOG.info("SIZE :::: " + input.size());
        return mapFilteredClaims(input);

    }

    public List<Claim> getProcessClaimsResults(boolean establishedDate, Date fromDate, Date toDate, String contentionType, Long regionalOfficeNumber) {
        if (fromDate == null) {
            Calendar instance = Calendar.getInstance();
            instance.set(1900, 12, 31);
            fromDate = instance.getTime();
        }
        if (toDate == null) {
            toDate = new Date();
        }
        List<gov.va.vba.persistence.entity.Claim> result = claimRepository.findClaimSByRangeOnClaimDate(contentionType, regionalOfficeNumber, fromDate, toDate);
        List<Claim> output = new ArrayList<>();
        mapper.mapAsCollection(result, output, outputClass);
        return output;
    }

    public List<Claim> calculateContentions(Long claimId, Long veteranId) {
        List<Object[]> objects = claimRepository.aggregateContentions(claimId, veteranId);
        List<Date> previousClaims = ratingDecisionRepository.findPreviousClaims(21213L, new Date());
        return null;
    }

    private Date calculateCestDate(Date claimDate) {
        if (claimDate != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(claimDate);
            instance.add(Calendar.DAY_OF_MONTH, 10);
            return instance.getTime();
        }
        return null;
    }

    private Date calculateClaimDate(Date claimDate) {
        if (claimDate != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(claimDate);
            return instance.getTime();
        }
        return null;
    }

    private List<Claim> mapKneeClaimsToClaims(List<ClaimDetails> kneeClaims) {
        List<Claim> claims = new ArrayList<>();
        for (ClaimDetails kneeClaim : kneeClaims) {
            Claim c = new Claim();
            gov.va.vba.domain.util.Veteran v = new gov.va.vba.domain.util.Veteran();
            v.setVeteranId(kneeClaim.getVeteranId());
            c.setClaimDate(kneeClaim.getClaimDate());
            c.setClaimId(kneeClaim.getClaimId());
            c.setRegionalOfficeOfClaim(kneeClaim.getClaimROName());
            c.setContentionId(kneeClaim.getContentionId());
            //Added CNTN_CLSFCN_ID to map to request header
            c.setContentionClassificationId(kneeClaim.getContentionClassificationId());
            c.setContentionClaimTextKeyForModel(kneeClaim.getContentionClaimantText());
            c.setCestDate(calculateCestDate(kneeClaim.getClaimDate()));
            c.setVeteran(v);
            c.setModelType(kneeClaim.getModelType());
            claims.add(c);
        }
        return claims;
    }

    private List<Claim> mapFilteredClaims(List<ClaimDetails> filteredClaims) {
        List<Claim> claims = new ArrayList<>();
        for (ClaimDetails filteredClaim : filteredClaims) {
            Claim c = new Claim();
            gov.va.vba.domain.util.Veteran v = new gov.va.vba.domain.util.Veteran();
            v.setVeteranId(filteredClaim.getVeteranId());
            c.setVeteran(v);
            c.setClaimDate(filteredClaim.getClaimDate());
            c.setClaimId(filteredClaim.getClaimId());
            c.setContentionId(filteredClaim.getContentionId());
            c.setRegionalOfficeOfClaim(filteredClaim.getClaimROName());
            c.setContentionClaimTextKeyForModel(filteredClaim.getContentionClaimantText());
            c.setCestDate(calculateCestDate(filteredClaim.getClaimDate()));
            c.setContentionClassificationId(filteredClaim.getContentionClassificationId());
            c.setModelType(filteredClaim.getModelType());
            claims.add(c);
        }
        return claims;
    }
    
    public ServerRequestStatus prepareBulkProcessClaims( Date fromDate, Date toDate, String modelType, Long regionalOffice) {
        LOG.debug("REST request to prepare bulk process claims");
        Long claimCountToProcess = ratingDao.getClaimCountToProcess(fromDate, toDate, modelType, regionalOffice);
        LOG.info("claimCountToProcess :::: " + claimCountToProcess);
        ServerRequestStatus requestStatus = new ServerRequestStatus();
        if(claimCountToProcess >= 0){
        	requestStatus.setStatus("success");
        	requestStatus.setRecordCount(claimCountToProcess);
        }else{
        	requestStatus.setStatus("failed");;
        	requestStatus.setReason("Could not extract count to process.");
        	requestStatus.setError("please check with your admininstrator.");
        }
        return requestStatus;
    }
    
    public ServerRequestStatus saveBulkProcessParams( Date fromDate, Date toDate, String modelType, Long regionalOffice, Long count, String userId) {
    	ServerRequestStatus requestStatus = new ServerRequestStatus();
    	try{
	        LOG.debug("REST request to save bulk process params");
	        int requestSaved = ratingDao.saveBulkProcessRequest(fromDate, toDate, modelType, regionalOffice, userId, count);
	        LOG.info("SAVED BULK PROCESS CLAIM REQUEST SUCCESSFULLY");
	        if(requestSaved == 1){
	        	requestStatus.setStatus("success");
	        }else{
	        	requestStatus.setStatus("failed");;
	        	requestStatus.setReason("insert failed.");
	        	requestStatus.setError("please check with your admininstrator.");
	        }
	        
	        return requestStatus;
    	}
    	catch(Exception e){
    		requestStatus.setStatus("failed");;
        	requestStatus.setReason(e.getMessage());
        	requestStatus.setError(e.getStackTrace().toString());
    		return requestStatus;
    	}
    }
    
    public EditModelPatternResults findModelRatingPatternInfo(Long patternId) throws CustomBCDSSException{
    	List<EditModelPatternResults> results = null;
    	EditModelPatternResults patterns = new EditModelPatternResults();
    	try{
			if(patternId == null) {
				return null;
			}
			//Get the patternId from the request and query the DDM Index table
			results = ratingDao.getDDMPatternInfo(patternId);
			if(results!=null && results.size() > 0){
				patterns = mapEditModelResults(results);
			}else{
				throw new CustomBCDSSException("Pattern not found with pattern ID : " + patternId);
			}
    	}catch(CustomBCDSSException e){
    		e.printStackTrace();
    	}
    	return patterns;	
	}
    
    public String updateModelRatingPatternInfo(Long patternId, Double accuracy, Long cdd, Long patternIndexNumber, String createdBy, Date createdDate, 
    											int ctlgId, String modelType) throws CustomBCDSSException {
    	String editResponse="";
    	try{
			int newPatternCategory = ratingDao.createEditModelPattern(patternId, accuracy, cdd, patternIndexNumber, createdBy, createdDate, ctlgId+1, modelType);
			if(newPatternCategory==1){
				editResponse="Edit Model with new Category is saved.";
			}else{
				editResponse = "Failed";
			}
			
    	}catch(CustomBCDSSException e){
    		 System.err.println("Pattern ID not found: " + e.getMessage());
    	}
    	return editResponse;	
	}
    
    private EditModelPatternResults mapEditModelResults(List<EditModelPatternResults> patternResults) {
    	EditModelPatternResults patterns = new EditModelPatternResults();
        for (EditModelPatternResults editModelPatternResults : patternResults) {
            patterns.setPatternId(editModelPatternResults.getPatternId());
            patterns.setAccuracy(editModelPatternResults.getAccuracy());
            patterns.setCategoryId(editModelPatternResults.getCategoryId());
            patterns.setCDD(editModelPatternResults.getCDD());
            patterns.setModelType(editModelPatternResults.getModelType());
            patterns.setCreatedBy(editModelPatternResults.getCreatedBy());
            patterns.setCreatedDate(editModelPatternResults.getCreatedDate());
            patterns.setPatternIndexNumber(editModelPatternResults.getPatternIndexNumber());
            
        }
        return patterns;
    }
}
