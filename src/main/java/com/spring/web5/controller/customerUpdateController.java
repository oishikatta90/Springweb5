package com.spring.web5.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.spring.web5.dao.CustomerDAO;
import com.spring.web5.vo.CustomerVO;


@RequestMapping("customer")
public class customerUpdateController {
	private static final Logger logger = LoggerFactory.getLogger(customerUpdateController.class);
	
	@Autowired
	CustomerDAO dao;
	
	public String updateForm(HttpSession session, Model model) {
		String id = (String) session.getAttribute("loginId");
		CustomerVO customer = dao.selectCustomer(id);
		
		model.addAttribute("customer", customer);
		return "customer/updateForm";
		
		
	}
	
	public String update(@ModelAttribute("customer") CustomerVO customer, Model model) {
		int result = dao.updateCustomer(customer);
		
		if(result != 1) {
			model.addAttribute("errorMsg","회원정보 수정 실패");
			return "customer/updateForm";
		}
		return "redirect:updateComplete";
	}
	
	public String updateComplete(@ModelAttribute CustomerVO customer, SessionStatus sessionStatus, Model model) {
		model.addAttribute("result", customer);
		sessionStatus.setComplete();
		
		return "customer/updateComplete";
	}

}
