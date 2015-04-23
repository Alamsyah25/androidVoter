package firman.droidvoter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class services {
	Global global = new Global();
	private String URL = "http://10.0.2.2/project";
	
	public String logIn(String nick, String pwd) {
		String url ="";
		String response ="";
		
		try {
			url = URL+"/AplydroidVoter.php?c=s&k="+nick+"&p="+pwd;
			response = call(url);
		}
		catch (Exception e ) {}
		return response;
	}
	
	public String signOut(String nick) {
		String url ="";
		String response ="";
		
		try {
			url = URL+"/AplydroidVoter.php?c=0&k="+nick;
			response = call(url);
		}
		catch (Exception e ) {}
		return response;
	}
	
	public String activate(String id){
		String url ="";
		String response ="";
		
		try {
			url = URL+"/AplydroidVoter.php?c=r&id="+id;
			response = call(url);
		}
		catch (Exception e ) {}
		return response;
	}
	
	public String vote(String name, String user) {
		String url ="";
		String response ="";
		
		try {
			url = URL+"/AplydroidVoter.php?c=v&name="+name+"&user="+user;
			response = call(url);
		}
		catch (Exception e ) {}
		return response;
	}
	
	
	public String[] getName() {
		String[] candidate = null;
		
		String url 	= "";
		String response = "";
        	
			
			try {
        	        //bagian ini bila Anda menggunakan server localhost
        	        url = URL+"/AplydroidVoter.php?c=candidate";
        	        	
        	        //bagian ini bila Anda menggunakan server AplysIT
        	        //url = "http://www.aplysit.com/android/AplydroidVoter.php?c=candidate";
        	        
        	        response = call(url);
        	    }
        	    catch (Exception er)
        	    {
        	      	
        	    }        
        	    finally
        	    {
        	    	String ol = response;
    	            
    	            int alpha = 0 , omega = 0;

    	            int posNumber = ol.indexOf("_",0);
    	            int olNumber  = Integer.parseInt(ol.substring(0,posNumber));
    	            
    	            candidate = new String[olNumber];
    	            ol = ol.substring(posNumber+1,ol.length());

    	            try {
    	            	candidate = global.parseData(ol,olNumber);
    	            } catch (IOException ex) {
    	            }

    	    }
    	    return candidate;
    }
	
	public String[] getPicture() {
		String[] picture = null;
		String url 	= "";
		String response = "";
        	
			
			try {
        	        //bagian ini bila Anda menggunakan server localhost
        	        url = URL+"/AplydroidVoter.php?c=candidatepict";
        	        	
        	        //bagian ini bila Anda menggunakan server AplysIT
        	        //url = "http://www.aplysit.com/android/AplydroidVoter.php?c=candidate";
        	        
        	        response = call(url);
        	    }
        	    catch (Exception er)
        	    {
        	      	
        	    }        
        	    finally
        	    {
        	    	String ol = response;
    	            
    	            int alpha = 0 , omega = 0;

    	            int posNumber = ol.indexOf("_",0);
    	            int olNumber  = Integer.parseInt(ol.substring(0,posNumber));
    	            
    	            picture = new String[olNumber];
    	            ol = ol.substring(posNumber+1,ol.length());

    	            try {
    	            	picture = global.parseDataPict(ol, olNumber);
    	            } catch (IOException ex) {
    	            }

        	    }
        	    return picture;
		
	}
	
	public String[] getTotal(){
		String[] out = null;
		String url 	= "";
		String response = "";
        	
			
			try {
        	        //bagian ini bila Anda menggunakan server localhost
        	        url = URL+"/AplydroidVoter.php?c=tv";
        	        	
        	        //bagian ini bila Anda menggunakan server AplysIT
        	        //url = "http://www.aplysit.com/android/AplydroidVoter.php?c=candidate";
        	        
        	        response = call(url);
        	    }
        	    catch (Exception er)
        	    {
        	      	
        	    }        
        	    finally
        	    {
        	    	String ol = response;
    	            
    	            int alpha = 0 , omega = 0;

    	            int posNumber = ol.indexOf("_",0);
    	            int olNumber  = Integer.parseInt(ol.substring(0,posNumber));
    	            
    	            out = new String[olNumber];
    	            ol = ol.substring(posNumber+1,ol.length());

    	            try {
    	            	out = global.parseData(ol,olNumber);
    	            } catch (IOException ex) {
    	            }

        	    }
        	    return out;
	}
	
	public String[] getLastVote(){
		String[] out = null;
		String url 	= "";
		String response = "";
        	
			
			try {
        	        //bagian ini bila Anda menggunakan server localhost
        	        url = URL+"/AplydroidVoter.php?c=lv";
        	        	
        	        //bagian ini bila Anda menggunakan server AplysIT
        	        //url = "http://www.aplysit.com/android/AplydroidVoter.php?c=candidate";
        	        
        	        response = call(url);
        	    }
        	    catch (Exception er)
        	    {
        	      	
        	    }        
        	    finally
        	    {
        	    	String ol = response;
    	            
    	            int alpha = 0 , omega = 0;

    	            int posNumber = ol.indexOf("_",0);
    	            int olNumber  = Integer.parseInt(ol.substring(0,posNumber));
    	            
    	            out = new String[olNumber];
    	            ol = ol.substring(posNumber+1,ol.length());

    	            try {
    	            	out = global.parseData(ol,olNumber);
    	            } catch (IOException ex) {
    	            }
        	    }
        	    return out;
	}
	
	private String call(String url) {
		// TODO Auto-generated method stub
		int BUFFER_SIZE = 2000;
		InputStream in = null;
		try {
			in = OpenHttpConnection(url);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
		
		InputStreamReader isr = new InputStreamReader(in);
		int charRead;
		String str="";
		char[] inputBuffer = new char[BUFFER_SIZE];
		try {
			while((charRead = isr.read(inputBuffer))>0){
				String readString = String.copyValueOf(inputBuffer,0,charRead);
				str += readString;
				inputBuffer = new char[BUFFER_SIZE];
			}
			in.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
		return str;
	}

	private InputStream OpenHttpConnection(String urlString) throws IOException {
		// TODO Auto-generated method stub
		InputStream in =null;
		int response = -1;
		
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not An HTTP connection");
		
		try {
			HttpURLConnection httpconn = (HttpURLConnection) conn;
			httpconn.setAllowUserInteraction(false);
			httpconn.setInstanceFollowRedirects(true);
			httpconn.setRequestMethod("GET");
			httpconn.connect();
			
			response = httpconn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK){
				in = httpconn.getInputStream();
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new IOException("Error connecting");
		}
		
		
		
		return in;
	}
}
