<%-- 
    Document   : indexador
    Created on : 03/05/2015, 12:34:48
    Author     : nesanche
--%>
<%@page import="com.dlc.uniquework.utils.PropertyProvider"%>
<%@page import="com.dlc.uniquework.data.DataConstants"%>
<%@page import="com.dlc.uniquework.servlets.IndexerServlet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Indexer</title>
        <link rel="shortcut icon" href="img/Google.ico" type="image/x-icon" />
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/styles.css">
    </head>
    <body>
        <nav id="navbar-example" class="navbar navbar-default navbar-static">
            <div class="container-fluid">
                <div class="navbar-header"><a class="navbar-brand" href="Index.jsp">Docoogle</a></div>
                <div class="collapse navbar-collapse bs-example-js-navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="Index.jsp">Search</a></li>
                        <li class="active"><a href="Indexer.jsp">Indexer</a></li>
                    </ul>                        
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-lg-offset-4">
                    <div><img src="img/Docoogle.png" /></div>
                    <form method="POST" action="indexer">
                        <% String path = (String) PropertyProvider.getInstance().getProperty(IndexerServlet.DOCUMENTS_PATH) + "\\"; %>
                        <input id="searchBar" type="text" name="url" class="form-control" value="<%=path%>">
                        <input type="submit" value="Index file" class="btn btn-primary col-lg-4 col-lg-offset-4">
                    </form>
                </div>
            </div>
            <br><br><br><br>
            <div class="col-lg-4 col-lg-offset-4">
                <form method="POST" action="dirindexer">
                    <input type="text" name="cadena" class="form-control">  
                    <input type="submit" value="Index directory" class="btn btn-primary col-lg-4 col-lg-offset-4">
                </form>
            </div>
        </div>
    </body>
</html>
