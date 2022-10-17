package com.board.boot3.service;


import com.board.boot3.dto.BoardDTO;
import com.board.boot3.dto.PageRequestDTO;
import com.board.boot3.dto.PageResultDTO;
import com.board.boot3.entity.Board;
import com.board.boot3.entity.Member;

public interface BoardService {

    //추상메서드
    //게시물 등록 메서드
    Long register(BoardDTO dto);

    //게시물 목록 처리 메서드
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    //게시물 단일 조회 메서드
    BoardDTO get(Long bno);

    //게시물 번호를 받아 댓글까지 모두 지우는 메서드
    //-> 댓글먼저 지우고, 게시물을 삭제한다.
    void removeWithReplies(Long bno);

    //게시물 수정 메서드
    void modify(BoardDTO boardDTO);


    //디폴트 메서드
    //DTO -> 엔티티 변환
    // : Board 엔티티는 FK키인 writer 값을 가지고 있기 때문에
    // FK키에 대한 Member Entity 처리가 필요해, Member 엔티티 객체를 만들어서 처리하는데, 이때 PK키를 BoardDTO에서 전달한다.
    default Board dtoToEntity(BoardDTO dto){

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    //엔티티 -> DTO 변환
    //: PageResultDTO에서 필요한 BoardDTO를 Object[]를 이용해 반환시켜준다.
    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) //int로 처리하도록
                .build();

        return boardDTO;

    }//Board 엔티티 객체,Member 엔티티 객체, 댓글의 수를 파라미터로 받아 BoardDTO 객체를 생성한다.
    //객체를 생성해야하므로, 기본키나 외래키를 받아 (Object[])로 형변환을 이용해 객체로 변경하는 작업이 필요하다.
}
