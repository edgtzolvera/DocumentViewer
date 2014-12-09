package com.qualcomm.demo

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Document)
class DocumentSpec extends Specification {

    @Unroll("Test Constraints for author: #author, description: #description, pubDate: #pubDate")
    void "Parametrized constraints test for the document domain class"() {
        given:
        Document document = new Document(author: author, description: description, pubDate: pubDate)

        expect:
        document.validate() == shouldValidate
        document.errors?.errorCount  == errorCount

        where:
        author              |   description |   pubDate     || shouldValidate   | errorCount
        ""                  |   ""          |   null        ||      false       | 3
        null                |   null        |   null        ||      false       | 3
        ""                  |   "D"         |   null        ||      false       | 2
        "A"                 |   "D"         | new Date()    ||      true        | 0
        "A" * 255           |   "D"         | new Date()    ||      true        | 0
        "A" * 256           |   "D"         | new Date()    ||      false       | 1
        "A"                 |   "D" * 2000  | new Date()    ||      true        | 0
        "A"                 |   "D" * 2001  | new Date()    ||      false       | 1
    }
}

