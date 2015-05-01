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

	    Parse.initialize(this, "mLUbtG8L32TT1qDsSkEHyh0ONq0j2HG2pSPT5V4h", "oxeGIrPi9BxZrpv7Lx7pJyGxJ0WA7tcBMEg21fFs");
	    PushService.setDefaultPushCallback(this, MainActivity.class);
	    
	    super.onCreate();
	  }



}
