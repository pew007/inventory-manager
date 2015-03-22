$(document).ready(function() {
  
//  var servletHome = "/servlet/";
  var servletHome = "/";
  var username = $("[name='username']");
  var password = $("[name='password']");

  username.focus();
  $(".button").button();
  $(".tabs").tabs();
  $(".date").datepicker().val(getTodaysDate());

  $('.loginSubmit').on('click', function(e) {
    console.log('click');
    if (username.val().trim() == "") {
      $('#status').text('Please enter username.');
      username.focus();
      e.preventDefault();
    } else if (password.val().trim() == "") {
      $('#status').text('Please enter password.');
      password.focus();
      e.preventDefault();
    }
  });

  // Fetch product
  $("input[name=sku]").change(function(){
    var container = $(this).closest("form");
    var sku = container.find("input[name=sku]").val();
    var url = servletHome + "FetchProduct";
    var param = {sku: sku};

    if (isValidSku(sku)) {
      $.post(url, param, function(response){
        if (response.status == "OK") {
          $("#status").hide();
          container.find("[name=vendor]").val(response.vendor);
          container.find("[name=category]").val(response.category);
          container.find("[name=productName]").val(response.productName);
        } else if (response.status == "Error") {
          displayMessage(response.message);
          container.find("[name=vendor]").val();
          container.find("[name=category]").val();
          container.find("[name=productName]").val();
          resetInventoryForm();
        } else if (response.status == "Invalid") {
          window.location = "/login.html";
        }
      }, 'json').fail(function(){
        displayMessage("Failed to fetch product by sku " + sku);
      });
    } else {
      $("input[name=sku]").val("Invalid SKU");
    }
  });

  // Receive or send inventory
  $('.inventorySubmit').on('click', function(){
    var action = $(this).data('action');
    var url, form;

    if (action == "receiveInventory") {
      url = servletHome + "ReceiveInventory";
      form = $(".inventoryReceived");
    } else if (action == "sendInventory") {
      url = servletHome + "SendInventory";
      form = $(".inventorySent");
    }

    var params = {
        'date': form.find("input[name=date]").val(),
        'sku': form.find("input[name=sku]").val(),
        'quantity': form.find("input[name=quantity]").val()
    };

    if (isValidQuantity(params['quantity'])) {
      $.post(url, params, function(response){
        if (response.status == "OK") {
          resetInventoryForm();
          displayMessage(response.message, action);
        } else if (response.status == "Error") {
          resetInventoryForm();
          displayMessage(response.message, action);
        } else if (response.status == "Invalid") {
          window.location = "/login.html";
        }
      }, 'json').fail(function(){
        displayMessage("Failed to update inventory", action);
      })
    } else {
      form.find("input[name=quantity]").val("Quantity must be a number greater than 0");
    }
  });

  $('.inventoryReset').on('click', function(){
    resetInventoryForm();
  });

  function getTodaysDate() {
    var today = new Date();
    var month = today.getMonth() + 1;
    var day = today.getDate();
    var year = today.getFullYear();

    return month + "/" + day + "/" + year;
  }

  function displayMessage(message, action) {
    var status;
    if (action == 'receiveInventory') {
      status = $(".receiveStatus");
    } else if (action == 'sendInventory') {
      status = $(".sendStatus")
    }
    status.html(message).show();
  }

  function isValidSku(sku) {
    return sku.match(/^[A-Z]{3}-{1}[0-9]{3}$/);
  }

  function isValidQuantity(number) {
    return !isNaN(number) && (number > 0);
  }

  function resetInventoryForm() {
    $("input[name=sku]").val("");
    $("input[name=vendor]").val("");
    $("input[name=category]").val("");
    $("input[name=productName]").val("");
    $("input[name=quantity]").val("");

    $(".date").val(getTodaysDate());

    $(".status").hide();
  }

});
