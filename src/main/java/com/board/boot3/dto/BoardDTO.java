package com.board.boot3.dto;


import lombok.*;

import java.time.LocalDateTime;
//DTO는 뷰를 처리하므로, Getter, Setter 생성
//뷰를 위한 데이터이기 때문에 엔티티와 달리 Member 참조는 없다.
//-> 작성자 이메일과 작성자 이름으로 처리 또한 목록 화면에서 댓글 수를 전달하기 위해 댓글 수 변수 추가
@Data
@ToString
@Builder
//인자가 모두 존재하는 생성자, 기본 생성자 자동생성
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    private String title;

    private String content;

    private String writerEmail; //작성자의 이메일(id)

    private String writerName; //작성자의 이름

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private int replyCount; //해당 게시글의 댓글 수


}
