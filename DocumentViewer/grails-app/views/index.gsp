<!DOCTYPE html>
<html ng-app="myApp">
<head>
	<meta name="layout" content="main"/>
	<title>Welcome to Grails</title>
</head>
<body>
<div class="container" style="width: 100%">

	<script language="JavaScript">
		var documentsUrl = "${g.createLink(uri: '/documents')}";
		var historyUrl = "${g.createLink(uri: '/documents/history')}";
	</script>


	<div ng-switch on="mode" ng-controller="EditableRowCtrl">

		<div id="edit-pane" ng-switch-when="edit">
			<g:render template="edit" contextPath="/document"/>
		</div>
		<div id="history-pane" ng-switch-when="history">
			<g:render template="history" contextPath="/document" />
		</div>
	</div>

</div>
</body>
</html>
