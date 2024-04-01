package com.comment.section.service.mongo;


import com.comment.section.models.Comment;
import com.comment.section.utils.PaginatedResults;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentMongoService {

    private final MongoTemplate mongoTemplate;

    public Comment addComment(final String content, final String user, final String parentId) {
        final Comment comment = Comment.builder()
                .content(content)
                .user(user)
                .commentId(ObjectId.get().toString())
                .likeCount(Double.valueOf(0))
                .dislikeCount(Double.valueOf(0))
                .parentId(parentId)
                .build();
        return mongoTemplate.insert(comment);
    };

    public Comment addLike(final String commentId) {
        final Update update = new Update();
        update.inc(Comment.FieldConstants.LIKE_COUNT, 1);
        Criteria criteria = Criteria.where(Comment.FieldConstants.COMMENT_ID).is(commentId);
        return mongoTemplate.findAndModify(Query.query(criteria), update, Comment.class);
    }

    public Comment removeLike(final String commentId) {
        final Update update = new Update();
        update.inc(Comment.FieldConstants.LIKE_COUNT, -1);
        Criteria criteria = Criteria.where(Comment.FieldConstants.COMMENT_ID).is(commentId);
        return mongoTemplate.findAndModify(Query.query(criteria), update, Comment.class);
    }

    public Comment addDislike(final String commentId) {
        final Update update = new Update();
        update.inc(Comment.FieldConstants.DISLIKE_COUNT, 1);
        Criteria criteria = Criteria.where(Comment.FieldConstants.COMMENT_ID).is(commentId);
        return  mongoTemplate.findAndModify(Query.query(criteria), update, Comment.class);
    }

    public Comment removeDislike(final String commentId) {
        final Update update = new Update();
        update.inc(Comment.FieldConstants.DISLIKE_COUNT, -1);
        Criteria criteria = Criteria.where(Comment.FieldConstants.COMMENT_ID).is(commentId);
        return  mongoTemplate.findAndModify(Query.query(criteria), update, Comment.class);
    }

    public PaginatedResults findByParentId(final String parentId, final int size, final long cursor) {

        final Query query = new Query()
                .with(Sort.by(Sort.Direction.ASC, Comment.FieldConstants.ID))
                .addCriteria(Criteria
                        .where(Comment.FieldConstants.PARENT_ID)
                        .is(parentId))
                .limit(size);

        if (cursor > 0) {
            Criteria criteria = Criteria.where(Comment.FieldConstants.ID).gt(new ObjectId(Long.toHexString(cursor+1) + "0000000000000000"));
            query.addCriteria(criteria);
        }
        final List<Comment> comments =  mongoTemplate.find(query, Comment.class);

        long nextCursor = 0;
        if (!comments.isEmpty()) {
            nextCursor = comments.get(comments.size() - 1).getId().getTimestamp();
        }

        return new PaginatedResults(comments, nextCursor);
    };
}