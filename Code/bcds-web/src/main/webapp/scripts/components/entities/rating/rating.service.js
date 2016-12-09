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

            findModelRatingResults: function (filters) {
                console.log(filters);
                var data = {
                        processId : 1, //filters.modelResultId,           
                        fromDate: filters.fromDate,
                        toDate: filters.toDate,
                        modelType: filters.regionalOfficeOption
                }
                return $http.post('api/modelRatingResults', data)
                    .then(function (response) {
                        return angular.fromJson(response);
                    });
            }
        };
    });
