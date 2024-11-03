package gachonherald.domain.user.domain;

public enum Position {
    EDITOR_IN_CHIEF("Editor In Chief"),
    HEAD_OF_ADMINISTRATION("Head Of Administration"),
    EDUCATION_DIRECTOR("Education Director"),
    REGULAR_REPORTER("Regular Reporter"),
    INTERN_REPORTER("Intern Reporter");

    private final String displayName;

    Position(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
