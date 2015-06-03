package org.bicsi.canada2014.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.bicsi.canada2014.Meal;
import org.bicsi.canada2014.SessionNotes;
import org.bicsi.canada2014.adapter.SQLiteDBSessNotes;
import org.bicsi.fall2015.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barry on 6/2/15.
 */
public class NotesActivity extends Activity{

    private SQLiteDBSessNotes sqlite_obj;
    private Context mContext = this;

    TextView title;
    EditText comment;
    Button savebutton;
    Button cancelbutton;
    List<SessionNotes> list = new ArrayList<SessionNotes>();
    List<SessionNotes> noteList = new ArrayList<SessionNotes>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_session_note);


        sqlite_obj = new SQLiteDBSessNotes(this);

        sqlite_obj.open();


        title = (TextView) findViewById(R.id.functiontitle);
        comment = (EditText) findViewById(R.id.comment);
        savebutton = (Button) findViewById(R.id.save_button);
        cancelbutton = (Button) findViewById(R.id.cancel_button);

        savebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //openInternalWebview("http://www.bicsi.org/m/Schedule.aspx");

                final String ftitle = getIntent().getStringExtra("functiontitle");

                final String funccd = getIntent().getStringExtra("_id");
                System.out.println("The session code is: " + funccd + ".");

                SessionNotes note = new SessionNotes();
                //SessionNotes note = ((NewNoteActivity) getActivity()).getCurrentSessionNotes();
                note.code = funccd;
                note.title = ftitle;
                note.desc = comment.getText().toString();
                sqlite_obj.insertNote(note);

                Toast.makeText(
                        mContext.getApplicationContext(),
                        "Note saved!",
                        Toast.LENGTH_SHORT).show();

                finish();
                

                list = sqlite_obj.getAllNotes();
                //System.out.println(print(list));
                print(list);

                //back();
            }
        });

        cancelbutton = ((Button) findViewById(R.id.cancel_button));
        cancelbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View vew) {

                finish();
            }
        });

    }

    public void onStart(){
        super.onStart();
        //TextView tv=(TextView)findViewById(R.id.txt_view);
//show text in the Intent object in the TextView
        title.setText(getIntent().getStringExtra("functiontitle"));
    }

    private void print(List<SessionNotes> list) {
        // TODO Auto-generated method stub
        String value = "";
        for(SessionNotes sn : list){
            value = value+"id: "+sn._id+", funccode: "+sn.code+" title: "+sn.title+" comment: "+sn.desc+"\n";
        }
        System.out.println(value);
    }

}
