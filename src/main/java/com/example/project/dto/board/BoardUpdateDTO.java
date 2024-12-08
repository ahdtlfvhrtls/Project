package com.example.project.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class BoardUpdateDTO {
    private Long boardId;
    private String title;
    private String content;
}
