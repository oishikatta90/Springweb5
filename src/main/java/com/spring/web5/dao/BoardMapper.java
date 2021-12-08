package com.spring.web5.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;

import com.spring.web5.vo.BoardVO;
import com.spring.web5.vo.ReplyVO;

public interface BoardMapper {
	// 게시글 저장
	public int insertBoard(BoardVO board);

	// 글 번호로 게시글 검색
	public BoardVO getBoard(int boardnum);

	// 조회수 1증가
	public int addHits(int boardnum);

	// 검색 후의 총 글 개수
	public int getTotal(String searchText);

	// 검색 후의 현재 페이지 목록
	// public ArrayList<BoardVO> listBoard(String searchText, RowBounds rd);
	public ArrayList<BoardVO> listBoard();

	// 글 번호와 아이디로 해당 게시글 삭제
	public int deleteBoard(BoardVO board);

	// 글 수정
	public int updateBoard(BoardVO board);

	// 리플 저장
	public int insertReply(ReplyVO reply);

	// 한 게시물의 리플 목록
	public ArrayList<ReplyVO> listReply(int boardnum);

	// 리플번호와 아이디로 해당 리플 삭제
	public int deleteReply(ReplyVO reply);

	// 리플 수정
	public int updateReply(ReplyVO reply);

}
