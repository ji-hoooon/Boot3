package com.board.boot3.controller;


import com.board.boot3.dto.ReplyDTO;
import com.board.boot3.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@ResponseBody 포함 : 데이터와 상태코드 함께 전송
@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    //게시물 댓글 목록 가져오는 GET방식
    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno ){

        log.info("bno: " + bno);

        return new ResponseEntity<>( replyService.getList(bno), HttpStatus.OK);

    }


    //댓글 데이터 전송하는 POST방식
    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){

        log.info(replyDTO);

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    //댓글 데이터 삭제하는 메서드
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {

        log.info("RNO:" + rno );

        replyService.remove(rno);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    //댓글 데이터 수정하는 POST방식
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {

        log.info(replyDTO);

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }





}
