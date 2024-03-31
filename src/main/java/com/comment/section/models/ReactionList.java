package com.comment.section.models;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Builder
@Getter
@Document(collection = "reaction-list")
public class ReactionList {

    @Id
    @Field(FieldConstants.ID)
    private ObjectId _id;

    @Field(FieldConstants.COMMENT_ID)
    private String commentId;

    @Field(FieldConstants.REACTION_TYPE)
    private ReactionType reactionType;

    @Field(FieldConstants.USER_LIST)
    private List<String> userList;


    public static class FieldConstants {
        public static final String ID = "_id";
        public static final String COMMENT_ID = "comment_id";
        public static final String REACTION_TYPE = "reaction_type";
        public static final String USER_LIST = "reaction_user_list";
    }

    public enum ReactionType {
        LIKE, DISLIKE
    }
}
