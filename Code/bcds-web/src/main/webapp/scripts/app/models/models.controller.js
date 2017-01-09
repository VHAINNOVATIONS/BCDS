'use strict';

angular.module('bcdssApp').controller('ModelsController', function($rootScope, $scope, $state, Account,
														$q, $filter, DTOptionsBuilder, DTColumnBuilder, $compile, 	
														$stateParams, ModelService, $timeout) {

	$scope.results = [];
	$scope.modelRatingResultsStatus = [];
	$scope.resultDetailsData = [];
	$scope.dtInstance = {};
	$scope.displayModelPatternTable = false;
	$scope.onlyNumbers = /^\d+$/;
	$scope.displayInsertStatus = false;

	$scope.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
   	 return new Promise( function(resolve, reject){
            if ($scope.results)
              resolve($scope.results);
            else
              resolve([]);
          });
   })
   .withOption('filter', false)
   .withOption('createdRow', function(row, data, dataIndex) {
           // Recompiling so we can bind Angular directive to the DT
       $compile(angular.element(row).contents())($scope);
   })

    $scope.dtColumns = [
		DTColumnBuilder.newColumn('patternIndex.modelType').withTitle('Model Type').notSortable().renderWith(function(data, type, full) {
	        return "<div>"+data+"</div>"
	    }),
	    DTColumnBuilder.newColumn('patternIndex.patternId').withTitle('Pattern Id').notSortable(),
	    DTColumnBuilder.newColumn('patternIndex.accuracy').withTitle('Pattern Accuracy').notSortable(),
	    DTColumnBuilder.newColumn('createdDate').withTitle('Created Date').notSortable().renderWith(function(data, type, full) {
	        return "<div>"+$scope.formatDate(data)+"</div>"
	    }),
	    DTColumnBuilder.newColumn('createdBy').withTitle('Created/Updated By').notSortable(),
	    DTColumnBuilder.newColumn('patternIndex.cdd').withTitle('CDD').notSortable()
    ];

    $scope.formatDate = function(date) {
        var date = new Date(date);
        return date.getFullYear() + '-' +  
            ('0' + (date.getMonth()+1)).slice(-2) + '-' + 
            ('0' + date.getDate()).slice(-2);
    };

     $scope.checkValidationErrors = function(){
    	$scope.errMessageModal = '';
        $scope.frmEditCDD.$invalid = false;

        if($scope.Cdd > 100){
        	$scope.errMessageModal = 'Cdd value must be less than 100.';
          	$scope.frmEditCDD.$invalid = true;
          	return false;
        }

        if(!($scope.Cdd % 10) == 0 || $scope.Cdd == null || $scope.Cdd.length == 0){
        	$scope.errMessageModal = 'Cdd value must be a multiple of 10.';
          	$scope.frmEditCDD.$invalid = true;
          	return false;
        }

        return true;
    };

    $scope.clear = function() {
    	$scope.errMessageModal = null;
        $scope.Cdd = null;
    };

    $scope.createCddValue = function(){
    	$('#modelDialogCreateCdd').modal('show');
    };

    $scope.getResultDisplayStatus = function(){
		return (($scope.results && $scope.results.length >= 0) || $scope.results === null);
	};

	$scope.searchPatternResults = function(){
		$scope.results = [];
		
		ModelService.findModelPatternResults($scope.patternId)
			.then(function(result){
				console.log('>>>successful');
				$scope.results = result.data;
				$scope.displayModelPatternTable = $scope.getResultDisplayStatus();
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

    $scope.createPatternCdd = function(event){
    	var patternInfo = $scope.results[0];
		if(!$scope.checkValidationErrors()) {
			event.preventDefault();
			return false;
		}

		ModelService.createModelPatternCdd($scope.patternId, $scope.Cdd, patternInfo.patternIndex.accuracy, patternInfo.patternIndex.patternIndexNumber, 
											patternInfo.patternIndex.modelType, (patternInfo.categoryId+1), $scope.userId)
			.then(function(result){
				console.log('>>>successful');
				$scope.results = result.data;
				$scope.displayModelPatternTable = $scope.getResultDisplayStatus();
				var promise = new Promise( function(resolve, reject){
	                if ($scope.results)
	                  resolve($scope.results);
	                else
	                  resolve([]);
	            });

	    		$scope.dtInstance.changeData(function() {
	    			$('#modelDialogCreateCdd').modal('hide');
	    			$scope.displayInsertStatus = true;
	    			$timeout(function(){
			        	$scope.displayInsertStatus = false;
			       }, 3000);
	                return promise;
	            });
		});
    };
});