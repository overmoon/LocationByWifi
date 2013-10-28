package edu.vt.ece4564.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.json.JSONObject;

public class MainServlet extends HttpServlet{
	
	public static List<String> rooms = new ArrayList<String>();

	public static void main(String[] args) throws Exception{
		Server server = new Server(8080);
		
		WebAppContext context = new WebAppContext();
		context.setWar("war");
		context.setContextPath("/");
		server.setHandler(context);
		
		server.start();
		server.join();

	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.getWriter().write("This is the Main Servlet");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		 StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = req.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }
		  
		  String data = jb.toString();
		  System.out.println(data);
		  String roomResp = "";
		  
		  if (data.indexOf("SSID:") != 11)
		  {
			  MainServlet.rooms.add(data);
			  roomResp = "Room successfully added";
		  }
		  else
		  {
		  List<Integer> scores = new ArrayList<Integer>();
		  String[] networks = data.split("SSID: ");
		  List<String> ssids = new ArrayList<String>();
		  List<String> bssids = new ArrayList<String>();
		  List<Integer> levels = new ArrayList<Integer>();
		  List<String> ssids2 = new ArrayList<String>();
		  List<String> bssids2 = new ArrayList<String>();
		  List<Integer> levels2 = new ArrayList<Integer>();
/*		  for (int i = 0; i < networks.length; i++)
		  {
			  System.out.println(networks[i]);
		  }*/
		  for (int i = 1; i < networks.length; i++)
		  {
			  String ssid = networks[i].substring(0,networks[i].indexOf("BSID:"));
			  String bsid = networks[i].substring(networks[i].indexOf("BSID: ")+6, networks[i].indexOf("Level"));
			  String level = networks[i].substring(networks[i].indexOf("Level: ")+7, networks[i].indexOf("Level: ")+10);
			  ssids.add(ssid);
			  bssids.add(bsid);
			  levels.add(Integer.parseInt(level));
		  }
		  for (int i = 0; i < rooms.size(); i++)
		  {
			  Integer score = 0;
			  String[] networks2 = rooms.get(i).split("SSID: ");
			  for (int j = 1; j < networks2.length; j++)
			  {
				  String ssid = networks2[j].substring(0,networks2[j].indexOf("BSID:"));
				  String bsid = networks2[j].substring(networks2[j].indexOf("BSID: ")+6, networks2[j].indexOf("Level"));
				  String level = networks2[j].substring(networks2[j].indexOf("Level: ")+7, networks2[j].indexOf("Level: ")+10);
				  ssids2.add(ssid);
				  bssids2.add(bsid);
				  levels2.add(Integer.parseInt(level));
			  }
			  for (int k = 0; k < ssids.size(); k++)
			  {
				  score = 0;
				  Integer index;
				  if (ssids2.contains(ssids.get(k)))
				  {
					  score+=5;
				  }
				  if (bssids2.contains(bssids.get(k)))
				  {
					  index = bssids2.indexOf(bssids.get(k));
					  score+=25;
					  score -= Math.abs(levels.get(k) - levels2.get(index));
				  }
			  }
			  scores.add(score);
			  ssids2.clear();
			  bssids2.clear();
			  levels2.clear();
		  }
		  int max = -999;
		  int index = 0;
		  	for (int i = 0; i < scores.size(); i++)
		  	{
		  		if (scores.get(i) > max)
		  		{
		  			index = i;
		  		}
		  	}
		  	roomResp = MainServlet.rooms.get(index).substring(9,MainServlet.rooms.get(index).indexOf("SSID"));
		  }
		  System.out.println(roomResp);
		  out.print(roomResp);
		  out.flush();

	}

}
