package com.example.project.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class BoardWriteDTO {
    private Long boardId;
    private String title;
    private String content;
    private Long userId;
    private String createdDate;
}
