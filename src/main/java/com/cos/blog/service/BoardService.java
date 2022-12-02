package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//스프링이 컴포넌트 스캔을 통해 Bean에 등록을 해줌. IoC를 해줌.
@Service
@RequiredArgsConstructor//초기화 되지 않은 것들을 초기화 시켜줌.
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	
	/*
	 * @Autowired private UserRepository userRepository;
	 */
	
	@Transactional
	public void 글쓰기(Board board, User user) { //title,content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		// TODO Auto-generated method stub
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패");
				});
		
	}
	
	@Transactional
	public void 삭제하기(int id) {
		// TODO Auto-generated method stub
		boardRepository.deleteById(id);
	}

	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		// TODO Auto-generated method stub
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패");
				});
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
	}

	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		// TODO Auto-generated method stub
		replyRepository.mySave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		/*
		 * User user =
		 * userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
		 * return new IllegalArgumentException("댓글 쓰기 실패: 유저id찾기 불가"); });
		 * 
		 * Board board =
		 * boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
		 * return new IllegalArgumentException("댓글 쓰기 실패: 게시글 id찾기 불가"); });
		 * 
		 * 
		 * Reply reply = new Reply(); reply.update(user, board,
		 * replySaveRequestDto.getContent());
		 */

		//replyRepository.save(reply);
	}

	@Transactional
	public void 댓글삭제(int replyId) {
		// TODO Auto-generated method stub
		replyRepository.deleteById(replyId);
	}
}
