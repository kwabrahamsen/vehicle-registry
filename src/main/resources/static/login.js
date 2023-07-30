$(function () {
    $("#loginbtn").prop("disabled", true);
})

function login() {
    const user = {
        username : $("#username").val(),
        password : $("#password").val()
    }
    $.get("login", user, function (innlogget){
        if (innlogget) {
            window.location.replace("index.html");
        }
        else {
            $("#alert").html("<div class='alert alert-danger' role='alert'>Feil i brukernavn  eller passord.</div>");
        }
    })
    .fail(function () {
       $("#alert").html("<div class='alert alert-danger' role='alert'>Serverfeil - prøv igjen senere.</div>");
    });
}

function encrypt() {
    $("#encryptbtn").html("<span class='spinner-border spinner-border-sm' role='status'></span> Krypterer...");
    $("#encryptbtn").prop("disabled", true);
    const url = "encrypt";
    $.post(url, function () {
        $("#alert").html("<div class='alert alert-success' role='alert'>Passordene er krypterte</div>");
        $("#encryptbtn").html("Kryptert");
        $("#loginbtn").prop("disabled", false);
    })
    .fail(function () {
        $("#alert").html("<div class='alert alert-danger' role='alert'>Kryperingsfeil.</div>");
    });
}

function logout() {
    const url = "logout";
    $.get(url, function () {
        window.location.replace("login.html");
    });
}