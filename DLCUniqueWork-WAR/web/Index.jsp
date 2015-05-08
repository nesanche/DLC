<%-- 
    Document   : index
    Created on : 02/05/2015, 12:27:00
    Author     : nesanche
--%>

<%@page import="com.dlc.uniquework.servlets.SearcherServlet"%>
<%@page import="org.apache.jasper.tagplugins.jstl.ForEach"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Docoogle Search</title>
        <link rel="shortcut icon" href="img/Google.ico" type="image/x-icon" />
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/styles.css">
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/paging.js"></script>
        <style>

        </style>
    </head>
    <body class="page-wrap">
        <nav id="navbar-example" class="navbar navbar-default navbar-static">
            <div class="container-fluid">
                <div class="collapse navbar-collapse bs-example-js-navbar-collapse">
                    <ul class="nav navbar-nav">
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
                        <input type="text" name="cadena" class="form-control searchBar" value="${cadena}">
                        <input type="submit" value="Buscar" class="btn btn-primary col-lg-4 col-lg-offset-4">
                    </form>
                </div>
            </div>
            <br><br>  
            <div class="row">
                <div style="display:none; border: 0px;" id="NavPosicion"></div>
                <table class="table table-hover" id="resultados">
                    <tr><td class="text-center">${cantidadRealResultados}</td></tr>  
                    <% if( request.getAttribute(SearcherServlet.RESULT_PARAMETER) != null ) {
                        String[] results = (String[]) request.getAttribute(SearcherServlet.RESULT_PARAMETER);
                        double[] rank = (double[]) request.getAttribute(SearcherServlet.RANK_PARAMETER);
                        String documents_path = (String) request.getAttribute(SearcherServlet.DOCUMENTS_PATH);
                        for(int i = 0; i<results.length; i++){%>
                    <tr><td><%=i+1%>- El resultado es: <%=rank[i]%> correspondiente al archivo: <a href="<%=documents_path%>\<%=results[i]%>"><%=results[i]%></a></td></tr>
                    <% } request.removeAttribute(SearcherServlet.RESULT_PARAMETER);request.removeAttribute(SearcherServlet.RANK_PARAMETER); } %>
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
                pages = total / 10;
                var actual = 1;
                if(total!==0){
                    var pager = new Pager('resultados', 10);
                    pager.init();
                    pager.showPageNav('pager', 'NavPosicion');
                    pager.showPage(1);

                    for(var i = 0; i<pages; i++){
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
                    if(actual > pages){
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
        <footer class="site-footer">DLC | 4k6 | UTN - FRC | Presacco, Juan Cruz - Salonia, Franco Ariel - Sánchez, Nicolás Esteban</footer>
    </body>
</html>
