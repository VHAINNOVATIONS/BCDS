'use strict';

angular.module('bcdssApp').run(function($rootScope, Idle) {
        Idle.watch();
});
