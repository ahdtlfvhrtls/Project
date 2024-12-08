package com.example.project.dto.page;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@EqualsAndHashCode
public class Criteria {
    private int page; //현재 페이지
    private int amount; //한 페이지 당 게시물 수

    public Criteria(){
        this(1, 9);
    }

    public Criteria(int page, int amount) {
        this.page = page;
        this.amount = amount;
    }
}
