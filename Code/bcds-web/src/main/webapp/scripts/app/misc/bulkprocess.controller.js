'use strict';

angular.module('bcdssApp').controller('BulkProcessController', function($rootScope, $scope, $state, Account, $filter, $modal,
														$q, $compile, $timeout,	$stateParams, spinnerService, BulkProcessClaimService) {
    $scope.searchTerm = undefined;
    $scope.serverErrorMsg = "Something went wrong! Please contact the site administrator."
    $scope.filters = {};
    $scope.maxDefaultDate = new Date('01/01/2100');
    $scope.minDefaultDate = new Date('01/01/1900');
    $scope.isDataAvaialbleToProcess = false;
    $scope.infoMessage = false;
    $scope.bpFromDate = null;
    $scope.bpToDate = null;
    $scope.filters = {
        bpFromDate: null,
        bpToDate: null,
        modelTypeOption: null,
        bpRegionalOfficeOption: null
    };

    $scope.modal = {
      instance: null
    };
    
    $scope.modelTypeOptions = [
        { value:'knee', label:'Knee'},
        { value:'ear',  label:'Ear'}
    ];
          
    $scope.getUserName = function(){
    	if ($rootScope.userName != null) {
    		$scope.userName = $rootScope.userName;
    	} else {
    		$scope.userName = $scope.account.firstName;
    	}
    };
    
    $scope.getUserName();
   
    $scope.bpRegionalOfficeOptions = [
        { value:'463',  label:'Anchorage RO'},
        { value:'350',  label:'Little Rock Regional Office'},
        { value:'320',  label:'Nashville Regional Office'},
        { value:'315',  label:'Huntington Regional Office'},
        { value:'316',  label:'Atlanta Regional Office'},
        { value:'354',  label:'Reno Regional Office'},
        { value:'344',  label:'Los Angeles Regional Office'},
        { value:'335',  label:'St. Paul Regional Office'},
        { value:'460',  label:'Wilmington RO'},
        { value:'311',  label:'Pittsburgh Regional Office'},
        { value:'322',  label:'Montgomery Regional Office'},
        { value:'321',  label:'New Orleans Regional Office'},
        { value:'372',  label:'Washington Regional Office'},
        { value:'330',  label:'Milwaukee Regional Office'},
        { value:'339',  label:'Denver Regional Office'},
        { value:'328',  label:'Chicago Regional Office'},
        { value:'341',  label:'Salt Lake City Regional Office'},
        { value:'452',  label:'Wichita RO'},
        { value:'405',  label:'White River Junction RO'},
        { value:'319',  label:'Columbia Regional Office'},
        { value:'334',  label:'Lincoln Regional Office'},
        { value:'438',  label:'Sioux Falls RO'},
        { value:'442',  label:'Cheyenne RO'},
        { value:'318',  label:'Winston-Salem Regional Office'},
        { value:'307',  label:'Buffalo Regional Office'},
        { value:'313',  label:'Baltimore Regional Office'},
        { value:'323',  label:'Jackson Regional Office'},
        { value:'304',  label:'Providence Regional Office'},
        { value:'309',  label:'Newark Regional Office'},
        { value:'327',  label:'Louisville Regional Office'},
        { value:'402',  label:'Togus RO'},
        { value:'346',  label:'Seattle Regional Office'},
        { value:'310',  label:'Philadelphia Regional Office'},
        { value:'347',  label:'Boise Regional Office'},
        { value:'376',  label:'St. Louis RMC'},
        { value:'101',  label:'Central Office'},
        { value:'325',  label:'Cleveland Regional Office'},
        { value:'317',  label:'St. Petersburg Regional Office'},
        { value:'301',  label:'Boston Regional Office'},
        { value:'306',  label:'New York Regional Office'},
        { value:'348',  label:'Portland Regional Office'},
        { value:'377',  label:'San Diego Regional Office'},
        { value:'345',  label:'Phoenix Regional Office'},
        { value:'362',  label:'Houston Regional Office'},
        { value:'314',  label:'Roanoke Regional Office'},
        { value:'459',  label:'Honolulu RO'},
        { value:'436',  label:'Fort Harrison RO'},
        { value:'358',  label:'Manila Regional Office'},
        { value:'437',  label:'Fargo RO'},
        { value:'355',  label:'San Juan Regional Office'},
        { value:'329',  label:'Detroit Regional Office'},
        { value:'351',  label:'Muskogee Regional Office'},
        { value:'331',  label:'St. Louis Regional Office'},
        { value:'340',  label:'Albuquerque Regional Office'},
        { value:'326',  label:'Indianapolis Regional Office'},
        { value:'397',  label:'Appeals Management Center'},
        { value:'349',  label:'Waco Regional Office'},
        { value:'343',  label:'Oakland Regional Office'},
        { value:'333',  label:'Des Moines Regional Office'},
        { value:'308',  label:'Hartford Regional Office'},
        { value:'373',  label:'Manchester Regional Office'}

    ];
    
    //$scope.filters.bpRegionalOfficeOption = $scope.bpRegionalOfficeOptions[0].value; // Default

	$scope.formatDate = function(date) {
        var date = new Date(date);
        return date.getFullYear() + '-' +  
            ('0' + (date.getMonth()+1)).slice(-2) + '-' + 
            ('0' + date.getDate()).slice(-2);
    };
    
    $scope.checkErr = function(startDate,endDate) {
        $scope.errMessage = '';
        $scope.frmBulkProcess.$invalid = false;
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
            $scope.errMessage = 'Invalid date. Date should be a value between 01/01/2000 - 01/01/2100.';
            $scope.frmBulkProcess.$invalid = true;
            return false;
        }

        if(new Date(startDate) > new Date(endDate)){
          $scope.errMessage = 'Date to should be greater than date from.';
          $scope.frmBulkProcess.$invalid = true;
          return false;
        }

         $scope.cancelProcess();
    };
    
    $scope.isValidDate = function(date){
        return (date > $scope.minDefaultDate && date < $scope.maxDefaultDate);  
    };

    $scope.clear = function(reset){
        $scope.bpFromDate = null;
        $scope.bpToDate = null;
        $scope.filters.bpFromDate = null;
        $scope.filters.bpToDate = null;
        $scope.filters.modelTypeOption = null;
        $scope.filters.bpRegionalOfficeOption = null;
        $scope.errMessage = '';
        $scope.frmBulkProcess.$invalid = false;
        $scope.isDataAvaialbleToProcess = false;
        $scope.infoMessage = reset;
        $scope.noRecordsMessage = false;
    }
        
    $scope.isActiveRoleTab = function (userRoleTab) {
    	$stateParams.userRoleType == userRoleTab;
    	return userRoleTab;
    };

    $scope.cancelProcess = function(){
        $scope.isDataAvaialbleToProcess = false;
        $scope.isDataAvaialbleToProcess = false;
        $scope.infoMessage = false;
        $scope.noRecordsMessage = false;
    };

    $scope.searchClaimsToProcess = function(doSave){
        $scope.noRecordsMessage = false;
        spinnerService.show('bulkProcessSpinner');
        if ($scope.filters != null) {
            $scope.filters.bpFromDate = ($scope.bpFromDate === null || $scope.bpFromDate === undefined) ? null : $scope.formatDate($scope.bpFromDate);
            $scope.filters.bpToDate = ($scope.bpToDate === null || $scope.bpToDate === undefined) ? null : $scope.formatDate($scope.bpToDate);
            var processCount = (doSave) ? $scope.claimCount : null;
            BulkProcessClaimService.bulkProcessClaims($scope.filters, $scope.userName, processCount, doSave)
                .then(function(result){
                    console.log('>>>successful');
                    $scope.result = result.data;
                    var promise = new Promise( function(resolve, reject){
                        if ($scope.result){
                            resolve($scope.result);
                            $scope.claimCount = $scope.result.recordCount;
                            $scope.isDataAvaialbleToProcess = ($scope.claimCount > 0);
                            if(!$scope.isDataAvaialbleToProcess){
                                $scope.noRecordsMessage = true;
                            }
                            $scope.infoMessage = false;
                            console.log($scope.result.status);
                        }
                        else
                            resolve([]);
                    });
                    spinnerService.hide('bulkProcessSpinner');
                })
                .catch(function(e){
                    $scope.serverErrorMsg = (e && e.data.message != null) ? e.data.message : $scope.serverErrorMsg;
                    $scope.callErrorDialog();
                    console.log($scope.result && $scope.result.error);
                    spinnerService.hide('bulkProcessSpinner');
                });
        }
    };

    $scope.saveClaimsToProcess = function(doSave){
        $scope.noRecordsMessage = false;
        spinnerService.show('bulkProcessSpinner');
        if ($scope.filters != null) {
            $scope.filters.bpFromDate = ($scope.bpFromDate === null || $scope.bpFromDate === undefined) ? null : $scope.formatDate($scope.bpFromDate);
            $scope.filters.bpToDate = ($scope.bpToDate === null || $scope.bpToDate === undefined) ? null : $scope.formatDate($scope.bpToDate);
            var processCount = (doSave) ? $scope.claimCount : null;
            BulkProcessClaimService.bulkProcessClaims($scope.filters, $scope.userName, processCount, doSave)
                .then(function(result){
                    console.log('>>>successful');
                    $scope.result = result.data;
                    var promise = new Promise( function(resolve, reject){
                        if ($scope.result) {
                            resolve($scope.result);
                            $scope.clear(0);
                            $scope.claimCount = 0;
                            console.log("Bulk Process Request:"+ $scope.result.status);
                            $scope.infoMessage = ($scope.result && $scope.result.status === "success");
                        }
                        else
                          resolve([]);
                    });
                    spinnerService.hide('bulkProcessSpinner');
                })
                .catch(function(e){
                    $scope.serverErrorMsg = (e && e.data.message != null) ? e.data.message : $scope.serverErrorMsg;
                    $scope.callErrorDialog();
                    console.log($scope.result && $scope.result.error);
                    spinnerService.hide('bulkProcessSpinner');
                });
        }
    };

    $scope.callErrorDialog = function (size) {
            $scope.modal.instance = $modal.open({
            template: '<error-dialog modal="modal" bold-text-title="Error:" text-alert="'+ $scope.serverErrorMsg + '" mode="danger"></error-dialog>',
            scope: $scope,
        });
    };
});