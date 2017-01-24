'use strict';

angular.module('bcdssApp').controller('ModelsController', function($rootScope, $scope, $state, Account, Auth,
														$q, $filter, DTOptionsBuilder, DTColumnBuilder, $compile, 	
														$stateParams, ModelService, $timeout, spinnerService) {

	$scope.results = [];
	$scope.serverErrorMsg = "Something went wrong! Please contact the site administrator."
	$scope.userName = Auth.getCurrentUser();
	$scope.modelRatingResultsStatus = [];
	$scope.resultDetailsData = [];
	$scope.dtInstance = {};
	$scope.displayModelPatternTable = false;
	$scope.onlyNumbers = /^\d+$/;
	$scope.displayInsertStatus = false;
	$scope.modal = {
      instance: null
    };

	$scope.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
   	 return new Promise( function(resolve, reject){
            if ($scope.results)
              resolve($scope.results);
            else
              resolve([]);
          });
   })
   .withOption('filter', false)
   .withOption('paging', false)
   .withOption('createdRow', function(row, data, dataIndex) {
           // Recompiling so we can bind Angular directive to the DT
       $compile(angular.element(row).contents())($scope);
   })

    $scope.dtColumns = [
		DTColumnBuilder.newColumn('patternIndex.modelType').withTitle('Model Type').notSortable().renderWith(function(data, type, full) {
	        return "<div>"+data+"</div>"
	    }),
	    DTColumnBuilder.newColumn('patternIndex.patternId').withTitle('Pattern Id').notSortable(),
	    DTColumnBuilder.newColumn('patternIndex.accuracy').withTitle('Pattern Accuracy').notSortable().renderWith(function(data, type, full) {
	        return "<div>"+data+"%</div>"
	    }),
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
        	$scope.errMessageModal = 'Cdd value must be in the multiple of 10.';
          	$scope.frmEditCDD.$invalid = true;
          	return false;
        }

        return true;
    };

    $scope.clear = function() {
    	$scope.errMessageModal = null;
        $scope.Cdd = null;
    };

    $scope.isValidPatternId = function(){
    	$scope.frmPatternSearch.$invalid = false;
    	if($scope.patternId == undefined || $scope.patternId == null || $scope.patternId.length === 0 || $scope.patternId == ''){
    		$scope.frmPatternSearch.$invalid = true;
    	}

    	if($scope.patternId == undefined || $scope.patternId == null || $scope.patternId.length > 9999999999){
    		$scope.frmPatternSearch.$invalid = true;
    	}
    };

    $scope.createCddValue = function(){
    	$('#modelDialogCreateCdd').modal('show');
    };

    $scope.getResultDisplayStatus = function(){
		return (($scope.results && $scope.results.length >= 0) || $scope.results === null);
	};

	$scope.searchPatternResults = function(){
		$scope.results = [];
		spinnerService.show('modelCddSpinner');
		ModelService.findModelPatternResults($scope.patternId)
			.then(function(result){
				console.log('>>>successful');
				$scope.results = result.data;
				$scope.displayModelPatternTable = $scope.getResultDisplayStatus();
				$scope.displayBtnOnDataAvailability = ($scope.results && $scope.results.length > 0);
				var promise = new Promise( function(resolve, reject){
	                if ($scope.results)
	                  resolve($scope.results);
	                else
	                  resolve([]);
	            });
				spinnerService.hide('modelCddSpinner');
	    		$scope.dtInstance.changeData(function() {
	                return promise;
	            });
			})
			.catch(function(e){
                $scope.serverErrorMsg = (e.errMessage && e.errMessage != null) ? e.errMessage : $scope.serverErrorMsg;
                $scope.callErrorDialog();
        	});
    };

    $scope.createPatternCdd = function(event){
    	var patternInfo = $scope.results[0];
		if(!$scope.checkValidationErrors()) {
			event.preventDefault();
			return false;
		}

		ModelService.createModelPatternCdd($scope.patternId, $scope.Cdd, patternInfo.patternIndex.accuracy, patternInfo.patternIndex.patternIndexNumber, 
											patternInfo.patternIndex.modelType, (patternInfo.categoryId+1), $scope.userName)
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
			})
			.catch(function(e){
                $scope.serverErrorMsg = (e.errMessage && e.errMessage != null) ? e.errMessage : $scope.serverErrorMsg;
                $scope.callErrorDialog();
        	});
    };

    $scope.callErrorDialog = function (size) {
            $scope.modal.instance = $modal.open({
            template: '<error-dialog modal="modal" bold-text-title="Error:" text-alert="'+ $scope.serverErrorMsg + '" mode="danger"></error-dialog>',
            scope: $scope,
        });
    };
});