'use strict';

angular.module('bcdssApp').config(function ($stateProvider, KeepaliveProvider, IdleProvider) {
	IdleProvider.idle(600);
    IdleProvider.timeout(120);
    KeepaliveProvider.interval(897);
    KeepaliveProvider.http('/api/keepsessionalive');
});