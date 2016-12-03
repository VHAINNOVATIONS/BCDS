'use strict';

angular.module('bcdssApp').controller('ResultsController', function($rootScope, $scope, $state, Account,
														$q, DTOptionsBuilder, DTColumnBuilder, $compile, 	
														$stateParams, ClaimService) {
	
		$scope.results = [];
		$scope.dtInstance = {};
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
	}
	
	 $scope.dtColumns = [
	        DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Veteran ID'),
	        DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Veteran Name').renderWith(function(data, type, full) {
	            return "<div>"+ data +"veteran</div>"
	        }),
	        DTColumnBuilder.newColumn('claim.claimId').withTitle('Claim ID'),
	        DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Model').renderWith(function(data, type, full) {
	            return "<div>Knee</div><hr/><div>Ear</div>"
	        }),
	        DTColumnBuilder.newColumn('rating.ratingDecisions.processId').withTitle('Model Result ID').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div><hr/><div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('rating.priorCdd').withTitle('Prior Rating').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div><hr/><div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('rating.currentCdd').withTitle('Rater Evaluation').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div><hr/><div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('rating.currentCdd').withTitle('Model Results').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div><hr/><div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('rating.currentCdd').withTitle('RE/MR Match').renderWith(function(data, type, full) {
	            return '<div><span class="glyphicon glyphicon-thumbs-up"></span></div><hr/>'+
	                   '<div><span class="glyphicon glyphicon-thumbs-down"></span></div>'
	        }),
	        DTColumnBuilder.newColumn('rating.currentCdd').withTitle('Pattern Rate of Use').renderWith(function(data, type, full) {
	            return "<div>"+data+"</div><hr/><div>"+data+"</div>"
	        }),
	        DTColumnBuilder.newColumn('rating.quantCdd').withTitle('Pattern Accuracy').renderWith(function(data, type, full) {
	            return "<div>"+data+"%</div><hr/><div>"+data+"%</div>"
	        })
	    ];
	 
	 $rootScope.$on('ProcessClaims', function(event, data) {
		$scope.veteranId = data.veteranId;
		$scope.claimId = data.claimId;
		$scope.results = []
		ClaimService.processClaims({},{
     		  "veteranClaimInput": [
     			    {
     			      "veteran": {
     			        "veteranId": data.veteranId,
     			        "veteranName": null,
     			        "dob": null
     			      },
     			      "claim": [
     			        {
     			          "claimId": data.claimId,
     			          "profileDate": null,
     			          "productTypeCode": null,
     			          "claimDate": null,
     			          "contentionId": 0,
     			          "contentionClassificationId": null,
     			          "contentionBeginDate": null
     			        }
     			      ]
     			    }
     			  ]
     			},function(data){
     				data = {"veteranClaimRatingOutput":[{"veteran":{"veteranId":244390,"veteranName":null,"dob":null},"claimRating":[{"claim":{"claimId":5614193,"profileDate":1147665600000,"productTypeCode":"020","claimDate":1091073600000,"contentionId":2991274,"contentionClassificationId":"6850","contentionBeginDate":null},"rating":{"claimantAge":20,"promulgationDate":null,"recentDate":null,"contationCount":2,"priorCdd":64,"quantPriorCdd":0,"currentCdd":0,"claimAge":20,"cddAge":20,"claimCount":1,"processId":18380497,"patternId":0,"processDate":null,"modelType":null,"modelContentionCount":0,"quantCdd":80,"ratingDecisions":{"processId":18380497,"kneeRatings":{"contentionKnee":0,"contentionLeft":0,"contentionRight":0,"contentionBilateral":0,"contentionLeg":0,"contentionAmputation":0,"decisionKnee":0,"decisionLeft":0,"decisionRight":0,"decisionBilateral":0,"decisionLimitation":0,"decisionImpairment":0,"decisionAnkyloses":0,"decisionAmputation":0},"earRatings":{"contentionLoss":0,"contentionTinitu":0,"decisionLoss":0,"decisionTinitu":0}},"status":[],"diagnosisCodeCounts":[],"contentionsCodeCounts":[]}}]}]};
     				var formattedResults = $scope.processResults(data.veteranClaimRatingOutput);
     				$scope.results = formattedResults;
     				
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