'use strict';

angular.module('bcdssApp')
    .factory('RatingService', function ($http, $resource, DateUtils) {
        return {
            getRatingResults: function(){
                return $http.get('api/results')
                    .then(function (response) {
                        return angular.fromJson(response);
                    });
            },

            findModelRatingResults: function (processIds, filters) {
                var data = {
                    processIds : (processIds.length ==0) ? null : processIds,           
                    fromDate: (filters == null) ? null : filters.fromDate,
                    toDate: (filters == null) ? null : filters.toDate,
                    modelType: (filters == null) ? null : filters.modelTypeOption
                }
                return $http.post('api/modelRatingResults', data)
                    .then(function (response) {
                        return angular.fromJson(response);
                    });
            },

            updateModelRatingResultsStatus: function (processIds, decisions) {
                var data = {
                    processIds : (processIds.length == 0) ? null : processIds,   
                    resultsStatus : (decisions.length == 0) ? null : decisions,           
                }
                return $http.post('api/updateModelRatingResultsStatus', data)
                    .then(function (response) {
                        return angular.fromJson(response);
                    });
            }
        };
    });
