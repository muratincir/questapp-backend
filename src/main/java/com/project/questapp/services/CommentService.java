package com.project.questapp.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.responses.CommentResponse;

@Service
public class CommentService {
	private CommentRepository commentRepository;
	private UserService userService;
	private PostService postService;

	
	
	public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
		super();
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postService = postService;
	}


	// Kullanıcının yorumları veya bir postun altındaki yorumlar veya her ikisi birden gelebilir
	public List<CommentResponse> getAllCommentsWithParam(Optional<Long>userId , Optional<Long>postId){
		List<Comment> comments;
		if(userId.isPresent()&& postId.isPresent()) { // İkisi de var ise
			comments =  commentRepository.findByUserIdAndPostId(userId.get(),postId.get()); // Bir kullanıcının bir posta yorumu
		}else if(userId.isPresent()) {
			comments =  commentRepository.findByUserId(userId.get()); // Kullanıcının yorumları
		}else if(postId.isPresent()) {
			comments =  commentRepository.findByPostId(postId.get()); // Postun Yorumları
		}else {
			comments =  commentRepository.findAll();					// TÜm yorumlar
		}
		return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
	}
	
	
	public Comment getOneCommentById(long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}
	
	public Comment createOneComment(CommentCreateRequest request) {
		User user = userService.getOneUserById(request.getUserId());
		Post post = postService.getOnePostById(request.getPostId());
		if(user != null && post != null) {
			Comment commentToSave = new Comment();
			commentToSave.setId(request.getId());
			commentToSave.setPost(post);
			commentToSave.setUser(user);
			commentToSave.setText(request.getText());
			commentToSave.setCreateDate(new Date(0));
			return commentRepository.save(commentToSave);
		}else {
			return null;
		}
	}


	public Comment updateOneCommentById(Long commentId,CommentUpdateRequest request) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if(comment.isPresent()) {
			Comment commentToUpdate = comment.get();
			commentToUpdate.setText(request.getText());
			return commentRepository.save(commentToUpdate);
		}else
			return null;
	}


	public void deleteOneCommentById(Long commentId) {
		commentRepository.deleteById(commentId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
