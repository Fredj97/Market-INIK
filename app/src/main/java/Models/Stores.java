package Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Stores")
public class Stores extends ParseObject {
    public static final String KEY_STATUS= "status";
    public static final String KEY_NAME= "Name";
    public static final String KEY_DESCRIPTION=  "description";
    public static final String KEY_IMAGE= "image";
    public static final String KEY_USER= "store";
    public static final String KEY_CREATED_KEY= "createdAt";

    public String getDescription(){
            return getString(KEY_DESCRIPTION);
}
    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }
    public ParseFile getImage(){

        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile parseFile){

        put(KEY_IMAGE,parseFile);
    }
    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void set(ParseUser seller){
        put(KEY_USER,seller);
    }
    public Boolean getStatus(){
        return getBoolean(KEY_STATUS);
    }
    public void setUser(ParseUser store){
        put(KEY_STATUS,store);
    }

 public String getName(){
        return getString(KEY_NAME);
 }
 public void setKeyName(ParseUser Name){
        put(KEY_NAME, Name);
 }

}
