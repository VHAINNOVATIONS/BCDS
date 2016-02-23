"use strict";

angular.module('bcdsApp').directive('dualList', function() {
    return {
        restrict: 'E',
        replace: true,
        scope: {
            leftReferenceElements: "=",
            rightReferenceElements: "=",
            nameParameter: "@",
            leftLabel: "@",
            rightLabel: "@"
        },
        template:   '<div class="row">' +
                        '<div class="col-md-5">' +
                            '<multiselect reference-elements="leftReferenceElements" label="{{leftLabel}}" sorted-reference-elements="leftSortedReferenceElements" name-parameter="nameParameter" input-collection="leftInputCollection"></multiselect>' +
                        '</div>' +
                        '<div class="col-md-2" style="padding-top:10%">' +
                            '<button type="button" class="btn btn-sm btn-default" ng-click="addSelectedElements()" style="width: 100%">Add Selected <span class="glyphicon glyphicon-arrow-right"></span></button>' +
                            '<button type="button" class="btn btn-sm btn-default" ng-click="removeSelectedElements()" style="width: 100%; margin-top: 15px"><span class="glyphicon glyphicon-arrow-left"></span> Remove Selected</button>' +
                        '</div>' +
                        '<div class="col-md-5">' +
                            '<multiselect reference-elements="rightReferenceElements" label="{{rightLabel}}" sorted-reference-elements="rightSortedReferenceElements" name-parameter="nameParameter" input-collection="rightInputCollection"></multiselect>' +
                        '</div>' +
                    '</div>',
        link: function (scope, element, attrs) {

            /* ==========================================================================
             Scope Variables
             ========================================================================== */
            scope.leftCurrentIndex = null;
            scope.leftInputCollection = null;
            if (scope.leftReferenceElements == null) {
                scope.leftReferenceElements = [];
            }
            scope.leftSortedReferenceElements = [];

            scope.rightCurrentIndex = null;
            scope.rightInputCollection = null;
            if(scope.rightReferenceElements == null) {
                scope.rightReferenceElements = [];
            }
            scope.rightSortedReferenceElements = [];

            scope.addSelectedElements = function() {
                $('.multiselect li').removeClass('select-highlight select-highlight-blur');

                //console.log("add selected elements");
                //console.log(scope.leftInputCollection);

                var newLeftInputCollection = [];
                for (var i=0; i < scope.leftInputCollection.length; i++) {
                    if (scope.leftInputCollection[i]) {
                        //console.log("right side");
                        //console.log(scope.leftReferenceElements[i]);
                        //scope.rightReferenceElements.push(angular.copy(scope.leftSortedReferenceElements[i]));
                        scope.rightReferenceElements.push(scope.leftSortedReferenceElements[i]);
                    }
                    else {
                        //console.log("left side");
                        //console.log(scope.leftReferenceElements[i]);
                        //newLeftInputCollection.push(angular.copy(scope.leftSortedReferenceElements[i]));
                        newLeftInputCollection.push(scope.leftSortedReferenceElements[i]);
                    }
                }
                scope.leftReferenceElements = newLeftInputCollection;
            };

            scope.removeSelectedElements = function() {
                $('.multiselect li').removeClass('select-highlight select-highlight-blur');

                //console.log("remove selected elements");
                //console.log(scope.rightInputCollection);

                var newRightInputCollection = [];
                for (var i=0; i < scope.rightInputCollection.length; i++) {
                    if (scope.rightInputCollection[i]) {
                        //scope.leftReferenceElements.push(angular.copy(scope.rightSortedReferenceElements[i]));
                        scope.leftReferenceElements.push(scope.rightReferenceElements[i]);
                    }
                    else {
                        //newRightInputCollection.push(angular.copy(scope.rightSortedReferenceElements[i]));
                        newRightInputCollection.push(scope.rightReferenceElements[i]);
                    }
                }
                scope.rightReferenceElements = newRightInputCollection;
            };
        }
    };
});
