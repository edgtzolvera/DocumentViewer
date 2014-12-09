class UrlMappings {

	static mappings = {

        /*
         * Adding the mapping for documents and history, the reason as to why we do
         * documents/history/1 (for example) instead of documents/1/history is that
         * the document can be deleted and thus the path would no longer make logical sense
         * However, by doing documents/history/1 we can congruently access even deleted documents
         */
        "/documents" (resources: 'document')

        "/documents/history/$id" (controller: 'document', action: 'history')

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
