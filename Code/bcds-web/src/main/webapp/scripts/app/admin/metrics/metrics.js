'use strict';

angular.module('bcdsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('metrics', {
                parent: 'admin',
                url: '/metrics',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'Application Metrics'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/metrics/metrics.html',
                        controller: 'MetricsController'
                    }
                },
                resolve: {
                    
                }
            });
    });
