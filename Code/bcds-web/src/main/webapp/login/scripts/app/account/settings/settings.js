'use strict';

angular.module('bcdsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('settings', {
                parent: 'account',
                url: '/settings',
                data: {
                    roles: ['ROLE_MODEL_EXECUTOR','ROLE_MODEL_MANAGER','ROLE_ADMIN'],
                    pageTitle: 'Settings'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/settings/settings.html',
                        controller: 'SettingsController'
                    }
                },
                resolve: {
                    
                }
            });
    });
