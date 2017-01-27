"use strict";

angular.module('bcdssApp').directive('errorDialog', function($log) {
   return {
        restrict: 'E',
        templateUrl: './scripts/app/customized/customDirectives/customError.html',
         scope: {
          modal: '=',
          mode: '@',
          boldTextTitle: '@',
          textAlert : '@'
        },
        controller: function ($scope) {
          $scope.selected = {
          };
          
          $scope.ok = function () {
            $scope.modal.instance.close($scope.selected);
          };
          
          $scope.close = function () {
            $scope.modal.instance.close($scope.selected);
          };

          $scope.cancel = function () {
            $scope.modal.instance.dismiss('cancel');
          };
          
          $scope.modal.instance.result.then(function (selectedItem) {
            $scope.selected = selectedItem;
          }, function () {
            $log.info('Modal dismissed at: ' + new Date());
          });
        },
        link: function(scope, elm, attrs) {
         scope.data= {
            mode:scope.mode || 'info',
            boldTextTitle:scope.boldTextTitle || 'title',
            textAlert:scope.textAlert || 'text'
          }
        }
    };
});
