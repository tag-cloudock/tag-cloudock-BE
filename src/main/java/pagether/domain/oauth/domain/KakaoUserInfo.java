package pagether.domain.oauth.domain;

public class KakaoUserInfo {
    private String email;
    private String phoneNumber;

    public KakaoUserInfo(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
