'use strict';

angular.module('bcdssApp').controller('DashboardController', function($rootScope, $scope, $state, Account, USER_ROLE, $stateParams) {
    $scope.searchTerm = undefined;
    //$scope.USER_ROLE = USER_ROLE;
    
    $scope.getUserName = function(){
    	if ($rootScope.userName != null) {
    		$scope.userName = $rootScope.userName;
    	} else {
    		$scope.userName = $scope.account.firstName;
    	}
    };
    $scope.getUserName();
        
        $scope.claims = [{
            "is_collapsed": false,
            "veteranId" : 212029876,
            "veteranName": "John Doe",
            "regionalOffice": "Houston, TX",
            "claimNumber": 20160621,
            "dateOfClaim": "03/10/2016",
            "cestDate": "03/10/2016",
            "contentions": [{
                "code": 2200,
                "description": "Hand",
                "lastModel": "03/10/2016",
            }, {
                "code": 2210,
                "description": "Ear",
                "lastModel": "03/10/2016"
            }]
	    }, {
            "is_collapsed": false,
            "veteranId" : 900125836,
            "veteranName": "Albert Smith",
            "regionalOffice": "New York City, NY",
            "claimNumber": 20160516,
            "dateOfClaim": "03/10/2016",
            "cestDate": "03/10/2016",
            "contentions": [{
                "code": 2250,
                "description": "Elbow",
                "lastModel": "03/10/2016",
            }, {
                "code": 2220,
                "description": "Finger",
                "lastModel": "03/10/2016"
            }, {
                "code": 2230,
                "description": "Arm",
                "lastModel": "03/10/2016"
            }]
	    }
	    
	    ];
	    /*$scope.page = 1;

	    $scope.loadPage = function(page) {
		    $scope.page = page;
	    };*/

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
            if(searchTerm == 1234){
                $('#serviceErrorDialog').modal('show');
            }
            else {
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
        }
        
        $scope.isActiveRoleTab = function (userRoleTab) {
        	$stateParams.userRoleType == userRoleTab;
        	return userRoleTab;
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
        };*/
        
        
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
        };

    });
