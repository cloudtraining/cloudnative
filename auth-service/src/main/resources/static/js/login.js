angular.module("app", []).controller("home", function($http) {
    var self = this;

    $http.get("/user").success(function(data) {
      self.user = data.userAuthentication.details.name;
      self.authenticated = true;
      self.admin = data && data.roles && data.roles.indexOf("ROLE_ADMIN")>-1;
    }).error(function() {
      self.user = "N/A";
      self.authenticated = false;
      self.admin = false;
    });

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