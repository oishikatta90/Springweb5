package com.spring.web5.controller;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.web5.dao.BoardDAO;
import com.spring.web5.util.FileService;
import com.spring.web5.util.PageNavigator;
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
	public String list(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "searchText", defaultValue = "") String searchText, Model model) {
		logger.debug("page : {}, searchText : {}", page, searchText);
		// 페이지 계산을 위한 게시물 개수 조회
		int total = dao.getTotal(searchText);

		// 페이징 객체 생성
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
		// 게시글 목록 읽어오기
		ArrayList<BoardVO> boardList = dao.listBoard(searchText, navi.getStartRecord(), navi.getCountPerPage());

		model.addAttribute("boardList", boardList);
		model.addAttribute("searchText", searchText);
		model.addAttribute("navi", navi);
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

	// 게시글 저장
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String write(@ModelAttribute("customer") CustomerVO customer, BoardVO board, Model model,
			MultipartFile upload) {
		logger.debug("board : {}", board);

		// 세선에서 로그인 사용자의 아이디 읽어오기
		logger.debug("customer : {} ", customer);

		board.setId(customer.getCustid());

		// 첨부파일이 있는 경우 지정된 경로에 파일을 저장
		// 원본 파일명과 저장된 파일명을 Board 객체에 저장
		if (!upload.isEmpty()) {
			String savedFile = FileService.saveFile(upload, uploadPath);
			board.setOriginalfile(upload.getOriginalFilename());
			board.setSavedfile(savedFile);
		}

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
	public String edit(BoardVO board, @ModelAttribute("customer") CustomerVO customer, MultipartFile upload) {
		// 수정할 글이 본인 글인지 확인
		String id = customer.getCustid();
		BoardVO oldBoard = dao.getBoard(board.getBoardnum());
		if (oldBoard == null || !oldBoard.getId().equals(id)) {
			return "redirect:list";
		}
		// 수정할 보드 정보에 로그인 아이디 저장
		board.setId(id);

		// 기존의 첨부파일이 있으면 기존 파일을 삭제하고 다시 업로드
		if (!upload.isEmpty()) {
			// 기존 글에서 첨부된 파일의 실제 저장된 이름을 가져온다.
			String savedFile = oldBoard.getSavedfile();
			// 기존의 파일ㅇ ㅣ있으면 삭제
			if (savedFile != null) {
				FileService.deleteFile(uploadPath + "/" + savedFile);
			}
				
				//새로운 파일 업로드
				savedFile = FileService.saveFile(upload, uploadPath);
			
				//수정 정보에 새로 저장된 파일명과 원래 파일명을 저장
				board.setOriginalfile(upload.getOriginalFilename());
				board.setSavedfile(savedFile);
			
		}

			dao.updateBoard(board);
		
			return "redirect:read?boardnum=" + board.getBoardnum();
	}

	// 게시글 삭제
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(int boardnum, @ModelAttribute("customer") CustomerVO customer) {
		// 삭제할 글 번호와 본인 글인지 확인할 로그인 아이디
		BoardVO board = new BoardVO();
		board.setBoardnum(boardnum);
		board.setId(customer.getCustid());
		
		//첨부된 파일이 있으면 삭제
		String savedFile = dao.getBoard(boardnum).getSavedfile();
				
		int result = dao.deleteBoard(board);
		if (result == 1 && savedFile != null) {
			FileService.deleteFile(uploadPath + "/" + savedFile);
		}
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

	// 댓글 수정
	@RequestMapping(value = "replyEdit", method = RequestMethod.POST)
	public String updateReply(ReplyVO reply, @ModelAttribute("customer") CustomerVO customer) {
		String id = customer.getCustid();
		reply.setId(id);
		dao.updateReply(reply);
		return "redirect:read?boardnum=" + reply.getBoardnum();
	}

	// 파일 다운로드
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public String fileDownload(int boardnum, HttpServletResponse response) {
		BoardVO board = dao.getBoard(boardnum);
		logger.debug("board {} ", board);
		String originalFile = board.getOriginalfile();
		try {
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(originalFile, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 저장된 파일의 경로지정
		String fullPath = uploadPath + "/" + board.getSavedfile();

		// 서버의 파일을 읽을 입력 스트림과 클라이언트에게 전달할 출력 스트림 작성
		FileInputStream fileIn = null;
		ServletOutputStream fileOut = null;
		try {
			fileIn = new FileInputStream(fullPath);
			fileOut = response.getOutputStream();

			// 파일 관련 유틸
			FileCopyUtils.copy(fileIn, fileOut);

			fileIn.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
