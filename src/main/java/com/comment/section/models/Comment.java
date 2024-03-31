package com.comment.section.models;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Getter
@Document(collection = "comment")
public class Comment {

    @Id
    @Field(FieldConstants.ID)
    private ObjectId id;

    @Field(FieldConstants.COMMENT_ID)
    private String commentId;

    @Field(FieldConstants.USER)
    private String user;

    @Field(FieldConstants.CONTENT)
    private String content;

    @Field(FieldConstants.LIKE_COUNT)
    private Double likeCount;

    @Field(FieldConstants.DISLIKE_COUNT)
    private Double dislikeCount;

    @Field(FieldConstants.PARENT_ID)
    private String parentId;

    public static class FieldConstants {
        public static final String ID = "_id";
        public static final String USER = "user";
        public static final String CONTENT = "content";
        public static final String LIKE_COUNT = "like_count";
        public static final String DISLIKE_COUNT = "dislike_count";
        public static final String COMMENT_ID = "comment_id";
        public static final String PARENT_ID = "parent_id";
    }
}
