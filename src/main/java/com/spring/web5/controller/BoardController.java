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
import com.spring.web5.vo.ReplyVO;

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

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model) {
		ArrayList<BoardVO> boardList = dao.listBoard();
		model.addAttribute("boardList", boardList);
		return "board/list";
	}

	// 게시글 읽기
	@RequestMapping(value = "read", method = RequestMethod.GET)
	public String read(int boardnum, Model model) {
		// 전달 된 글 번호로 게시글 정보 읽기.
		BoardVO board = dao.getBoard(boardnum);
		if (board == null) {
			return "redirect:/list";
		}

		// 해당 글에 달린 리플목록 읽기
		ArrayList<ReplyVO> replyList = dao.listReply(boardnum);
		model.addAttribute("replyList", replyList);

		model.addAttribute("board", board);
		return "board/read";
	}

	// 게시글 쓰기 창 출력
	@RequestMapping("/write")
	public String writeForm() {
		return "board/writeForm";
	}

	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String write(@ModelAttribute("customer") CustomerVO customer, BoardVO board, Model model) {
		logger.debug("board : {}", board);

		// 세선에서 로그인 사용자의 아이디 읽어오기
		logger.debug("customer : {} ", customer);

		board.setId(customer.getCustid());
		// board객체를 DB에 전달
		dao.insertBoard(board);
		return "redirect:list";
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String editForm(int boardnum, Model model) {

		BoardVO board = dao.getBoard(boardnum);
		model.addAttribute("board", board);

		return "board/editForm";
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(BoardVO board, @ModelAttribute("customer") CustomerVO customer) {
		String id = customer.getCustid();
		if (id.equals(board.getId())) {
			dao.updateBoard(board);
		}

		return "redirect:read?boardnum=" + board.getBoardnum();

	}
	
	//게시글 삭제
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete(int boardnum, @ModelAttribute("customer") CustomerVO customer) {
		//삭제할 글 번호와 본인 글인지 확인할 로그인 아이디
		BoardVO board = new BoardVO();
		board.setBoardnum(boardnum);
		board.setId(customer.getCustid());
		dao.deleteBoard(board);
		return "redirect:list";
	}

	// 리플 달기
	@RequestMapping(value = "replyWrite", method = RequestMethod.POST)
	public String replyWrite(ReplyVO reply, @ModelAttribute("customer") CustomerVO customer, Model model) {
		// 세션에서 로그인 한 사용자의 아이디를 받아서 reply 객체에 저장
		String id = customer.getCustid();
		reply.setId(id);
		dao.insertReply(reply);
		return "redirect:read?boardnum=" + reply.getBoardnum();
	}

	// 리플 삭제
	@RequestMapping(value = "replyDelete", method = RequestMethod.GET)
	public String deleteReply(ReplyVO reply, @ModelAttribute("customer") CustomerVO customer) {
		// 세선에서 로그인 한 사용자의 아이디를 읽어서 reply 객체에 저장
		String id = customer.getCustid();
		reply.setId(id);
		dao.deleteReply(reply);
		return "redirect:read?boardnum=" + reply.getBoardnum();
	}
	
	//댓글 수정
	@RequestMapping(value="replyEdit", method=RequestMethod.POST)
	public String updateReply(ReplyVO reply, @ModelAttribute("customer")CustomerVO customer) {
		String id = customer.getCustid();
		reply.setId(id);
		dao.updateReply(reply);
		return "redirect:read?boardnum=" +reply.getBoardnum();
	}

}
