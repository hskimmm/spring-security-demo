package org.spring.springsecuritydemo.dto;

import lombok.Getter;
import org.spring.springsecuritydemo.util.Criteria;

@Getter
public class PageDTO {
    /*
        PageDTO 클래스는 페이징의 전체적인 값을 계산하는 DTO 이다.
    */

    private int startPage; //시작 페이지 번호
    private int endPage; //마지막 페이지 번호
    private boolean prev, next; //이전, 다음 버튼
    private int total; //전체 데이터의 개수
    private Criteria cri;

    public PageDTO(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;

        this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
        this.startPage = endPage - 9;

        int realEnd = (int) Math.ceil((total * 1.0) / cri.getAmount());

        if (realEnd <= this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;
    }
}
