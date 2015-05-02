<%-- 
    Document   : indexador
    Created on : 13/04/2015, 12:34:48
    Author     : Emi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Indexador</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/estilo.css">
    </head>
    <body>
        <nav id="navbar-example" class="navbar navbar-default navbar-static">
            <div class="container-fluid">
                <div class="navbar-header"><a class="navbar-brand" href="index.jsp">Motor de Búsqueda</a></div>
                <div class="collapse navbar-collapse bs-example-js-navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="index.jsp">Buscador</a></li>
                        <li class="active"><a href="indexador.jsp">Indexador</a></li>
                    </ul>                        
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                
                <form method="POST" action="Indexador" class="form-inline">
                    <div class="form-group col-xs-8"><input type="text" name="url" class="form-control" value="C:/DocumentosTP1/"></div> 
                    <input type="submit" value="Indexar" class="btn btn-primary col-xs-4">
                </form>
            </div>
            <br><br><br><br>
            <form method="POST" action="IndexadorDirectorio" style="display: none;">
                <input type="text" name="cadena">  
                <input type="submit" value="Indexat Todo">
            </form>
        </div>
    </body>
</html>
