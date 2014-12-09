package com.qualcomm.demo

import grails.converters.JSON
import grails.converters.XML
import javax.annotation.PostConstruct


/**
 * Created by edgtzolvera on 12/9/14.
 */

/*
 * Creating a custom document marshaller to sanitize the properties of the domain objects that will be exposed
 * in the restful responses — while hiding internal implementation details (like class)
 */
class CustomDocumentMarshaller {

    @PostConstruct
    void registerMarshallers() {
        JSON.registerObjectMarshaller(Document) {
            return getDocumentAttributes(it)
        }

        XML.registerObjectMarshaller(Document) {
            return getDocumentAttributes(it)
        }

        JSON.registerObjectMarshaller(AuditDocument) {
            return getAuditDocumentAttributes(it)
        }

        XML.registerObjectMarshaller(AuditDocument) {
            return getAuditDocumentAttributes(it)
        }
    }


    private Map getDocumentAttributes(Document document) {
        [                    id: document.id,
                             version: document.version,
                             author: document.author,
                             pubDate: document.pubDate,
                             description: document.description,
                             hasHistory: document.hasHistory(),
                             dateCreated: document.dateCreated,
                             lastUpdated: document.lastUpdated,
        ]
    }

    private Map getAuditDocumentAttributes(AuditDocument document) {
        [                    id: document.id,
                             author: document.author,
                             pubDate: document.pubDate,
                             description: document.description,
                             fromDate: document.fromDate,
                             toDate: document.toDate,
                             version: document.version
        ]
    }

}
