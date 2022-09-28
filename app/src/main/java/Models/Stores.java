package Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Stores")
public class Stores extends ParseObject {
    public static final String KEY_STATUS= "status";

    public ParseUser getStatus(){
        return getParseUser(KEY_STATUS);
    }

}
