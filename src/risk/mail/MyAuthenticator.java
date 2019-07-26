package risk.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public final class MyAuthenticator extends Authenticator {

    private String id;
    private String pw;

    public MyAuthenticator(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(id, pw);
    }
}