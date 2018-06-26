<%-- 
    Document   : tableauEmployes
    Created on : 3 nov. 2016, 11:13:04
    Author     : Jacques Augustin
--%>

<%@page import="java.util.ArrayList"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <% //<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script> %>
        <% //<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>%>
    </head>
    <body>

        <div class="container" >
            <div class="row col-md-6 col-md-offset-0 custyle">
                <form method="post" action="Controleur" name="suppression">
                    <table class="table table-striped custab">
                        <thead>
                        <h1>Liste des employ�s</h1>
                        <tr class="text-center">
                            <th>S�l</th>
                            <th>NOM</th>
                            <th>PRENOM</th>
                            <th>TEL DOMICILE</th>
                            <th>TEL PORTABLE</th>
                            <th>TEL PRO</th>
                            <th>ADRESSE</th>
                            <th>CODE POSTAL</th>
                            <th>VILLE</th>
                            <th>EMAIL</th>
                        </tr>
                        </thead>
                        </tr>

                        <c:forEach items="${cleListeEmployes}" var="employe">
                            <tr>
                                <td><input type="radio" name="idEmploye" value=${employe.id}></td>
                                <td>${employe.nom}</td>
                                <td>${employe.prenom}</td>
                                <td>${employe.teldom}</td>
                                <td>${employe.telport}</td>
                                <td>${employe.telpro}</td>
                                <td>${employe.adresse}</td>
                                <td>${employe.codepostal}</td>
                                <td>${employe.ville}</td>
                                <td>${employe.email}</td>

                            </tr>
                        </c:forEach>

                    </table>
                       
                    
                    <c:if test="${!empty CLE_TEST_SUPPRIMER_SUCCES}">
                    <font color="green">${CLE_TEST_SUPPRIMER_SUCCES}</font> 
                    <br>
                     </c:if>
                    
                      
                    <c:if test="${!empty CLE_TEST_SUPPRIMER_ECHEC}">
                    <font color="red">${CLE_TEST_SUPPRIMER_ECHEC}</font> 
                    <br>
                     </c:if>
                   
                    
                    
                    <c:if test="${!empty cleListeEmployes}">
                     
                    <input type="submit" name="action" value="Supprimer" class="btn btn-primary"/>
                    <input type="submit" name="action" value="Details" class="btn btn-primary"/>
                    </c:if>
                    
                    <c:if test="${empty cleListeEmployes}">
                        <font color="blue">L'entreprise doit recruter !</font>
                        <br>
                        <br>
                        <input type="submit" name="action" value="Cr�er 5 Employ�s" class="btn btn-primary"/>
                                            </c:if>
                        
             
                </form>
            </div>
        </div>         
    </body>
</html>
