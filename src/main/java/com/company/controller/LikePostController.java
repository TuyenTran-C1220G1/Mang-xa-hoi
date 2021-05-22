package com.company.controller;

import com.company.model.LikePost;
import com.company.model.Post;
import com.company.model.User;
import com.company.service.ILikePostService;
import com.company.service.IPostService;
import com.company.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/like")
public class LikePostController {
    @Autowired
    IPostService iPostService;
    @Autowired
    IUserService userService;
    @Autowired
    ILikePostService iLikePostService;
    @PostMapping("/{postId}")
    public void checkLike(Principal principal, @PathVariable Long postId){
        User user = userService.findByUsername(principal.getName());
        Optional<Post> post = iPostService.findById(postId);
        Optional<LikePost> likePost = iLikePostService.findByPostIdAndUserId(postId,user.getId());

        if(!likePost.isPresent()){
            LikePost likePost1 = new LikePost(post.get(),user,1);
            iLikePostService.save(likePost1);
        } else {
            if(likePost.get().getIsLike()==0){
                likePost.get().setIsLike(1);
                int likeChange = post.get().getLikes();
                likeChange++;
                post.get().setLikes(likeChange);
                iPostService.save(post.get());
            } else {
                likePost.get().setIsLike(0);
                int likeChange = post.get().getLikes();
                if(likeChange>0){
                    likeChange--;
                }
                post.get().setLikes(likeChange);
                iPostService.save(post.get());
            }
            iLikePostService.save(likePost.get());
        }
    }
    @GetMapping("/quantity/{postid}")
    public ResponseEntity<Optional<Post>> getQuantityLikes(@PathVariable Long postid){
        Optional<Post> post= iPostService.findById(postid);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }
}
