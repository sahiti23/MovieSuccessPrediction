import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import java.net.*;

public class OMDB {
	public static void main(String args[]){
		try {
			BufferedReader br=new BufferedReader(new FileReader("C:/Users/sahiti/Desktop/DSTitles.txt"));
	            PrintWriter pw1=new PrintWriter("C:/Users/sahiti/Desktop/movie_details.txt");
	            ConfigurationBuilder cb = new ConfigurationBuilder();
	   		 cb.setDebugEnabled(true)
	   		 .setOAuthConsumerKey("consumer key value type here")
	   		 .setOAuthConsumerSecret("type here")
	   		 .setOAuthAccessToken("type access token here")
	   		 .setOAuthAccessTokenSecret("type here");
	   		    cb.setJSONStoreEnabled(true);

	   		    Twitter twitter = new TwitterFactory(cb.build()).getInstance();

			StringBuilder sb=new StringBuilder();
			String line=br.readLine();
			while(line!=null)
			{
				String s[]=line.split(" ");
				String s1="";
				for(int i=0;i<s.length;i++)
				{
					s1=s1+s[i]+"%20";
				}
				String a="http://www.omdbapi.com/?t="+s1+"&plot=full";
           URL oracle = new URL(a);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));
JSONObject jb;
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	        {
	            System.out.println(inputLine);
	            jb=new JSONObject(inputLine);
	            if(jb.has("Title"))
	            {
	            System.out.println(jb.get("Title"));
	            pw1.append(jb.get("Title").toString());
	            pw1.append("|");
	            pw1.append(jb.get("Year").toString());
	            pw1.write("|");
	           // pw1.append(jb.get("Rated").toString());
	          //  pw1.write("|");
	            pw1.append(jb.get("Runtime").toString());
	            pw1.write("|");
	           // pw1.append(jb.get("Genre").toString());
	           // pw1.write("|");
	           // pw1.append(jb.get("Director").toString());
	           // pw1.write("|");
	            String dir=jb.get("Director").toString();

	            String dr[]=dir.split(",");
	            int cou[]=new int[dr.length];
	            int sum=0,total=0;
	            for(int j=0;j<dr.length;j++)
	            {
	            	ResponseList<User> r=twitter.searchUsers(dr[j], 1);
int Max=0;
	            	for(User u: r)
	    		    {
	    		    	int co=u.getFollowersCount();
	    		    	if(co>Max)
	    		    		Max=co;
	    		    }
	            	if(Max>2000)
	            	{
	            		cou[j]=Max;
	            		sum=sum+Max;
	            		total++;
	            	}
	            	else
	            		cou[j]=0;
	            }
	            int diravg=0;
	            if(total!=0)
	            diravg=sum/total;
	            
	            //pw1.append(jb.get("Actors").toString());
	           // pw1.append("|");

	            String dir1=jb.get("Actors").toString();
	            String dr1[]=dir.split(",");
	            int cou1[]=new int[dr.length];
	            int sum1=0,total1=0;
	            for(int j=0;j<dr1.length;j++)
	            {
	            	ResponseList<User> r=twitter.searchUsers(dr1[j], 1);
                    int Max1=0;
	            	for(User u: r)
	    		    {
	    		    	int co=u.getFollowersCount();
	    		    	if(co>Max1)
	    		    		Max1=co;
	    		    }
	            	if(Max1>2000)
	            	{
	            		cou1[j]=Max1;
	            		sum1=sum1+Max1;
	            		total1++;
	            	}
	            	else
	            		cou1[j]=0;
	            }
	            int diravg1=0;
	            int nof=sum1+sum;
	            int cof=total+total1;
	            if(cof!=0)
	            	diravg1=nof/cof;

	            pw1.append(jb.get("imdbRating").toString());
	            pw1.append("|");
	            if(jb.has("Production"))
	            {
	            //pw1.append(jb.get("Production").toString());
	            ResponseList<User> r=twitter.searchUsers(jb.get("Production").toString(), 1);
	            int Max2=0;
	            	            	for(User u: r)
	            	    		    {
	            	    		    	int co=u.getFollowersCount();
	            	    		    	if(co>Max2)
	            	    		    		Max2=co;
	            	    		    }
	            	            	if(Max2>2000)
	            	            	{
	            	            		nof=sum1+sum+Max2;
	            	    	            cof=total+total1+1;
	            	            		if(cof!=0)
	            	    	            	diravg1=nof/cof;
	            	    	            
	            	            	}
	            	            	else
	            	            		if(cof!=0)
	            	    	            	diravg1=nof/cof;
	            }
	            //pw1.append("|");
	            	
	            pw1.append(String.valueOf(diravg1));
	            		            pw1.append("|");
	            		            	        
	            pw1.append("\n");
	            }
	            
	        }
	        in.close();
	        line=br.readLine();
			}
			pw1.close();
	    }	    
		catch(Exception e){e.printStackTrace();}
	}

}
