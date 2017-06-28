package com.moviebase.database.model;

import java.util.List;

public class MovieDetails {
    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public MovieDetails(int rating, int userRating, boolean like, List<Comment> commentList) {
        this.rating = rating;
        this.userRating = userRating;
        this.like = like;
        this.commentList = commentList;
    }

    private int rating;
    private int userRating;
    private boolean like;
    private List<Comment> commentList;
}
