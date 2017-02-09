'use strict';

angular.module('bcdssApp').controller('ReportsController', function($rootScope, $scope, $state, Account,
														$q, $filter, DTOptionsBuilder, DTColumnBuilder, $compile, $modal,	
														$stateParams, ClaimService, RatingService, spinnerService) {
	
  $scope.results = [];
	$scope.serverErrorMsg = "Something went wrong! Please contact the site administrator."
	$scope.diagnosticCodes = [];
	$scope.modelRatingResultsStatus = [];
	$scope.resultDetailsData = [];
	$scope.resultAggregateData = [];
  $scope.isReportTypeAggregate = false;
	$scope.maxDefaultDate = new Date('01/01/2100');
  $scope.minDefaultDate = new Date('01/01/1900');
	$scope.reportsFromDate = null;
  $scope.reportsToDate = null;
	$scope.filters = {
		reportsFromDate: null,
		reportsToDate: null
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
	    { value:'DETAILED',	label:'Detailed'},
      { value:'AGGREGATE', label:'Aggregate'}
	];

	$scope.modal = {
      instance: null
  };

  $scope.customColumns = ["User Id", "Process Date", "Model Result Id", "Claim Id", "Date Of Claim", "Contention"];

  $scope.dtDetailsOptions = DTOptionsBuilder.fromFnPromise(function() {
   	 return new Promise( function(resolve, reject){
            if ($scope.results)
              resolve($scope.results);
            else
              resolve([]);
          });
   })
   .withOption('filter', false)
   .withOption('headerCallback', function(header) {
        angular.forEach(header.cells, function(cell){
            $(cell).attr('title', function (index, attr) {
                return this.outerText;
            });
        });

        angular.forEach(header.cells, function(cell) {
            if($scope.customColumns.indexOf(cell.outerText) > -1) {
                $(cell).attr('style', 'vertical-align:top;width:70px;word-break:keep-all;');
            }else if(cell.outerText === "Prior Relevant Diagonostic Codes"){
                $(cell).attr('style', 'vertical-align:top;width:40px;word-break:keep-all;');
            }else {
                $(cell).attr('style', 'vertical-align:top;word-break:keep-all;');
            }
        });
    })
   .withOption('createdRow', function(row, data, dataIndex) {
        // Recompiling so we can bind Angular directive to the DT
       $compile(angular.element(row).contents())($scope);
       angular.forEach(row.cells, function(cell){
          $(cell).attr('style', 'word-break:keep-all;');
          $(cell).attr('title', function (index, attr) {
              return this.outerText;
          });
       }); 
   }) 	
   .withOption('processing', true)
   .withBootstrap()
   .withDOM('Bfrtip')
   .withOption('pageLength', 6)
   .withOption('bAutoWidth', false)
   .withOption('ordering', false)
   .withButtons([
          {
              extend: 'pdf',
              text:  '<a name="DowloadPDF" id="btnDowloadPDF">Dowload PDF</a>',
              titleAttr: 'Download PDF',
              title: "Detailed Analysis Report",
              orientation: 'landscape',
              pageSize: 'LEGAL',
              exportOptions: {
                  columns: ':visible',
                  columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 , 10 , 11, 12, 13, 14]
              },
              init: function(dt, node, config) {
    			       $("#reportType").on('change', function() {
    			             config.title = this.selectedOptions[0].label + " Analysis Report";
        			   })
              },
              customize: function ( doc ) {
                  doc.content[1].layout = 'borders';
                  var pdfTable = doc.content[1].table;
                  var headers = pdfTable.body[0];
                  var rows = pdfTable.body[1];
                  angular.forEach(pdfTable.body[0], function(header) {
                      header.width = 'auto';
                      header.alignment = 'left';
                  });
                  doc['footer']=(function(page, pages) {
                  return {
                    columns: [
                      'Page',{
                          alignment: 'right',
                            text: [
                              { text: page.toString() },
                              ' of ',
                              { text: pages.toString() }
                            ]
                          }
                        ],
                      margin: [20,20]
                      }
                  });
              }
          }
    ]);

    $scope.dtDetailsColumns = [
		DTColumnBuilder.newColumn('userId').withOption('width', '50px').withTitle('User Id').notSortable().renderWith(function(data, type, full) {
          return "<div>"+$scope.getModelRatingResultStatusUserId(full.processId)+"</div>"
    }),
		DTColumnBuilder.newColumn('processDate').withOption('width', '60px').withTitle('Session Date').notSortable().renderWith(function(data, type, full) {
	        return "<div>" +$scope.formatDate(data)+ "</div>"
	    }),
	    DTColumnBuilder.newColumn('processId').withOption('width', '60px').withTitle('Model Result Id').notSortable(),
	    DTColumnBuilder.newColumn('claimId').withOption('width', '60px').withTitle('Claim Id').notSortable(),
	    DTColumnBuilder.newColumn('claimDate').withOption('width', '60px').withTitle('Date Of Claim').notSortable().renderWith(function(data, type, full) {
	        return "<div>" +$scope.formatDate(data)+ "</div>"
	    }),
	    DTColumnBuilder.newColumn('modelType').withTitle('Model').notSortable(),
	    DTColumnBuilder.newColumn('claim.contentionClaimTextKeyForModel').withOption('width', '110px').withTitle('Contention').notSortable(),
	    DTColumnBuilder.newColumn(null).withOption('width', '40px').withTitle('Prior Relevant Diagonostic Codes').notSortable().renderWith(function(data, type, full) {
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
   .withOption('headerCallback', function(header) {
        angular.forEach(header.cells, function(cell){
            $(cell).attr('title', function (index, attr) {
                return this.outerText;
            });
        });
    })
   .withOption('createdRow', function(row, data, dataIndex) {
       // Recompiling so we can bind Angular directive to the DT
       $compile(angular.element(row).contents())($scope);
       angular.forEach(row.cells, function(cell){
          $(cell).attr('title', function (index, attr) {
              return this.outerText;
          });
       }); 
   }) 	
   .withDOM('Bfrtip')
   .withOption('pageLength', 6)
   .withOption('bAutoWidth', false)
   .withOption('ordering', false)
   .withButtons([
            {
                extend: 'pdf',
                text:  '<a name="DowloadPDF" id="btnDowloadPDF">Dowload PDF</a>',
                titleAttr: 'Download PDF',
                title: "Detailed Analysis Report",
                orientation: 'potrait',
                pageSize: 'LETTER',
                pageMargins: [40, 55, 40, 60],
                exportOptions: {
                    columns: ':visible'
                },
                init: function(dt, node, config) {
      			        $("#reportType").on('change', function() {
      			            config.title = this.selectedOptions[0].label + " Analysis Report";
      			        })
      			    },
               customize: function ( doc ) {
                  doc.content[1].layout = 'borders';
                  //doc.content[0].margin = [10,10,12,12];
                  var pdfTable = doc.content[1].table;
                  var headers = pdfTable.body[0];
                  var rows = pdfTable.body[1];
                  angular.forEach(pdfTable.body[0], function(header) {
                      header.width = 'auto';
                      header.alignment = 'left';
                  });
                  doc['footer']=(function(page, pages) {
                  return {
                    columns: [
                      'Page',{
                          alignment: 'right',
                            text: [
                              { text: page.toString() },
                              ' of ',
                              { text: pages.toString() }
                            ]
                          }
                        ],
                      margin: [20,20]
                      }
                  });
              }
          }
    ]);

    $scope.dtAggregateColumns = [
		  DTColumnBuilder.newColumn('modelType').withTitle('Model').notSortable().renderWith(function(data, type, full) {
	        return "<div>" + data +"</div>"
	    }),
	    DTColumnBuilder.newColumn('userCount').withTitle('No. Of Users').notSortable(),
	    DTColumnBuilder.newColumn('sessionsCount').withTitle('No. Of Sessions').notSortable().renderWith(function(data, type, full) {
          return "<div>"+data+"</div>"
      }),
	    DTColumnBuilder.newColumn('claimsCount').withTitle('No. Of Claims').notSortable(),
	    DTColumnBuilder.newColumn('patternsCount').withTitle('No. Of Patterns').notSortable().renderWith(function(data, type, full) {
	        return "<div>"+data+"</div>"
	    }),
	    DTColumnBuilder.newColumn('avgStatedAccuracy').withTitle('Avg Stated Accuracy').notSortable(),
	    //DTColumnBuilder.newColumn(null).withTitle('Actual Resulting Accuracy').notSortable(),
	    //DTColumnBuilder.newColumn(null).withTitle('% Agree').notSortable(),
	    //DTColumnBuilder.newColumn(null).withTitle('Claims Rated').notSortable(),
	   	//DTColumnBuilder.newColumn(null).withTitle('% Throughput').notSortable(),
    ];

    $scope.hasData = function(isEnabled) {
	     if(isEnabled) {
	        $('#btnDowloadPDF').closest('.dt-button').removeClass('disabled');
	        $('#btnDowloadPDF').closest('.dt-button').removeClass('disabledLink');
	    } else {
	        $('#btnDowloadPDF').closest('.dt-button').addClass('disabled');
	        $('#btnDowloadPDF').closest('.dt-button').addClass('disabledLink');
	    }
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

  $scope.getModelRatingResultStatusUserId = function(processId){
   var status = $filter('filter')($scope.modelRatingResultsStatus, {processId: processId}, true);
     if (status && status.length) {
      return status[0].createdBy;
     }

     return '';
  };
  
  $scope.formatDate = function(date) {
      var date = new Date(date);
      date = new Date(date.getTime() + Math.abs(date.getTimezoneOffset()*60000));
      return date.getFullYear() + '-' +  
          ('0' + (date.getMonth()+1)).slice(-2) + '-' + 
          ('0' + date.getDate()).slice(-2);  
  };
    
  $scope.checkErr = function(startDate,endDate) {
      $scope.errMessage = '';
      $scope.frmReportsSearchFilter.$invalid = false;
      var isValidStartDate = true;
      var isValidEndDate = true;

      if(startDate != null || startDate != undefined || startDate != "") {
          console.log('startDate-' +$scope.formatDate(startDate));
          isValidStartDate = $scope.isValidDate(startDate);
          console.log('isValidStartDate-' +isValidStartDate);
      }

      if(endDate != null || endDate != undefined || endDate != "") {
          console.log('startDate-' +$scope.formatDate(endDate));
          isValidEndDate = $scope.isValidDate(endDate);
          console.log('isValidEndDate-' +isValidEndDate);
      }

      if(!isValidStartDate || !isValidEndDate){
          $scope.errMessage = 'Invalid date. Date should be a value between 01/01/1900 - 01/01/2100.';
          $scope.frmReportsSearchFilter.$invalid = true;
          return false;
      }

       if(new Date(startDate) > new Date(endDate)){
       	$scope.errMessage = 'Date to should be greater than date from.';
        	$scope.frmReportsSearchFilter.$invalid = true;
        	return false;
      }

      if(startDate != null && endDate != null){
        var minutes = 1000*60;
        var hours = minutes*60;
        var days = hours*24;
        var diffDays = Math.round((new Date(endDate) - new Date(startDate))/days);
		    var isDateLeapYear = $scope.isLeapYear(startDate) || $scope.isLeapYear(endDate);
  			console.log("isLeapYear:" + isDateLeapYear);
  			var totalDays = isDateLeapYear ? 366 : 365;

  			if(diffDays > totalDays) {
  				$scope.errMessage = 'Max date range can only be one year.';
  	        	$scope.frmReportsSearchFilter.$invalid = true;
  	        	return false;
  			}
  		}
    };

    $scope.isLeapYear = function(date) {
    	var y = (date && date !=null) ? date.getFullYear() : null; 
    	return ((y % 4 == 0) && (y % 100 != 0)) || (y % 400 == 0);
    };

    $scope.isValidDate = function(date){
        return (date > $scope.minDefaultDate && date < $scope.maxDefaultDate);  
    };

    $scope.checkReportTypeErr =function(){
      $scope.onReportTypeChange();
    	$scope.errMessage = '';
      $scope.frmReportsSearchFilter.$invalid = false;
    	if($scope.filters && $scope.filters.reportTypeOption === null){
			$scope.errMessage = 'Report type must be selected.';
	        $scope.frmReportsSearchFilter.$invalid = true;
	        return false;
		  }
		  return true;
    };

    $scope.onReportTypeChange = function(){
      $scope.isReportTypeAggregate = false;
      if(angular.lowercase($scope.filters.reportTypeOption) === "aggregate"){
        $scope.isReportTypeAggregate = true;
      }
      console.log("aggregateReport-"+$scope.isReportTypeAggregate);
    };

    $scope.clear = function(){
      $scope.results = [];
    	$scope.errMessage = '';
    	$scope.reportsFromDate = null;
    	$scope.reportsToDate = null;
    	($scope.filters) ? $scope.filters.modelTypeOption = null : $scope.filters = null;
    	($scope.filters) ? $scope.filters.modelResultId = null : $scope.filters = null;
    	($scope.filters) ? $scope.filters.reportTypeOption = null : $scope.filters = null;
    	($scope.filters) ? $scope.filters.outputTypeOption = null : $scope.filters = null;
    	$scope.displayResultsRatingDetailsTable  = false;
    	$scope.displayResultsRatingAggregateTable  = false;
    	spinnerService.hide('reportsSpinner');
    };


    $scope.setSearchParameters = function(){
     	//case when no params or only model type
    	if(($scope.filters.modelResultId || $scope.filters.modelResultId == null) && $scope.reportsFromDate == null && $scope.reportsToDate == null){
    		var today = new Date();
    		$scope.filters.reportsFromDate = $scope.formatDate(new Date());
    		$scope.filters.reportsToDate = $scope.formatDate(new Date(today.getFullYear(), today.getMonth() + 12, today.getDate()));
   			$scope.processIds = [];
    	}

    	//case when only resultid
    	if(($scope.filters.modelResultId && $scope.filters.modelResultId != null) && $scope.fromDate == null && $scope.toDate == null){
    		$scope.filters.reportsFromDate = null;
    		$scope.filters.reportsToDate = null;
    		$scope.processIds.push($scope.filters.modelResultId);
    	}

    	//case when only dates and/or resultid/modeltype
    	if($scope.reportsFromDate != null && $scope.reportsToDate != null){
    		$scope.filters.reportsFromDate = $scope.formatDate($scope.reportsFromDate);
    		$scope.filters.reportsToDate = $scope.formatDate($scope.reportsToDate);
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
		$scope.hasData(false);
		if(!$scope.checkReportTypeErr()) return;
		
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
            if ($scope.results){
                if($scope.filters && $scope.filters.reportTypeOption === "DETAILED"){
                   resolve($scope.results.modelRatingResults);
                   $scope.hasData($scope.results.modelRatingResults && $scope.results.modelRatingResults.length > 0);
                }
                if($scope.filters && $scope.filters.reportTypeOption === "AGGREGATE"){
                  resolve($scope.results.aggregateReport);
                  $scope.hasData($scope.results.aggregateReport && $scope.results.aggregateReport.length > 0);
                }
            } else{
                resolve([]);
            }
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
			})
			.catch(function(e){
          $scope.serverErrorMsg = (e.errMessage && e.errMessage != null) ? e.errMessage : $scope.serverErrorMsg;
          $scope.callErrorDialog();
          spinnerService.hide('reportsSpinner');
    	});
    };

    $scope.callErrorDialog = function (size) {
            $scope.modal.instance = $modal.open({
            template: '<error-dialog modal="modal" bold-text-title="Error:" text-alert="'+ $scope.serverErrorMsg + '" mode="danger"></error-dialog>',
            scope: $scope,
        });
    };
});
