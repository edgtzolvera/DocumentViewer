/**
 * Created by edgtzolvera on 12/9/14.
 */


//Calling this app the document viewer, depending upon X-Editable (3rd party), Angular Animate and Angular Resource (REST)
var app = angular.module('documentViewer', ['xeditable', 'ngAnimate', 'ngResource']);

//Using bootstrap 3 theme
app.run(function(editableOptions) {
    editableOptions.theme = 'bs3';
});


//Register 3rd party transformer to convert any string dates into JS counterparts
app.config(["$httpProvider", function ($httpProvider) {
    $httpProvider.defaults.transformResponse.push(function(responseData){
        convertDateStringsToDates(responseData);
        return responseData;
    });
}]);


/**
 * This was sourced from http://embed.plnkr.co/27fBNbHmxx144ptrCuXV/preview
 * It was just modified to use sweet alerts instead of the regular alerting.
 * A generic confirmation for risky actions.
 * Usage: Add attributes: ng-really-message="Really?" ng-really-click="takeAction()" function
 */
angular.module('documentViewer').directive('ngReallyClick', [function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('click', function() {
                swal({
                    title: "Are you sure?",
                    text: attrs.ngReallyMessage,
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonClass: "btn-danger",
                    confirmButtonText: "Yes, delete it!",
                    closeOnConfirm: true
                }, function(){
                    scope.$apply(attrs.ngReallyClick);
                });
            });
        }
    }
}]);


