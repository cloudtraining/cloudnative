angular.module("app", []).controller("home", function($http) {
    var self = this;


    $http.get("/socialUser").success(function(data) {
      if(data.userAuthentication != undefined) {
          self.user = data.userAuthentication.details.name;
          self.authenticated = true;
          self.admin = data && data.roles && data.roles.indexOf("ROLE_ADMIN")>-1;
      }
    }).error(function() {
      self.user = "N/A";
      self.authenticated = false;
      self.admin = false;
    });


    self.credentials = {};

    self.login = function() {
      loginWithCredentials(self.credentials);
    };

    var loginWithCredentials = function(credentials) {
    		self.user = ''
    		$http.post('login', $.param(credentials), {
              headers : {
                "content-type" : "application/x-www-form-urlencoded"
              }
             }).success(function(data) {
    			loginSuccess();
    			credentials.username = null;
    			credentials.password = null;
    		}).error(function() {
    			self.authenticated = false;
    		});
    };

    var loginSuccess = function() {
            $http.get("/user").success(function(data) {
              self.user = data.name;
              self.authenticated = true;
              self.admin = data && data.roles && data.roles.indexOf("ROLE_ADMIN")>-1;
            }).error(function() {
              self.user = "N/A";
              self.authenticated = false;
              self.admin = false;
            });
     };

    self.logout = function() {
          $http.post('/logout', {}).success(function() {
            self.authenticated = false;
            $location.path("/");
          }).error(function(data) {
            console.log("Logout failed")
            self.authenticated = false;
            self.admin = false;
          });
     };
});