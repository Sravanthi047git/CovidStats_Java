package jproject;


//import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.IOException;

public class tblFirstLoad {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	int g_confirmed,g_recovered,g_deaths;

    Connection connect;
    PreparedStatement insGbl,insSrc, insCus;
    String[] c_country,c_state;
    int[] c_confirmed,c_recovered,c_deaths,c_active;
	int i=0;
	
    public tblFirstLoad(){
    try {
    
     connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/covid", "root", "");
     String insCustQry = " insert into Country_data (c_country, c_state, c_confirmed,c_recovered,c_deaths,c_active)"
   	        + " values (?, ?, ?, ?, ?, ?)";
     
     insCus = connect.prepareStatement(insCustQry);
     
     URL url = new URL("https://coronavirus.m.pipedream.net");
     
     JsonNode root = objectMapper.readTree(url);
          
     JsonNode statsRoot=root.path("summaryStats");
     JsonNode gblRoot=statsRoot.path("global");  // load in Global_data table
     
     g_confirmed = gblRoot.path("confirmed").asInt();
     g_recovered = gblRoot.path("recovered").asInt();
     g_deaths = gblRoot.path("deaths").asInt();
     
     System.out.println("Global confirmed : " + g_confirmed);
     System.out.println("Global recovered : " + g_recovered);
     System.out.println("Global deaths : " + g_deaths);
     
 
     JsonNode cacheRoot=root.path("cache");   // load in Source_info table
     String lastUpdated = cacheRoot.path("lastUpdated").asText();
     System.out.println("Last updated : " + lastUpdated);
     
     JsonNode dataSource=root.path("dataSource");
     String durl = dataSource.path("url").asText();
     String publishedBy = dataSource.path("publishedBy").asText();
     String ref = dataSource.path("ref").asText();
     
     System.out.println("Data source url : " + durl);
     System.out.println("Publishedby : " + publishedBy);
     System.out.println("Reference : " + ref);
     
     
     String apiSourceCode = root.path("apiSourceCode").toString();
     System.out.println("API source code : "+ apiSourceCode);
     
     // insert data into tables
     
     // load data into global table
     String insGblQry = " insert into Global_data (g_confirmed, g_recovered, g_deaths)"
  	        + " values (?, ?, ?)";
  	
     // create the mysql insert preparedstatement
  	insGbl = connect.prepareStatement(insGblQry);
  	
  	insGbl.setInt(1, g_confirmed);
  	insGbl.setInt(2, g_recovered);
  	insGbl.setInt(3, g_deaths);

     // execute the preparedstatement
  	insGbl.execute();
     

      // load data into source info table
      String srcInfoQry = " insert into Source_info (url, publishedBy, ref,lastUpdated)"
   	        + " values (?, ?, ?, ?)";
   	
      // create the mysql insert preparedstatement
      insSrc = connect.prepareStatement(srcInfoQry);
   	
      insSrc.setString(1, durl);
      insSrc.setString(2, publishedBy);
      insSrc.setString(3, ref);
      insSrc.setString(4, lastUpdated);

      // execute the preparedstatement
      insSrc.execute();

     
     JsonNode rawRoot=root.path("rawData");  // load in Country_data table
     
//     String country=null,state=null;
//     int confirmed=0,recovered=0,deaths=0,active=0;
     
     if(rawRoot.isArray()) {
    	 System.out.println("Is this node an Array? " + rawRoot.isArray());	 
    	 
    	 for(JsonNode node: rawRoot) {
    		 String c_country = node.path("Country_Region").asText();
    		 String c_state = node.path("Province_State").asText();
    		 int c_confirmed = node.path("Confirmed").asInt();
    		 int c_deaths = node.path("Deaths").asInt();
    		 int c_recovered = node.path("Recovered").asInt();
    		 int c_active = node.path("Active").asInt();
    		 
    		 System.out.println("Country : " + c_country);
    		 System.out.println("State : " + c_state);
    		 System.out.println("Confirmed : " + c_confirmed);
    		 System.out.println("Deaths : " + c_deaths);
    		 System.out.println("Recovered : " + c_recovered);
    		 System.out.println("Active : " + c_active);
    		 
    	     insCus.setString(1, c_country);
    	     insCus.setString(2, c_state);
    	     insCus.setInt(3, c_confirmed);
    	     insCus.setInt(4, c_recovered);
    	     insCus.setInt(5, c_deaths);
    	     insCus.setInt(6, c_active);

    	     // execute the preparedstatement
    	     insCus.execute();
    		 
    	 }

     }
     
   	connect.close();
     

 	
    } catch(JsonMappingException e) {e.printStackTrace();}
    catch (SQLException ex) {ex.printStackTrace();}
 	catch (IOException e) { e.printStackTrace();}
     catch (Exception ex) {}
    

 }
    
     
public boolean nullChkStr( String col) {
	 if((col == null & col.length() ==0)) {
		 return true;
	 }
	 return false;
}
	

public static void main(String args[]){
	
    new tblFirstLoad();
// try {


     
/*     JsonNode cacheRoot=root.path("cache");   // load in Source_info table
     String lastUpdated = cacheRoot.path("lastUpdated").asText();
     System.out.println("Last updated : " + lastUpdated);
     
     JsonNode dataSource=root.path("dataSource");
     String durl = dataSource.path("url").asText();
     String publishedBy = dataSource.path("publishedBy").asText();
     String ref = dataSource.path("ref").asText();
     
     System.out.println("Data source url : " + durl);
     System.out.println("Publishedby : " + publishedBy);
     System.out.println("Reference : " + ref);
     
     
     String apiSourceCode = root.path("apiSourceCode").toString();
     System.out.println("API source code : "+ apiSourceCode);
     
     JsonNode rawRoot=root.path("rawData");  // load in Country_data table
     if(rawRoot.isArray()) {
    	 System.out.println("Is this node an Array? " + rawRoot.isArray());	 
    	 
    	 for(JsonNode node: rawRoot) {
    		 String c_country = node.path("Country_Region").asText();
    		 String c_state = node.path("Province_State").asText();
    		 int c_confirmed = node.path("Confirmed").asInt();
    		 int c_deaths = node.path("Deaths").asInt();
    		 int c_recovered = node.path("Recovered").asInt();
    		 int c_active = node.path("Active").asInt();
    		 
    		 System.out.println("Country : " + c_country);
    		 System.out.println("State : " + c_state);
    		 System.out.println("Confirmed : " + c_confirmed);
    		 System.out.println("Deaths : " + c_deaths);
    		 System.out.println("Recovered : " + c_recovered);
    		 System.out.println("Active : " + c_active);
    		 
    	 }
     }
     */
//	} catch (JsonMappingException e) { e.printStackTrace();	}
// catch (IOException e) { e.printStackTrace();	}
	
}


}
