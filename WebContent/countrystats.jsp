<%@ page errorPage="exception.jsp" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
<title>COVID-19 CORONAVIRUS PANDEMIC</title>
<form action="statestats.jsp" > 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
      body {
      background-image: url('https://img.rawpixel.com/s3fs-private/rawpixel_images/website_content/rm208batch8-kwan-02-g.jpg?w=1000&dpr=1&fit=default&crop=default&auto=format&fm=pjpg&q=75&vib=3&con=3&usm=15&bg=F4F4F3&ixlib=js-2.2.1&s=0f299bd2f5cbfd71656d67952d400e01'); 
      background-repeat: no-repeat;
      background-size: 110%; 
    }
    </style>
</head>
<body>
 
<%
/* Common Variable */
/* --------------- */
String Cname=request.getParameter("Countryname");
%>

<%
/* JDBC Connection */
/* --------------- */
Connection con;
Statement stmt;
Class.forName("com.mysql.jdbc.Driver");
con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Covid","root","");
%>
<br><br><br><br><br>
<%
/* DISPLAY COUNTRY FLAG BASED ON COUNTRY SELECTED */
/* ------------------------------------------------------- */
try{
String queryimg="SELECT * FROM country_flag WHERE Country ='"
				+ Cname + "'";
stmt = con.createStatement();   				 
ResultSet rs1 = stmt.executeQuery(queryimg);
rs1.next();	%>
<h1>  
<center>
<img src= <%=rs1.getString(2)%> alt="USA" width="70" height="40"> 
 <font size="6" face="arial" color="white"> <%=rs1.getString(1)%> </font> 
</center>
</h1>  
<% 
}catch (SQLException ex) { %> 
<h1>  
<center>
	   <font size="6" face="arial" color="white"> <%=Cname%> </font> 
	<% } %> 
</center>
</h1> 

<%
/* SELECT COVID INFORMATION FROM TABLE AS PER SELECTED COUNTRY */
/* ----------------------------------------------------------- */
int CC = 0;
int Dth = 0;
int Rec = 0;
String querycovid ="SELECT * FROM Country_data WHERE c_country ='"
				+ Cname + "'";
stmt = con.createStatement();   				 
ResultSet rs = stmt.executeQuery(querycovid);
while(rs.next()){ 	
	CC = CC + rs.getInt(3);
	Dth = Dth + rs.getInt(5);
	Rec = Rec + rs.getInt(4);
}
%>

<!-- COVID COUNTRY STATS TABLE DISPLAY -->
<center>
<table border="1" width="30%" cellpadding="7" bgcolor="#FFFFFF">
<thead>
<tr>
<th colspan="2">Coronavirus Status </th>
</tr>
</thead>
<tbody>
<tr>
<td>Confirmed Cases</td>
<td><%=CC%></td>
</tr>
<tr>
<td>Deaths</td>
<td><%=Dth%></td> 
</tr>
<tr>
<td>Recovered</td>
<td><%=Rec%></td>
</tr>
</tbody>
</table>

<br><br><br><br>
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
<h3>  <font size="5" face="arial" color="white"> State view : <select name="Statename"> </font> 
<option> Select State      </option>

<%
/* Dropdown load for selecting State based on Selected country */
/* ----------------------------------------------------------- */
try {
String querystate ="SELECT DISTINCT c_state FROM Country_data WHERE c_country ='"
		+ Cname + "'";
stmt = con.createStatement();   				 
ResultSet rs4 = stmt.executeQuery(querystate);
while(rs4.next()){ 
%> 
<option><%=rs4.getString("c_state")%></option>
<% } 
 }catch (SQLException ex) {  }
 catch (Exception ex) {  }
%> 

<input type="hidden" name="Cn" value="<%=Cname%>"/>
<input type="submit" id="search" value="SUBMIT"  
      style=" border: none; background: url(https://elearningimages.adobe.com/files/2018/05/Button1.png);
        background-size: 100%;  color: white; font-size: 14px; width: 100px; height: 27px; border-radius: 8px;" />
</h3>
</select>
<br><br><br><br><br><br>
 <font size="2" color="white"> Reference url: </font>  <a href="${g_durl}"> 
       <font size="2" color="white"> CSSE_covid_19_data </font> </a>
</center>  
</form>
</body>
</html>