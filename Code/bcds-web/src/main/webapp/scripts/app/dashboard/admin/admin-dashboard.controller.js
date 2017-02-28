'use strict';

angular.module('bcdssApp').controller('AdminDashboardController', function($rootScope, $scope, $state, $stateParams, UserEditable, $filter, Account, UserRole,
																			$q, DTOptionsBuilder, DTColumnBuilder, $compile, $timeout, $modal, spinnerService,
																			ResetPassword, Principal) {
	$scope.displayStatus = false;
	$scope.editableUsers = [];
	$scope.serverErrorMsg = "Something went wrong! Please contact the site administrator."
	$scope.dtInstance = {};
	$scope.editUser = {};
	$scope.userroles = [];
	$scope.modal = {
      instance: null
    };

	$scope.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
    	 return new Promise( function(resolve, reject){
             if ($scope.editableUsers)
               resolve($scope.editableUsers);
             else
               resolve([]);
           });
    })
    .withOption('createdRow', function(row, data, dataIndex) {
        // Recompiling so we can bind Angular directive to the DT
        $compile(angular.element(row).contents())($scope);
        angular.forEach(row.cells, function(cell){
            $(cell).attr('title', function (index, attr) {
                return this.outerText;
            });
        }); 
    })
    .withOption('headerCallback', function(header) {
        angular.forEach(header.cells, function(cell){
            $(cell).attr('title', function (index, attr) {
                return this.outerText;
            });
        }); 
        $('.dataTables_filter input').attr('title', 'Type here to search in the table');
        	if (!self.headerCompiled) {
            // Use this headerCompiled field to only compile header once
	            self.headerCompiled = true;
	            $compile(angular.element(header).contents())($scope);
	            $compile(angular.element('.dt-buttons').contents())($scope);
       		}
    })
    .withBootstrap()
    .withDOM('Bfrtip')
    //.withOption('bLengthChange', false)
    .withOption('processing', true)
    .withOption('bAutoWidth', false)
    .withOption('pageLength', 15)
    .withOption('fnDrawCallback', function (settings) {
        console.log("DataTable drawCallback");
        if (settings.aoData.length > 0) {
            var paginationButtons =  $('.pagination');
            angular.forEach(paginationButtons, function(node) {
                var childNodes = node && node.childNodes;
                angular.forEach(childNodes, function(listNode){
                    var pbutton = listNode.childNodes && listNode.childNodes[0];
                    if(pbutton){
                        var text = $(pbutton).text(),
                        title = isNaN(text) ? text+' page' : 'Page '+text;
                        $(pbutton).attr('title', title);
                    }
                });
            }); 
        }
    })
    //.withOption('responsive', true)
    .withOption('order', [[0, 'desc']])
    .withButtons([{
	        text: '<a name="Create User" id="btnProcessClaim">Create User</a>',
            titleAttr: 'Create User',
	        action: function (e, dt, node, config) {
	        	$('#createOrEditUserDialog').modal('show');
	        },
	    },
        {
          text: '<a name="Clear">Clear</a>',
          titleAttr: 'Clear',
          action: function (e, dt, node, config) {
              $scope.clear();
          }
        }
    ]);

    $scope.dtColumns = [
		DTColumnBuilder.newColumn('id').withTitle('ID').notSortable().renderWith(function(data, type, full, meta) {
		   	return "<div>"+ data + "</div>"
        }),
        DTColumnBuilder.newColumn('login').withTitle('Login ID'),
        DTColumnBuilder.newColumn('firstName').withTitle('Name').renderWith(function(data, type, full) {
            return "<div>"+ data + "</div>"
        }),
        DTColumnBuilder.newColumn('email').withTitle('E-mail'),
        DTColumnBuilder.newColumn(null).withTitle('Status').renderWith(function(data, type, full) {
        	return "<div>"+ $scope.getStatusString(data.activated) + "</div>"
        }),
        DTColumnBuilder.newColumn(null).withOption('width', '250px').withTitle('Role').renderWith(function(data, type, full) {
            return "<div>"+ $scope.getRoles(data.authorities) + "</div>"
        }),
        DTColumnBuilder.newColumn('createdDate').withOption('width', '100px').withTitle('Date Created').renderWith(function(data, type, full) {
            return "<div>"+ $scope.formatDate(data) + "</div>"
        }),
        DTColumnBuilder.newColumn(null).withOption('width', '15px').withTitle('Action').renderWith(function(data, type, full) {
            return "<div>" + '<a href="" id="'+ full.id +'" title="Edit User" ng-click="createOrEditUser('+full.id+')"><span>Edit</span></a>' + "</div>"
        })
    ];

    $scope.activeUserOptions = [
	    {value: false, label: 'False'},
	    {value: true, label: 'True'},
	];

	$scope.editUser.activated = $scope.activeUserOptions[0].value; // Default
    
    $scope.loadRoles = function () {
		UserRole.query(function(result){
			$scope.userroles = result;
		});
	};

	$scope.loadRoles();

    $scope.getUserName = function(){
   		return ($rootScope.userName != null) ? $rootScope.userName : Principal.userName();
    };

    $scope.formatDate = function(date) {
		var date = new Date(date);
            return ('0' + (date.getMonth()+1)).slice(-2) + '/' +
                ('0' + date.getDate()).slice(-2) + '/'
                + date.getFullYear();
	};

    $scope.createOrEditUser = function(userId) {
    	$scope.isEditUser = false;
    	$scope.editUser = {};
    	if(userId != null && userId != ''){
    		var editUser = $filter('filter')($scope.editableUsers, {id: userId}, true);
    		if(editUser[0] != null && editUser[0] != undefined && editUser.length > 0){
    			console.log("editUser::" + editUser[0].id);
    			$scope.isEditUser = true;
    			$scope.editUser = editUser[0];
    			$scope.setUserValuesForEdit(editUser[0]);
    		}
    	}

    	$('#createOrEditUserDialog').modal('show');
    };

    $scope.setUserValuesForEdit = function(editUserData){
    	$scope.editUser.login = editUserData.login;
    	$scope.editUser.activated = editUserData.activated;
    	$scope.editUser.authorities = editUserData.authorities;
    	$scope.editUser.firstName = editUserData.firstName;
    	$scope.editUser.email = editUserData.email;
    	$scope.editUser.password = editUserData.password;
    };

    $scope.getRoles = function(data){
    	if (data != null) {
    		var roles = "";
    		angular.forEach(data, function(role){
    			roles+=role.name;
    		});
    	}

    	return roles;
    };

    $scope.getStatusString = function(state){
    	return ((state) ? "Active" : "Inactive");
    };

    $scope.clear = function(){
    	$scope.editUser = {};
    	$scope.isEditUser = false;
    	$scope.displayStatus = false;
    	$scope.loadAllUsers();
        $scope.dtInstance.DataTable.search('');
    };

	$scope.loadAllUsers = function (){
		$scope.displayStatus = false;
		spinnerService.show('manageUsersSpinner');
    	UserEditable.getAllUsers().then(function(result) {
    		$scope.editableUsers = result.data;
           	var promise = new Promise( function(resolve, reject){
                if ($scope.editableUsers)
                  resolve($scope.editableUsers);
                else
                  resolve([]);
              });
    		if($scope.editableUsers.length > 0) {
                spinnerService.hide('manageUsersSpinner');
                $timeout(function() {
                    $scope.dtInstance.changeData(function() {
                        return promise;
                    });
                }, 10);
			}
    	})
    	.catch(function(e){
            $scope.serverErrorMsg = (e && e.data.message != null) ? e.data.message : $scope.serverErrorMsg;
            $scope.callErrorDialog();
            spinnerService.hide('manageUsersSpinner');
        });
	};

	$scope.resetPassword = function() {
		console.log("userId Before Change::" + $scope.editUser.password);
		if($scope.editUser && $scope.editUser.login != null && $scope.editUser.login != ''){
			ResetPassword.get({login: $scope.editUser.login}, function(result){
				$scope.editUser.password = result[0];
				console.log("userId After Change::" + $scope.editUser.password);
				$scope.displayStatus = true;
			});
		}else{
			$scope.serverErrorMsg = "No user id found.";
            $scope.callErrorDialog();
		}

	};

	$scope.save = function () {
		$scope.editUser.userId = $scope.getUserName();
		var roles = [];
		angular.forEach($scope.editUser.authorities, function(role){
    			roles.push(role);
    	});
    	$scope.editUser.authorities = roles;
    	spinnerService.show('manageUsersSpinner');
    	$('#createOrEditUserDialog').modal('hide');
		if ($scope.editUser.id != null) {
			UserEditable.updateUser($scope.editUser).then(function(result) {
				console.log('>>>successful update');
    			$scope.loadAllUsers();
	    	})
			.catch(function(e){
		        $scope.serverErrorMsg = (e && e.data.message != null) ? e.data.message : $scope.serverErrorMsg;
		        $scope.callErrorDialog();
		        spinnerService.hide('manageUsersSpinner');
	        });
		} else {
			UserEditable.saveUser($scope.editUser).then(function(result) {
				console.log('>>>successful save');
    			$scope.loadAllUsers();
	    	})
			.catch(function(e){
		        $scope.serverErrorMsg = (e && e.data.message != null) ? e.data.message : $scope.serverErrorMsg;
		        $scope.callErrorDialog();
		        spinnerService.hide('manageUsersSpinner');
	        });
		}
    };
    
	$scope.callErrorDialog = function (size) {
            $scope.modal.instance = $modal.open({
            template: '<error-dialog modal="modal" bold-text-title="Error:" text-alert="'+ $scope.serverErrorMsg + '" mode="danger"></error-dialog>',
            scope: $scope,
        });
    };
});