package firman.droidvoter;

import java.io.IOException;
import android.app.ListActivity;
import android.widget.Toast;

public class Global{
	
	public String urlImage = "http://10.0.2.2/project/image/";	
	public String[] parseData(String input, int jlh_data) throws IOException
    {
        int alpha = 0, omega = 0;
        String[] output = new String[jlh_data];

        for (int i=0;i<jlh_data;i++) {
            omega = input. indexOf("_",alpha);
            output[i] = input.substring(alpha,omega);
            alpha = omega+1;
           }
        return(output);
    }
	
	public String[] parseDataPict(String input, int jlh_data) throws IOException
    {
		Global global = new Global();
		
        int alpha = 0, omega = 0;
        String[] output = new String[jlh_data];

        for (int i=0;i<jlh_data;i++) {
            omega = input. indexOf("_",alpha);
            output[i] = global.urlImage + input.substring(alpha,omega);
            alpha = omega+1;
           }
        return(output);
    }
}

