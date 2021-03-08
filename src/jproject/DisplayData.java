package jproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
//import javax.servlet.annotation.*;
//import javax.servlet.http.*;

@WebServlet("/DisplayData")
public class DisplayData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	Connection connect;
	Statement selTbl;
	ResultSet rs;
	String g_region;
	int gconfirmed, grecovered, gdeaths;
	int g_confirmed, g_recovered, g_deaths;
	String c_country, c_state;
	int c_confirmed, c_recovered, c_deaths, c_active;
	String[] cc_country, cc_state;
	int[] cc_confirmed, cc_recovered, cc_deaths, cc_active;
	// ArrayList cntryData;
	ArrayList<Object> cntryData = new ArrayList<>();

	public class country {
		String c_country, c_state;
		int c_confirmed, c_recovered, c_deaths, c_active;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
//	    PrintWriter out = response.getWriter();  

		// Reading data from json file
		URL url = new URL("https://coronavirus.m.pipedream.net");

		JsonNode root = objectMapper.readTree(url);

		JsonNode statsRoot = root.path("summaryStats");
		JsonNode gblRoot = statsRoot.path("global"); // load in Global_data table

		g_confirmed = gblRoot.path("confirmed").asInt();
		g_recovered = gblRoot.path("recovered").asInt();
		g_deaths = gblRoot.path("deaths").asInt();

		JsonNode cacheRoot = root.path("cache"); // load in Source_info table
		String lastUpdated = cacheRoot.path("lastUpdated").asText();

		JsonNode dataSource = root.path("dataSource");
		String durl = dataSource.path("url").asText();
		String publishedBy = dataSource.path("publishedBy").asText();
		String ref = dataSource.path("ref").asText();

		String apiSourceCode = root.path("apiSourceCode").toString();

		// Setting global stats here
		HttpSession session = request.getSession();
		session.setAttribute("g_region", "Global");
		session.setAttribute("g_confirmed", g_confirmed);
		session.setAttribute("g_recovered", g_recovered);
		session.setAttribute("g_deaths", g_deaths);

		// Setting information attributes here
		session.setAttribute("g_lastUpdated", lastUpdated);
		session.setAttribute("g_durl", durl);
		session.setAttribute("g_publishedBy", publishedBy);
		session.setAttribute("g_ref", ref);
		session.setAttribute("g_apiSourceCode", apiSourceCode);

		// Load data into customer table in this class-readNUpdJson
		readNUpdJson.readNUpdJsonMethod();

		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
