 var app=angular.module("AutoTextSummarizer",["ngRoute", 'ngCookies']);

 app.run(['$rootScope', function($rootScope) {
        $rootScope.API_URL = 'http://localhost:8080';
    }]);

 app.config(function($routeProvider, $locationProvider) {
        $routeProvider
        .when("/", {
            templateUrl : "views/home.html"
        })
        .when("/home", {
            templateUrl : "views/home.html"
        })
        .when("/history", {
            templateUrl : "views/history.html"
        });
        //$locationProvider.hashPrefix('');
        $locationProvider.html5Mode(true);
    });


  app.controller("AtsController", ['$scope', '$rootScope', '$http', '$cookies', '$cookieStore', function($scope, $rootScope,$http,$cookies,$cookieStore, $timeout){
     
	  $rootScope.a="DeepDai";
    $rootScope.resetSession = function(){
	        $rootScope.loggedIn = false;
	        $rootScope.userId = false;
	        $rootScope.user = {}
	        $cookieStore.put('loggedIn', $rootScope.loggedIn);
	        $cookieStore.put('userId', $rootScope.userId);
	        $cookieStore.put('user', $rootScope.user);
	    }

	   // Cookies 
	    $rootScope.loggedIn = $cookieStore.get('loggedIn');
	    $rootScope.userId = $cookieStore.get('userId');
	    $rootScope.user = $cookieStore.get('user');
	
	    console.log('login', $rootScope.loggedIn);
	    
	    if(typeof($rootScope.loggedIn) === 'undefined'||$rootScope.loggedIn==false){
	        $rootScope.resetSession()
	
	    }else{
	    	$http.get($rootScope.API_URL+'/ats/user/'+$rootScope.userId).then(function(response){
	                var id = response.data.id;
	                if (typeof(id) !== 'undefined'){
	                    $rootScope.user = response.data;
	                    $rootScope.loggedIn = true;
	                    $rootScope.userId = response.data.id;
	                    $cookieStore.put('loggedIn', $rootScope.loggedIn);
	                    $cookieStore.put('userId', $rootScope.userId);
	                    $cookieStore.put('user', $rootScope.user);
	                }else{
	                    //alert('no user')
	                }
	        });
	
	    }
     $scope.logout=function()
      {
    	 
         	var retVal = confirm("Are You Aure to Logout ?");
                 if( retVal == true ) {
                	 $rootScope.resetSession();
                     return true;
                 } else {
                    $scope.loggedIn=true;
                    return false;
                 }
      }

      $scope.myHistory=function()
      {
        var user={id:1,userEmail:"dip@gmail.com"};
          $http({
          url:'http://127.0.0.1:8080/ats/history',
          method: "POST",
          data:user
        }).then(function(response){
                
                console.log(response.data);
                $scope.histories = response.data;
                window.location.href = '/history';          
               
           }, 
        function(response){
          swal("Oops!", "Error In Getting History!"+response, "warning");
              });
       }
  
   }]);

  var loginController=function($scope,$http,$rootScope, $cookies, $cookieStore){
	    $scope.user = {};
	    console.log('Hello Log in controller!!!');
	    $scope.userLogin=function(user)
	    {
	  
	      console.log('Hello Login');
	      var user=$scope.user;
	      console.log(user);
	      alert(user.userEmail);

	       $http({
	          url:'http://127.0.0.1:8080/ats/login',
	          method: "POST",
	          data:user
	        }).then(function(response){
	                
	                console.log(response.data);
	                var mydata = response.data;
	                if(mydata.userEmail==null)
	                {
	                  swal("Sorry!", "Incorrect username or Password !", "error");
	                }
	                else
	                { 
	                    var userid = mydata.id;
		                console.log('logged in user : ' + userid );
		                $rootScope.userId = userid;
		                $rootScope.loggedIn = true;
		                $cookieStore.put('loggedIn', $rootScope.loggedIn);   
		                $cookieStore.put('userId', $rootScope.userId);
		                $rootScope.helloUser=mydata.userFullname;
		                swal("Welcome "+$rootScope.helloUser+"!", "Logging In Successful!!", "success");
		                window.location.href = '/home';
		             }    
	           }, 
	        function(response){
	          swal("Oops!", "Error In Logging In!"+response, "warning");
	              });

	    }

	     $scope.forgotPassword=function()
	      {
	        alert($scope.forgotEmail);
	        var s='http://127.0.0.1:8080/ats/forgotpassword/'+$scope.forgotEmail;
	        alert(s);
	        $http({
	            url:s,
	            method: "GET",
	        }).then(function(response){
	            var mydata = response.data;
	            alert(mydata);
	            swal("Hello", mydata, "success");
	           }, 
	        function(response){
	        	   alert(response.data);
	          //swal("Oops!", "Error In Sending Email!"+response, "warning");
	              });
	          }
	  };

  var signupController =  function($scope, $http){
      $scope.user = {};
      $scope.errorMsz="";
      console.log('Hello Sign Up!!!');
      
      $scope.validatePassword = function(pw1, pw2){
    	  	if(pw1 != pw2){
				return "Two passwords not matched";
    	  		}

			if(pw1.length < 6){
				return "Password must be at least 6 characters";
				}

			return true;
		}

      
      $scope.userSignup = function(){
    	  
        var user = $scope.user;
         console.log("Hello Signup");
          console.log(user);
          
          var v = $scope.validatePassword(user.userPass,user.userConpass);

			if(v != true){
				alert(v)
				return;
			}

        $http({
          url: 'http://127.0.0.1:8080/ats/register',
          method: "POST",
          data: user
        }).then(function(response){
          var data=response.data;
          if(data==true)
          {
        	  swal("Welcome "+user.userFullname+"!", "Registration Successfull!!", "success");
              $scope.loggedIn=true;
           }
           else{
        	   swal("Sorry!", "Registration Failed!"+response, "error");

           }
        }, 
        function(response){
        	swal("Error!", "Registration Failed!"+response, "warning");

        });
      }
    };

    var summaryController =  function($scope,$rootScope,$http,$cookies,$cookieStore){

      $scope.source={};  
      $scope.getSourceContent=function()
      {
        var source=$scope.source;
       	$scope.source.userActive=$rootScope.loggedIn;
        $scope.source.userId=$rootScope.userId;
        
        console.log(source);
        alert(source.sourceUrl);
        alert(source.sentenceNumber);
     
        $http({
          url: 'http://127.0.0.1:8080/ats/getcontent',
          method: "POST",
          data: source
        }).then(function(response){
          console.log(response.data);
          var data=response.data;
          console.log(response);          
          if(data.sourceText==null){
        	  swal("Problem!", "Please enter the valid url", "error");
        	
	          }
          else
        	  {
	          	source.sourceText=data.sourceText;
        	  }
        }, 
        function(response){
        	swal("Problem!", "Error in generating summary !!!", "error");
        });
      }


      $scope.summarizer=function()
      {   
        var source=$scope.source;
        $scope.source.userActive=$rootScope.loggedIn; //for checking user logged in or not
        $scope.source.userId=$rootScope.userId;
        
        console.log(source);
        alert(source.userId);
        $http({
          url: 'http://127.0.0.1:8080/ats/summary',
          method: "POST",
          data: source
        }).then(function(response){
          var data=response.data;
          console.log(response);  

          $rootScope.mysummary=data.finalSummary;
          $scope.sumLength=(data.finalSummary).length;

          var text,i;
          for (i = 0; i <($scope.sumLength); i++) {
            text +=  ($rootScope.mysummary[i]);
          }
          $scope.myFinal=text;
          $rootScope.isSummary=true;

        }, 
        function(response){
          swal("OOps!", "Error in Generating Summary!"+response, "warning");

        });
      }
   };

    app.controller('signupController', signupController);
    app.controller('loginController', loginController);    
    app.controller('summaryController', summaryController);

