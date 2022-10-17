package com.board.boot3.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
//연관관계 테이블의 객체까지 모두 출력하므로, exclude처리
@ToString(exclude = "writer")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    //일대일 관계, 다대일 관계는 글로벌 패치 전략을 지연으로 설정
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
    // -> 댓글 조회시 에러 발생 (세션 없음 오류)

    //게시물 수정을 위한 메서드 : 제목과 내용만 변경가능하도록 설정 [Setter가 존재하지 않으므로, 외부에선 메서드를 이용한 접근만 가능]
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}

