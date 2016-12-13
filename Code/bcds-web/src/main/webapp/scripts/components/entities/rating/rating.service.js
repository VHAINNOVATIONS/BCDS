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
                console.log(filters);
                var data = {
                        processIds : processIds,           
                        fromDate: (filters == null) ? null : filters.fromDate,
                        toDate: (filters == null) ? null : filters.toDate,
                        modelType: (filters == null) ? null : filters.modelTypeOption
                }
                return $http.post('api/modelRatingResults', data)
                    .then(function (response) {
                        return angular.fromJson(response);
                    });
            }
        };
    });
