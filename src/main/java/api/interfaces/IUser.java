package api.interfaces;

public interface IUser {
    void authenticate() throws Exception;
    boolean logOut();
}
