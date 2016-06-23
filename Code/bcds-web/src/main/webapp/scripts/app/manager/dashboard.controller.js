'use strict';

angular.module('bcdssApp').controller('DashboardController', function($scope, $state, Principal, Auth) {
        $scope.searchTerm = undefined;
        
        //console.log($state.current.data);
        
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
                "description": "Hearing",
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
	    $scope.page = 1;

	    $scope.loadPage = function(page) {
		    $scope.page = page;
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
            }
            else {
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


    });
