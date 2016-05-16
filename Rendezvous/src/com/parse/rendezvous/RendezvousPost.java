package com.example.rendezvous;

import org.json.JSONArray;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Data model for a post.
 */
@ParseClassName("Posts")
public class RendezvousPost extends ParseObject {
  public String getText() {
    return getString("text");
  }
  
  public String getDescription() {
	    return getString("Description");
	  }
  
  public String getAddress() {
	    return getString("Location");
	  }
  
  public String getEventTime() {
	    return getString("Event_Time");
	  }

  public void setDescription(String value) {
    put("Description", value);
  }

  public void setAddress(String value) {
	    put("Address", value);
	  }
  
  public void setEventTime(String value) {
	    put("Event_Time", value);
	  }
  
  public void setText(String value) {
	    put("text", value);
	  }
  
  public void setAttendees(JSONArray sessionJSON){
	  put("Attendees", sessionJSON);
  }
  
  public Object getAttendees(){
	  return get("Attendees");
  }

  public ParseUser getUser() {
    return getParseUser("user");
  }

  public void setUser(ParseUser value) {
    put("user", value);
  }

  public ParseGeoPoint getLocation() {
    return getParseGeoPoint("location");
  }

  public void setLocation(ParseGeoPoint value) {
    put("location", value);
  }

  public static ParseQuery<RendezvousPost> getQuery() {
    return ParseQuery.getQuery(RendezvousPost.class);
  }
}
