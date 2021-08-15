package model;

public class Session {
    public String username;
    public String sessionId;

    public Session(String username, String sessionId) {
        this.username = username;
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        String s = String.format(
                "(username: %s, sessionId: %s)",
                this.username,
                this.sessionId
        );
        return s;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
