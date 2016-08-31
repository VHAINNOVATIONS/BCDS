'use strict';

angular.module('bcdssApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('usereditor', {
                parent: 'home',
                url: 'user-editor/:userId',
                data: {
                    roles: [],
                    pageTitle: 'User Editor'
                },
                params: {},
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/user-editor/user-editor.html',
                        controller: 'UserEditorController'
                    }
                },
                resolve: {
                	editUser: ['$stateParams', 'UserEditable', function($stateParams, UserEditable){
                		return UserEditable.get({id: $stateParams.userId}, function(result){
                			return result;
                		})
                	}]

                }
            });
    });
