package com.comment.section.service.mongo;

import com.comment.section.models.Post;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PostMongoService {

    private final MongoTemplate mongoTemplate;

    public Post addPost() {
        final Post post = Post.builder()
                .postId(ObjectId.get().toString())
                .build();
        return mongoTemplate.insert(post);
    }

    public List<Post> findAll() {
        return mongoTemplate.findAll(Post.class);
    }
}
