"use strict";

angular.module('bcdsApp').directive('multiselect', function() {
    return {
        restrict: 'E',
        replace: true,
        scope: {
            referenceElements: "=",
            inputCollection: "=",
            sortedReferenceElements: "=",
            nameParameter: "=",
            label: "@"
        },
        template:   '<div>' +
                        '<div>{{label}}</div>' +
                        '<ul ui-validate="validateMultiselect()" tabindex=0 aria-label="listbox" class="form-control multiselect">' +
                            '<li aria-label="option" ng-repeat="reference in referenceElements | orderBy:nameParameter as sortedReferenceElements track by reference.id" class="list-unstyled" ng-class="{\'select-highlight\': inputCollection[$index] || $index == currentIndex}">' +
                                '<label>' +
                                    '<input type="checkbox" ng-model="inputCollection[$index]" ng-change="processChange($index)" ng-click="processClick($event)">{{reference[nameParameter]}}' +
                                '</label>' +
                            '</li>' +
                       '</ul>' +
                    '</div>',
        link: function (scope, element, attrs) {
            scope.currentIndex = null;
            scope.inputCollection = null;
            if (scope.referenceElements == null) {
                scope.referenceElements = [];
            }
            scope.sortedReferenceElements = [];

            // Default to displaying the 'name' parameter
            if (scope.nameParameter == null) {
                scope.nameParameter = "name";
            }

            scope.$watchCollection('referenceElements', function(newValue, oldValue) {
                compareReferenceElements(newValue, oldValue);
            });

            scope.validateMultiselect = function() {
                return false;
            };

            scope.processClick = function(event) {
                //console.log("click");
            };

            scope.processChange = function(index) {
                if(scope.inputCollection[index]) {
                    scope.currentIndex = index;
                }
                else if (scope.currentIndex == index) {
                    scope.currentIndex = null;
                }
            };

            /* ==========================================================================
             JQuery Methods
             ========================================================================== */
            // Grey out highlight affect on blur
            element.find('.multiselect').on('blur', function() {
                $('.multiselect li').each(function(index) {
                    if ( $(this).hasClass('select-highlight')) {
                        $(this).removeClass('select-highlight');
                        if (scope.inputCollection[index]) {
                            $(this).addClass('select-highlight-blur')
                        }
                    }
                });
            }).on('focus', function() {
                $('.multiselect li').each(function(index) {
                    if ( $(this).hasClass('select-highlight-blur')) {
                        $(this).removeClass('select-highlight-blur');
                        $(this).addClass('select-highlight')
                    }
                    else if (scope.currentIndex == index && scope.inputCollection[scope.currentIndex]) {
                        $(this).addClass('select-highlight')
                    }
                });
            }).on('keydown', function(e) {   // Emulate keyboard behavior for Up/Down and Spacebar

                processUpDownKeydown(e);
                processSpacebarKeydown(e);

                scope.$apply();
            });

            /* ==========================================================================
             Helper Local Methods
             ========================================================================== */

            // Determine if reference elements were added or removed
            var compareReferenceElements = function(newValue, oldValue) {
                var oldLength = 0;
                if (oldValue != undefined) {
                    oldLength = oldValue.length;
                }

                var newLength = 0;
                if (newValue != undefined) {
                    newLength = newValue.length;
                }

                // Update Input Collection if the length has changed
                // or if the model has initialized a variable
                if (oldLength != newLength || newValue === oldValue) {
                    updateInputCollection(newValue);
                }
            };

            // Update the input collection array to track selected reference elements
            var updateInputCollection = function(newValue) {
                scope.inputCollection = [];
                for (var i=0; i < newValue.length; i++) {
                    scope.inputCollection[i] = false;
                }
                scope.currentIndex = null;
            };

            var processUpDownKeydown = function(e) {
                if (e.which == 38 || e.which == 40) {

                    e.preventDefault();

                    // Up arrow logic
                    if (e.which == 38) {
                        //console.log("up");
                        if (scope.currentIndex == null || scope.currentIndex == 0) {
                            scope.currentIndex = scope.inputCollection.length - 1;
                        }
                        else {
                            scope.currentIndex--;
                        }
                    }

                    // Down arrow logic
                    else if (e.which == 40) {
                        if (scope.currentIndex == null || scope.currentIndex == scope.inputCollection.length - 1) {
                            scope.currentIndex = 0;
                        }
                        else {
                            scope.currentIndex++;
                        }
                        //console.log("down");
                    }

                    makeElementVisible($(e.currentTarget).find('li').eq(scope.currentIndex)[0]);
                }
            };

            var processSpacebarKeydown = function(e) {
                if (e.which == 32) {
                    e.preventDefault();

                    scope.inputCollection[scope.currentIndex] = !scope.inputCollection[scope.currentIndex];
                }
            };

            // TODO: autoscroll the multiselect to show offscreen values when using up/down arrow keys
            var makeElementVisible = function (e) {
                //var top = e.offsetTop;
                //var height = e.offsetHeight;
                //
                //var parentHeight = e.offsetParent.offsetHeight;
                //var parentTop = e.offsetParent.offsetTop;
                //
                //console.log(height);
                //console.log(top);
                //console.log(parentHeight);
                //console.log(parentTop);
                //
                //console.log(top + height);
                //console.log(parentHeight - height);
                //
                //if (top + height > parentHeight - height) {
                //    e.offsetParent.scrollTop += height;
                //}
            };
        }
    }
});
