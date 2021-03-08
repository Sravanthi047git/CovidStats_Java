<%@ page errorPage="exception.jsp" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
<title>COVID-19 CORONAVIRUS PANDEMIC</title>
 <style>
      body {
      background-image: url('https://files.bovill.com/wp-content/uploads/2020/03/Covid-19-and-conduct-risk-%E2%80%93-are-you-taking-reasonable-steps-1024x512.jpg'); 
      background-repeat: no-repeat;
      background-size: 130%;  
    }
    </style>

</head>
<form action="countrystats.jsp"> 
<body>
<center>
<h1>  
<img src=https://previews.123rf.com/images/1xpert/1xpert1504/1xpert150400297/39057635-earth-globe-elements-of-this-image-furnished.jpg
alt="CVD" width="120" height="120"> 
COVID GLOBAL STATUS:

</h1> 
<%-- <h>4 ${g_region}</h4> --%>
<h3> Confirmed cases: <br> <font size="5" color="#368BC1"> ${g_confirmed} </font> </h3> 
<h3> Recovered cases: <br> <font size="5" color="#008080"> ${g_recovered} </font> </h3> 
<h3> Deceased cases:  <br> <font size="5" color="#E55451"> ${g_deaths} </font> </h3> 
<h5> Last updated: ${g_lastUpdated}</h5> 
<h3>
<br>
</center>

<%
/* JDBC Connection */
/* --------------- */
Connection con = null;
Statement stmt = null;
 try {
Class.forName("com.mysql.jdbc.Driver");
con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Covid","root","");
 }catch (SQLException ex) {  }
catch (Exception ex) {  } 
%>
<center>
<style>
    select {
        width: 350px;
        height: 30px;
        margin: 20px;
        font-size:12pt;
    }
    select:focus {
        min-width: 350px;
        width: auto;
    }    
</style>
<h3>Country view : <select name="Countryname">
<option>Select Country   </option>
<% 
/* Dropdown load for selecting COUNTRY */
/* ----------------------------------- */
try {
String querycntry ="SELECT DISTINCT c_country FROM Country_data";
stmt = con.createStatement();   				 
ResultSet rs3 = stmt.executeQuery(querycntry);
while(rs3.next()){ 
%> 
<option><%=rs3.getString("c_country")%></option>
<% } 
 }catch (SQLException ex) {  }
 catch (Exception ex) {  }
%> 
</select>
<input type="submit" id="search"  value="SUBMIT" 
      style=" border: none; background: url(https://elearningimages.adobe.com/files/2018/05/Button1.png);
        background-size: 100%;  color: white; font-size: 14px; width: 100px; height: 31px; border-radius: 8px;" />
</h3>
<font size="2" color="white"> Reference url: </font>  <a href="${g_durl}"> 
        <font size="2" color="white"> CSSE_covid_19_data </font> </a>
      
<%-- <h5>Reference url: <a href="${g_durl}"> CSSE_covid_19_data </a></h5> --%>
<%-- <h6>Published by:  ${g_publishedBy}</h6> --%>
<%-- <h6>Reference: ${g_ref}/h6>
<h6>API source code: ${g_apiSourceCode}/h6> --%>

<br><br><br><br><br><br><br><br>
 <font size="2" color="Black"> Reference url: </font>  <a href="${g_durl}"> 
        <font size="2" color="black"> CSSE_covid_19_data </font> </a>
</center>
</form>
</body>
</html>