package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {

	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	
	//	input:	userId
	//	output:	글 목록 List<Post>
	public List<Post> getPostByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	//	input:	제목, 내용, 글쓴이 번호, 글쓴이 로그인 아이디, 멀티파트 파일
	//	output:	int(성공한 행 개수)
	public int addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		
		String imagePath = null;
		
		//	업로드 할 이미지가 있을 때 업로드
		if (file != null) {
			imagePath = fileManager.saveFile(userLoginId, file);
		}
			
		
		return postMapper.insertPost(userId, subject, content, imagePath);
	}
}
