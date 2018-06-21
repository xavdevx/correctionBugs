<%-- 
    Document   : bienvenue
    Created on : 19 juin 2018, 14:51:09
    Author     : esic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
  
        <title>Bienvenue</title>
    </head>
    <body>      
        <div class="container" style="margin-top:30px">
            <div class="col-md-4">
                
                <h1>Bienvenue admin!</h1>
                <h2>Choisissez votre action:</h2>
                <div class="login-panel panel panel-default">
                    <div class="panel-body">
                        <form action="Controleur" method="post">
                            <fieldset>
                                <input type="submit" name="action" value="Voir Liste" class="btn btn-primary"/>
                                <input type="submit" name="action" value="Ajouter" class="btn btn-primary"/>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
