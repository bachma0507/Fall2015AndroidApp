package org.bicsi.canada2014.activities;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import org.bicsi.canada2014.AndroidTwitterSample;
import org.bicsi.canada2014.TwitterUtils;
import org.bicsi.canada2014.web.OAuthRequestTokenTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrepareRequestTokenActivity extends Activity {

	  final String TAG = getClass().getName();
	 
	    private OAuthConsumer consumer; 
	    private OAuthProvider provider;
	    
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	     try {
	      this.consumer = new CommonsHttpOAuthConsumer(org.bicsi.canada2014.Contstants.CONSUMER_KEY, org.bicsi.canada2014.Contstants.CONSUMER_SECRET);
	         this.provider = new CommonsHttpOAuthProvider(org.bicsi.canada2014.Contstants.REQUEST_URL,org.bicsi.canada2014.Contstants.ACCESS_URL,org.bicsi.canada2014.Contstants.AUTHORIZE_URL);
	     } catch (Exception e) {
	      Log.e(TAG, "Error creating consumer / provider",e);
	  }

	        Log.i(TAG, "Starting task to retrieve request token.");
	  new OAuthRequestTokenTask(this,consumer,provider).execute();
	 }

	  /**
	  * Called when the OAuthRequestTokenTask finishes (user has authorized the request token).
	  * The callback URL will be intercepted here.
	  */
	 @Override
	 public void onNewIntent(Intent intent) {
	  super.onNewIntent(intent); 
	  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	  final Uri uri = intent.getData();
	  if (uri != null && uri.getScheme().equals(org.bicsi.canada2014.Contstants.OAUTH_CALLBACK_SCHEME)) {
	   Log.i(TAG, "Callback received : " + uri);
	   Log.i(TAG, "Retrieving Access Token");
	   new RetrieveAccessTokenTask(this,consumer,provider,prefs).execute(uri);
	   finish(); 
	  }
	 }
	 
	 public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

	   private Context context;
	  private OAuthProvider provider;
	  private OAuthConsumer consumer;
	  private SharedPreferences prefs;
	  
	  public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,OAuthProvider provider, SharedPreferences prefs) {
	   this.context = context;
	   this.consumer = consumer;
	   this.provider = provider;
	   this.prefs=prefs;
	  }


	   /**
	   * Retrieve the oauth_verifier, and store the oauth and oauth_token_secret 
	   * for future API calls.
	   */
	  @Override
	  protected Void doInBackground(Uri...params) {
	   final Uri uri = params[0];
	   final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

	    try {
	    provider.retrieveAccessToken(consumer, oauth_verifier);

	     final Editor edit = prefs.edit();
	    edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
	    edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
	    edit.commit();
	    
	    String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
	    String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
	    
	    consumer.setTokenWithSecret(token, secret);
	    context.startActivity(new Intent(context,AndroidTwitterSample.class));

	     executeAfterAccessTokenRetrieval();
	    
	    Log.i(TAG, "OAuth - Access Token Retrieved");
	    
	   } catch (Exception e) {
	    Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
	   }

	    return null;
	  }


	   private void executeAfterAccessTokenRetrieval() {
	   String msg = getIntent().getExtras().getString("tweet_msg");
	   try {
	    TwitterUtils.sendTweet(prefs, msg);
	   } catch (Exception e) {
	    Log.e(TAG, "OAuth - Error sending to Twitter", e);
	   }
	  }
	 } 
	 
	}