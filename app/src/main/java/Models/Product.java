package Models;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Product")
public class Product extends ParseObject {
    public static final String KEY_DESCRIPTION=  "description";
    public static final String KEY_IMAGE= "download";
    public static final String KEY_USER= "Shop";
    public static final String KEY_ORDER= "Order";
    public static final String KEY_NAME= "title";
    public static final String KEY_PRICE= "Prix";
    public static final String KEY_CREATED_KEY= "createdAt";
    public String Description;

    public static ParseObject post;

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
    public ParseUser getString(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put(KEY_USER,user);
    }
    public String getName(){
        return getString(KEY_NAME);
    }
    public void setKeyName(ParseUser Name){
        put(KEY_NAME, Name);
    }

    public String getPrice(){

        return getString(KEY_PRICE);
    }
    public void setPrice(String Prix){
        put(KEY_PRICE, Prix);
    }

    public String getOrder(){

        return getString(KEY_ORDER);
    }
    public void setOrder(String Order){
        put(KEY_ORDER, Order);
    }
}