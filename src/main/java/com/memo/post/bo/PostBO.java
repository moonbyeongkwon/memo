package com.memo.post.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {
	
	//private Logger logger = LoggerFactory.getLogger(PostBO.class);
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	// input: userId(로그인 된 사람, 글쓴이)
	// output: 글 목록 List<Post>
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	// input: 제목, 내용, 글쓴이 번호, 글쓴이 로그인 아이디, 멀티파트파일
	// output: int(성공 행 개수)
	public int addPost(int userId, String userLoginId, String subject, 
			String content, MultipartFile file) {
		
		String imagePath = null;
		
		// 업로드 할 이미지가 있을 때 업로드
		if (file != null) {
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		
		return postMapper.insertPost(userId, subject, content, imagePath);
	}
	
	public Post getPostByPostIdUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdUserId(postId, userId);
	}
	
	//	input: 파라미터들
	//	output: void
	public void updatePostByIdUserId(int userId, String userLoginId,
			int postId, String subject, String content, MultipartFile file) {
		
		//	기존글을 가져온다.(1. 기존 이미지 교체시 삭제 2. 업데이트 대상이 있는지 확인)
		Post post = getPostByPostIdUserId(postId, userId);
		if (post == null) {
			logger.info("[### post update] Post is null. postId:{}, userId{}", postId, userId);
			return;
		}
		//	파일이 존재하면
		//	1) 새 이미지를 업로드한다.
		//	2) 1번 단계가 성공하면 기존 이미지 제거(기존 이미지가 있었다면), 1번 실패시 기존 이미지 그대로
		String imagePath = null;
		if (file != null) {
			//	업로드
			imagePath = fileManager.saveFile(userLoginId, file);
			
			//	업로드 성공 시 기존 이미지가 있으면 제거
			if (imagePath != null && post.getImagePath() != null) {
				//	서버의 파일 제거
				fileManager.deleteFile(post.getImagePath());
			}
		}
		
		//	DB 업데이트
		postMapper.updatePostByPostId(postId, subject, content, imagePath);
	}
	public void deletePostByPostIdUserId(int postId, int userId) {
		//	기존글 가져오고, 이미지가 있다면 삭제
		Post post = getPostByPostIdUserId(postId, userId);
		if (post == null) {
			logger.info("[글 삭제] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
		
		//	db 삭제
		int rowCount = postMapper.deletePostById(postId);
		
		//	이미지 있으면 삭제
		if (post.getImagePath() != null && rowCount > 0) {
			fileManager.deleteFile(post.getImagePath());
		}
	}
}




