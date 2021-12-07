package com.spring.web5.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.spring.web5.dao.CustomerDAO;
import com.spring.web5.vo.CustomerVO;

@SessionAttributes("customer")
@RequestMapping("customer")
@Controller
public class customerLoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(customerLoginController.class);
	
	@Autowired
	CustomerDAO dao;
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginFrom() {
		return "customer/loginForm";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(String id, String password, Model model) {
		logger.debug("id, password : {}, {}", id, password);
		
		CustomerVO customer = dao.selectCustomer(id);
		
		if (customer != null && customer.getPassword().equals(password)) {
//			session.setAttribute("loginId", customer.getCustid());
//			session.setAttribute("loginName", customer.getName());
			model.addAttribute("customer", customer);
			
			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "ID 혹은 Password가 잘못되었습니다.");
			
			return "customer/loginForm";
		}
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(SessionStatus sessionStatus) {
//		session.invalidate();
		sessionStatus.setComplete();
		
		return "redirect:/";
	}
}