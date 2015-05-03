
<%@page import="com.dlc.uniquework.data.FileAccess"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Estado</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/estilo.css">
        <script src="js/jquery.min.js"></script>
        <script>
	$(document).ready(function() {
                var resultado = "";
		interval = setInterval(function(){
                        
			$.post('FileAccessServlet', {
				
			}, function(responseText) {
                            resultado = responseText; 
                            if(resultado.length != 2){ 
                                $('#resultado').html(responseText);
                                clearInterval(interval); 
                            }                           
			});
		},1000);                
	});
    </script>
    </head>
    <body>
        <nav id="navbar-example" class="navbar navbar-default navbar-static">
            <div class="container-fluid">
                <div class="navbar-header"><a class="navbar-brand" href="index.jsp">Motor de Búsqueda</a></div>
                <div class="collapse navbar-collapse bs-example-js-navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="index.jsp">Buscador</a></li>
                        <li><a href="indexador.jsp">Indexador</a></li>
                    </ul>                        
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <h4>Estado:</h4>
                <div id="resultado">procesando...</div>
                <br>  
                <a class="btn btn-primary" href="indexador.jsp" role="button">Volver</a> 
            </div>
        </div>
    </body>
</html>
