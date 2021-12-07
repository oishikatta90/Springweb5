package com.spring.web5.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.spring.web5.dao.CustomerDAO;
import com.spring.web5.vo.CustomerVO;

@Controller
@SessionAttributes("customer")
@RequestMapping("/customer/*")
public class customerJoinController {
	static Logger logger = LoggerFactory.getLogger(customerJoinController.class);
	
	@Autowired
	CustomerDAO dao;
	
	@RequestMapping("/join")
	public String joinForm(Model model) {
		CustomerVO customer = new CustomerVO();
		model.addAttribute("customer", customer);
		
		return "customer/joinForm";
	}

	@RequestMapping("/idcheck")
	public String idcheck() {
		return "customer/idcheck";
	}
	
	@RequestMapping(value="idcheck", method=RequestMethod.POST)
	public String idcheck(String searchId, Model model) {
		
		CustomerVO customer = dao.selectCustomer(searchId);
			
		model.addAttribute("searchId",customer);
		model.addAttribute("searchResult",customer);
		model.addAttribute("search",true);
		
		return "customer/idcheck";
	}
	
	@RequestMapping(value="join", method=RequestMethod.POST)
	public String join(@ModelAttribute("customer") CustomerVO customer, Model model) {
		//회원 가입 처리
		logger.debug("customer : {} ", customer);
		int result = dao.insertCustomer(customer);
		if (result != 1) {
			return "customer/joinForm";
		}
		
		return "redirect:joinComplete";
	}
	
	@RequestMapping("/joinComplete")
	public String joinComplete(@ModelAttribute("customer")CustomerVO customer,SessionStatus sessionStatus, Model model) {
		logger.debug("customer: {}" , customer);
		
		sessionStatus.setComplete();
		model.addAttribute("id", customer.getCustid());
		return "customer/joinComplete";
	}
}
