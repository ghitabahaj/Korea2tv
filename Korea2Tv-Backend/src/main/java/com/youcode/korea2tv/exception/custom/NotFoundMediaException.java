package  com.youcode.korea2tv.exception.custom;

public class NotFoundMediaException extends RuntimeException{
    public NotFoundMediaException(String content){
        super(content);
    }
}
