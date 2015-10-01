package org.bicsi.canada2014.application;



import org.bicsi.canada2014.Meal;
import org.bicsi.canada2014.activities.MainActivity;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.PushService;



import android.app.Application;



public class BICSIApplication extends Application {
	  @Override
	  public void onCreate() {
	    // The following line triggers the initialization of ACRA
		  
		  ParseObject.registerSubclass(Meal.class);

	    Parse.initialize(this, "jnsv9lO2crT74EbL2mdx17qn47e3dlyMtEVQ9E9s", "pLEmmQhpJ9g9A12KM97Q3n00f9KMpK8QITL8faI0");
	    PushService.setDefaultPushCallback(this, MainActivity.class);
	    
	    super.onCreate();
	  }



}
