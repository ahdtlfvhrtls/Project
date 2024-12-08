package com.example.project.mapper.reply;

import com.example.project.dto.page.Criteria;
import com.example.project.dto.reply.ReplyListDTO;
import com.example.project.dto.reply.ReplyUpdateDTO;
import com.example.project.dto.reply.ReplyWriteDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {

    void insertReply(ReplyWriteDTO replyWriteDTO);

    List<ReplyListDTO> selectList(Long boardId);

    void updateReply(ReplyUpdateDTO replyUpdateDTO);

    void deleteReply(Long replyId);

    List<ReplyListDTO> selectSlice(@Param("criteria")Criteria criteria, @Param("boardId") Long boardId);
}
