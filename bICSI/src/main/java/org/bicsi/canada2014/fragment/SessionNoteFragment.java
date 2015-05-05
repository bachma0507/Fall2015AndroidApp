package org.bicsi.canada2014.fragment;

/**
 * Created by barry on 5/4/15.
 */

import org.bicsi.canada2014.activities.MainActivity;
import org.bicsi.canada2014.common.MizeUtil.NavigateToTabFragmentListener;
import org.bicsi.fall2015.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.app.FragmentManager;


public class SessionNoteFragment extends Fragment {

    private NavigateToTabFragmentListener mCallback;

    //public String newFunctioncd;

    TextView title;
    Button savebutton;
    Button cancelbutton;

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (NavigateToTabFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NavigateToListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        final View v = inflater.inflate(R.layout.fragment_session_note, container, false);

        title = (TextView) v.findViewById(R.id.functiontitle);
        savebutton = (Button) v.findViewById(R.id.save_button);
        cancelbutton = (Button) v.findViewById(R.id.cancel_button);

        Bundle bundle = getArguments();

        String ftitle = bundle.getString("functiontitle");
        title.setText(ftitle);

        String funccd = bundle.getString("_id");
        System.out.println("The session code is: " + funccd + ".");



        return v;
    }



    /*@Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).updateTracker("Home Tab");
    }*/

}
