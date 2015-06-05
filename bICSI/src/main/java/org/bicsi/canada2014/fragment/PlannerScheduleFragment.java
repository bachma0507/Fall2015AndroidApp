package org.bicsi.canada2014.fragment;

/**
 * Created by barry on 6/3/15.
 */

import org.bicsi.canada2014.adapter.SQLiteDBPlanner;
import org.bicsi.fall2015.R;
import org.bicsi.canada2014.adapter.SQLiteDBAllData;
import org.bicsi.canada2014.common.MizeUtil.NavigateToTabFragmentListener;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.FilterQueryProvider;


public class PlannerScheduleFragment extends Fragment implements AdapterView.OnItemClickListener {

    private NavigateToTabFragmentListener mCallback;//interface from MizeUtil
    private SQLiteDBPlanner sqlite_obj;
    private MyCursorAdapter dataAdapter;
    //public String planner;


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
        final View v = inflater.inflate(R.layout.planner_schedule, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){

            //planner = bundle.getString("planner");

        }

        sqlite_obj = new SQLiteDBPlanner(getActivity());

        try {
            sqlite_obj.open();

            //Cursor cursor = sqlite_obj.getAllSChedulesByPlanner();
            Cursor cursor = sqlite_obj.fetchAllPlannerItems();
            //cursor.getCount();
            System.out.println("Table row count is: " + cursor.getCount());

            String[] columns = new String[] {
                    SQLiteDBPlanner.KEY_FUNCCD,
                    SQLiteDBPlanner.KEY_FUNCTITLE,
                    SQLiteDBPlanner.KEY_FUNCDESC,
                    SQLiteDBPlanner.KEY_LOCATION,
                    SQLiteDBPlanner.KEY_DATE,
                    SQLiteDBPlanner.KEY_START,
                    SQLiteDBPlanner.KEY_END



            };

            int[] to = new int[] {
                    R.id.textViewFUNCTIONCD,
                    R.id.textViewfunctiontitle,
                    R.id.textViewfunctiondescription,
                    R.id.textViewlocationname,
                    R.id.textViewfunctiondate,
                    R.id.textViewfunctionStartTimeStr,
                    R.id.textViewfunctionEndTimeStr

            };

            dataAdapter = new MyCursorAdapter(

                    getActivity(), R.layout.planner_schedule_info_list,
                    cursor,
                    columns,
                    to,
                    0);

            ListView listView = (ListView)v. findViewById(android.R.id.list);
            // Assign adapter to ListView
            listView.setAdapter(dataAdapter);
            listView.setOnItemClickListener(this);

            /*EditText myFilter = (EditText)v. findViewById(R.id.myFilter);
            myFilter.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    dataAdapter.getFilter().filter(s.toString());
                }
            });

            dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                public Cursor runQuery(CharSequence constraint) {
                    return sqlite_obj.fetchScheduleByDate(constraint.toString(), newConfDate);

                }
            });*/


            return v;

        } finally {
            sqlite_obj.close();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> listView, View view,
                            int position, long id) {
        /*try {
            sqlite_obj.open();

            //Toast.makeText(getActivity(), "Clicked "+ position, Toast.LENGTH_LONG).show();
            // Get the cursor, positioned to the corresponding row in the result set
            Cursor cursor = (Cursor) listView.getItemAtPosition(position);

            String functioncd =
                    cursor.getString(cursor.getColumnIndexOrThrow("_id"));

            String functionTitle =
                    cursor.getString(cursor.getColumnIndexOrThrow("functiontitle"));

            String functionDate =
                    cursor.getString(cursor.getColumnIndexOrThrow("fucntioindate"));

            String functionStart =
                    cursor.getString(cursor.getColumnIndexOrThrow("functionStartTimeStr"));

            String functionEnd =
                    cursor.getString(cursor.getColumnIndexOrThrow("functionEndTimeStr"));

            String functionDescription =
                    cursor.getString(cursor.getColumnIndexOrThrow("functiondescription"));

            String functionLocation =
                    cursor.getString(cursor.getColumnIndexOrThrow("LOCATIONNAME"));

            String trainer1firstname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer1firstname"));

            String trainer1lastname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer1lastname"));

            String trainer2firstname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer2firstname"));

            String trainer2lastname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer2lastname"));

            String trainer3firstname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer3firstname"));

            String trainer3lastname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer3lastname"));

            String trainer4firstname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer4firstname"));

            String trainer4lastname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer4lastname"));

            String trainer5firstname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer5firstname"));

            String trainer5lastname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer5lastname"));

            String trainer6firstname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer6firstname"));

            String trainer6lastname =
                    cursor.getString(cursor.getColumnIndexOrThrow("trainer6lastname"));



            ConfSchedSingleFragment mySingleFragment = new ConfSchedSingleFragment();

            Bundle bundle = new Bundle();

            String newFunctioncd = new String("'" + functioncd + "'");

            bundle.putString("_id", newFunctioncd);
            bundle.putString("functiontitle", functionTitle);
            bundle.putString("fucntioindate", functionDate);
            bundle.putString("functionStartTimeStr", functionStart);
            bundle.putString("functionEndTimeStr", functionEnd);
            bundle.putString("functiondescription", functionDescription);
            bundle.putString("LOCATIONNAME", functionLocation);
            bundle.putString("trainer1firstname", trainer1firstname);
            bundle.putString("trainer1lastname", trainer1lastname);
            bundle.putString("trainer2firstname", trainer2firstname);
            bundle.putString("trainer2lastname", trainer2lastname);
            bundle.putString("trainer3firstname", trainer3firstname);
            bundle.putString("trainer3lastname", trainer3lastname);
            bundle.putString("trainer4firstname", trainer4firstname);
            bundle.putString("trainer4lastname", trainer4lastname);
            bundle.putString("trainer5firstname", trainer5firstname);
            bundle.putString("trainer5lastname", trainer5lastname);
            bundle.putString("trainer6firstname", trainer6firstname);
            bundle.putString("trainer6lastname", trainer6lastname);



            mySingleFragment.setArguments(bundle);

            mCallback.navigateToTabFragment(mySingleFragment, null); //interface method



        } finally{

            sqlite_obj.close();
        }*/

    }

    private class MyCursorAdapter extends SimpleCursorAdapter{

        public MyCursorAdapter(Context context, int layout, Cursor c,
                               String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //get reference to the row
            View view = super.getView(position, convertView, parent);
            //check for odd or even to set alternate colors to the row background
            if(position % 2 == 0){
                view.setBackgroundColor(Color.rgb(246, 235, 253));
            }
            else {
                view.setBackgroundColor(Color.rgb(255, 255, 255));
            }
            return view;
        }

    }

}
