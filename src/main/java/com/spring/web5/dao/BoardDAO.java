package com.spring.web5.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.web5.vo.BoardVO;
import com.spring.web5.vo.ReplyVO;

@Repository
public class BoardDAO {
	@Autowired
	SqlSession sqlSession;

	public ArrayList<BoardVO> listBoard(String searchText, int startRecord, int countPerPage) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		RowBounds rb = new RowBounds(startRecord, countPerPage);

		ArrayList<BoardVO> boardList = mapper.listBoard(searchText, rb);
		
		return boardList;
	}

	public BoardVO getBoard(int boardnum) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		BoardVO board = mapper.getBoard(boardnum);
		
		//조회수 증가
		mapper.addHits(boardnum);

		return board;
	}

	public int insertBoard(BoardVO board) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.insertBoard(board);
		return result;
	}

	public int updateBoard(BoardVO board) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.updateBoard(board);
		return result;

	}
	
	public int deleteBoard(BoardVO board) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.deleteBoard(board);
		return result;

	}
	
	public int getTotal(String searchText) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int total = mapper.getTotal(searchText);
		 
		return total;
	}

	public int insertReply(ReplyVO reply) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.insertReply(reply);
		return result;
	}

	public ArrayList<ReplyVO> listReply(int boardnum) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		ArrayList<ReplyVO> replyList = mapper.listReply(boardnum);
		return replyList;
	}

	public int deleteReply(ReplyVO reply) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.deleteReply(reply);
		return result;

	}

	public int updateReply(ReplyVO reply) {
		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
		int result = mapper.updateReply(reply);
		return result;
	}
}
