package gachonherald.domain.note.domain;

import gachonherald.domain.feed.domain.FeedType;
import gachonherald.domain.note.exception.TypeNotFountException;

public enum NoteType {
    NEW, SENTENCE, REVIEW, THOUGHT, DISCUSSION, COMMENT;

    public FeedType toFeedType() {
        switch (this) {
            case NEW:
                return FeedType.NEW;
            case SENTENCE:
                return FeedType.SENTENCE;
            case REVIEW:
                return FeedType.REVIEW;
            case THOUGHT:
                return FeedType.THOUGHT;
            case DISCUSSION:
                return FeedType.DISCUSSION;
            case COMMENT:
                return FeedType.COMMENT;
            default:
                throw new TypeNotFountException();
        }
    }

    public static NoteType fromString(String statusString) {
        try {
            return NoteType.valueOf(statusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TypeNotFountException();
        }
    }
}