package firman.droidvoter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class myAdapter extends BaseAdapter {
	
	private Activity activity;
    private String[] data;
    private String[] name;
    private String[] total;
    private String[] lastVote;
    private static LayoutInflater inflater=null;
    private ViewHolder holder;
    
    public myAdapter(Activity a, String[] d, String[] Name, String[] tot, String[] lv) {
    	activity =  a;
    	name = Name;
    	total = tot;
    	lastVote = lv;
        data = d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public static class ViewHolder{
        public TextView text1;
        public TextView text2;
        public TextView text3;
        public ImageView image;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout vi= (RelativeLayout) convertView;
        if(convertView==null){
            vi = (RelativeLayout) inflater.inflate(R.layout.vote_item, parent,false);
            holder=new ViewHolder();
            holder.text1=(TextView)vi.findViewById(R.id.TextView01);
            holder.text2=(TextView)vi.findViewById(R.id.TextView02);
            holder.text3=(TextView)vi.findViewById(R.id.TextView03);
            holder.image=(ImageView)vi.findViewById(R.id.ImageView01);
            vi.setTag(holder);
        }
        else
            holder=(ViewHolder)vi.getTag();
        
        holder.text1.setText(name[position]);
        holder.text2.setText("Total vote "+total[position]);
        holder.text3.setText("last voted "+lastVote[position]);
        holder.image.setTag(data[position]);
        
        Bitmap bitmap = DownloadImage(data[position]);
        holder.image.setImageBitmap(bitmap);
        
        return vi;
    }
    
    private InputStream OpenHttpConnection(String urlString) throws IOException
    {
    	InputStream in = null;
    	int response = -1;
    	
    	URL url = new URL(urlString);
    	URLConnection conn = url.openConnection();
    	
    	if(!(conn instanceof HttpURLConnection)) throw new IOException("Not an Http Connection");
    	
    	try
    	{
    		HttpURLConnection httpConn = (HttpURLConnection) conn;
    		httpConn.setAllowUserInteraction(false);
    		httpConn.setInstanceFollowRedirects(true);
    		httpConn.setRequestMethod("GET");
    		httpConn.connect();
    		
    		response = httpConn.getResponseCode();
    		if(response == HttpURLConnection.HTTP_OK) {
    			in = httpConn.getInputStream();
    		}
    	}
    	catch (Exception e)
    	{
    		throw new IOException("Error Connecting");
    	}
    	return in;
    }
    
    private Bitmap DownloadImage(String URL)
    {
    	Bitmap bitmap = null;
    	InputStream in = null;
    	try
    	{
    		in = OpenHttpConnection(URL);
    		bitmap = BitmapFactory.decodeStream(in);
    		in.close();
    	}
    	catch (IOException el)
    	{
    		el.printStackTrace();
    	}
    	return bitmap;
    }
}
