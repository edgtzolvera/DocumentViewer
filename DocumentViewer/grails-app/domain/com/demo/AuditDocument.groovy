package com.demo

import groovy.transform.EqualsAndHashCode


@EqualsAndHashCode
class AuditDocument extends AbstractDocument implements Serializable {

    long id
    Date fromDate
    Date toDate
    long version

    static mapping = {
        version bindable: true
        id composite: ['id', 'fromDate'], bindable: true
    }

    static constraints = {
        //not really necessary as they are non-nullable by default, but here for readability and domain documentation
        fromDate nullable: false
        toDate nullable: false
    }
}
