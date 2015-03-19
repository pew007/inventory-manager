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

  $("input[name=sku]").change(function(){
    var container = $(this).closest("form");
    var sku = container.find("input[name=sku]").val();
    var url = servletHome + "FetchProduct";
    var param = {sku: sku};

    $.post(url, param, function(response){
      if (response.status == "OK") {
        $("#status").hide();
        container.find("[name=vendor]").val(response.vendor);
        container.find("[name=category]").val(response.category);
        container.find("[name=productName]").val(response.productName);
      } else {
        displayMessage(response.message);
        container.find("[name=vendor]").val();
        container.find("[name=category]").val();
        container.find("[name=productName]").val();
      }
    }, 'json').fail(function(){
      displayMessage("Failed to fetch product by sku " + sku);
    });
  });

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

    $.post(url, params, function(response){
      if (response.status == "OK") {
        displayMessage(response.message, action);
      } else {
        displayMessage(response.message, action);
      }
    }, 'json').fail(function(){
      displayMessage("Failed to update inventory", action);
    })
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

});
