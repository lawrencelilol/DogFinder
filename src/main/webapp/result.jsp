<%--
* Author: Lawrence Li
* Last Modified: Feb 10, 2022
*
* It contains the title and information of the breed
* chosen by the user.
*
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Dog Breed</title>
</head>
<body>
<%--user's choice of breed--%>
<h1>Dog: <%= request.getParameter("dog-breeds") %></h1>

<%--breed's information--%>
<div>
    <h2>Friendly : <%= request.getAttribute("friendly") %> Stars </h2>
    <h2>Intelligence : <%= request.getAttribute("intelligence") %> Stars </h2>
    <h2>Height : <%= request.getAttribute("height") %>  </h2>
    <h2>Weight : <%= request.getAttribute("weight") %>  </h2>
    <h2>Lifespan : <%= request.getAttribute("lifespan") %> </h2>
    <h3>Credit: https://dogtime.com/dog-breeds/profiles</h3>
</div>

<%--breed's picture--%>
<div>
    <img src="<%= request.getAttribute("picture")%>">
    <h3>Credit: https://dog.ceo/dog-api/</h3>
</div>

</br>
<%--continue to choose another breed--%>
<a href="index.jsp">
    <button class ="submit">Continue</button>
</a>

</body>
</html>
