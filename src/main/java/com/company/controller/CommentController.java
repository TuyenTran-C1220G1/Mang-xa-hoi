package com.company.controller;

import com.company.model.Comment;
import com.company.model.CommentForm;
import com.company.service.ICommentService;
import com.company.service.IPostService;
import com.company.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    ICommentService commentService;
    @Autowired
    IUserService userService;
    @Autowired
    IPostService iPostService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<Void> createNewComment(@RequestBody CommentForm commentForm, Principal principal, @PathVariable Long postId){
        Comment comment = new Comment();
        comment.setPost(iPostService.findById(postId).get());
        comment.setUser(userService.findByUsername(principal.getName()));
        comment.setCreatedAt(Date.valueOf(LocalDate.now()));
        comment.setLikes(0);
        comment.setContent(commentForm.getContent());
        comment.setStatus(0);
        commentService.save(comment);
       return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/listcomment/{id}")
    public ResponseEntity<Iterable<Comment>> getListComment(@PathVariable Long id) {
        Iterable<Comment> comments = commentService.findAllByPostId(id);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }
}
