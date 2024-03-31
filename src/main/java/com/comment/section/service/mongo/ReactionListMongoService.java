package com.comment.section.service.mongo;

import com.comment.section.models.*;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ReactionListMongoService {


    private final MongoTemplate mongoTemplate;

    public UpdateResult addUserToReactionList(final String commentId, final String name, final ReactionList.ReactionType type) {
        final Update update = new Update();
        update.addToSet(ReactionList.FieldConstants.USER_LIST, name);
        Criteria criteria = Criteria.where(ReactionList.FieldConstants.COMMENT_ID).is(commentId)
                .andOperator(Criteria.where(ReactionList.FieldConstants.REACTION_TYPE).is(type));
        return mongoTemplate.upsert(Query.query(criteria), update, ReactionList.class);
    };

    public Optional<ReactionList> findByCommentId(final String commentId, final ReactionList.ReactionType type) {
        final Query query = new Query()
                .addCriteria(Criteria.where(ReactionList.FieldConstants.COMMENT_ID).is(commentId)
                        .andOperator(Criteria.where(ReactionList.FieldConstants.REACTION_TYPE).is(type)));
        return Optional.ofNullable(mongoTemplate.findOne(query, ReactionList.class));
    };
}
