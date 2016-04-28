'use strict';

angular.module('rulesmanagerApp').controller('PublishController', function($scope, $stateParams, $uibModalInstance, evaluationBuilder, evaluationRules, EvaluationBuilderPublisher) {

    $scope.evaluationBuilder = evaluationBuilder;
    $scope.evaluationRules = evaluationRules;
    //console.log(evaluationBuilder);
    //console.log(schedules);

    $scope.getNumRules = function() {
        var numRules = 0;
        for (var i=0; i < $scope.evaluationRules.length; i++) {
            if ($scope.evaluationRules[i] != null && $scope.evaluationRules[i].rules != null) {
                numRules = numRules + $scope.evaluationRules[i].rules.length;
            }
        }
        return numRules;
    };

    $scope.publish = function() {
        EvaluationBuilderPublisher.save($scope.evaluationBuilder);
        $uibModalInstance.close();
    }
});
