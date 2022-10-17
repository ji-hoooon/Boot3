package com.board.boot3.service;

import com.board.boot3.dto.BoardDTO;
import com.board.boot3.dto.PageRequestDTO;
import com.board.boot3.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    //BoardService에서 만든, dtoTOEntity가 제대로 수행되었는지 확인
    @Test
    public void testRegister() {

        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@aaa.com")  //현재 데이터베이스에 존재하는 회원 이메일
                .build();

        Long bno = boardService.register(dto);

    }

    //게시물 목록 페이징 처리 테스트
    //요청 받은 목록을 getList를 통해 페이징, 정렬, entityToDto 수행한 목록 처리 테스트
    @Test
    public void testList() {

        //1페이지 10개
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }

    }

    //게시물 번호를 받아 게시물을 반환하는, 게시물 조회 테스트
    @Test
    public void testGet() {

        Long bno = 100L;

        BoardDTO boardDTO = boardService.get(bno);

        System.out.println(boardDTO);
    }

    //게시물 삭제 테스트
    //(1) 게시물의 모든 댓글 삭제
    //(2) 해당 게시물 삭제
    //: 실제로는 객체 엔티티에 state를 추가해, 삭제가능한 상태인지 체크한 후, 제거처리

    @Test
    public void testRemove() {

        Long bno = 1L;

        boardService.removeWithReplies(bno);

    }

    //게시물 수정 테스트
    @Test
    public void testModify() {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경합니다.2")
                .content("내용 변경합니다.2")
                .build();

        boardService.modify(boardDTO);

    }

//
//    @Test
//    public void testSearch(){
//
//        PageRequestDTO pageRequestDTO = new PageRequestDTO();
//        pageRequestDTO.setPage(1);
//        pageRequestDTO.setSize(10);
//        pageRequestDTO.setType("t");
//        pageRequestDTO.setKeyword("11");
//
//        Pageable pageable = pageRequestDTO.getPageable(Sort.by("bno").descending());
//
//        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
//
//        for (BoardDTO boardDTO : result.getDtoList()) {
//            System.out.println(boardDTO);
//        }
//    }
//

}
