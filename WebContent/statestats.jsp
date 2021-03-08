<%@ page errorPage="exception.jsp" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
<title>COVID-19 STATE STATUS</title>
<form action="index.jsp" > 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style>
      body {
      background-image: url('https://img.rawpixel.com/s3fs-private/rawpixel_images/website_content/rm208batch8-kwan-02-g.jpg?w=1000&dpr=1&fit=default&crop=default&auto=format&fm=pjpg&q=75&vib=3&con=3&usm=15&bg=F4F4F3&ixlib=js-2.2.1&s=0f299bd2f5cbfd71656d67952d400e01'); 
      background-repeat: no-repeat;
      background-size: 110%; 
    }
    </style>
</head>
<body>
<!-- <img src=https://media.istockphoto.com/vectors/stay-home-stay-safe-vector-id1214174873
alt="USA" width="100" height="100">  -->

<%
/* Common Variable */
/* --------------- */
String Cname = request.getParameter("Cn").toString();
String Sname=request.getParameter("Statename");
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
	 <font size="6" face="arial" color="white"> <%=Sname%> </font> 
	<% } %> 
</center>
</h1> 
         
<!-- COUNTRY FLAG AND NAME DISPLAY -->             
<%
/* SELECT COVID INFORMATION FROM TABLE AS PER SELECTED COUNTRY */
/* ----------------------------------------------------------- */
String querycovid ="SELECT * FROM Country_data WHERE c_state ='"
				+ Sname + "'";
stmt = con.createStatement();   				 
ResultSet rs = stmt.executeQuery(querycovid);
rs.next();			
%>

<!-- COVID STATE STATS TABLE DISPLAY -->
<center>
<table border="1" width="30%" cellpadding="7" bgcolor="#FFFFFF">
<thead>
<tr>
<th colspan="2">Coronavirus Status </th>
</tr>
</thead>
<tbody>
<tr>
<td>Country</td>
<td><%=rs.getString(1)%> </td>
</tr>
<tr>
<td>State</td>
<td><%=rs.getString(2)%> </td>
</tr> 
<tr>
<td>Confirmed Cases</td>
<td><%=rs.getString(3)%></td>
</tr>
<tr>
<td>Deaths</td>
<td><%=rs.getString(4)%></td>
</tr>
<tr>
<td>Recovered</td>
<td><%=rs.getString(5)%></td>
</tr>
</tbody>
</table>

<br>
<input type="submit" id="search"  value="CLICK"
      style=" border: none; background: url(https://elearningimages.adobe.com/files/2018/05/Button1.png);
        background-size: 100%;  color: white; font-size: 14px; width: 70px; height: 20px; border-radius: 8px;" />
        <font  color="white"> to go back Main Page </font> 

<br><br><br><br><br><br><br><br><br><br><br><br><br><br>

 <font size="2" color="white"> Reference url: </font>  <a href="${g_durl}"> 
        <font size="2" color="white"> CSSE_covid_19_data </font> </a>
</center>
</form>
</body>
</html>

