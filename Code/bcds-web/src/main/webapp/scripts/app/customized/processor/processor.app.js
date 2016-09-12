'use strict';

angular
	.module('bcdssApp')
	.config(
		function($stateProvider) {
            $stateProvider
                .state(
                'processor',
                {
                    parent: 'home',
                    url: '/processor',
                    data: {
                        roles: [ 'ROLE_MODEL_EXECUTOR' ],
                        pageTitle: 'Process Results'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'scripts/app/manager/processor/processor.html',
                            controller: 'ProcessorController'
                        }
                    },
                    resolve: {
                    }
                })
        });

