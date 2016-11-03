'use strict';

angular.module('bcdssApp').controller('ClaimsController', function($rootScope, $scope, $state, Account, $stateParams, ClaimService, ClaimFilterService) {
    $scope.searchTerm = undefined;
    $scope.claims = [];
    $scope.orderByField = 'veteranId';
    $scope.reverseSort = false;
         
    $scope.setFilterDates  = function(){
    	$scope.today = new Date();
        $scope.minDate = new Date($scope.today.getFullYear(), $scope.today.getMonth(), $scope.today.getDate());
        $scope.maxDate = new Date($scope.today.getFullYear(), $scope.today.getMonth() + 2, $scope.today.getDate());
    };
    
    $scope.getUserName = function(){
    	if ($rootScope.userName != null) {
    		$scope.userName = $rootScope.userName;
    	} else {
    		$scope.userName = $scope.account.firstName;
    	}
    };
    
    $scope.setFilterDates();
    $scope.getUserName();
    
    $scope.toggleCheckAll = function () {
        angular.forEach($scope.claims, function (claim) {
            claim.Selected = $scope.selectAll;
        });
    };  
    
    $scope.loadClaims = function(){
    	ClaimService.query(function(result){
    		$scope.claims = result;
    	});
    };

    $scope.loadClaims();

	    $scope.getCestDate = function(date) {
			return (date + (10*24*60*60*1000));
		};
	
        $scope.formatDate = function(date) {
            var date = new Date(date);
            return ('0' + (date.getMonth()+1)).slice(-2) + '/' +
                ('0' + date.getDate()).slice(-2) + '/'
                + date.getFullYear();
        };

        $scope.isCollapsed = function(claim) {
            if (claim.isCollapsed == undefined) {
                claim.isCollapsed = true;
            }
            return claim.isCollapsed;
        };

        $scope.toggleCollapse = function(claim) {
            if (claim.isCollapsed == undefined) {
                claim.isCollapsed = true;
            } else {
                claim.isCollapsed = !claim.isCollapsed;
            }
        };

        $scope.displayProcess = function() {
          $('#modelDialog').modal('show');
        };

        $scope.click = function() {
            $('#next').removeClass('hidden');
        }

        $scope.search = function(searchTerm) {
            if (searchTerm == 1234) {
                $('#serviceErrorDialog').modal('show');
            } else {
                $scope.claims.push({
                    "is_collapsed": false,
                    "file_number": "515049876",
                    "name": "John Smith",
                    "claim_num": "5625193",
                    "claim_date": "01/10/2016",
                    "contentions": [
                        {
                            "code": 5100,
                            "description": "Knee"
                        },
                        {
                            "code": 2100,
                            "description": "Ear"
                        }
                    ]
                });
            }
        }

        $scope.deleteClaim = function(claim) {
            var index = $scope.claims.indexOf(claim);
            $scope.claims.splice(index,1);
        }

        $scope.clear = function(){
            $scope.claims = [];
            $scope.loadClaims();
        }
        
        $scope.isActiveRoleTab = function (userRoleTab) {
        	$stateParams.userRoleType == userRoleTab;
        	return userRoleTab;
        };
        
        $scope.clearFilter = function() {
            $scope.filterKey = '';
        }; 
          
        $scope.advancedFilter = function() {
        	$scope.filters = {};
        	$scope.setFilterDates();
        	$('#advancedFilterDialog').modal('show');
        };
        
        $scope.advanceFilterSearch = function(){
        	var claimfilters = $scope.filters;
        	claimfilters.dateFrom = $scope.minDate
        	claimfilters.dateTo = $scope.maxDate;
        	
        	ClaimFilterService.getFilteredClaims({filters: claimfilters}, function(result){
    			$scope.claims = result;
    			console.log($scope.claims);
    		});
        };
            
        
          /*$scope.loadTabView = function (tabUserRole) {
        	if(tabUserRole == USER_ROLE.userRoleRater){
        		console.log("rater");
        		$state.go('home', {userRoleType: USER_ROLE.userRoleRater});
        	} else if (tabUserRole == USER_ROLE.userRoleModeler){
        		console.log("modeler")
        		$state.go('home', {userRoleType: USER_ROLE.userRoleModeler})
        	} else {
        		console.log("admin")
        		$state.go('home', {userRoleType: USER_ROLE.userRoleAdmin})
        	}
        };

        $scope.userRoleTabs = [
                           {title: "Rater", active: $scope.isActiveRoleTab(USER_ROLE.userRoleRater)},
                           {title: "Modeler", active: $scope.isActiveRoleTab(USER_ROLE.userRoleModeler)}, 
                           {title: "Admin", active: $scope.isActiveRoleTab(USER_ROLE.userRoleAdmin)}
                       ];

        $scope.loadRaterTab = function () {
        	console.log("rater");
        	console.log(USER_ROLE);
            $state.go('home', {userRoleType: USER_ROLE.userRoleRater});
        };

        $scope.loadModelerTab = function () {
        	console.log("modeler");
            $state.go('home', {userRoleType: USER_ROLE.userRoleModeler});
        };

        $scope.loadAdminTab = function () {
        	console.log("admin");
            $state.go('home', {userRoleType: USER_ROLE.userRoleAdmin});
        };*/
    });
