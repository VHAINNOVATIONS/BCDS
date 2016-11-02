'use strict';

angular.module('bcdssApp')
  .controller('TabController', ['$scope', function($scope) {
	  	$scope.tab = 1;
		$scope.Dashboardtab = 1;
		
		$scope.setTab = function(newTab){
		  $scope.tab = newTab;	  
		};
		
		$scope.setDashboardTab = function(tabName){
			$scope.Dashboardtab = tabName;
		}
		
		$scope.isDashboardTabSet = function(tabNum){
			return $scope.Dashboardtab === tabNum;
		}
		
		$scope.isSet = function(tabNum){
		  return $scope.tab === tabNum;
		};
}]);

