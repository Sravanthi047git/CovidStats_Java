package jproject;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class readNUpdJson {

	private static final ObjectMapper objectMapper = new ObjectMapper();


	public static void readNUpdJsonMethod() {
		try {

			int g_confirmed, g_recovered, g_deaths;

			Connection connect;
			PreparedStatement updCntry, updGbl, updSrcInfo,updCntryTtl;
		    Statement stmt ;
		    
		    
			URL url = new URL("https://coronavirus.m.pipedream.net");

			JsonNode root = objectMapper.readTree(url);

			JsonNode statsRoot = root.path("summaryStats");
			JsonNode gblRoot = statsRoot.path("global"); // load in Global_data table

			g_confirmed = gblRoot.path("confirmed").asInt();
			g_recovered = gblRoot.path("recovered").asInt();
			g_deaths = gblRoot.path("deaths").asInt();

			System.out.println("Global confirmed : " + g_confirmed);
			System.out.println("Global recovered : " + g_recovered);
			System.out.println("Global deaths : " + g_deaths);

			JsonNode cacheRoot = root.path("cache"); // load in Source_info table
			String lastUpdated = cacheRoot.path("lastUpdated").asText();
			System.out.println("Last updated : " + lastUpdated);

			JsonNode dataSource = root.path("dataSource");
			String durl = dataSource.path("url").asText();
			String publishedBy = dataSource.path("publishedBy").asText();
			String ref = dataSource.path("ref").asText();

			System.out.println("Data source url : " + durl);
			System.out.println("Publishedby : " + publishedBy);
			System.out.println("Reference : " + ref);

			String apiSourceCode = root.path("apiSourceCode").toString();
			System.out.println("API source code : " + apiSourceCode);

			// Country data
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/covid", "root", "");



			String updGblQry = "update Global_data set g_confirmed=?, g_recovered=?, g_deaths=?";

			// Update global table
			updGbl = connect.prepareStatement(updGblQry);

			updGbl.setInt(1, g_confirmed);
			updGbl.setInt(2, g_recovered);
			updGbl.setInt(3, g_deaths);

			updGbl.executeUpdate();

			String updSrcInfoQry = "update Source_info set url=?, publishedBy=?, ref=?, lastUpdated=?";

			// Update information table
			updSrcInfo = connect.prepareStatement(updSrcInfoQry);

			updSrcInfo.setString(1, durl);
			updSrcInfo.setString(2, publishedBy);
			updSrcInfo.setString(3, ref);
			updSrcInfo.setString(4, lastUpdated);

			updSrcInfo.executeUpdate();

			String updCntryQry = "update Country_data set c_confirmed=?, c_recovered=?, c_deaths=?, c_active=?"
					+ " where c_country=? and c_state=?";
			
			// Update Country table
			updCntry = connect.prepareStatement(updCntryQry);

			JsonNode rawRoot = root.path("rawData"); // load in Country_data table

			if (rawRoot.isArray()) {
				for (JsonNode node : rawRoot) {
					String c_country = node.path("Country_Region").asText();
					String c_state = node.path("Province_State").asText();
					int c_confirmed = node.path("Confirmed").asInt();
					int c_deaths = node.path("Deaths").asInt();
					int c_recovered = node.path("Recovered").asInt();
					int c_active = node.path("Active").asInt();

					updCntry.setInt(1, c_confirmed);
					updCntry.setInt(2, c_recovered);
					updCntry.setInt(3, c_deaths);
					updCntry.setInt(4, c_active);
					updCntry.setString(5, c_country);
					updCntry.setString(6, c_state);

					// execute the preparedstatement
					updCntry.executeUpdate();

				}

			}
			
			// Update country total table
			String updCntryTtlQry = "update Country_total set " +
			"ct_confirmed=?, ct_recovered=?, ct_deaths=?, ct_active=?" +
					" where ct_country=? ";

		     stmt = connect.createStatement();
		     ResultSet rs = stmt.executeQuery("SELECT c_country," +
		    	     "max(c_confirmed) c_confirmed,max(c_recovered) c_recovered, " +
		    	    		 " max(c_deaths) c_deaths,max(c_active) c_active FROM Country_data group by c_country");
		     
			// Update global table
			updCntryTtl = connect.prepareStatement(updCntryTtlQry);

		     while ( rs.next() ) {
	             
		    	 updCntryTtl.setInt(1, rs.getInt(2));
		    	 updCntryTtl.setInt(2, rs.getInt(3));
		    	 updCntryTtl.setInt(3, rs.getInt(4));
		    	 updCntryTtl.setInt(4, rs.getInt(5));
		    	 updCntryTtl.setString(5, rs.getString(1));

		    	 updCntryTtl.executeUpdate();
	        }

			
			// Close all the connections
			updCntry.close();
			updGbl.close(); 
			updSrcInfo.close();
			updCntryTtl.close();
			connect.close();

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
		}

	}

	public static void main(String args[]) {

		readNUpdJsonMethod();

	}
}
