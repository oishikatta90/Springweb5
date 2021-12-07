package com.spring.web5.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.spring.web5.dao.BoardDAO;
import com.spring.web5.vo.BoardVO;
import com.spring.web5.vo.CustomerVO;

@Controller
@SessionAttributes("customer")
@RequestMapping("/board/*")	
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	BoardDAO dao;
	
	final int countPerPage = 10;
	final int pagePerGroup = 5;
	final String uploadPath = "/boardfile";
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(Model model) {
		ArrayList<BoardVO> boardList = dao.listBoard();
		model.addAttribute("boardList", boardList);
		return "board/list";
	}
	
	//게시글 읽기
	@RequestMapping(value="read", method=RequestMethod.GET)
	public String read(int boardnum, Model model) {
		//전달 된 글 번호로 게시글 정보 읽기.
		BoardVO board = dao.getBoard(boardnum);
		if (board == null) {
			return "redirect:/list";
		}
		model.addAttribute("board", board);
		return "board/read";
	}
	
	//게시글 쓰기 창 출력
	@RequestMapping("/write")
	public String writeForm() {
		return "board/writeForm";
	}
	
	
	@RequestMapping(value="write", method=RequestMethod.POST)
	public String write(@ModelAttribute("customer") CustomerVO customer, BoardVO board, Model model) {
		logger.debug("board : {}", board);
		
		//세선에서 로그인 사용자의 아이디 읽어오기
		logger.debug("customer : {} " , customer);
		
		board.setId(customer.getCustid());
		//board객체를 DB에 전달
		dao.insertBoard(board);
		return "redirect:list";
	}
	
	@RequestMapping(value="edit", method=RequestMethod.GET)
	public String editForm(int boardnum, Model model) {
		
		BoardVO board = dao.getBoard(boardnum);
		model.addAttribute("board", board);
		
		return "board/editForm";
	}
	
	@RequestMapping(value="edit", method=RequestMethod.POST)
	public String edit(BoardVO board,@ModelAttribute("customer") CustomerVO customer) {
		String id = customer.getCustid();
		if (id.equals(board.getId())) {
			dao.updateBoard(board);
		}
		
		return "redirect:read?boardnum=" + board.getBoardnum();
		
	}

}
