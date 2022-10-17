package com.board.boot3.service;


import com.board.boot3.dto.BoardDTO;
import com.board.boot3.dto.PageRequestDTO;
import com.board.boot3.dto.PageResultDTO;
import com.board.boot3.entity.Board;
import com.board.boot3.entity.Member;
import com.board.boot3.repository.BoardRepository;
import com.board.boot3.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository repository;

    private final ReplyRepository replyRepository;



    @Override
    public Long register(BoardDTO dto) {

        log.info(dto);

        Board board  = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    //게시물 목록 조회 리스트
    //JPQL 반환 결과인 Object[]를 entityToDTO()로 BoardDTO로 변환

    //-> entityToDTO() 호출 후, Function 함수형 인터페이스를 이용
    //즉, PageRequestDTO에서 받은 엔티티를 PageResultDTO로 전달하기 위해
    //    PageResultDTO<> 인스턴스를 반환하는 getList() 구현
    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {


        log.info(pageRequestDTO);
        //일반적인 함수형태임으로 자주쓰이는 함수형 인터페이스: 하나의 매개변수를 받아 결과를 반환
        //Object[]을 받아서 apply()메서드에 BoardDTO를 파라미터로 전달해 결과를 반환하는 형태
        //메서드가 하나뿐인 인터페이스이므로 메서드명을 생략해서 사용 -> entityToDTO(Board board, Member member, Long replyCount)

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0],(Member)en[1], (Long) en[2]));

        Page<Object[]> result = repository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("bno").descending())  );
//        Page<Object[]> result = repository.searchPage(
//                pageRequestDTO.getType(),
//                pageRequestDTO.getKeyword(),
//                pageRequestDTO.getPageable(Sort.by("bno").descending())  );

//    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) -> PageResultDTO 객체 구성
        return new PageResultDTO<>(result, fn);
    }

    //게시물 조회
    @Override
    public BoardDTO get(Long bno) {

        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[])result;
        for (Object obj:arr) {
            System.out.println(obj);
        }

        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    //게시물 삭제 메서드 구현
    @Transactional //댓글부터 삭제하려면, 다른 테이블에 접근을 해야하기 때문에 @Transactional추가
    @Override
    public void removeWithReplies(Long bno) {

        //댓글 부터 삭제
        replyRepository.deleteByBno(bno);

        repository.deleteById(bno);

    }

    //게시물 수정 메서드
    //: findById()는 즉시로딩, getOne()는 지연로딩 하지만, getOne()은 사용 금지되었다.
    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {

        Board board = repository.getOne(boardDTO.getBno());

        if(board != null) {

            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());

            repository.save(board);
        }
    }
}
