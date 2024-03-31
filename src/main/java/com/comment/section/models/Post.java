package com.comment.section.models;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Getter
@Document(collection = "post")
public class Post {

    @Id
    @Field(FieldConstants.ID)
    private ObjectId id;

    @Field(FieldConstants.POST_ID)
    private String postId;

    public static class FieldConstants {
        public static final String ID = "_id";
        public static final String POST_ID = "post_id";
    }
}
