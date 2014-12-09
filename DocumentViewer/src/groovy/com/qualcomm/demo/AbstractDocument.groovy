package com.qualcomm.demo
/**
 * Created by edgtzolvera on 12/9/14.
 */

/*
 * Creating an abstract document class that I will use to inherit
 * some common behavior to both the history document and the base document
 * which will be kept in separate domains, this to simplify integration
 * with future applications or dashboards that may need to consume the data
 */
abstract class AbstractDocument {
    String author //author of the document
    String description //document content
    Date pubDate //publication date

    //Providing some sane defaults and supplementing it with Mark's feedback
    static constraints = {
        author size: 1..255 //can't be empty, and shouldn't have more than 255 characters
        description size: 1..2000 //no more than 2000 characters (text-area)
        pubDate nullable: false //false by default and thus unnecessary, but leaving this in for readability
    }
}
