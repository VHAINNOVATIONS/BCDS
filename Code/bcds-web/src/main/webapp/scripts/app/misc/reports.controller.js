'use strict';

angular.module('bcdssApp').controller('ReportsController', function($rootScope, $scope, $state, Account,
														$q, $filter, DTOptionsBuilder, DTColumnBuilder, $compile, 	
														$stateParams, ClaimService, RatingService, spinnerService) {
	
	$scope.results = [];
	$scope.diagnosticCodes = [];
	$scope.modelRatingResultsStatus = [];
	$scope.resultDetailsData = [];
	$scope.resultAggregateData = [];
	$scope.fromDate = null;
    $scope.toDate = null;
	$scope.filters = {
		fromDate: null,
		toDate: null
	};
	$scope.dtDetailsInstance = {};
	$scope.dtAggregateInstance = {};
	$scope.processIds = [];
	
	$scope.modelTypeOptions = [
		{ value:'knee',	label:'Knee'},
	    { value:'ear',	label:'Ear'}
	];

	$scope.reportTypeOptions = [
		//{ value:'AGGREGATE', label:'Aggregate'}, //no longer there for now - 1/17/2017
	    { value:'DETAILED',	label:'Detailed'}
	];

	/*$scope.outputTypeOptions = [
		{ value:'HTML',	label:'HTML'},
	    { value:'PDF',	label:'PDF'}
	];*/

	$scope.dtDetailsOptions = DTOptionsBuilder.fromFnPromise(function() {
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
   .withOption('scrollX', true)
   .withOption('scrollY', '40vh')
   .withOption('processing', true)
   .withBootstrap()
   .withOption('bLengthChange', false)
   .withDOM('Bfrtip')
   .withButtons([
            {
                extend: 'pdf',
                text:  '<a name="DowloadPDF" id="btnDowloadPDF">Dowload PDF</a>',
                title: "Detailed Analysis Report",
            	orientation: 'landscape',
            	pageSize: 'LEGAL',
                exportOptions: {
                    columns: ':visible'
                },
                init: function(dt, node, config) {
			        $("#reportType").on('change', function() {
			            config.title = this.selectedOptions[0].label + " Analysis Report";
			        })
			    }
            }
    ]);

    $scope.dtDetailsColumns = [
		//DTColumnBuilder.newColumn('userId').withTitle('User Id').notSortable(),
		DTColumnBuilder.newColumn('processDate').withTitle('Session Date').notSortable().renderWith(function(data, type, full) {
	        return "<div>" +$scope.formatDate(data)+ "</div>"
	    }),
	    DTColumnBuilder.newColumn('veteran.veteranId').withTitle('Veteran Id').notSortable(),
	    DTColumnBuilder.newColumn('processId').withTitle('Model Result Id').notSortable(),
	    DTColumnBuilder.newColumn('claimId').withTitle('Claim Id').notSortable(),
	    DTColumnBuilder.newColumn('claimDate').withTitle('Date Of Claim').notSortable().renderWith(function(data, type, full) {
	        return "<div>" +$scope.formatDate(data)+ "</div>"
	    }),
	    DTColumnBuilder.newColumn('modelType').withTitle('Model').notSortable(),
	    DTColumnBuilder.newColumn('modelType').withTitle('Contention').notSortable(),
	    DTColumnBuilder.newColumn(null).withTitle('Prior Relevant Diagonostic Codes').notSortable().renderWith(function(data, type, full) {
	            return "<div>"+$scope.getDiagonosticCodesByProcessId(full.processId)+"</div>"
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
	        return "<div>"+$scope.getModelRatingResultStatusByProcessId(full.processId)+"</div>"
	    }),
    ];

    $scope.dtAggregateOptions = DTOptionsBuilder.fromFnPromise(function() {
   	 return new Promise( function(resolve, reject){
            if ($scope.results)
              resolve($scope.results);
            else
              resolve([]);
          });
   })
   .withOption('filter', false)
   .withBootstrap()
   .withOption('createdRow', function(row, data, dataIndex) {
           // Recompiling so we can bind Angular directive to the DT
       $compile(angular.element(row).contents())($scope);
   }) 	
   .withDOM('Bfrtip')
   .withOption('bLengthChange', false)
   .withButtons([
            {
                extend: 'pdf',
                text:  '<a name="DowloadPDF" id="btnDowloadPDF">Dowload PDF</a>',
                title: "Detailed Analysis Report",
            	orientation: 'landscape',
            	pageSize: 'LEGAL',
                exportOptions: {
                    columns: ':visible'
                },
                init: function(dt, node, config) {
			        $("#reportType").on('change', function() {
			            config.title = this.selectedOptions[0].label + " Analysis Report";
			        })
			    }
            }
    ]);

   $scope.dtAggregateColumns = [
		DTColumnBuilder.newColumn(null).withTitle('Model').notSortable().renderWith(function(data, type, full) {
	        return "<div></div>"
	    }),
	    DTColumnBuilder.newColumn(null).withTitle('No. Of Users').notSortable(),
	    DTColumnBuilder.newColumn(null).withTitle('No. Of Sessions').notSortable(),
	    DTColumnBuilder.newColumn(null).withTitle('No. Of Claims').notSortable(),
	    DTColumnBuilder.newColumn(null).withTitle('No. Of Patterns').notSortable().renderWith(function(data, type, full) {
	        return "<div></div>"
	    }),
	    DTColumnBuilder.newColumn(null).withTitle('Avg Stated Accuracy').notSortable(),
	    DTColumnBuilder.newColumn(null).withTitle('Actual Resulting Accuracy').notSortable(),
	    DTColumnBuilder.newColumn(null).withTitle('% Agree').notSortable(),
	    DTColumnBuilder.newColumn(null).withTitle('Claims Rated').notSortable(),
	   	DTColumnBuilder.newColumn(null).withTitle('% Throughput').notSortable(),
    ];

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

        if(startDate != null && endDate != null){
        	var minutes = 1000*60;
            var hours = minutes*60;
            var days = hours*24;
	        var diffDays = Math.round((new Date(endDate) - new Date(startDate))/days);
			
			if(diffDays > 365){
				$scope.errMessage = 'Max date range can only be one year.';
	        	$scope.frmResultsSearchFilter.$invalid = true;
	        	return false;
			}
		}
    };

    $scope.checkReportTypeErr =function(){
    	$scope.errMessage = '';
        $scope.frmResultsSearchFilter.$invalid = false;
    	if($scope.filters && $scope.filters.reportTypeOption === null){
			$scope.errMessage = 'Report type must be selected.';
	        $scope.frmResultsSearchFilter.$invalid = true;
	        return false;
		}

		return true;
    };

    $scope.clear = function(){
    	$scope.results = [];
    	$scope.errMessage = '';
    	$scope.fromDate = null;
    	$scope.toDate = null;
    	($scope.filters) ? $scope.filters.modelTypeOption = null : $scope.filters = null;
    	($scope.filters) ? $scope.filters.modelResultId = null : $scope.filters = null;
    	($scope.filters) ? $scope.filters.reportTypeOption = null : $scope.filters = null;
    	($scope.filters) ? $scope.filters.outputTypeOption = null : $scope.filters = null;
    	$scope.displayResultsRatingDetailsTable  = false;
    	$scope.displayResultsRatingAggregateTable  = false;
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
		$scope.filters.reportTypeOption = ($scope.filters.reportTypeOption == null) ? null : $scope.filters.reportTypeOption;
		//$scope.filters.outputTypeOption = ($scope.filters.outputTypeOption == null) ? null : $scope.filters.outputTypeOption;
	};

	$scope.getResultDetailsDisplayStatus = function(){
		return ((($scope.results.modelRatingResults && $scope.results.modelRatingResults.length >= 0)
					|| $scope.results.modelRatingResults === null) 
					&& ($scope.filters && $scope.filters.reportTypeOption === "DETAILED"));
	};

	$scope.getResultAggregateDisplayStatus = function(){
		return ((($scope.results.modelRatingResults && $scope.results.modelRatingResults.length >= 0)
					|| $scope.results.modelRatingResults === null)
					&& ($scope.filters && $scope.filters.reportTypeOption === "AGGREGATE"));
	};

	$scope.searchRatingResults = function(){
		$scope.processIds = [];
		$scope.results = [];
		$scope.setSearchParameters();
		if(!$scope.checkReportTypeErr()) return;
		//$scope.processIds.push(1); //this is for test and needs to change..... it should come from process claims
    	//$scope.processIds.push(2);
    	//$scope.processIds.push(3);
    	spinnerService.show('reportsSpinner');
		RatingService.generateModelRatingResultsReport($scope.processIds, $scope.filters)
			.then(function(result){
				console.log('>>>successful');
				$scope.results = result.data;
				$scope.diagnosticCodes = result.data.diagnosticCodes;
				$scope.modelRatingResultsStatus = result.data.resultsStatus;
				$scope.displayResultsRatingDetailsTable = $scope.getResultDetailsDisplayStatus();
				$scope.displayResultsRatingAggregateTable = $scope.getResultAggregateDisplayStatus();
				var promise = new Promise( function(resolve, reject){
	                if ($scope.results && $scope.results.modelRatingResults)
	                  resolve($scope.results.modelRatingResults);
	                else
	                  resolve([]);
	              });
				spinnerService.hide('reportsSpinner');
				if($scope.filters && $scope.filters.reportTypeOption === "AGGREGATE"){
		    		$scope.dtAggregateInstance.changeData(function() {
		                return promise;
		            });
		    	}
		    	if($scope.filters && $scope.filters.reportTypeOption === "DETAILED"){
		    		$scope.dtDetailsInstance.changeData(function() {
		                return promise;
		            });
		    	}
		});
    };

    $scope.$watch('fromDate', function (newValue, oldValue, scope) {
        console.log("startDate:" + scope.fromDate);
        var formats = ['MM/DD/YYYY'];
       
        if (newValue === undefined || newValue === null || newValue === "") {
            return true;
        }
        
        return moment(newValue, formats, true).isValid();
    
    }, true);

    $scope.$watch('toDate', function (newValue, oldValue, scope) {
       	console.log("endDate:" + scope.toDate);
        var formats = ['MM/DD/YYYY'];
       
        if (newValue === undefined || newValue === null || newValue === "") {
            return true;
        }
        
        return moment(newValue, formats, true).isValid();
    
    }, true);
});
