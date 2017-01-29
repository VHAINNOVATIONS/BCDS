'use strict';

angular.module('bcdssApp')
    .factory('BulkProcessClaimService', function ($http, $resource, DateUtils) {
        return {
            'bulkProcessClaims': function (filters, userId, recordCount, doSave) {
                var data = {
                    fromDate: filters.bpFromDate,
                    toDate: filters.bpToDate,
                    regionalOfficeNumber: filters.regionalOfficeOption,
                    modelType: filters.modelTypeOption,
                    userId: userId,
                    recordCount: recordCount,
                    saveParams: doSave
                }
                return $http.post('api/claims/bulkprocess', data)
                .then(function (response) {
                    return angular.fromJson(response);
                });
            }
        };
    });
