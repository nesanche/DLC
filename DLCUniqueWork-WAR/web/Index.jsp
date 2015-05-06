<%-- 
    Document   : index
    Created on : 02/05/2015, 12:27:00
    Author     : nesanche
--%>

<%@page import="org.apache.jasper.tagplugins.jstl.ForEach"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Docoogle Search</title>
        <link rel="shortcut icon" href="img/Google.ico" type="image/x-icon" />
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/estilo.css">
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/paging.js"></script>
        <style>

        </style>
    </head>
    <body>
        <nav id="navbar-example" class="navbar navbar-default navbar-static">
            <div class="container-fluid">
                <div class="navbar-header"><a class="navbar-brand" href="index.jsp">Docoogle</a></div>
                <div class="collapse navbar-collapse bs-example-js-navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="Index.jsp">Search</a></li>
                        <li><a href="Indexer.jsp">Indexer</a></li>
                    </ul>                        
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-lg-offset-4">
                    <div><img src="img/Docoogle.png" /></div>
                    <form method="POST" action="search">
                        <input  id="searchBar" type="text" name="cadena" class="form-control" value="${cadena}">
                        <input type="submit" value="Buscar" class="btn btn-primary col-lg-4 col-lg-offset-4">
                    </form>
                </div>
            </div>
            <br><br>  
            <div class="row">
                <div style="display:none; border: 0px;" id="NavPosicion"></div>
                <table class="table table-hover" id="resultados">
                    <tr><td class="text-center">${cantidadRealResultados}</td></tr>  
                    <% if( request.getAttribute("resultado") != null ) {
                        String[] resultados = (String[]) request.getAttribute("resultado");
                        double[] rank = (double[]) request.getAttribute("rank");
                        for(int i = 0; i<resultados.length; i++){%>
                    <tr><td><%=i+1%>- El resultado es: <%=rank[i]%> correspondiente al archivo: <a href="C:\Users\Franco\Desktop\DLC\DLC\DocumentosTP1\<%=resultados[i]%>"><%=resultados[i]%></a></td></tr>
                    <% } request.removeAttribute("resultado");request.removeAttribute("rank"); } %>
                </table>
            </div>
                
                
            <nav class="text-center">
                <ul class="pagination">
                    <li id="prev" class="disabled">
                        <a href="#" aria-label="Previous">
                          <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>                                      
            <script>  
                var total = 0${cantidad};
                paginas = total / 10;
                var actual = 1;
                if(total!==0){
                    var pager = new Pager('resultados', 10);
                    pager.init();
                    pager.showPageNav('pager', 'NavPosicion');
                    pager.showPage(1);

                    for(var i = 0; i<paginas; i++){
                        document.write("<li class='pag' id="+(i+1)+"><a href='#' onclick='changePage("+(i+1)+")'>"+(i+1)+"</a></li>");
                    }
                    $("#"+actual).addClass("active");
                }
                else $(".pagination").hide();                
                
                function changePage(n){
                    pager.showPage(n);
                    actual=n;
                    $(".pag").removeClass("active");
                    $("#"+actual).addClass("active");       
                    
                    if(actual > 1){
                        $("#prev").removeClass("disabled");
                        $("#prev > a").attr("onclick", "changePage("+(actual-1)+")");
                    }
                    else {
                        $("#prev").addClass("disabled");
                    }
                    if(actual > paginas){
                        $("#next").addClass("disabled");
                    }
                    else {
                        $("#next").removeClass("disabled");
                        $("#next > a").attr("onclick", "changePage("+(actual+1)+")");
                    }
                }
            </script>
                    <li id="next">
                        <a href="#" aria-label="Next" onclick="changePage(2)">
                        <span aria-hidden="true">&raquo;</span>
                      </a>
                    </li>
                </ul>
            </nav>                
        </div>    
        <script>$("#resultados > tbody > tr > td > a").attr("target","_blank");</script>
    </body>
</html>
