package com.demo

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(AuditDocument)
class AuditDocumentSpec extends Specification {

    @Unroll("Test Audit Document constraints for author: #author, description: #description, pubDate: #pubDate")
    void "Parametrized constraints test for the auditable document domain class"() {
        given:
        AuditDocument document = new AuditDocument(author: author,
                description: description,
                pubDate: pubDate,
                fromDate: fromDate,
                toDate: toDate)

        expect:
        document.validate() == shouldValidate
        document.errors?.errorCount  == errorCount

        where:
        author      | description   |   pubDate     | fromDate  | toDate    || shouldValidate | errorCount
        ""          |   ""          |   null        | null      | null      ||   false        | 5
        null        |   null        |   null        |new Date() |new Date() ||   false        | 3
        ""          |   "D"         |   null        |new Date() |new Date() ||   false        | 2
        "A"         |   "D"         | new Date()    |new Date() |new Date() ||   true         | 0
        "A" * 255   |   "D"         | new Date()    |new Date() |new Date() ||   true         | 0
        "A" * 256   |   "D"         | new Date()    |new Date() |new Date() ||   false        | 1
        "A"         |   "D" * 2000  | new Date()    |new Date() |new Date() ||   true         | 0
        "A"         |   "D" * 2001  | new Date()    |new Date() |new Date() ||   false        | 1
    }
}
