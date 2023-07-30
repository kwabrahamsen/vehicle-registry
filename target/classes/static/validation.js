//Validating personal number
function validatePersonalNumber(personalNumber) {
    const regexp = /^[0-9]{11}$/;
    const ok = regexp.test(personalNumber);
    if (!ok) {
        $("#failPersonalNumber").html("Personnummer må være 11 siffer.");
        return false;
    }
    else {
        $("#failPersonalNumber").html("");
        return true;
    }
}

//Validating name
function validateName(name) {
    const regexp = /^[a-zA-ZæøåÆØÅ. \-]{2,50}$/;
    const ok = regexp.test(name);
    if (!ok) {
        $("#failName").html("Navn må være 2 - 20 bokstaver.");
        return false;
    }
    else {
        $("#failName").html("");
        return true;
    }
}

//Validating address
function validateAddress(address) {
    const regexp = /^[0-9a-zA-ZæøåÆØÅ. \-]{2,50}$/;
    const ok = regexp.test(address);
    if (!ok) {
        $("#failAddress").html("Addresse må inneholde 2 - 50 bokstaver og/eller tall.");
        return false;
    }
    else {
        $("#failAddress").html("");
        return true;
    }
}

//Validating licence number
function validateLicenceNumber(licenceNumber) {
    const regexp = /^[A-Z]{2}[0-9]{5}$/;
    const ok = regexp.test(licenceNumber);
    if (!ok) {
        $("#failLicenceNumber").html("Registreringsnummer må være 2 store bokstaver og 5 tall.");
        return false;
    }
    else {
        $("#failLicenceNumber").html("");
        return true;
    }
}

function validateAndRegister() {
    const personalNumberOK = validatePersonalNumber($("#personalNumber").val());
    const nameOK = validateName($("#name").val());
    const addressOK = validateAddress($("#address").val());
    const licenceNumberOK = validateLicenceNumber($("#licenceNumber").val());

    if (personalNumberOK && nameOK && addressOK && licenceNumberOK) {
        registerEntry();
    }
}