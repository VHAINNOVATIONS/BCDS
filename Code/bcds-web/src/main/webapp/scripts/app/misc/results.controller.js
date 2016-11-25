'use strict';

angular.module('bcdssApp').controller('ResultsController', function($rootScope, $scope, $state, Account,
														$q, DTOptionsBuilder, DTColumnBuilder, $compile, 	
														$stateParams, ClaimService, ClaimFilterService,ModelService) {
	
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
	
	 $scope.dtColumns = [
	        DTColumnBuilder.newColumn('veteranId').withTitle('Veteran ID'),
	        DTColumnBuilder.newColumn('claimId').withTitle('Veteran Name'),
	        DTColumnBuilder.newColumn('claimantAge').withTitle('Age'),
	        DTColumnBuilder.newColumn('priorCdd').withTitle('Prior CDD'),
	        DTColumnBuilder.newColumn('cddAge').withTitle('CDD'),
	        DTColumnBuilder.newColumn('contentionCount').withTitle('Contention Count'),
	        DTColumnBuilder.newColumn('modelType').withTitle('Model')
	    ];
	 
	 $rootScope.$on('ProcessClaims', function(event, data) {
		$scope.veteranId = data.veteranId;
		$scope.claimId = data.claimId;
		$scope.results = []
     	ModelService.post({},{
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
     				data.veteranId = $scope.veteranId;
     				data.claimId = $scope.claimId;
     				$scope.results.push(data);
     				
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
	 });
});