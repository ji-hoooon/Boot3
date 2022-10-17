package com.board.boot3.repository;
import com.board.boot3.entity.Board;
import com.board.boot3.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {

        IntStream.rangeClosed(1, 300).forEach(i -> {
            //1부터 100까지의 임의의 번호
            long bno  = (long)(Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply......." +i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);

        });


    }
    @Test
    public void readReply1() {

        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());

    }

    //특정 게시물 번호로 댓글 가져오는 테스트
    @Test
    public void testListByBoard() {

        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(
                Board.builder().bno(100L).build());

        replyList.forEach(reply -> System.out.println(reply));
    }
}
