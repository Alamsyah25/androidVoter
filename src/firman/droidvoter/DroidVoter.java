package firman.droidvoter;

import android.app.Activity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DroidVoter extends Activity {   
	services service = new services();
	String res;
	String user="";
	String pwd="";
	String cand;
	boolean loginStatus = false;
	
	String[] candidateName = null;
	String[] candidatePict = null;
	String[] totalVote = null;
	String[] lastVoted = null;
	
	static final String[] menuList = new String[]{"Login","Aktivasi","Perihal","Exit"};
	static final String[] menuList2 = new String[]{"Give Vote","Vote Result","Sign Out"};
	private ArrayAdapter<String> menuAdapter;
	myAdapter adapter;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setupMenu();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.widget74:
        	if (loginStatus == true) {
        		setContentView(R.layout.main);
        		setupMenu2();
        	} else Toast.makeText(getApplicationContext(), "you are not sign in yet", 5).show();
        	return true;
        	}
        return false;
    }
    
    private void setupMenu(){
    	// Menyusun menu 
    	menuAdapter = new ArrayAdapter<String>(this, R.layout.menu,menuList);
    	ListView daftarMenu = (ListView) findViewById(R.id.menu1);
    	daftarMenu.setAdapter(menuAdapter);
        daftarMenu.setOnItemClickListener(menuClickListener);
    }
    
    private void setupMenu2(){
    	// Menyusun menu 
    	menuAdapter = new ArrayAdapter<String>(this, R.layout.menu,menuList2);
    	ListView menu2 = (ListView) findViewById(R.id.menu1);
    	menu2.setAdapter(menuAdapter);
        menu2.setOnItemClickListener(menuClickListener);
    }
    
    private void showGiveVote() {
    	candidateName = service.getName();
    	candidatePict = service.getPicture();
    	totalVote = service.getTotal();
    	lastVoted = service.getLastVote();
    	adapter = new myAdapter(this, candidatePict, candidateName,totalVote,lastVoted);
    	ListView voteResult = (ListView) findViewById(R.id.menu1);
    	voteResult.setAdapter(adapter);
    	voteResult.setOnItemClickListener(voteClickListener);
    }
    
    private void showVoteResult() {
    	candidateName = service.getName();
    	candidatePict = service.getPicture();
    	totalVote = service.getTotal();
    	lastVoted = service.getLastVote();
    	adapter = new myAdapter(this, candidatePict, candidateName,totalVote,lastVoted);
    	ListView voteResult = (ListView) findViewById(R.id.menu1);
    	voteResult.setAdapter(adapter);
    }
        
  //Button handler
    public void clickHandler(View view) {
    	switch (view.getId()){
    	
    	case R.id.button_Login:
    		EditText userIn = (EditText) findViewById (R.id.userName);
    		EditText passIn = (EditText) findViewById (R.id.password);
    		user = userIn.getText().toString();
    		pwd = passIn.getText().toString();
    		System.out.println("User = "+user+" pwd = "+pwd);
    		res = service.logIn(user, pwd);
    		if (res.equals("1")){
    			loginStatus = true;
    			setContentView(R.layout.main);
    			setupMenu2();
    		}
    		else Toast.makeText(getApplicationContext(), "Silahkan diulang lagi", 10).show(); 
    		break;
    		
    	case R.id.completeReg_button:
    		EditText id = (EditText) findViewById (R.id.regname);
    		String ids = id.getText().toString();
    		res = service.activate(ids);
    		setContentView(R.layout.main);
    		setupMenu();
    		Toast.makeText(getApplicationContext(), res, 5).show();
    		break;
    		
    	case R.id.vote_btn:
    		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
    	    
    		// set the message to display
            alertbox.setMessage("Anda yakin untuk memilih " + cand );

            // set a positive/yes button and create a listener
            alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                	String res = service.vote(cand,user);
                	Toast.makeText(getApplicationContext(), res, 15).show();
                	setContentView (R.layout.main);
                	showGiveVote();
                }
            });

            // set a negative/no button and create a listener
            alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(getApplicationContext(), "Voting was canceled.", Toast.LENGTH_SHORT).show();
                }
            });
            alertbox.show();
    		break;
    	}
    }
    
    private OnItemClickListener voteClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int position, long arg3) {
        	setContentView(R.layout.detail);
        	TextView candView = (TextView) v.findViewById(R.id.TextView01);
        	cand = candView.getText().toString();
        	Toast.makeText(getApplicationContext(), "The detail of candidate "+cand, 30).show();
        }
    };
    
    private OnItemClickListener menuClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
        	String option = ((TextView) v).getText().toString(); 
        	
        	if (option.equals("Aktivasi")) setContentView(R.layout.aktivasi);
    
        	else if (option.equals("Login")) setContentView(R.layout.login);
    
        	else if (option.equals("Perihal")) Toast.makeText(getApplicationContext(), "Perihal", 10).show();
        	
        	else if (option.equals("Exit")) finish();
        		
        	else if (option.equals("Give Vote")) {
        		
        		showGiveVote();
        	}
        	else if (option.equals("Vote Result")) {
        		
        		showVoteResult();
        	}
            	
        	else if (option.equals("Sign Out")) {
        		service.signOut(user);
        		setupMenu();
        	}
        }
    };
}