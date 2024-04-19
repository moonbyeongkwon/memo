package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {

	@Autowired
	private PostMapper postMapper;
	//	input:	userId
	//	output:	글 목록 List<Post>
	public List<Post> getPostByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
}
