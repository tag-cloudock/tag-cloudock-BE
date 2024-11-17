package tagCloudock.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"),
    REPORTER("ROLE_REPORTER"),
    READER("ROLE_READER");

    private final String role;

//    Role(String role) {
//        this.role = role;
//    }
//
//    public List<String> value() {
//        return Collections.unmodifiableList(Arrays.asList(this.role.split(",")));
//    }
}
