package com.board.boot3.repository;


import com.board.boot3.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //연관관계가 있는 글과 작성자 정보 조회 메서드
    @Query("select b, w from Board b left join b.writer w where b.bno =:bno")
    //한 개의 로우(Object) 내에 Object[]로 나옴
    Object getBoardWithWriter(@Param("bno") Long bno);
    //Board를 사용하지만, Member를 함께 조회 -> 따라서 b.writer로 사용
    //-> 내부의 엔티티 사용시에는 ON을 붙이지 않는다.

    //연관관계가 없는, 글과 댓글 정보 조회 메서드
    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);


    //목록 화면 출력을 위한 메서드 : 오라클에서 발생하는 GROUP BY 오류 -> group by 하나씩 적용하거나 서브쿼리
    @Query(value ="SELECT min(r.board),min(b.writer), count(r) "+
            " FROM Board b" +
            " LEFT JOIN b.writer w " +
            " LEFT JOIN Reply r ON r.board = b "+
            "GROUP BY b",
            countQuery ="SELECT count(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);


    //단일 조회
    @Query(value="SELECT min(r.board), min(b.writer), count(r) " +
            " FROM Board b LEFT JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.board = b" +
            " Group by b" +
            " having b.bno = :bno",
            countQuery ="SELECT count(b) FROM Board b")
    Object getBoardByBno(@Param("bno") Long bno);


}
