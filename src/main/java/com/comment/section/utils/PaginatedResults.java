package com.comment.section.utils;

import java.util.List;

public class PaginatedResults<M, T>{
    private List<M> results;
    private T nextCursor;

    public PaginatedResults(List<M> results, T nextCursor) {
        this.results = results;
        this.nextCursor = nextCursor;
    }

    public List<M> getResults() {
        return results;
    }

    public void setResults(List<M> results) {
        this.results = results;
    }

    public T getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(T nextCursor) {
        this.nextCursor = nextCursor;
    }
}
