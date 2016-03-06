layout 'layout.tpl', title: 'Phone Book',
content: contents {
	div (class:'navbar') {}

	if (globalStatus) {
		div (class:'alert alert-success', globalStatus)
	}
	
	if (econtact) {
		div (class:'form-actions') {

			h5("Edit contact")
			div (class:'navbar') {}
			form (id:'editForm', action: econtact?.id? "/form-${econtact.id}": "", method:'post') {
				if (editStatus) {
					div (class:'alert alert-success', editStatus)
				}

				label (for:'edit-name', 'Name')
				textarea (name:'edit-name') {yield econtact?.name?:"" }
				label (for:'edit-phone', 'Phone')
				textarea (name:'edit-phone') {yield econtact?.phone?:"" }
				div(class:'container') {
					input (type:'submit', value:'Edit')
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

	div (class:'navbar') {}
	div(class:'container', style:'max-height:500px; overflow-x:hidden; overflow-y:scroll') {
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
		h5("New contact:")
		div (class:'navbar') {}

		form (id:'addForm', action:'/add', method:'post') {
			if (addStatus) {
				div (class:'alert alert-success', addStatus)
			}

			label (for:'name', 'Name')
			textarea (name:'name', type:'text') {yield newcontact?.name?:""}
			label (for:'phone', 'Phone')
			textarea (name:'phone') {yield newcontact?.phone?:""}
			div(class:'container') {
				input (type:'submit', value:'Add')
			}
		}
	}
}