'use strict';

angular.module('bcdssApp').controller('ResultsController', function($rootScope, $scope, $state, Account, Auth,
														$q, $filter, DTOptionsBuilder, DTColumnBuilder, $compile, 	
														$stateParams, ClaimService, RatingService) {
	
	$scope.userName = Auth.getCurrentUser();
	$scope.processedClaimsUserName = '';
	$scope.results = [];
	$scope.diagnosticCodes = [];
	$scope.modelRatingResultsStatus = [];
	$scope.resultDetailsData = [];
	$scope.updateDecisions = {};
	$scope.arrDecisions = [];
	$scope.filters = {
		fromDate: null,
		toDate: null
	};
	$scope.dtInstance = {};
	$scope.dtDetailsInstance = {};
	$scope.selected = {};
	$scope.processIds = [];
	$scope.selected = {};
    $scope.selectAll = false;
    $scope.isSelected = false;
    $scope.selectedDrpDecision = [];
    $scope.selectedDecisions = [
    	{ value:'0',	label:'Select a Decision'},
        { value:'Disagree',	label:'Disagree'},
	    { value:'Agree',	label:'Agree'}
	];

	$scope.columnTitles = [
        {columnName : "Veteran Id", title : "Unique Identifier for each Veteran/Customer"},
        {columnName : "Veteran Name", title : "Name of Veteran (Derived value for Pilot)"},
        {columnName : "Claim Id", title : "Unique Identifier for each claim filled by the Veteran/Customer"},
        {columnName : "Model", title : "The name and version of the model applied by the user"},
        {columnName : "Model Result Id", title : "Unique Identifier for the result generated by the model"},
        {columnName : "Prior Rating", title : "The Prior ating or CDD included in the matched patter"},
        {columnName : "Rater Evaluation", title : "The rating produced by the RVSR as a result of the manual adjudication process"},
        {columnName : "Model Results", title : "The rating generated by the model (i.e contained in the matched pattern)"},
        {columnName : "RE/MR Match?", title : "Indication as to whether the model rating is equal to the actual rating"},
        {columnName : "Pattern Rate of Use", title : "The number of times the matched pattern has occurred within last 8 years"},
        {columnName : "Pattern Accuracy", title : "The number of times the matched pattern has resulted in the same rating as a fraction of the number of timesit has occurred within the last 8 years"},
        {columnName : "Agree/Disagree", title : "Rater indicates if he/she agrees with the model output/result"},
    ];
     
    $scope.toggleOne = function toggleOne(selectedDecision, pId) {
    	$scope.arrDecisions = [];
    	if($scope.updateDecisions[pId]){
    		$scope.updateDecisions[pId] = selectedDecision;
    	}
    	else{
    		$scope.updateDecisions[pId] = selectedDecision;
    	}
    	
    	angular.forEach($scope.updateDecisions, function(d,p){
       		$scope.arrDecisions.push(d+"-"+p);
	    });
    }

	$scope.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
	   	 return new Promise( function(resolve, reject){
	            if ($scope.results)
	              resolve($scope.results);
	            else
	              resolve([]);
	          });
	   })
	 	.withBootstrap()
	 	.withOption('info', false)
	 	.withOption('bLengthChange', false)
	   	.withOption('createdRow', function(row, data, dataIndex) {
	    	// Recompiling so we can bind Angular directive to the DT        
	    	$compile(angular.element(row).contents())($scope);
	   })
	   	.withOption('headerCallback', function(header) {
	    	angular.forEach(header.cells, function(cell){
	       		$(cell).attr('title', function (index, attr) {
				    return $scope.setTableHeaderDescription(this.outerText);
				});
	    	}) 

	    	if (!self.headerCompiled) {
	           // Use this headerCompiled field to only compile header once
	           self.headerCompiled = true;
	           $compile(angular.element(header).contents())($scope);
	           $compile(angular.element('.dt-buttons').contents())($scope);
	       }
	   })
	   	.withOption('rowCallback', rowCallback);

	$scope.setTableHeaderDescription = function(columnName){
		 var header = $filter('filter')($scope.columnTitles, {columnName: columnName}, true);
	     if (header.length) {
	         return header[0].title;
	     } 
	     return columnName;
	};

	$scope.dtDetailsOptions = DTOptionsBuilder.fromFnPromise(function() {
   	 return new Promise( function(resolve, reject){
            if ($scope.resultDetailsData)
              resolve($scope.resultDetailsData);
            else
              resolve([]);
          });
   })
   .withOption('filter', false)
   .withOption('createdRow', function(row, data, dataIndex) {
           // Recompiling so we can bind Angular directive to the DT
       $compile(angular.element(row).contents())($scope);
   })
   .withOption('paging', false)
   .withOption('info', false)
   .withOption('bLengthChange', false)
   .withBootstrap()
   .withOption('rowCallback', rowCallback);
    
    /*These are changes for version 3.0*/
    $scope.dtDetailsColumns = [
		DTColumnBuilder.newColumn(null).withTitle('User Id').notSortable().renderWith(function(data, type, full) {
	            return "<div>"+$scope.processedClaimsUserName+"</div>"
	    }),
		DTColumnBuilder.newColumn('processDate').withTitle('Session Date').notSortable().renderWith(function(data, type, full) {
            return "<div>{{" + data +"| date:'yyyy-MM-dd'}} </div>"
        }),
	    DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Veteran Id').notSortable(),
	    DTColumnBuilder.newColumn('processId').withTitle('Model Result Id').notSortable(),
	    DTColumnBuilder.newColumn('claimId').withTitle('Claim Id').notSortable(),
	    DTColumnBuilder.newColumn('claimDate').withTitle('Date Of Claim').notSortable().renderWith(function(data, type, full) {
            return "<div>{{" + data +"| date:'yyyy-MM-dd'}} </div>"
        }),
	    DTColumnBuilder.newColumn('modelType').withTitle('Model').notSortable(),
	    DTColumnBuilder.newColumn('modelType').withTitle('Contention').notSortable(),
	    DTColumnBuilder.newColumn(null).withTitle('Prior Relevant Diagonostic Codes').notSortable().renderWith(function(data, type, full) {
	            return "<div>"+$scope.modelRatingDiagonosticCodes+"</div>"
	    }),
	    DTColumnBuilder.newColumn('priorCDD').withTitle('Prior Rating').notSortable(),
	    DTColumnBuilder.newColumn('cddage').withTitle('Prior Rating Age (Yr)').notSortable(),
	    DTColumnBuilder.newColumn('currentCDD').withTitle('Modeled Target Claim Rating').notSortable(),
	    DTColumnBuilder.newColumn('patternIndex.cdd').withTitle('Actual Target Claim Rating').notSortable(),
	    DTColumnBuilder.newColumn('patternIndex.patternIndexNumber').withTitle('Pattern Rate of Use').notSortable(),
	    DTColumnBuilder.newColumn('patternIndex.accuracy').withTitle('Pattern Accuracy Rate').notSortable().renderWith(function(data, type, full) {
	            return "<div>"+Math.round(data)+"%</div>"
	    }),
	    DTColumnBuilder.newColumn(null).withTitle('Agree/Disagree').notSortable().renderWith(function(data, type, full) {
            return "<div>"+$scope.modelRatingStatus+"</div>"
        }),
    ];

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        $('td', nRow).unbind('click');
        $('td', nRow).bind('click', function(e) {
            $scope.$apply(function() {
                $scope.rowClickHandler(e,aData);
            });
        });
       
        return nRow;
    }
    
    $scope.rowClickHandler= function(e,info) {
    	if($(e.target).hasClass('clickable')) {	
    		$('#gridPopUp').modal('show');
    		$scope.resultDetailsData = [];
    		$scope.reliability = '';
    		$scope.rateOfAccuracy = '';
    		$scope.contentionType = '';
    		$scope.modelRatingDiagonosticCodes = '';
    		$scope.modelRatingStatus = '';
    		$scope.resultDetailsData.push(info);
    		var promise = new Promise( function(resolve, reject) {
                if ($scope.resultDetailsData) {
                	resolve($scope.resultDetailsData);
                  	$scope.reliability =  $scope.setReliability($scope.resultDetailsData[0].patternIndex.accuracy) ;
    				$scope.rateOfAccuracy = Math.round($scope.resultDetailsData[0].patternIndex.accuracy);
    				$scope.contentionType = $scope.resultDetailsData[0].modelType;
    				$scope.claimCount = $scope.resultDetailsData[0].claimCount;
    				$scope.modelRatingDiagonosticCodes = $scope.getDiagonosticCodesByProcessId($scope.resultDetailsData[0].processId);
    				$scope.modelRatingStatus = $scope.getModelRatingResultStatusByProcessId($scope.resultDetailsData[0].processId);
    				$scope.processedClaimsUserName =  $scope.userName;//$scope.resultDetailsData[0].userId;
                }
                else
                  resolve([]);
            });

    		if($scope.resultDetailsData.length > 0)	{
    			$scope.dtDetailsInstance.changeData(function() {
                    return promise;
                });
			}
		}
    }

    $scope.setReliability = function(accuracy){
    	if(accuracy >= 99.50)
    		return 'EXTREMELY RELIABLE';
    	if(accuracy >= 98.5 && accuracy <= 99.49)
    		return 'VERY RELIABLE';
    	if(accuracy >= 90.00 && accuracy <= 98.49)
    		return 'SOMEWHAT RELIABLE';
    	if(accuracy <= 90.00)
    		return  'NOT RELIABLE';
    };
	
	$scope.getDiagonosticCodesByProcessId = function(processId){
		var codes = $filter('filter')($scope.diagnosticCodes, {processId: processId}, true);
	     if (codes.length) {
	     	var arrCodes = [];
	     	angular.forEach(codes, function(code){
	     		arrCodes.push(code.diagId);
	     	});
	    	
	    	return (arrCodes.sort()).join();
	     }

	     return '';
	};

	$scope.getModelRatingResultStatusByProcessId = function(processId){
		 var status = $filter('filter')($scope.modelRatingResultsStatus, {processId: processId}, true);
	     if (status && status.length) {
	     	return status[0].processStatus;
	     }

	     return '';
	};

	$scope.modelTypeOptions = [
		{ value:'knee',	label:'Knee'},
	    { value:'ear',	label:'Ear'}
	];

	$scope.processResults = function(resultsArray){
		var results = [];
		angular.forEach(resultsArray,function(ele,id){
			var obj = {};
			angular.forEach(ele.claimRating, function(claimrating,id){
				obj.veteran = ele.veteran;
				obj.claim = claimrating.claim;
				obj.rating = claimrating.rating;
				results.push(obj);
			});
		});
		return results;
	};

	$scope.formatDate = function(date) {
        var date = new Date(date);
        return date.getFullYear() + '-' +  
            ('0' + (date.getMonth()+1)).slice(-2) + '-' + 
            ('0' + date.getDate()).slice(-2);
    };
    
    $scope.checkErr = function(startDate,endDate) {
        $scope.errMessage = '';
        $scope.frmResultsSearchFilter.$invalid = false;
        if(new Date(startDate) > new Date(endDate)){
          $scope.errMessage = 'To date should be greater than from date.';
          $scope.frmResultsSearchFilter.$invalid = true;
          return false;
        }
    };

    $scope.clear = function(){
    	$scope.results = [];
    	$scope.errMessage = '';
    	$scope.fromDate = null;
    	$scope.toDate = null;
    	($scope.filters) ? $scope.filters.modelTypeOption = null : $scope.filters = null;
    	($scope.filters) ? $scope.filters.modelResultId = null : $scope.filters = null;
        $scope.cleanScopeVariables();
    };

     $scope.setSearchParameters = function(){
     	//case when no params or only model type
    	if(($scope.filters.modelResultId || $scope.filters.modelResultId == null) && $scope.fromDate == null && $scope.toDate == null){
    		var today = new Date();
    		$scope.filters.fromDate = $scope.formatDate(new Date());
    		$scope.filters.toDate = $scope.formatDate(new Date(today.getFullYear(), today.getMonth() + 12, today.getDate()));
   			$scope.processIds = [];
    	}

    	//case when only resultid
    	if(($scope.filters.modelResultId && $scope.filters.modelResultId != null) && $scope.fromDate == null && $scope.toDate == null){
    		$scope.filters.fromDate = null;
    		$scope.filters.toDate = null;
    		$scope.processIds.push($scope.filters.modelResultId);
    	}

    	//case when only dates and/or resultid/modeltype
    	if($scope.fromDate != null && $scope.toDate != null){
    		$scope.filters.fromDate = $scope.formatDate($scope.fromDate);
    		$scope.filters.toDate = $scope.formatDate($scope.toDate);
    		($scope.filters === null || $scope.filters.modelResultId === null || $scope.filters.modelResultId === undefined) 
    			? $scope.processIds = [] 
    			: $scope.processIds.push($scope.filters.modelResultId);
    	}

		$scope.filters.modelTypeOption = ($scope.filters.modelTypeOption == null) ? null : $scope.filters.modelTypeOption;
	};

	$scope.searchRatingResults = function(){
		$scope.processIds = [];
		$scope.setSearchParameters();
    	
		RatingService.findModelRatingResults($scope.processIds, $scope.filters, $scope.userName)
			.then(function(result){
				console.log('>>>successful');
				$scope.results = result.data;
				$scope.diagnosticCodes = result.data.diagnosticCodes;
				$scope.modelRatingResultsStatus = result.data.resultsStatus;
				$scope.displayResultsRatingTable = $scope.results.modelRatingResults.length >= 0;
				$scope.cleanScopeVariables();
				var promise = new Promise( function(resolve, reject){
	                if ($scope.results)
	                  resolve($scope.results.modelRatingResults);
	                else
	                  resolve([]);
	              });
	    		$scope.dtInstance.changeData(function() {
	                return promise;
	            });
		});
    };
	
	/*These are changes for version 3.0*/
	$scope.dtColumns = [
	        DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Veteran Id'),
	        DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Veteran Name').renderWith(function(data, type, full) {
	            return "<div>"+ data +"-veteran</div>"
	        }),
	        DTColumnBuilder.newColumn('claimId').withTitle('Claim Id'),
	        DTColumnBuilder.newColumn('modelType').withTitle('Model').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('processId').withTitle('Model Result Id').renderWith(function(data, type, full) {
	            return "<a href='#' class='clickable' style='cursor: pointer;' data-toggle='modal' data-target='gridPopUp'>" + data + "</a>"
	        }),
	        DTColumnBuilder.newColumn('priorCDD').withTitle('Prior Rating').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('currentCDD').withTitle('Rater Evaluation').renderWith(function(data, type, full) {
	        	$scope.modeledRating = data;
	            return "<div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('patternIndex.cdd').withTitle('Model Results').renderWith(function(data, type, full) {
	        	$scope.actualRating = data;
	        	return "<div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('currentCDD').withTitle('RE/MR Match?').renderWith(function(data, type, full) {
	        	if($scope.modeledRating === $scope.actualRating){
	        		return "<div><span class='glyphicon glyphicon-thumbs-up'></span></div>"
	        	}
	        	return "<div><span class='glyphicon glyphicon-thumbs-down'></span></div>"
	        }),
	        DTColumnBuilder.newColumn('patternIndex.patternIndexNumber').withTitle('Pattern Rate of Use').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('patternIndex.accuracy').withTitle('Pattern Accuracy').renderWith(function(data, type, full) {
	            return "<div>"+Math.round(data)+"%</div>"
	        }),
	        DTColumnBuilder.newColumn(null).withTitle('Status').renderWith(function(data, type, full) {
	        	return "<div>"+$scope.getModelRatingResultStatusByProcessId(full.processId)+"</div>"
	        }),
	        DTColumnBuilder.newColumn(null).withTitle('Agree/Disagree').notSortable().renderWith(function(data, type, full, meta) {
	        	var html = '<select class="drpDownDecisions" id="selectdrpdwn' + data.processId + '" ng-model="selectedDrpDecision[' + data.processId + ']" ng-change="toggleOne(this.selectedDrpDecision[' + data.processId + '], ' + data.processId + ')" ng-options="o.value as o.label for o in selectedDecisions"></select>';
	        	$scope.selectedDrpDecision[data.processId] = $scope.selectedDecisions[0].value;
	        	return html
	        })
	    ];

	    /*These are changes for version 2.0
	    }
	    $scope.dtDetailsColumns = [
			//DTColumnBuilder.newColumn('userId').withTitle('User Id').notSortable(),
			DTColumnBuilder.newColumn('rating.processDate').withTitle('Session Date').notSortable().renderWith(function(data, type, full) {
	            return "<div>{{" + data +"| date:'yyyy-MM-dd'}} </div>"
	        }),
		    DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Veteran Id').notSortable(),
		    DTColumnBuilder.newColumn('rating.processId').withTitle('Model Result Id').notSortable(),
		    DTColumnBuilder.newColumn('claim.claimId').withTitle('Claim Id').notSortable(),
		    DTColumnBuilder.newColumn('claim.claimDate').withTitle('Date Of Claim').notSortable().renderWith(function(data, type, full) {
	            return "<div>{{" + data +"| date:'yyyy-MM-dd'}} </div>"
	        }),
		    DTColumnBuilder.newColumn('rating.modelType').withTitle('Model').notSortable(),
		    DTColumnBuilder.newColumn('rating.modelType').withTitle('Contention').notSortable(),
		    DTColumnBuilder.newColumn('rating.priorCdd').withTitle('Prior Relevant Diagonostic Codes').notSortable(),
		    DTColumnBuilder.newColumn('rating.priorCdd').withTitle('Prior Rating').notSortable(),
		    DTColumnBuilder.newColumn('rating.cddAge').withTitle('Prior Rating Age (Yr)').notSortable(),
		    DTColumnBuilder.newColumn('rating.raterEvaluation').withTitle('Modeled Target Claim Rating').notSortable(),
		    DTColumnBuilder.newColumn('rating.quantCdd').withTitle('Actual Target Claim Rating').notSortable(),
		    DTColumnBuilder.newColumn('rating.rateOfUse').withTitle('Pattern Rate of Use').notSortable(),
		    DTColumnBuilder.newColumn('rating.accuracy').withTitle('Pattern Accuracy Rate').notSortable(),
		    DTColumnBuilder.newColumn(null).withTitle('Agree Y/N').notSortable().renderWith(function(data, type, full) {
	            return "<div>Yes/No</div>"
	        }),
	    ];

	    /*Below is version 2.0 changes
	    $scope.dtColumns = [
 	        DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Veteran Id'),
 	        DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Veteran Name').renderWith(function(data, type, full) {
 	            return "<div>"+ data +"-veteran</div>"
 	        }),
 	        DTColumnBuilder.newColumn('claim.claimId').withTitle('Claim Id'),
 	        DTColumnBuilder.newColumn('rating.modelType').withTitle('Model').renderWith(function(data, type, full) {
 	            return "<div>"+data+"</div>"
 	        }),
 	        DTColumnBuilder.newColumn('rating.processId').withTitle('Model Result Id').renderWith(function(data, type, full) {
 	           return "<a class='clickable' style='cursor: hand;'>" + data + "</a>"
 	        }),
 	        DTColumnBuilder.newColumn('rating.priorCdd').withTitle('Prior Rating').renderWith(function(data, type, full) {
  	            return "<div>"+data+"</div>"
  	        }),
  	        DTColumnBuilder.newColumn('rating.raterEvaluation').withTitle('Rater Evaluation').renderWith(function(data, type, full) {
 	           	$scope.modeledRating = data;
 	            return "<div>"+data+"</div>"
  	        }),
  	        DTColumnBuilder.newColumn('rating.quantCdd').withTitle('Model Results').renderWith(function(data, type, full) {
  	        	$scope.actualRating = data;
 	            return "<div>"+data+"</div>"
  	        }),
  	        DTColumnBuilder.newColumn('rating.currentCdd').withTitle('RE/MR Match?').renderWith(function(data, type, full) {
  	           	if($scope.modeledRating === $scope.actualRating){
	        		return "<div><span class='glyphicon glyphicon-thumbs-up'></span></div>"
	        	}
	        	return "<div><span class='glyphicon glyphicon-thumbs-down'></span></div>"
 	        }),
 	        DTColumnBuilder.newColumn('rating.rateOfUse').withTitle('Pattern Rate of Use').renderWith(function(data, type, full) {
 	             return "<div>"+data+"</div>"
 	        }),
 	        DTColumnBuilder.newColumn('rating.accuracy').withTitle('Pattern Accuracy').renderWith(function(data, type, full) {
 	             return "<div>"+data+"%</div>"
 	        }),
 	        DTColumnBuilder.newColumn(null).withTitle('Agree Y/N').notSortable().renderWith(function(data, type, full, meta) {
		     	//$scope.selected[full.processId] = false;
		     	return '<label for="selectchk' + data.processId + '" style="display: none">select</label><input id="selectchk' + data.processId + '" type="checkbox" ng-model="selected[' + data.processId + ']" ng-click="toggleOne(selected)">';
			})
 	    ];*/
	
	$scope.getProcessIds = function(results){
		var processIds = [];
    	//processIds.push(1); //this is for test and needs to change..... it should come from process claims
    	//processIds.push(2);
    	//processIds.push(3);
    	angular.forEach(results, function(result,idx){
    		processIds.push(result.rating.processId);
    	});
    	return processIds;
    };

	$scope.getRatingResults = function(results){
		$scope.processIds = $scope.getProcessIds(results);
		if($scope.processIds == null){
			alert("No processId found."); // need to change to validation message
			return;
		}
		$scope.filters = null;
		RatingService.findModelRatingResults($scope.processIds, $scope.filters, $scope.userName)
			.then(function(result){
				console.log('>>>successful');
				$scope.results = result.data;
				$scope.diagnosticCodes = result.data.diagnosticCodes;
				$scope.modelRatingResultsStatus = result.data.resultsStatus;
				$scope.displayResultsRatingTable = $scope.results.modelRatingResults.length >= 0;
				$scope.cleanScopeVariables();
				var promise = new Promise( function(resolve, reject){
	                if ($scope.results)
	                  resolve($scope.results.modelRatingResults);
	                else
	                  resolve([]);
	              });
	    		$scope.dtInstance.changeData(function() {
	                return promise;
	            });
		});
	};

	$scope.updateResultsDecisions = function(){
		RatingService.updateModelRatingResultsStatus($scope.processIds, $scope.arrDecisions, $scope.userName)
			.then(function(result){
				console.log('>>>successful');
				$scope.results = result.data;
				$scope.diagnosticCodes = result.data.diagnosticCodes;
				$scope.modelRatingResultsStatus = result.data.resultsStatus;
				$scope.displayResultsRatingTable = $scope.results.modelRatingResults.length > 0;
				$scope.cleanScopeVariables();
				var promise = new Promise( function(resolve, reject){
	                if ($scope.results)
	                  resolve($scope.results.modelRatingResults);
	                else
	                  resolve([]);
	              });
	    		$scope.dtInstance.changeData(function() {
	                return promise;
	            });
		});
	};

	$scope.cleanScopeVariables = function(){
		$scope.updateDecisions = {};
        $scope.arrDecisions = [];
	};

	/*version 3.0*/
	$rootScope.$on('ProcessClaims', function(event, data) {
		var inputObj = [];
		$scope.userName = Auth.getCurrentUser();
		angular.forEach(data,function(ele,idx){
			var obj = {
				 	"userId": $scope.userName,
   			      	"veteran": {
   			        "veteranId": ele.veteranId,
   			        "veteranName": null,
   			        "dob": null
   			      },
   			      "claim": [
   			        {
   			          "claimId": ele.claimId,
   			          "profileDate": null,
   			          "productTypeCode": null,
   			          "claimDate": null,
   			          "contentionId": ele.contentionId,
   			          "contentionClassificationId": null,
   			          "contentionBeginDate": null,
   			          "modelType": modelType
   			        }
   			      ]
   			    }
			inputObj.push(obj);
		});
		$scope.results = [];
		
		ClaimService.processClaims({},{
	  			"veteranClaimInput": inputObj
			},function(data){
			//data = {"veteranClaimRatingOutput":[{"veteran":{"veteranId":244390,"veteranName":null,"dob":null},"claimRating":[{"claim":{"claimId":5614193,"profileDate":1147665600000,"productTypeCode":"020","claimDate":1091073600000,"contentionId":2991274,"contentionClassificationId":"6850","contentionBeginDate":null},"rating":{"claimantAge":20,"promulgationDate":null,"recentDate":null,"contationCount":2,"priorCdd":64,"quantPriorCdd":0,"currentCdd":0,"claimAge":20,"cddAge":20,"claimCount":1,"processId":18380497,"patternId":0,"processDate":null,"modelType":null,"modelContentionCount":0,"quantCdd":80,"ratingDecisions":{"processId":18380497,"kneeRatings":{"contentionKnee":0,"contentionLeft":0,"contentionRight":0,"contentionBilateral":0,"contentionLeg":0,"contentionAmputation":0,"decisionKnee":0,"decisionLeft":0,"decisionRight":0,"decisionBilateral":0,"decisionLimitation":0,"decisionImpairment":0,"decisionAnkyloses":0,"decisionAmputation":0},"earRatings":{"contentionLoss":0,"contentionTinitu":0,"decisionLoss":0,"decisionTinitu":0}},"status":[],"diagnosisCodeCounts":[],"contentionsCodeCounts":[]}}]}]};
			var formattedResults = $scope.processResults(data.veteranClaimRatingOutput);
			//make api call using rating service to get rest of the column values and then populate results.
    		$scope.getRatingResults(formattedResults);
		});
	});
});