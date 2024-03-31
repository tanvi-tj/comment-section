package com.comment.section.models;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Getter
@Document(collection = "user")
public class User {

    @Id
    @Field(FieldConstants.ID)
    private ObjectId id;

    @Field(FieldConstants.NAME)
    private String name;

    @Field(FieldConstants.PASSWORD)
    private String password;

    public static class FieldConstants {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String PASSWORD = "password";
    }

}
