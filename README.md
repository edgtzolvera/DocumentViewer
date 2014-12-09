DocumentViewer
==============

Document Viewer Demo, using Grails 2.4.4 and Angular JS

The project uses Grails 2.4.4 and Java 7 — MySQL is an optional component and is currently only enabled for the production environment.

1) The application has the following configurable option:

demo.bootstrapData Defaults to false.

If enabled, it will create bootstrap data for an environment other than development. 

2) If running as production, the application expects to find a MySQL Instance in the default port (3306) of the localhost, with a database called 'document-viewer' — It will attempt to connect using the username root with no password (default for most MySQL installations).

This may be easily overriden by editing the DataSource.groovy

IMPLEMENTED FEATURES:

- Creation, edition and deletion of document records
- Server side filtering, pagination and sorting by any of the document's data fields
- Change tracking: clicking on the history button will display all previous historical data values for a given document. 
