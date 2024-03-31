package com.comment.section.controllers;

import com.comment.section.models.Comment;
import com.comment.section.models.Post;
import com.comment.section.models.ReactionList;
import com.comment.section.service.mongo.CommentMongoService;
import com.comment.section.service.mongo.PostMongoService;
import com.comment.section.service.mongo.ReactionListMongoService;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1")
public class CommentController {

    public final CommentMongoService commentMongoService;

    public final PostMongoService postMongoService;

    public final ReactionListMongoService reactionListMongoService;

    @GetMapping("/comment/add")
    public Comment addComment(@RequestParam(name = "parent_id") final String parentId,
                              @RequestParam(name = "content") final String content,
                              @RequestParam(name = "user") final String user) {

        final Comment comment = commentMongoService.addComment(content, user, parentId);
        return comment;
    }

    @GetMapping("/comments")
    public Page<Comment> getCommentList(@RequestParam(name = "parent_id") final String parentId,
                                        @RequestParam(name = "page", defaultValue = "0") final int page,
                                        @RequestParam(name = "size", defaultValue = "100") final int size) {
       return commentMongoService.findByParentId(parentId, PageRequest.of(page, size));
    }

    @GetMapping("/like/add")
    public int addLike(@RequestParam(name = "comment_id") final String commentId,
                                @RequestParam(name = "user") final String user) {
          final UpdateResult result = reactionListMongoService.addUserToReactionList(commentId, user, ReactionList.ReactionType.LIKE);
          if(result.getMatchedCount()==result.getModifiedCount()){
              commentMongoService.addLike(commentId);
              return 1;
          }
          return 0;
    }

    @GetMapping("/dislike/add")
    public int addDislike(@RequestParam(name = "comment_id") final String commentId,
                                   @RequestParam(name = "user") final String user) {
        final UpdateResult result = reactionListMongoService.addUserToReactionList(commentId, user, ReactionList.ReactionType.DISLIKE);
        if(result.getMatchedCount()==result.getModifiedCount()){
            commentMongoService.addDislike(commentId);
            return 1;
        }
        return 0;
    }

    @GetMapping("/likes")
    public List<String> getLikesList(@RequestParam(name = "comment_id") final String commentId) {
        final Optional<ReactionList> optionalReactionList = reactionListMongoService.findByCommentId(commentId, ReactionList.ReactionType.LIKE);
        if(optionalReactionList.isPresent()){
            return optionalReactionList.get().getUserList();
        } else {
            return Collections.emptyList();
        }
    }

    @GetMapping("/dislikes")
    public List<String> getDislikesList(@RequestParam(name = "comment_id") final String commentId) {
        final Optional<ReactionList> optionalReactionList =  reactionListMongoService.findByCommentId(commentId, ReactionList.ReactionType.DISLIKE);
        if(optionalReactionList.isPresent()){
            return optionalReactionList.get().getUserList();
        } else {
            return Collections.emptyList();
        }
    }

    @GetMapping("/post")
    public Post addPost() {
        return postMongoService.addPost();
    }

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postMongoService.findAll();
    }

    @GetMapping("/test")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}

