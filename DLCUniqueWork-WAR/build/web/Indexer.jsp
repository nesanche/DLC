<%-- 
    Document   : indexador
    Created on : 03/05/2015, 12:34:48
    Author     : nesanche
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Indexer</title>
        <link rel="shortcut icon" href="img/Google.ico" type="image/x-icon" />
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/estilo.css">
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
                        <input id="searchBar" type="text" name="url" class="form-control" value="C:\Users\juancruz\Desktop\DocumentosTP1">
                        <input type="submit" value="Indexar" class="btn btn-primary col-lg-4 col-lg-offset-4">
                    </form>
                </div>
            </div>
            <br><br><br><br>
            <form method="POST" action="dirindexer">
                <input type="text" name="cadena">  
                <input type="submit" value="Indexat Todo">
            </form>
        </div>
    </body>
</html>
