package com.mstr.mapper;

import java.util.List;


import com.mstr.domain.BoardVO;

public interface BoardMapper {
	
//	@Select("select * from hyochan.tbl_board where bno > 0")
	public List<BoardVO> getList();
	
	public void insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);
	
	public BoardVO read(Long bno);
	
	public int delete(Long bno);
	
	public int update(BoardVO board);
}
