package com.board.boot3.repository;

import com.board.boot3.entity.Board;
import com.board.boot3.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {


    //게시물에 있는 댓글 테이블까지 CASCADE옵션으로 삭제
    //1. 해당 게시물의 모든 댓글 삭제
    //2. 해당 게시물 삭제
    //3. 이 작업은 하나의 트랜잭션

    //게시물 번호로 댓글 삭제
    //->Update, delete 를 위한 어노테이션
    @Modifying
    @Query("delete from Reply r where r.board.bno =:bno ")
    void deleteByBno(@Param("bno") Long bno);

    //특정 게시물 번호로 댓글 가져오기
    List<Reply> getRepliesByBoardOrderByRno(Board board);

}
