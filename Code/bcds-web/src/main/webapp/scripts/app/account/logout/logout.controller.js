'use strict';

angular.module('bcdssApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
