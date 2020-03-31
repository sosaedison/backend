package AutoGarcon;

public enum StatusResponse {
    SUCCESS ("SUCCESS"),
    ERROR ("ERROR");

    private String status;

    StatusResponse(String status) {
        this.status = status;
    }

    public String getStatus(){ return this.status;}

    public void setStatus(String status) {this.status = status;}
}
