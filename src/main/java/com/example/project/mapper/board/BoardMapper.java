package com.example.project.mapper.board;

import com.example.project.dto.board.BoardListDTO;
import com.example.project.dto.board.BoardUpdateDTO;
import com.example.project.dto.board.BoardViewDTO;
import com.example.project.dto.board.BoardWriteDTO;
import com.example.project.dto.page.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    void insertBoard(BoardWriteDTO boardWriteDTO);

    void updateBoard(BoardUpdateDTO boardUpdateDTO);

    void deleteBoard(Long boardId);

    Optional<BoardViewDTO> selectById(Long boardId);

    List<BoardListDTO> selectAll();

    List<BoardListDTO> selectAllPage(Criteria criteria);

    int selectTotal();
}
