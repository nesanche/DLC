
<%@page import="com.dlc.uniquework.data.FileAccess"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Status</title>
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
                <div class="navbar-header"><a class="navbar-brand" href="index.jsp">Docoogle</a></div>
                <div class="collapse navbar-collapse bs-example-js-navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="Index.jsp">Search</a></li>
                        <li><a href="Indexer.jsp">Indexer</a></li>
                    </ul>                        
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <h4>Status:</h4>
                <div id="resultado">processsing...</div>
                <br>  
                <a class="btn btn-primary" href="Indexer.jsp" role="button">Back</a> 
            </div>
        </div>
    </body>
</html>
