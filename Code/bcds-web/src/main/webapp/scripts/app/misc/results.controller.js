'use strict';

angular.module('bcdssApp').controller('ResultsController', function($rootScope, $scope, $state, Account,
														$q, DTOptionsBuilder, DTColumnBuilder, $compile, 	
														$stateParams, ClaimService, RatingService) {
	
	$scope.results = [];
	$scope.filters = [];
	$scope.dtInstance = {};
	$scope.processIds = [];

	$scope.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
	   	 return new Promise( function(resolve, reject){
	            if ($scope.results)
	              resolve($scope.results);
	            else
	              resolve([]);
	          });
	   })
	   .withOption('createdRow', function(row, data, dataIndex) {
	               // Recompiling so we can bind Angular directive to the DT
	       $compile(angular.element(row).contents())($scope);
	   })
	   .withOption('headerCallback', function(header) {
	       if (!self.headerCompiled) {
	           // Use this headerCompiled field to only compile header once
	           self.headerCompiled = true;
	           $compile(angular.element(header).contents())($scope);
	           $compile(angular.element('.dt-buttons').contents())($scope);
	       }
	   });
	
	$scope.modelTypeOptions = [
		{ value:'knee',	label:'Knee'},
	    { value:'ear',	label:'Ear'},
	    { value:'leg',	label:'Leg'},
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

    $scope.getProcessIds = function(){
    	$scope.processIds.push(1); //this is for test and needs to change..... it should come from process claims
    	return $scope.processIds;
    };

	$scope.getRatingResults = function(){
		var processIds = $scope.getProcessIds();
		if(processIds == null){
			alert("No processId found."); // need to change to validation message
			return;
		}
		$scope.filters = null;
		RatingService.findModelRatingResults(processIds, $scope.filters)
			.then(function(result){
				console.log('>>>successful');
				$scope.results = result;
				var promise = new Promise( function(resolve, reject){
	                if ($scope.results)
	                  resolve($scope.results);
	                else
	                  resolve([]);
	              });
	    		$scope.dtInstance.changeData(function() {
	                return promise;
	            });
		});
	};

	$scope.clear = function(){
    	$scope.results = [];
    	$scope.errMessage = '';
    	$scope.frmResultsSearchFilter.$invalid = false;
        $scope.fromDate = null;
        $scope.toDate = null;
        $scope.filters.modelTypeOption = null;
        $scope.filters.modelResultId = null;
    };

	$scope.searchRatingResults = function(){
    	if ($scope.filters != null) {
    		$scope.filters.fromDate = $scope.formatDate($scope.fromDate);
    		$scope.filters.toDate = $scope.formatDate($scope.toDate);
    	}
    	//return if error ?;
		RatingService.findModelRatingResults($scope.processIds, $scope.filters)
			.then(function(result){
				console.log('>>>successful');
				$scope.results = result;
				var promise = new Promise( function(resolve, reject){
	                if ($scope.results)
	                  resolve($scope.results);
	                else
	                  resolve([]);
	              });
	    		$scope.dtInstance.changeData(function() {
	                return promise;
	            });
		});
    };
	
	 $scope.dtColumns = [
	        DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Veteran ID'),
	        DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Veteran Name').renderWith(function(data, type, full) {
	            return "<div>"+ data +"veteran</div>"
	        }),
	        DTColumnBuilder.newColumn('claim.claimId').withTitle('Claim ID'),
	        DTColumnBuilder.newColumn('rating.modelType').withTitle('Model').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('rating.processId').withTitle('Model Result ID').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('rating.priorCdd').withTitle('Prior Rating').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('rating.currentCdd').withTitle('Rater Evaluation').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('rating.currentCdd').withTitle('Model Results').renderWith(function(data, type, full) {
	            return "<div></div>"
	        }),
	        DTColumnBuilder.newColumn('rating.currentCdd').withTitle('RE/MR Match').renderWith(function(data, type, full) {
	            return "<div></div>"
	        }),
	        DTColumnBuilder.newColumn('rating.currentCdd').withTitle('Pattern Rate of Use').renderWith(function(data, type, full) {
	            return "<div></div>"
	        }),
	        DTColumnBuilder.newColumn('rating.quantCdd').withTitle('Pattern Accuracy').renderWith(function(data, type, full) {
	            return "<div></div>"
	        })
	    ];
	 
	 $rootScope.$on('ProcessClaims', function(event, data) {
		var inputObj = [];
		angular.forEach(data,function(ele,idx){
			var obj = {
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
   			          "contentionId": 0,
   			          "contentionClassificationId": null,
   			          "contentionBeginDate": null
   			        }
   			      ]
   			    }
			inputObj.push(obj);
		});
		$scope.results = []
		ClaimService.processClaims({},{
     		  			"veteranClaimInput": inputObj
     				},function(data){
     				//data = {"veteranClaimRatingOutput":[{"veteran":{"veteranId":244390,"veteranName":null,"dob":null},"claimRating":[{"claim":{"claimId":5614193,"profileDate":1147665600000,"productTypeCode":"020","claimDate":1091073600000,"contentionId":2991274,"contentionClassificationId":"6850","contentionBeginDate":null},"rating":{"claimantAge":20,"promulgationDate":null,"recentDate":null,"contationCount":2,"priorCdd":64,"quantPriorCdd":0,"currentCdd":0,"claimAge":20,"cddAge":20,"claimCount":1,"processId":18380497,"patternId":0,"processDate":null,"modelType":null,"modelContentionCount":0,"quantCdd":80,"ratingDecisions":{"processId":18380497,"kneeRatings":{"contentionKnee":0,"contentionLeft":0,"contentionRight":0,"contentionBilateral":0,"contentionLeg":0,"contentionAmputation":0,"decisionKnee":0,"decisionLeft":0,"decisionRight":0,"decisionBilateral":0,"decisionLimitation":0,"decisionImpairment":0,"decisionAnkyloses":0,"decisionAmputation":0},"earRatings":{"contentionLoss":0,"contentionTinitu":0,"decisionLoss":0,"decisionTinitu":0}},"status":[],"diagnosisCodeCounts":[],"contentionsCodeCounts":[]}}]}]};
     				var formattedResults = $scope.processResults(data.veteranClaimRatingOutput);
     				$scope.results = formattedResults;
     				//make api call using rating service to get rest of the column values and then populate results.
     	    		var promise = new Promise( function(resolve, reject){
     	                if ($scope.results)
     	                {
       	                    resolve($scope.results);
     	                }
     	                else
     	                {
     	                	 resolve([]);
     	                }
     	                
     	              });
     	    		$scope.dtInstance.changeData(function() {
     	                return promise;
     	            });
     	    		
     			});
	 });
});