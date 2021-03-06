<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <title>Main</title>
  <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
  <meta http-equiv="Cache-Control" content="no-cache no-store"/>
  <meta http-equiv="Pragma" content="no-cache"/>
  <meta http-equiv="expires" content="0"/>
  <script type="text/javascript" src="../js/jquery.js"></script>
  <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
  <script type="text/javascript" src="../js/main.js"></script>
  <link rel="stylesheet" type="text/css" href="../css/jquery-ui.min.css"/>
  <link rel="stylesheet" type="text/css" href="../css/jquery-ui.structure.min.css"/>
  <link rel="stylesheet" type="text/css" href="../css/jquery-ui.theme.min.css"/>
  <link rel="stylesheet" type="text/css" href="../css/main.css"/>
</head>

<%
  String username = null;
  Cookie[] cookies = request.getCookies();

  if (cookies != null) {
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("jadrn048")) {
        username = cookie.getValue();
      }
    }
  }

  if (username == null) {
    response.sendRedirect("/login.html");
  }
%>

<body>

<form method="post" action="/Logout">
  <button class="button">Logout Now</button>
</form>


<div class="tabs inventory-container">
  <ul>
    <li><a href="#tabs-1">Inventory Received</a></li>
    <li><a href="#tabs-2">Inventory Sent</a></li>
  </ul>
  <div id="tabs-1">
    <div class="receiveStatus status"></div>
    <form class="inventoryReceived" method="post" action="">
      <p>
        <label>Date</label>
        <input type="text"  name="date" class="date text ui-widget-content ui-corner-all"/>
      </p>

      <p>
        <label>SKU</label>
        <input type="text" name="sku" class="text ui-widget-content ui-corner-all"/>
      </p>

      <p>
        <label>Vendor</label>
        <input type="text" name="vendor" class="text ui-widget-content ui-corner-all" readonly/>
      </p>

      <p>
        <label>Category</label>
        <input type="text" name="category" class="text ui-widget-content ui-corner-all" readonly/>
      </p>

      <p>
        <label>Product Name</label>
        <input type="text" name="productName" class="text ui-widget-content ui-corner-all" readonly/>
      </p>

      <p>
        <label>Quantity</label>
        <input type="text" name="quantity" class="text ui-widget-content ui-corner-all"/>
      </p>
    </form>
    <p>
      <button class="button inventoryReceivedSubmit inventorySubmit" data-action="receiveInventory">Submit</button>
      <button class="button inventoryReset">Reset</button>
    </p>
  </div>

  <div id="tabs-2">
    <div class="sendStatus status"></div>
    <form class="inventorySent" method="post" action="">
      <p>
        <label>Date</label>
        <input type="text" name="date" class="date text ui-widget-content ui-corner-all"/>
      </p>

      <p>
        <label>SKU</label>
        <input type="text" name="sku" class="text ui-widget-content ui-corner-all"/>
      </p>

      <p>
        <label>Vendor</label>
        <input type="text" name="vendor" class="text ui-widget-content ui-corner-all" readonly/>
      </p>

      <p>
        <label>Category</label>
        <input type="text" name="category" class="text ui-widget-content ui-corner-all" readonly/>
      </p>

      <p>
        <label>Product Name</label>
        <input type="text" name="productName" class="text ui-widget-content ui-corner-all" readonly/>
      </p>

      <p>
        <label>Quantity</label>
        <input type="text" name="quantity" class="text ui-widget-content ui-corner-all"/>
      </p>
    </form>
    <p>
      <button class="button inventorySentSubmit inventorySubmit" data-action="sendInventory">Submit</button>
      <button class="button inventoryReset">Reset</button>
    </p>
  </div>
</div>
</body>
</html>
