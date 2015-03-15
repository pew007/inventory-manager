<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui.structure.min.css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui.theme.min.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<title>Login</title>
</head>
<body>
  <div>
    <h2>Login</h2>
    <form method="post" action="/proj2/Login">
      <!-- <form method="post" action="/jadrn048/servlet/Login"> -->
      <table class="login-container">
        <tr>
          <td><label>Username:</label></td>
          <td><input type="text" name="username"
            class="text ui-widget-content ui-corner-all" /></td>
        </tr>
        <tr>
          <td><label>Password:</label></td>
          <td><input type="password" name="password"
            class="text ui-widget-content ui-corner-all" /></td>
        </tr>
        <tr>
          <td><input class="button" type="reset" /></td>
          <td><input class="button" type="submit" value="Log In" /></td>
        </tr>
      </table>
    </form>
    <div id="status">
      <%=request.getAttribute("errorMessage")%>
    </div>
  </div>
</body>
</html>
