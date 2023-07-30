$(function () {
    loadCarMakeList();
});

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
    })
    .fail(function (jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#failCarMake").html(json.message);
    });
}

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

function registerEntry() {
    const registryEntry = {
        personalNumber : $("#personalNumber").val(),
        name : $("#name").val(),
        address : $("#address").val(),
        licenceNumber : $("#licenceNumber").val(),
        carMake : $("[name='carMake'] option:selected").val(),
        carModel : $("[name='carModel'] option:selected").val()
    };

    $.post("register", registryEntry, function () {
        window.location.replace('index.html');
    })
        .fail(function (status) {
            if (status.status == 422) {
                $("#fail").html(status.status+ " Feil i DB - prøv igjen senere.");
            }
            else {
                if (status.status == 403)  {
                    $("#fail").html(status.status+ " Må være innlogget.");
                }
                else {
                    $("#fail").html(status.status+ " Valideringsfeil - prøv igjen.");
                }
            }
        });
}