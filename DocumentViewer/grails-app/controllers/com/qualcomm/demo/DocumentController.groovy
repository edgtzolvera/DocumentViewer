package com.qualcomm.demo
import org.apache.commons.lang.time.DateUtils
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.grails.datastore.mapping.query.api.BuildableCriteria
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DocumentController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", history: "GET"]

    GrailsApplication grailsApplication

    /*
     * Creating a special restful index, being that the requirements state that the data may expand up to
     * 20,000 records, it would be a bad idea to attempt to sort and filter them out at the browser side only.
     * This method will not be a "classic" restful index in the sense that it will return both a list of relevant
     * documents and a total number of hits (when paginated).
     * User can filter through any of the properties, and they are all loosely matched with the exception of the id.
     * The rest of the controller is mostly a stock one.
     */
    def index(Integer max) {
        params.pubDate = params.date('pubDate', grailsApplication.config.grails.databinding.dateFormats)
        params.max = Math.min(max ?: 10, 100)
        params.offset = params.long('offset') ?: 0

        /*
         * Using an old fashioned criteria to create a dynamic query for the searching and filtering
         */
        BuildableCriteria criteria = Document.createCriteria()
        def criteriaResult = criteria.list(max: params.max, offset: params.offset) {
            and {
                if(params.id?.isLong())
                    eq('id', params.id?.toLong())
                if(params.author)
                    ilike('author', "%${params.author}%")
                if(params.description)
                    ilike('description', "%${params.description}%")
                if(params.pubDate)
                    between('pubDate', getStartOfDay(params.pubDate), getEndOfDay(params.pubDate))
            }
            order(params.sort ?: 'id', params.order ?: 'asc') //get order from params, if nothing specified do id / desc
        }
        respond([documents: criteriaResult, totalCount: criteriaResult.totalCount] as Object, [status: OK])
    }


    //Returns the start of the day for a given date
    private Date getStartOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE)
    }

    //Returns the end of the day for a given date
    private Date getEndOfDay(Date date) {
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1)
    }

    //Adding a custom history method, which will return all audit documents for a particular id
    def history(long id) {
        respond AuditDocument.findAllById(id), [status: OK]
    }


    def show(Document documentInstance) {
        respond documentInstance, [status: OK]
    }

    @Transactional
    def save(Document documentInstance) {
        if (documentInstance == null) {
            render status: NOT_FOUND
            return
        }

        documentInstance.validate()
        if (documentInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        documentInstance.save flush: true
        respond documentInstance, [status: CREATED]
    }

    @Transactional
    def update(Document documentInstance) {
        if (documentInstance == null) {
            render status: NOT_FOUND
            return
        }

        documentInstance.validate()
        if (documentInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        documentInstance.save flush: true
        respond documentInstance, [status: OK]
    }

    @Transactional
    def delete(Document documentInstance) {

        if (documentInstance == null) {
            render status: NOT_FOUND
            return
        }

        documentInstance.delete flush: true
        render status: NO_CONTENT
    }
}
