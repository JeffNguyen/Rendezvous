package com.example.rendezvous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseACL;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Activity which displays a login screen to the user, offering registration as well.
 */
public class PostActivity extends Activity {
  // UI references.
  private EditText postEditText;
  private EditText postEditDescription;
  private EditText postEditAddress;
  private EditText postEditEventTime;
  private Button postButton;
  public MultiSelectionSpinner spinner;
  private String currentUserId;
  private ParseUser currentUser;
  private String currentUserName;
  private ArrayList<String> users;
  private ArrayAdapter<String> namesArrayAdapter;
  private List<ParseUser> userList;
  private List<ParseUser> sessionUserList;
  private String[] sessionUserArray;
  private JSONArray sessionJSON;
  
  private ListView usersListView;

  private ParseGeoPoint geoPoint;
  private List<String> attendeeList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_post);

    Intent intent = getIntent();
    Location location = intent.getParcelableExtra(Application.INTENT_EXTRA_LOCATION);
    geoPoint = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

    postEditText = (EditText) findViewById(R.id.post_edittext);
    postEditDescription = (EditText) findViewById(R.id.post_editdescription);
    postEditAddress = (EditText) findViewById(R.id.post_editaddress);
    postEditEventTime = (EditText) findViewById(R.id.post_editeventtime);
    currentUserId = ParseUser.getCurrentUser().getObjectId();
    currentUserName = ParseUser.getCurrentUser().getUsername();
    currentUser = ParseUser.getCurrentUser();
    final ParseUser current = null;
    users = new ArrayList<String>();
    
    ParseQuery<ParseUser> query = ParseUser.getQuery();
    query.whereNotEqualTo("objectId", currentUserId);
    /*query.findInBackground(new FindCallback<ParseUser>()
    {
    	public void done(List<ParseUser> userList, com.parse.ParseException e) {
    		if (e == null)
    		{
    			for (int i = 0; i < userList.size(); i++)
    			{
    				users.add(userList.get(i).getUsername().toString());
    				//Log.d(userList.get(i).getUsername().toString(), "LOL");
    			}
    			

                } else {
                    Toast.makeText(getApplicationContext(),
                        "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
    		}
    	}); */
    
    try {
		userList = query.find();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
        Toast.makeText(getApplicationContext(),
                "Error loading user list",
                    Toast.LENGTH_LONG).show();
	}
    
	for (int i = 0; i < userList.size(); i++)
	{
		users.add(userList.get(i).getUsername().toString());
	}
    
    String[] usersArray = new String[users.size()];
    
    usersArray = users.toArray(usersArray);

    
    spinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner1);
    spinner.setItems(usersArray);
    postEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
      }

      @Override
      public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        updatePostButtonState();
        //updateCharacterCountTextViewText();
      }
    });
    

    //characterCountTextView = (TextView) findViewById(R.id.character_count_textview);

    postButton = (Button) findViewById(R.id.post_button);
    postButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
    	currentUser.put("SessionID", currentUserId);
    	currentUser.saveInBackground();
    	List<String> s = spinner.getSelectedStrings();
    	s.add(currentUserName);
    	
    	  ParseQuery<ParseUser> query = ParseUser.getQuery();
    	  for (int i = 0; i < s.size(); i++)
    	  {
    		  query.whereEqualTo("username", s.get(i));
    	  }
    	  //query.whereEqualTo("username", s);
    	  
    	  
    	  try {
    		  sessionUserList = query.find();
    	  }
    	  catch (ParseException e) {
    		  Toast.makeText(getApplicationContext(),
    	                "Error loading session user list",
    	                    Toast.LENGTH_LONG).show();
    	  }

    	  sessionJSON = new JSONArray(s);
    	  //Log.d(sessionJSON.toString(), "wtf");
    	  /*
    	  HashMap<String, Object> params = new HashMap<>();
    	 
    	
  	  	for (int i = 0; i < sessionUserList.size(); i++)
  		{
  			parameters.put(sessionUserList.get(i).toString(), currentUserId);
  		}
    	  
    	  String[] sessionUserArray = new String[sessionUserList.size()];
    	  	for (int i = 0; i < sessionUserList.size(); i++)
      		{
      			sessionUserArray[i] = sessionUserList.get(i).toString();
      		}
    	  	
    	 JSONArray jsArray = new JSONArray(Arrays.asList(sessionUserArray));
    	 params.put("IDs", jsArray); 
    	 params.put("SessionID", currentUserId);
    	 //Log.d("okay", "lol");
    	  ParseCloud.callFunctionInBackground("userMigration", params, new FunctionCallback<String>() {
    	        public void done(String object, ParseException e) {
    	          if (e == null) {
    	            //processResponse(object);
    	        	  Log.d("okay2", "lawl");
    	          } else {
    	            //handleError();
    	        	  Log.d(object, "lmao");
    	          }
    	        	
    	        }
    	   });
    	  
    	  Log.d("okay5", "lmao5");
    	  */
    	  
    	  
    	  /*
    	  	for (int i = 0; i < sessionUserList.size(); i++)
    		{
    			Log.d(sessionUserList.get(i).toString(), "lol");
    			sessionUserList.get(i).put("SessionID", currentUserId);
    			sessionUserList.get(i).saveInBackground();
    		}
    		*/
    	  /*
    	  query.findInBackground(new FindCallback<ParseUser>()
    	    {
    	    	public void done(List<ParseUser> userList, com.parse.ParseException e) {
    	    		if (e == null)
    	    		{
    	    			for (int i = 0; i < userList.size(); i++)
    	    			{
    	    				userList.get(i).put("SessionID", currentUserId);
    	    				userList.get(i).saveInBackground();
    	    				//query.saveInBackground();
    	    			}
    	    			

    	                } else {
    	                    Toast.makeText(getApplicationContext(),
    	                        "Error loading user list",
    	                            Toast.LENGTH_LONG).show();
    	                }
    	    		}
    	    	}); */
    	  
    	  
    	    
    	
        post();
 
      }
    });

    updatePostButtonState();
  }
  
  /*public void onClick(View v){
  	List<String> s = spinner.getSelectedStrings();
  	Toast.makeText(getApplicationContext(), (CharSequence) s, Toast.LENGTH_LONG).show();
  }*/

  private void post () {
    String text = postEditText.getText().toString().trim();
    String description = postEditDescription.getText().toString().trim();
    String address = postEditAddress.getText().toString().trim();
    String eventTime = postEditEventTime.getText().toString().trim();

    // Set up a progress dialog
    final ProgressDialog dialog = new ProgressDialog(PostActivity.this);
    dialog.setMessage(getString(R.string.progress_post));
    dialog.show();

    // Create a post.
    RendezvousPost post = new RendezvousPost();

    // Set the location to the current user's location
    post.setLocation(geoPoint);
    post.setText(text);
    post.setAddress(address);
    post.setDescription(description);
    post.setEventTime(eventTime);
    //post.add("Attendees", sessionJSON);
    //Log.d("reach here", "sessionJSON");
    post.setAttendees(sessionJSON);

    post.setUser(ParseUser.getCurrentUser());
    ParseACL acl = new ParseACL();

    // Give public read access
    acl.setPublicReadAccess(true);
    post.setACL(acl);

    // Save the post
    
    
    post.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        dialog.dismiss();
        finish();
      }
    });
  }

  private String getPostEditTextText () {
    return postEditText.getText().toString().trim();
  }

  private void updatePostButtonState () {
    int length = getPostEditTextText().length();
    boolean enabled = length > 0;
    postButton.setEnabled(enabled);
  }
}
