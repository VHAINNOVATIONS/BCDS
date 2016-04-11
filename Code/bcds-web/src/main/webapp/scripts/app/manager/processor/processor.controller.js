'use strict';

angular.module('bcdsApp').controller('ProcessorController', function($scope, $uibModal, $stateParams) {
    $scope.results = [{
        "is_collapsed": false,
        "file_number" : "212029876",
        "name": "John Doe",
        "success": 2,
        "failure": 0,
        "output": [{
            "code": "60% (Rater)",
            "description": "60% (Predicted)"
        }, {
            "code": "30% (Rater)",
            "description": "30% (Predicted)"
        }]
    },{
        "is_collapsed": true,
        "file_number" : "313029876",
        "name": "Jane Doe",
        "success": 1,
        "failure": 1,
        "output": [{
            "code": "50% (Rater)",
            "description": "50% (Predicted)"
        }, {
            "code": "40% (Rater)",
            "description": "10% (Predicted)"
        }]
    }];
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

    $scope.isCollapsed = function(result) {
        if (result.isCollapsed == undefined) {
            result.isCollapsed = true;
        }
        return result.isCollapsed;
    };

    $scope.toggleCollapse = function(result) {
        if (result.isCollapsed == undefined) {
            result.isCollapsed = true;
        }
        else {
            result.isCollapsed = !result.isCollapsed;
        }
    };

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
                        "code": 35,
                        "description": "Knee"
                    },
                    {
                        "code": 56,
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
