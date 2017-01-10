'use strict';

angular.module('bcdssApp')
    .factory('ModelService', function ($http, $resource, DateUtils) {
        return {
            findModelPatternResults: function (patternId) {
                var data = {
                    patternId : (patternId.length === 0) ? null : patternId,           
                }
                return $http.post('api/modelRatingPatternInfo', data)
                    .then(function (response) {
                        return angular.fromJson(response);
                    });
            },

            createModelPatternCdd: function (patternId, CDD, accuracy, patternIndexNumber, modelType, categoryId, userId) {
                var data = {
                    patternId : patternId,
                    cdd : CDD,
                    categoryId : categoryId,
                    accuracy : accuracy,
                    patternIndexNumber : patternIndexNumber,
                    modelType : modelType,
                    userId : userId        
                }
                return $http.post('api/updateModelRatingPatternInfo', data)
                    .then(function (response) {
                        return angular.fromJson(response);
                    });
            }
        };
    });
