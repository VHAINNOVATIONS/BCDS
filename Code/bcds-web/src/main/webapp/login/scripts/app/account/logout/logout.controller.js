'use strict';

angular.module('bcdsApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
