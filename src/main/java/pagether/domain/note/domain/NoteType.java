package pagether.domain.note.domain;

import pagether.domain.feed.domain.FeedType;
import pagether.domain.note.exception.TypeNotFountException;

public enum NoteType {
    NEW, SENTENCE, REVIEW, THOUGHT;

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
            default:
                throw new TypeNotFountException();
        }
    }
}