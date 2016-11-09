'use strict';

angular.module('bcdssApp').controller('ClaimsController', function($rootScope, $scope, $state, Account, $stateParams, ClaimService, ClaimFilterService) {
    $scope.searchTerm = undefined;
    $scope.claims = [];
    $scope.orderByField = 'veteranId';
    $scope.reverseSort = false;
    $scope.filters = {};
    
    $scope.regionalOfficeOptions = [
    	{ value: 'Select a regional office', label: 'Select a regional office'},
	    { value:'463',	label:'	Anchorage RO'},
	    { value:'350',	label:'	Little Rock Regional Office'},
	    { value:'320',	label:'	Nashville Regional Office'},
	    { value:'315',	label:'	Huntington Regional Office'},
	    { value:'316',	label:'	Atlanta Regional Office'},
	    { value:'354',	label:'	Reno Regional Office'},
	    { value:'344',	label:'	Los Angeles Regional Office'},
	    { value:'335',	label:'	St. Paul Regional Office'},
	    { value:'460',	label:'	Wilmington RO'},
	    { value:'311',	label:'	Pittsburgh Regional Office'},
	    { value:'322',	label:'	Montgomery Regional Office'},
	    { value:'321',	label:'	New Orleans Regional Office'},
	    { value:'372',	label:'	Washington Regional Office'},
	    { value:'330',	label:'	Milwaukee Regional Office'},
	    { value:'339',	label:'	Denver Regional Office'},
	    { value:'328',	label:'	Chicago Regional Office'},
	    { value:'341',	label:'	Salt Lake City Regional Office'},
	    { value:'452',	label:'	Wichita RO'},
	    { value:'405',	label:'	White River Junction RO'},
	    { value:'319',	label:'	Columbia Regional Office'},
	    { value:'334',	label:'	Lincoln Regional Office'},
	    { value:'438',	label:'	Sioux Falls RO'},
	    { value:'442',	label:'	Cheyenne RO'},
	    { value:'318',	label:'	Winston-Salem Regional Office'},
	    { value:'307',	label:'	Buffalo Regional Office'},
	    { value:'313',	label:'	Baltimore Regional Office'},
	    { value:'323',	label:'	Jackson Regional Office'},
	    { value:'304',	label:'	Providence Regional Office'},
	    { value:'309',	label:'	Newark Regional Office'},
	    { value:'327',	label:'	Louisville Regional Office'},
	    { value:'402',	label:'	Togus RO'},
	    { value:'346',	label:'	Seattle Regional Office'},
	    { value:'310',	label:'	Philadelphia Regional Office'},
	    { value:'347',	label:'	Boise Regional Office'},
	    { value:'376',	label:'	St. Louis RMC'},
	    { value:'101',	label:'	Central Office'},
	    { value:'325',	label:'	Cleveland Regional Office'},
	    { value:'317',	label:'	St. Petersburg Regional Office'},
	    { value:'301',	label:'	Boston Regional Office'},
	    { value:'306',	label:'	New York Regional Office'},
	    { value:'348',	label:'	Portland Regional Office'},
	    { value:'377',	label:'	San Diego Regional Office'},
	    { value:'345',	label:'	Phoenix Regional Office'},
	    { value:'362',	label:'	Houston Regional Office'},
	    { value:'314',	label:'	Roanoke Regional Office'},
	    { value:'459',	label:'	Honolulu RO'},
	    { value:'436',	label:'	Fort Harrison RO'},
	    { value:'358',	label:'	Manila Regional Office'},
	    { value:'437',	label:'	Fargo RO'},
	    { value:'355',	label:'	San Juan Regional Office'},
	    { value:'329',	label:'	Detroit Regional Office'},
	    { value:'351',	label:'	Muskogee Regional Office'},
	    { value:'331',	label:'	St. Louis Regional Office'},
	    { value:'340',	label:'	Albuquerque Regional Office'},
	    { value:'326',	label:'	Indianapolis Regional Office'},
	    { value:'397',	label:'	Appeals Management Center'},
	    { value:'349',	label:'	Waco Regional Office'},
	    { value:'343',	label:'	Oakland Regional Office'},
	    { value:'333',	label:'	Des Moines Regional Office'},
	    { value:'308',	label:'	Hartford Regional Office'},
	    { value:'373',	label:'	Manchester Regional Office'},

	];
    
    $scope.filters.regionalOfficeOption = $scope.regionalOfficeOptions[0];
    
    $scope.setFilterDates  = function(){
        $scope.today = new Date();
        $scope.fromDate = new Date();
        $scope.toDate = new Date($scope.today.getFullYear(), $scope.today.getMonth() + 1, $scope.today.getDate());
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
        	$scope.setFilterDates();
        	$('#advancedFilterDialog').modal('show');
        };
        
        $scope.advanceFilterSearch = function(){
        	if ($scope.filters != null) {
        		$scope.filters.fromDate = $scope.formatDate($scope.fromDate);
        		$scope.filters.toDate = $scope.formatDate($scope.toDate);;
        		ClaimFilterService.filterClaims($scope.filters)
        			.then(function(result){
        				console.log('>>>successful');
        				$scope.claims = result;
        				console.log(result);
        		});
    		}
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
