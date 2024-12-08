package com.example.project.mapper.file;

import com.example.project.dto.file.FileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    void insertFile(FileDTO fileDTO);

    void deleteFile(Long boardId);

    List<FileDTO> selectList(Long boardId);
}
