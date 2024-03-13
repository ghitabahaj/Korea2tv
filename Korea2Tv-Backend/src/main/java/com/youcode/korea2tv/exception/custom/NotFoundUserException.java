package  com.youcode.korea2tv.exception.custom;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException(String content){
        super(content);
    }

}
