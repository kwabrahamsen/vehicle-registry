$(function () {
    getRegistry();
});

//Fetching entire registry
function getRegistry() {
    $.get("getRegistry", function (data) {
        formatData(data);
    })
    .fail(function (status) {
        if (status.status="404") {
            $("#fail").html("<div class='alert alert-info' role='alert'>Du må være innlogget for å vise registeret.</div>");
            $(".logged-in").attr("disabled", "true");
        }
    });
}

//Formatting data
function formatData(registry) {
    let out = "<table class='table table-striped table-hover'><thead><tr><th>Personnummer</th><th>Navn</th><th>Adresse</th><th>Kjennetegn</th><th>Merke</th><th>Type</th><th>Endre/slette</th></tr></thead><tbody>";

    for (let i of registry) {
        out += "<tr><td>" +i.personalNumber+ "</td><td>" +i.name+ "</td><td>" +i.address+ "</td><td>" +i.licenceNumber+ "</td><td>" +i.carMake+ "</td><td>" +i.carModel+ "</td><td><div class='btn-group' role='group'><a class='btn btn-primary btn-sm' href='edit.html?id="+i.id+"'><i class='bi bi-pencil-fill'></i> Endre</a><a class='btn btn-danger btn-sm' onclick='deleteEntry("+i.id+")'><i class='bi bi-trash-fill'></i> Slett</a></div></td></tr>";
    }
    out += "</tbody></table>";
    $("#register").html(out);
}

//Deleting specific entry
function deleteEntry(id) {
    const deletedEntry = {
        id : id
    }
    $.post("deleteEntry", deletedEntry, function () {
        getRegistry();
        $("#alert").html("<br><div class='alert alert-danger' role='alert'>Kjøretøy slettet!</div>");
    })
    .fail(function (jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#fail").html(json.message);
    });
}

//Deleting entire registry
function deleteRegistry() {
    $.post("delete", function () {
       getRegistry();
       $("#alert").html("<br><div class=\"alert alert-danger\" role=\"alert\">Alle kjøretøy slettet!</div>");
    })
    .fail(function (jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#fail").html(json.message);
    });
}