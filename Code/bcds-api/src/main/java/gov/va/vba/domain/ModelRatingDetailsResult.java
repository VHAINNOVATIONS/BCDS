package gov.va.vba.domain;

import java.util.List;

public class ModelRatingDetailsResult {
	public List<ModelRatingResults> modelRatingResults;
	public List<ModelRatingResultsDiag> diagnosticCodes;
	public List<ModelRatingResultsStatus> resultsStatus;
	public List<ModelRatingAggregateResult> aggregateReport;
}
