package com.example.project.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter @ToString
@Getter @NoArgsConstructor
public class BoardViewDTO {
    private Long boardId;
    private String title;
    private String content;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private String loginId;
    private Long userId;
}
