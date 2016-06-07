'use strict';

// declare modules
angular.module('Authentication', []);
angular.module('Dashboard', []);

angular.module('LoginPage', [
    'Authentication',
    'Dashboard',
    'ngRoute',
    'ngCookies'
])

.config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/login', {
            controller: 'LoginController',
            templateUrl: 'modules/authentication/views/login.html',
            hideMenus: true
        })
 
        .when('/', {
            controller: 'DashboardController',
            templateUrl: 'modules/dashboard/views/dashboard.html'
        })
		
		.when('/admin', {
            controller: 'DashboardController',
            templateUrl: 'modules/dashboard/views/admin.html'
        })

		.when('/modeler', {
            controller: 'DashboardController',
            templateUrl: 'modules/dashboard/views/modeler.html'
        })
		
        .otherwise({ redirectTo: '/login' });
}])
 
.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }
 
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                $location.path('/login');
            }
        });
    }]);