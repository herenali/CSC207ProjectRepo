package interface_adapter.edit_message;

public class EditMessageState {
    private String userId = "";
    private String editError;

    public String getUsername() {
        return userId;
    }

    public String getEditError() {
        return editError;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEditError(String editError) {
        this.editError = editError;
    }
}
