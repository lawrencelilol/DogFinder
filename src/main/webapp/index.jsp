<%--
* Author: Lawrence Li
* Last Modified: Feb 10, 2022
*
* It contains the title and my name
* It also includes a drop down menu of dog breeds and a submit button.
* The submission button is used to send user's input the servlet
*
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Project 1 Task 2</title>
</head>
<body>
<h1><%= "Dog Finder" %>
</h1>
<h3>
    Created By Lawrence Li
</h3>

<h2>
    Dog Breeds
</h2>

<h3>
    Choose a dog breed
</h3>

<%--This is the form to send the users input to DogFinder Servlet--%>
<form method="GET" action="getADog">
    <p>
        <%--Drop down menu of dog breeds --%>
        <select name="dog-breeds" id="dog-breeds">
            <option value="Borzoi">Borzoi</option>
            <option value="Boxer">Boxer</option>
            <option value="Chihuahua">Chihuahua</option>
            <option value="Collie">Collie</option>
            <option value="Dachshund">Dachshund</option>
            <option value="Dalmatian">Dalmatian</option>
            <option value="Maltese">Maltese</option>
            <option value="Otterhound">Otterhound</option>
            <option value="Poodle">Poodle</option>
            <option value="Rottweiler">Rottweiler</option>
            <option value="Saluki">Saluki</option>
            <option value="Whippet">Whippet</option>
        </select>
    </p>

    <%-- Submit button to send users' input to DogFinder Servlet --%>
    <a href="getADog">
        <button class ="submit">Submit</button>
    </a>
</form>

</body>
</html>