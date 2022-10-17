package com.board.boot3.repository;


import com.board.boot3.entity.Board;
import com.board.boot3.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {

        IntStream.rangeClosed(1,100).forEach(i -> {

            Member member = Member.builder().email("user"+i +"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content...." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);

        });

    }


    //지연로딩을 할 경우 @Transactional 필요
    //writer를 가져오기위해 member테이블을 로딩해야하는데 연결이 이미 끝난상태이기 때문에
    @Transactional
    @Test
    public void testRead1() {

        Optional<Board> result = boardRepository.findById(100L); //데이터베이스에 존재하는 번호

        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());

    }

    //연관관계에 있는 조인 테스트 : 글과 작성자 정보
    @Test
    public void testReadWithWriter() {

        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[])result;

        System.out.println("-------------------------------");
        System.out.println(Arrays.toString(arr));

    }//연관관계가 있을 경우 즉시로딩으로 처리되어도, 한번에 조인처리가 되어서 수행된다.

    //연관관계가 없는 조인 테스트 : 글과 댓글
    @Test
    public void testGetBoardWithReply() {

        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }//Board와 Member 사이에는 연관관계가 있지만, Reply와 Board는 연관관계가 없으므로 Board입장에서 참조관계가 없어서 문제가 된다.
    //MappedBy로 해결할 수도 있지만, 여기선 직접조인해서 처리

    //목록 화면 출력 테스트 : Board 기준 조인 후, Board 기준 GROUP BY과 페이징과 정렬
    @Transactional
    @Test
    public void testWithReplyCount(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {

            Object[] arr = (Object[])row;

            System.out.println(Arrays.toString(arr));
        });
    }

    //글번호로 출력
    @Transactional
    @Test
    public void testRead3() {

        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[])result;

        System.out.println(Arrays.toString(arr));
    }




}
