package interface_adapter.send_message;

/**
 * The state for the Send Message View Model.
 */
public class SendMessageState {
    private String userId = "";
    private String sendError;

    public String getUsername() {
        return userId;
    }

    public String getLoginError() {
        return sendError;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSendError(String sendError) {
        this.sendError = sendError;
    }
}
