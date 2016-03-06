html {
	head {
		title("${title} &#9742;")
		link(rel:'stylesheet', href:'/css/bootstrap.min.css')
	}
	body {
		div(class:'container') {
			div(class:'navbar') {
				div(class:'navbar-inner') {
					img(class:'brand', src:'/logo.png', width:'25', height:'25')
					a(class:'brand', href:'/') {
						yield 'PhoneBook'
					}
				}
			}
			h1(title)
			div { content() }
		}
	}
}
