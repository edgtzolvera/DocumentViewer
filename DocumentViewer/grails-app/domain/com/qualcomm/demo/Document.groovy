package com.qualcomm.demo

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode


@Resource(uri="/documents") //Exposing this domain class as a restful resource
@EqualsAndHashCode
class Document extends AbstractDocument {

    //Auto-timestamps to determine creation and last modification dates
    Date dateCreated
    Date lastUpdated

    static constraints = {
        //I never want these to be modifiable by whatever comes from a web-service request
        dateCreated bindable: false
        lastUpdated bindable :false
    }


    /*
     * Tracking historical changes to the document whenever it is updated or deleted at the GORM side
     * There are several ways to do this, but I prefer to do it @ the GORM side so that the storage backend
     * can be decoupled from the application's implementation — and it also allows for good supplemental information
     * for instance, in the future we could decide to store the IP that modified a document or even the username
     * if authentication is warranted.
     */
    def beforeUpdate() {
        insertAuditRecord()
    }

    def beforeDelete() {
        insertAuditRecord()
    }

    boolean hasHistory() {
        boolean hasHistory
        if(version > 0)
            hasHistory = true
        return hasHistory
    }

    private void insertAuditRecord(){
        def dataRow = this //because inside the closure, this will have a different meaning (closure itself)
        AuditDocument document = new AuditDocument()
        //Get the previous database values and pop them into the new document
        ['author', 'pubDate', 'description', 'version'].each{ document."$it" = dataRow.getPersistentValue(it) }
        document.fromDate = lastUpdated
        document.toDate = new Date()
        document.id = this.id

        //Insert the historical document
        AuditDocument.withNewSession {
            document.save(insert: true, flush: true)
        }
    }
}
