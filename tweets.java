package ml5part2;
import org.json.simple.JSONObject;
import org.json.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.List;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;



import java.io.Serializable;


import java.util.HashSet;
import java.util.Set;

class Input
{
	Set<String> s;
	String Id;
	Input next;
	
	Input()
	{
		
	}
	Input(Set<String> val1, String val2)
	{
		s=val1;
		Id=val2;
	}
	
}

class list
{
	Input head;
	
	list()
	{
		head = null;
	}
	
	public void insert( Input element )
	{
		
		if(head==null)
		{
			head = element;
		}
		else
		{
			Input temp, temp1;
			temp1 = new Input();
			temp = head;
			while(temp!=null)
			{
				temp1 = temp;
				temp = temp.next;
			}
			temp1.next = element;
		}
	}
	
	public void traverse( Input move)
	{
		if( move == null)
			return;
		else
		{
			while(move != null)
			{
				System.out.println(move.Id);
				move = move.next;
			}
		}
	}
	
	public float computeSSE(Input head, Input centroid)
	{
		
		float SSE = 0;
		Input temp = head;
		float jacarda_distance;
		while(temp!=null)
		{
			
			Set<String> intersection = Sets.intersection(temp.s, centroid.s);
        	
    		Set<String> union = Sets.union(temp.s, centroid.s);
    		float a = intersection.size();
    		float b = union.size();

    		jacarda_distance = 1- (a/b);
    		SSE+= jacarda_distance;
			
			temp=temp.next;
		}
		return SSE;
		
	}
	
	public Input computeCentroid(Input head)
	{
		
		float distance = 0;
		float min_distance = 1000;
		Input newCentroid = new Input();
		Input temp, temp1;
		temp = new Input();
		temp1 = new Input();
		
		temp=head;
		while(temp!=null)
		{
			temp1 = head;
			while(temp1!=null)
			{
				Set<String> intersection = Sets.intersection(temp.s, temp1.s);
	        	
	    		Set<String> union = Sets.union(temp.s, temp1.s);
	    		float a = intersection.size();
	    		float b = union.size();

	    		distance += (1- (a/b));
	    		temp1 = temp1.next;
				
			}
			if(distance<min_distance)
			{
				min_distance = distance;
				newCentroid = temp;
			}
			
			temp= temp.next;
		}
		
		
		return newCentroid;
	}
	
}


 
class readJson
{
	
	String text;
	String id;
	public String getText() {
		return text;
	}
	public String getId()
	{
		return id;
	}
	
	
}
public class tweets {
	
	
	Input [] pair = new Input[300];  // pair is a pair of tweet set and its id
	
	
	private static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	
	
	public void readData()
	{
		
		BufferedReader br = null;
		
		try
		{
			String json = readUrl("http://www.utdallas.edu/~axn112530/cs6375/assignment5/Tweets.json");
			Gson gson = new Gson();    
			JsonReader reader = new JsonReader(new StringReader(json));
			reader.setLenient(true);
			readJson page = gson.fromJson(reader, readJson.class);
		  
			
			Set<String> s [] = new HashSet[305];
			int i=0;
			//At the end of this while, all my inputs ie. tweet set and its id stored in an array called points. 
			while( !page.getId().equals("326228987233841152"))
			{
					 String [] tweet = page.getText().split(" ");
					  s[i] = new HashSet<String>();
					 for (String a : tweet)
			               s[i].add(a);
					 
					 pair[i] = new Input(s[i], page.getId());
					 page = gson.fromJson(reader, readJson.class);
				   
				   i++;
				 
			}
			//now we have array of inputs pairs[]... now we have to sort in clusters


			//System.out.println("---"+page.getText());
			 //  System.out.println(page.getId());
			
	
			
			//---------------------INPUT Taken-----------------------------------//
			
			
			
			
			
		}catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Exception");
		}
		
		
		
	}
	
	
	public void function() throws Exception
	{
		readData();
		//now we have array of inputs pairs[]... now we have to sort in clusters
		
		try{
			
		
		list[] cluster = new list[25];  //bcos there are 25 clusters / centroids
		Input[] centroid = new Input[25];  // every cluster would have a centroid attached to it
		
		
		
		// now we have to read the file for initial centroids...store them in a string array
		
		String[] centroidId = new String[25];
		
		BufferedReader br2 = null;
		String sCurrentLine;
        URL oracle = new URL("http://www.utdallas.edu/~axn112530/cs6375/assignment5/InitialSeeds.txt");
        br2 = new BufferedReader(new InputStreamReader(oracle.openStream()));
         int i=0;
    
        while ((sCurrentLine = br2.readLine()) != null) {
        	String [] split = sCurrentLine.split(",");
        	//System.out.println(split[0]);
        	centroidId[i]=split[0];
        	//System.out.println(sCurrentLine);
        	i++;
        }
        
    	// --------------centroidId array of strings stors the Id for centroid
        // -------------now we have to  start filling Input[]centroid
        
        //compare each pair[] ka id with centroid... if match , make that centroid
        // initializing centroids```````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````
        for( i=0; i<25; i++)
        {
        	for(int j=0; j<250;j++)
        	{
        		if(centroidId[i].equals(pair[j].Id))
        		{
        			centroid[i] = pair[j];
        			System.out.println(centroid[i].Id);
        			break;
        		}
        	}
        }
    
        
        /// NOW WE HAVE All the centroids stored. 
        
        //System.out.println(centroid[7].s);
        
        // Now we will match each inputone at a time with all the
        // centroids and see with whom it is closest. Then add it in that list
        
        // list[] cluster stores all the 25 clusters.
        // compare 2 sets now... 1 set is input[] pair  ... total 250 pairs
        // and other is input [] centroid[]
        
        //initialising the clusters
        for(i=0;i<25;i++)
        {
        	cluster[i] = new list();
        }
        
        
        ///// might have to loop
       int iterations = 1;
       while (iterations <2)
       {
    	   for(i=0;i<25;i++)
           {
           	cluster[i] = new list();
           }
           
    	   for(i=0;i<250;i++)
           { 
           	float min_distance=1000;
           	float jacarda_distance = 0;
           	int min_pos=0;
           	
           	for(int j=0; j<25;j++)
           	{
           		
           		
           		Set<String> intersection = Sets.intersection(pair[i].s, centroid[j].s);
           	
           		Set<String> union = Sets.union(pair[i].s, centroid[j].s);
           		float a = intersection.size();
           		float b = union.size();

           		jacarda_distance = 1- (a/b);
           		if(jacarda_distance < min_distance)
           		{
           			min_distance = jacarda_distance;
           			min_pos=j;
           		}
           		
           	}
           	
           	cluster[min_pos].insert(pair[i]);
           		
           }
    	// calculating the new centroids//
    	   System.out.println("try");
    	   for(i=0;i<25;i++)
    	   {
    		   centroid[i]= new Input();
    	   }
    	   for(i=0;i<25;i++)
    	   {
    		   centroid[i]= cluster[i].computeCentroid(cluster[i].head);
    		   System.out.println(centroid[i].Id);
    	   }
    	   
    	   
    	   readData();
    	   iterations++;
    	   
       }
        
        
    	
        
        
        
        
        ////////////////////////////////////////////////////////////////////////////////////
        
        
        //System.out.println("Final Clustering : ");
        //cluster[21].traverse(cluster[21].head);
        
        
        
        //VALIDATION //
        
        
        //public float computeSSE(Input head, Input centroid)
        
        float totalSSE=0;
        for(i=0;i<25;i++)
        {
        	totalSSE+= cluster[i].computeSSE(cluster[i].head, centroid[i]);
        }
        System.out.println(totalSSE);
        
        
        
        
        
		}catch (Exception e)
		{
			System.out.println(e);
		}
	
		
		
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		
		tweets t1 = new tweets();
		t1.function();

	}
}
