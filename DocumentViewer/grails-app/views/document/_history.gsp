<h3>History for document {{historyCurrent.id}} <span class="badge">{{totalCount}}</span></h3>


<table class="table table-striped">
    <thead>
    <tr style="font-weight: bold">

        <th>ID</th>
        <th>Version</th>
        <th>Author</th>
        <th>Publication Date</th>
        <th>Description</th>
        <th>Valid From</th>
        <th>Valid To</th>
    </tr>
    </thead>
    <tbody>
    <tr ng-repeat="document in history">

        <td>{{document.id || 'Not Saved'}}</td>
        <td>{{document.version}}</td>
        <td>{{ document.author || 'empty' }}</td>
        <td>{{ (document.pubDate |date : 'longDate' : 'UTC') || 'empty' }}</td>
        <td>{{ document.description || 'empty' }}</td>
        <td>{{ document.fromDate | date: "MMMM d, y 'at' h:mm:ss a Z" }}</td>
        <td>{{ document.toDate | date: "MMMM d, y 'at' h:mm:ss a Z" }}</td>
    </tr>
    <tr ng-if="historyCurrent">
        <td>{{historyCurrent.id || 'Not Saved'}}</td>
        <td>{{historyCurrent.version}}</td>
        <td>{{ historyCurrent.author || 'empty' }}</td>
        <td>{{ (historyCurrent.pubDate |date : 'longDate' : 'UTC') || 'empty' }}</td>
        <td>{{ historyCurrent.description || 'empty' }}</td>
        <td>{{ historyCurrent.lastUpdated | date: "MMMM d, y 'at' h:mm:ss a Z" }}</td>
        <td>Currently Active</td>
    </tr>
    </tbody>
</table>

<button class="btn btn-primary" ng-click="changeMode()"><i class="fa fa-fw fa-chevron-left"></i> Return to Documents</button>

