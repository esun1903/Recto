package com.ssafy.recto.user;

// 사용자 계정 정보 모델 클래스
public class UserAccount {
    private String idToken;    // Firebase Uid (고유 토큰 정보)
    private String emailId;    // 이메일 아이디
    private String password;   // 비밀번호
    private String confirmpwd; // 비밀번호 확인
    private String nickname;   // 닉네임

    public UserAccount() {
    }

    public String getIdToken() {
        return idToken;
    }

    public String setIdToken(String idToken) {
        this.idToken = idToken;
        return idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public String setEmailId(String emailId) {
        this.emailId = emailId;
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpwd() {
        return confirmpwd;
    }

    public void setConfirmpwd(String confirmpwd) {
        this.confirmpwd = confirmpwd;
    }

    public String getNickname() {
        return nickname;
    }

    public String setNickname(String nickname) {
        this.nickname = nickname;
        return nickname;
    }
}
