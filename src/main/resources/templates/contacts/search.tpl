layout 'layout.tpl', title: 'Search Result',
	content: contents {
	div (class:'container') {

		div (class:'pull-right') {
			a (href:'/', 'Home')
		}

		div (class:'navbar') {}
		div(class:'container', style: 'max-height:500px; overflow-x:hidden; overflow-y:scroll') {
			div (class:'navbar') { yield query? "Query: '${query}'": "" }
			table(class:'table table-bordered table-striped') {
				thead {
					tr {
						td 'Id'
						td 'Name'
						td 'Phone'
						td '&#x270D;'
						td '&#10006;'
					}
				}
				tbody {
					if (!contacts) { tr { td(colspan:'5', 'No Contacts' ) } }
					contacts.each { contact ->
						tr {
							td contact.id 
							td contact.name
							td contact.phone
							td {
								a(href:"/edit-${contact.id}") {
									yield "edit"
								}
							}
							td {
								a(href:"/remove-${contact.id}") {
									yield "remove"
								}
							}
						}
					}
				}
			}
		}

		div (class:'form-actions') {
			form (id:'searchForm', action:'/search', method:'post') {
				if (searchStatus) {
					div (class:'alert alert-success', searchStatus)
				}

				label (for:'query', 'Search:')
				textarea (name:'query') {}
				div(class:'container') {
					input (type:'submit', value:'Search')
				}
			}
		}
	}
}