'use strict';

angular.module('bcdssApp').config(function ($stateProvider, KeepaliveProvider, IdleProvider) {
	IdleProvider.idle(3479);
    IdleProvider.timeout(120);
    KeepaliveProvider.interval(3500);
    KeepaliveProvider.http('/api/keepsessionalive');
});