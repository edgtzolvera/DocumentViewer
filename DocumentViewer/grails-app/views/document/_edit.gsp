<h3>Document Editor <span ng-if="total" class="label label-primary">{{total}} Results</span> </h3>

<table class="table table-striped">
    <thead>
    <tr style="font-weight: bold">

        <th style="width:10%">
            <div>
                <a href="#" ng-click="filters.order = filters.order == 'asc' ? 'desc' : 'asc'; sort('id');">
                    ID <span ng-if="filters.sort == 'id'">
                    <span ng-if="filters.order == 'desc'">
                        <span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
                    </span>
                    <span ng-if="filters.order == 'asc'">
                        <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
                    </span>
                </span>
                </a>
            </div>
            <div>
                <input class="form-control" type="text" ng-model="filters.id" ng-change="filter()" ng-model-options="{debounce:1000}">
            </div>

        </th>


        <th style="width:25%">
            <div>
                <a href="#" ng-click="filters.order = filters.order == 'asc' ? 'desc' : 'asc'; sort('author');">
                    Author <span ng-if="filters.sort == 'author'">
                    <span ng-if="filters.order == 'desc'">
                        <span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
                    </span>
                    <span ng-if="filters.order == 'asc'">
                        <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
                    </span>
                </span>
                </a>
            </div>
            <div>
                <input class="form-control" type="text" ng-model="filters.author" ng-change="filter()" ng-model-options="{debounce:1000}">
            </div>

        </th>
        <th style="width:20%">


            <div>
                <a href="#" ng-click="filters.order = filters.order == 'asc' ? 'desc' : 'asc'; sort('pubDate');">
                    Publication Date<!--[if IE 9 ]> (YYYY-MM-DD)<![endif]--><span ng-if="filters.sort == 'pubDate'">
                    <span ng-if="filters.order == 'desc'">
                        <span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
                    </span>
                    <span ng-if="filters.order == 'asc'">
                        <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
                    </span>
                </span>
                </a>
            </div>
            <div>
                <input class="form-control" type="date" placeholder="YYYY-MM-DD" ng-model="filters.pubDate" ng-change="filter()" ng-model-options="{debounce:1000, timezone:'UTC'}">
            </div>

        </th>

        <th style="width:20%">

            <div>
                <a href="#" ng-click="filters.order = filters.order == 'asc' ? 'desc' : 'asc'; sort('description');">
                    Description <span ng-if="filters.sort == 'description'">
                    <span ng-if="filters.order == 'desc'">
                        <span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
                    </span>
                    <span ng-if="filters.order == 'asc'">
                        <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
                    </span>
                </span>
                </a>
            </div>
            <div>
                <input class="form-control" type="text" ng-model="filters.description" ng-change="filter()" ng-model-options="{debounce:1000}">
            </div>

        </th>
        <th class="no-pad" style="width:25%">
            <button title="Clear Filters" class="btn btn-default" ng-click="clearFilters()"><i class="fa fa-ban fa-fw"></i> Clear Filters</button>
            <button class="btn btn-primary" ng-click="addDocument()"><i class="fa fa-fw fa-plus"></i> Add row</button>
        </th>
    </tr>
    </thead>
    <tbody>
    <tr ng-repeat="document in documents" class="{{ document.id ? '' : 'info' }} document">

        <td>
            {{document.id || 'Not Saved'}}
        </td>

        <td>
            <!-- editable username (text with validation) -->
            <span editable-text="document.author" e-name="author" e-class="form-control" e-form="rowform" onbeforesave="checkAuthor($data)" e-required>
                {{ document.author || 'empty' }}
            </span>
        </td>
        <td>
            <!-- editable status (select-local) -->

            <span editable-bsdate="document.pubDate" e-type="date" e-class="form-control"  e-placeholder="YYYY-MM-DD" e-ng-model-options="{timezone:'UTC'}" e-name="pubDate" e-form="rowform" onbeforesave="checkPubDate($data)" e-required>
                {{ (document.pubDate |date : 'longDate' : 'UTC') || 'empty' }}
            </span>
        </td>


        <td>
            <!-- editable group (select-remote) -->
            <span editable-textarea="document.description" e-class="form-control"  e-rows="3" e-cols="120" e-name="description" e-form="rowform" onbeforesave="checkDescription($data)" e-required>
                {{ document.description || 'empty' }}
            </span>
        </td>
        <td>
            <!-- form -->
            <form editable-form name="rowform" onbeforesave="document.id ? updateDocument(document) : saveDocument(document)" ng-show="rowform.$visible" class="form-buttons form-inline" shown="inserted == document" novalidate>
                <button type="submit" ng-disabled="rowform.$waiting; checkform(rowForm);" class="btn btn-primary">
                    <i class="fa fa-fw fa-save"></i> Save
                </button>
                <button type="button" ng-disabled="rowform.$waiting" ng-click="rowform.$cancel()" class="btn btn-default">
                    Cancel
                </button>
            </form>

            <div class="buttons" ng-show="!rowform.$visible">
                <button title="Edit Document" class="btn btn-primary" ng-click="rowform.$show()"><i class="fa fa-edit fa-fw"></i></button>
                <button title="Delete Document" class="btn btn-danger" ng-really-message="This action cannot be undone" ng-really-click="document.id ? removeDocument(document) : removeLocalDocument(document)"><i class="fa fa-trash fa-fw"></i></button>
                <span ng-if="document.hasHistory"><button title="Show Document History" class="btn btn-info" ng-click="showHistory(document)"><i class="fa fa-history  fa-fw"></i></button></span>
            </div>
        </td>
    </tr>
    </tbody>
</table>

<div ng-if="total > 0" paging page="page" page-size="pageSize" total="total" paging-action="setPage(page-1)"></div>








