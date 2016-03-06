package com.phonebook.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.phonebook.model.Contact;
import com.phonebook.service.PhoneBookService;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class PhoneBookController {

	@Autowired
	private PhoneBookService service = new PhoneBookService();

	private Contact contact;

	@RequestMapping("/")
	public ModelAndView list() {
		return new ModelAndView("contacts/list", "contacts", 
			(Iterable<Contact>)service.listContact());
	}

	@RequestMapping("/edit-{id}")
	public ModelAndView get(@PathVariable("id") Integer i) {
		
		ModelAndView model = new ModelAndView("contacts/list", "contacts", 
			(Iterable<Contact>)service.listContact());
		
		try {
			model.addObject("econtact", service.getContact(i));
			model.addObject("id", i);
		} catch (Exception e) {
			model.addObject("globalStatus", "[Error] Unknown id.");
		}
		
		return model;
	}

	@RequestMapping(value = "/form-{id}", method = RequestMethod.POST)
	public ModelAndView edit(@PathVariable("id") Integer i, 
		@RequestParam(value="edit-name") String name, 
		@RequestParam(value="edit-phone") String phone) {

		name = service.fixName(name);
		phone = service.fixPhone(phone);

		ModelAndView model;
		
		try {
			if(service.isValid(name, phone)) {
				service.editContact(i, name, phone);
				model = new ModelAndView("redirect:/");
				model.addObject("globalStatus", "[OK]");
			} else {
				model = new ModelAndView("contacts/list", "contacts", 
					(Iterable<Contact>)service.listContact());
				model.addObject("editStatus", "[Error] Not valid.");
				model.addObject("econtact", new Contact(name, phone));
			}
			
		} catch (Exception e) {
			model = new ModelAndView("contacts/list", "contacts", 
				(Iterable<Contact>)service.listContact());
			model.addObject("editStatus", "[Error] Something wrong.");
			model.addObject("econtact", new Contact(name, phone));
		}

		return model;
	}

	@RequestMapping("/remove-{id}")
	public ModelAndView remove(@PathVariable("id") Integer i) {
		ModelAndView model;

		try {
			service.removeContact(i);

			model = new ModelAndView("contacts/list", "contacts", 
				(Iterable<Contact>)service.listContact());
			model.addObject("globalStatus", "[OK]");
		} catch (Exception e) {
			model = new ModelAndView("contacts/list", "contacts", 
				(Iterable<Contact>)service.listContact());
			model.addObject("globalStatus", "[Error] Unknown id.");
		}

		return model;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(@ModelAttribute Contact c) {

		String name = service.fixName(c.getName());
		String phone = service.fixPhone(c.getPhone());
		
		ModelAndView model;
		
		try {
			if(service.isValid(name, phone)) {
				service.addContact(name, phone);
				
				model = new ModelAndView("contacts/list", "contacts", 
					(Iterable<Contact>)service.listContact());
				model.addObject("globalStatus", "[OK] Success."); 
			} else {
				model = new ModelAndView("contacts/list", "contacts", 
					(Iterable<Contact>)service.listContact());
				model.addObject("addStatus", "[Error] Not valid.");
				model.addObject("newcontact", c);
			}
		} catch (Exception e) {
			model = new ModelAndView("contacts/list", "contacts", 
				(Iterable<Contact>)service.listContact());
			model.addObject("addStatus", "[Error] Something wrong.");
			model.addObject("newcontact", c); 
		}

		return model;
	}

	@RequestMapping("/search")
	public ModelAndView search(@RequestParam(value="query", required = false) String query) {
		if (query != null) query = query
			.replaceAll("[^-\\d\\w\\s()а-яА-Я]", "").replaceAll("\\s+", " ")
			.replaceAll("^\\s+", "");

		ModelAndView model = new ModelAndView("contacts/search", "contacts", 
			(Iterable<Contact>)service.searchContact(query));
		
		if (query != null) model.addObject("query", query);

		return model;
	}
}
