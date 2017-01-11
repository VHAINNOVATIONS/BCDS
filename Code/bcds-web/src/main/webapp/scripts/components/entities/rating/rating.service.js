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

            findModelRatingResults: function (processIds, filters, userId) {
                var data = {
                    processIds : (processIds.length === 0) ? null : processIds,           
                    fromDate: (filters == null) ? null : filters.fromDate,
                    toDate: (filters == null) ? null : filters.toDate,
                    modelType: (filters == null) ? null : angular.lowercase(filters.modelTypeOption),
                    userId: userId
                }
                return $http.post('api/modelRatingResults', data)
                    .then(function (response) {
                        return angular.fromJson(response);
                    });
            },

            updateModelRatingResultsStatus: function (processIds, decisions, userId) {
                var data = {
                    processIds : (processIds.length === 0) ? null : processIds,   
                    resultsStatus : (decisions.length === 0) ? null : decisions, 
                    userId: userId          
                }
                return $http.post('api/updateModelRatingResultsStatus', data)
                    .then(function (response) {
                        return angular.fromJson(response);
                    });
            },

            generateModelRatingResultsReport: function (processIds, filters, userId) {
                var data = {
                    processIds : (processIds.length === 0) ? null : processIds,           
                    fromDate: (filters == null) ? null : filters.fromDate,
                    toDate: (filters == null) ? null : filters.toDate,
                    modelType: (filters == null) ? null : angular.lowercase(filters.modelTypeOption),
                    reportType: (filters == null) ? null : angular.lowercase(filters.reportTypeOption),
                    userId: userId
                }
                return $http.post('api/modelRatingResultsReport', data)
                    .then(function (response) {
                        return angular.fromJson(response);
                    });
            }
        };
    });
