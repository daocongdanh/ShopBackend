package project.shopbackend.exceptions;

public class DataNotFoundException extends RuntimeException{
    private int errorCode;
    public DataNotFoundException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

}
