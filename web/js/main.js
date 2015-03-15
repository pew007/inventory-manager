$(document).ready(function() {
  
//  var servletHome = "/servlet/";
  var servletHome = "/";
  
  $("[name='username']").focus();

  $(".button").button();

  $('.loginSubmit').on('click', function(e) {
    console.log('click');
    if ($("[name='username']").val().trim() == "") {
      $('#status').text('Please enter username.');
      $("[name='username']").focus();
      e.preventDefault();
    } else if ($("[name='password']").val().trim() == "") {
      $('#status').text('Please enter password.');
      $("[name='password']").focus();
      e.preventDefault();
    }
  });

  $(".tabs").tabs();
  $(".date").datepicker().val(getTodaysDate());
  
  $("input[name=sku]").change(function(){
    var sku = $("input[name=sku]").val();
    var url = servletHome + "FetchProduct";
    var param = {sku: sku};

    $.post(url, param, function(response){
      if (response.status == "OK") {
        $("#status").hide();
        $("input[name='vendor']").val(response.vendor);
        $("input[name='category']").val(response.category);
        $("input[name='productName']").val(response.productName);
      } else {
        displayMessage(response.message);
        $("input[name='vendor']").val();
        $("input[name='category']").val();
        $("input[name='productName']").val();
      }
    }, 'json').fail(function(){
      displayMessage("Failed to fetch product by sku " + sku);
    });
  });
  
  $('.inventoryReceivedSubmit').on('click', function(){
    var form = $("#inventoryReceived");
    var url = servletHome + "ReceiveInventory";
    var params = {
        'date': form.find("input[name=date]").val(),
        'sku': form.find("input[name=sku]").val(),
        'quantity': form.find("input[name=quantity]").val()
    };
    
    $.post(url, params, function(response){
      if (response.status == "OK") {
        displayMessage(response.message);
      }
    }, 'json').fail(function(){
      displayMessage("Failed to receive inventory");
    })
  });
  
  function getTodaysDate() {
    var today = new Date();
    var month = today.getMonth() + 1;
    var day = today.getDate();
    var year = today.getFullYear();

    return month + "/" + day + "/" + year;
  }
  
  function displayMessage(message) {
    $("#status").html(message).show();
  }

});
