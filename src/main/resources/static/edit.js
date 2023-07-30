$(function () {
   const id = window.location.search.substring(1);
   const url = "getRegistryEntry?"+id;

   $.get(url, function (registryEntry) {
      loadCarMakeList();
      $("#id").val(registryEntry.id);
      $("#personalNumber").val(registryEntry.personalNumber);
      $("#name").val(registryEntry.name);
      $("#address").val(registryEntry.address);
      $("#licenceNumber").val(registryEntry.licenceNumber);
      $("[name='carMake']").val(registryEntry.carMake);
      $("[name='carModel']").val(registryEntry.carModel);
   })
   .fail(function (status) {
      $("#fail").html(status.status+ " Må være innlogget.");
   });
});

//Loading car make list
function loadCarMakeList() {
   $.get("loadVehicleList", function (vehicles) {
      let distinctCarMakes =  [];
      for (let i of vehicles) {
         if (!distinctCarMakes.includes(i.carMake)) {
            distinctCarMakes.push(i.carMake);
         }
      }

      let out = "<select class='form-select' onchange='loadCarModelList()' name='carMake'>";
      for (let i of distinctCarMakes) {
         out += "<option value='" +i+ "'>" +i+ "</option>";
      }
      out += "</select>";
      $("#carMake").html(out);
      loadCarModelList();
   });
}

//Loading car model list
function loadCarModelList() {
   $.get("loadVehicleList", function (vehicles) {
      let selectedCarMake = $("[name='carMake'] option:selected").val();
      let out = "<select class='form-select' name='carModel'>";
      for (let i of vehicles) {
         if (i.carMake === selectedCarMake) {
            out += "<option value='" +i.carModel+ "'>" +i.carModel+ "</option>";
         }
      }
      out += "</select>";
      $("#carModel").html(out);
   });
}

//Updating existing entry
function editVehicle() {
   const registryEntry = {
      id : $("#id").val(),
      personalNumber : $("#personalNumber").val(),
      name : $("#name").val(),
      address : $("#address").val(),
      licenceNumber : $("#licenceNumber").val(),
      carMake : $("[name='carMake'] option:selected").val(),
      carModel : $("[name='carModel'] option:selected").val()
   };
   console.log(registryEntry);

   $.post("editEntry", registryEntry, function () {
      window.location.replace('index.html');
   })
   .fail(function(jqXHR) {
      const json = $.parseJSON(jqXHR.responseText);
      $("#fail").html(json.message);
   });
}