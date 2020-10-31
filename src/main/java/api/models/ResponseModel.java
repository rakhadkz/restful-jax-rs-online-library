package api.models;

public class ResponseModel {
    private String status;
    private String message;

    public ResponseModel() {
    }

    public ResponseModel(String message) {
        this.message = message;
    }

    public ResponseModel(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
