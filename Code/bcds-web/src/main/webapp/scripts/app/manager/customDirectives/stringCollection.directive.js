"use strict";

angular.module('bcdsApp').directive('stringCollection', function() {
    return {
        restrict: 'E',
        replace: true,
        scope: {
            collection: '='
        },
        template:   '<div>' +
                        '<ul ng-if="!isSingleValue()" style="margin:0; padding:0 0 0 20px">' +
                            '<li ng-repeat="string in collection">{{string}}</li>' +
                        '</ul>' +
                        '<div ng-if="isSingleValue()">{{collection[0]}}</div>' +
                    '</div>',
        link: function (scope, element, attrs) {

            scope.isSingleValue = function() {
                return scope.collection.length < 2;
            }
        }
    };
});
