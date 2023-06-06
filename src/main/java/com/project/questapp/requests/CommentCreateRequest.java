package com.project.questapp.requests;

import lombok.Data;

@Data
public class CommentCreateRequest {
	long id;
	Long userId;
	Long postId;
	String text;
}
