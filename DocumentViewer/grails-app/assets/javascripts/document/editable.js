/**
 * Created by edgtzolvera on 12/9/14.
 */


app.controller('EditableRowCtrl', function($scope, $http, $resource) {


    /*
    * Controller filters that correspond with their grails counterparts
    * sort: the field by which the data will be sorted
    * order: 'asc' or 'desc' ('asc' by default)
    * max: maximum number of results that should be returned
    * offset: pagination offset
    * */
    $scope.filters = {sort: 'id', order: 'asc', max: 100, offset: 0};

    //Document restful resource
    var Document = $resource(documentsUrl + '/:id',{id:'@id'},{
        query: {
            method: 'GET',
            url: documentsUrl,
            isArray: false, //false, the grails controller will return a result array + total results
            params: $scope.filters
        },
        history: {
            action: 'history',
            method: 'GET',
            isArray: true,
            url: documentsUrl + '/history/:id' //get historical change information
        },
        update: {
            method: 'PUT',
            isArray: false
        },
        create: {
            method: 'POST'
        }

    });

    //Variable initialization - Documents will be the placeholder for the documents information — null by default
    $scope.documents = [];

    //Data refresh, pageSize, page and total are required by the pagination directive, calculated based on the WS results.
    $scope.refreshData = function() {
        var data = Document.query(function(){
            $scope.documents = data.documents;
            $scope.pageSize = $scope.filters.max;
            $scope.page = Math.ceil($scope.filters.offset/ $scope.filters.max) + 1;
            $scope.total = data.totalCount;
        });
    };

    $scope.mode = 'edit';

    $scope.changeMode = function() {
        $scope.mode = 'edit';
    }

    //Perform the data refresh upon initialization (data load)
    $scope.refreshData();


    /*
    * Validation routines for author, description and pubDate
    */

    //Check that the author is not blank or over 255 characters
    $scope.checkAuthor = function(data) {
        if(!data)
            return "Author cannot be blank";
        if(data.length>255)
            return "Author cannot exceed 200 characters";
    }

    //Check that the description is not blank or over 2000 characters
    $scope.checkDescription = function(data) {
        if(!data)
            return "Description cannot be blank";
        if(data.length>2000)
            return "Description cannot exceed 2000 characters";
    }


    //Check that the publication Date is a indeed date-parcelable
    $scope.checkPubDate = function(data) {
        if(!data || !data instanceof Date)
            return "Publication Date must be in a yyyy-mm-dd format";
    }




    /*
     Sorting and pagination routines
     */

    //Sort the data by the specified field, reset the order to 'asc'
    $scope.sort = function(field) {
        //If we're changing to a new filter, always start by ascending order
        if($scope.filters.sort != field)
            $scope.filters.order = 'asc';
        $scope.filters.sort = field;
        $scope.refreshData();

    };

    //Perform the actual filtering of the data, reset the page and total counters to reflect the number of hits
    $scope.filter = function() {
        $scope.page = 1;
        $scope.total = 0;
        $scope.filters.offset = 0;
        $scope.refreshData();
    }


    //Change a result page (offset)
    $scope.setPage = function(page) {
        $scope.filters.offset = page * $scope.filters.max;
        $scope.refreshData();
    }

    //Clear filters if dirty and refresh data, also return to page 1
    $scope.clearFilters = function() {
        if($scope.filters.author || $scope.filters.description  || $scope.filters.pubDate || $scope.filters.id) {
            $scope.filters.author = null;
            $scope.filters.description = null;
            $scope.filters.pubDate = null;
            $scope.filters.id = null;
            $scope.page = 1;
            $scope.total = 0;
            $scope.filters.offset = 0;
            $scope.refreshData();
        }
    }



    /*
     * Restful document calls to get the history, update, create and delete remote documents
     */

    //Show an object's history (if available) and switch to the history view
    $scope.showHistory = function(object) {

        $scope.mode = 'history';
        $scope.historyCurrent = object;
        $scope.history = Document.history({id: object.id});
    }

    //Save a new document and replace the angular model with the returned value from the server side
    $scope.saveDocument = function(object) {
        var result = Document.create(object, function(){
            $scope.updateLocalDocument(object, result);
        });
    };

    //Update a remote document and update the local view with the results
    $scope.updateDocument = function(object) {
        var result = Document.update({id: object.id}, object, function(){
            $scope.updateLocalDocument(object, result);
        });
    };


    //Remove a remote document and update the local view with the result
    $scope.removeDocument = function(object) {
        Document.delete({id: object.id}, function(){
            $scope.removeLocalDocument(object);
        })
    };


    /*
     * Local operations over the dom.
     */

    //Remove the document from the local Angular model
    $scope.removeLocalDocument = function(object) {
        var index = $.inArray(object, $scope.documents);
        if(index > -1) {
            $scope.documents.splice(index, 1);
        }
    }

    //Update the local document with the results from the server
    $scope.updateLocalDocument = function(initial, final) {
        var index = $.inArray(initial, $scope.documents);
        if(index > -1) {
            $scope.documents[index] = final;
        }
    }

    //A a new local entry into the top
    $scope.addDocument = function() {
        $scope.inserted = new Document;
        $scope.documents.unshift($scope.inserted);
    };
});
