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
                params: {userId: {value : null, squash: true} },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/user-editor/user-editor.html',
                        controller: 'UserEditorController'
                    }
                },
                resolve: {
                	editUser: ['$stateParams', 'UserEditable', function($stateParams, UserEditable){if($stateParams.userId != null){
                		return UserEditable.get({id: $stateParams.userId}, function(result){
                			return result;
                		})
                	} else {
                		return {activated: false,
                    		authorities:[],
                            createdBy: null,
                            createdDate: null,
                            email: null,
                            firstName: null,
                            id: null,
                            login: null,
                            password: null,
                            lastModifiedBy: null,
                            lastModifiedDate: null};
                	}
                		
                	}]

                }
            });
    });
