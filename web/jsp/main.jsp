<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Main</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui.structure.min.css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui.theme.min.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
</head>

<body>
  <div class="tabs">
    <ul>
      <li><a href="#tabs-1">Inventory Received</a></li>
      <li><a href="#tabs-2">Inventory Sent</a></li>
    </ul>
    <div id="tabs-1">
      <div id="status"></div>
      <form id="inventoryReceived" method="post" action="">
        <p>
          <label for="date">Date</label> 
          <input type="text" class="date" id="date" name="date" />
        </p>

        <p>
          <label for="sku">SKU</label> 
          <input type="text" name="sku" id="sku" />
        </p>
        
        <p>
          <label for="vendor">Vendor</label> 
          <input type="text" name="vendor" id="vendor" readonly/>
        </p>
        
        <p>
          <label for="category">Category</label> 
          <input type="text" name="category" id="category" readonly/>
        </p>
        
        <p>
          <label for="productName">Product Name</label> 
          <input type="text" name="productName" id="productName" readonly/>
        </p>
        
        <p>
          <label for="quantity">Quantity</label> 
          <input type="text" name="quantity" id="quantity" />
        </p>
      </form>
      <p>
        <button class="button inventoryReceivedSubmit">Submit</button>
        <input class="button" type="reset" value="Reset" />
      </p>
    </div>
    <div id="tabs-2">

    </div>
  </div>
</body>
</html>

