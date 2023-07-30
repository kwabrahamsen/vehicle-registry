package kjellwiggo.vehicleregisterdb2;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VehicleRegistryController {

    Logger logger = LoggerFactory.getLogger(VehicleRegistryController.class);

    public boolean validateEntry(RegistryEntry entry) {
        String regExPersonalNumber = "[0-9]{11}";
        String regExName = "[a-zA-ZæøåÆØÅ. \\-]{2,50}";
        String regExAddress = "[0-9a-zA-ZæøåÆØÅ. \\-]{2,50}";
        String regExLicenceNumber = "[0-9a-zA-Z]{7}";
        boolean personalNumberOK = entry.getPersonalNumber().matches(regExPersonalNumber);
        boolean nameOK = entry.getName().matches(regExName);
        boolean addressOK = entry.getAddress().matches(regExAddress);
        boolean licenceNumberOK = entry.getLicenceNumber().matches(regExLicenceNumber);

        if (personalNumberOK && nameOK && addressOK && licenceNumberOK) {
            return true;
        }
        else {
            logger.error("Valideringsfeil");
            return false;
        }
    }

    @Autowired
    VehicleRegistryRepository rep;

    //Fetching entire registry
    @GetMapping("getRegistry")
    public List<RegistryEntry> getRegister(HttpServletResponse response) throws IOException {
        List<RegistryEntry> allEntries = new ArrayList<>();
        if (session.getAttribute("Innlogget") != null) {
            allEntries = rep.getRegistry();
            if (allEntries == null) {
                response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
            }
            return allEntries;
        }
        response.sendError(HttpStatus.NOT_FOUND.value());
        return null;
    }

    //Loading car make list
    @GetMapping("loadVehicleList")
    public List<Vehicle> loadCarMakeList() {
        return rep.loadCarMakeList();
    }

    //Registering new entry
    @PostMapping("register")
    public void registerEntry(RegistryEntry entry, HttpServletResponse response) throws IOException {
        if (session.getAttribute("Innlogget") != null) {
            if (!validateEntry(entry)) {
                response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
            }
            else {
                if (!rep.registerEntry(entry)) {
                    response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
                }
            }
        }
        else {
            response.sendError(HttpStatus.FORBIDDEN.value());
        }
    }

    //Fetching specific entry
    @GetMapping("getRegistryEntry")
    public RegistryEntry getRegistryEntry(int id,  HttpServletResponse response) throws IOException {
        if (session.getAttribute("Innlogget") != null)  {
            return rep.getRegistryEntry(id);
        }
        else {
            response.sendError(HttpStatus.FORBIDDEN.value());
            return null;
        }
    }

    //Updating existing entry
    @PostMapping("editEntry")
    public void editEntry(RegistryEntry entry, HttpServletResponse response) throws IOException {
        if (session.getAttribute("Innlogget") != null) {
            if (!validateEntry(entry)) {
                response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
            }
            else {
                if (!rep.editEntry(entry)) {
                    response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
                }
            }
        }
        else {
            response.sendError(HttpStatus.FORBIDDEN.value());
        }

    }

    //Deleting existing entry
    @PostMapping("deleteEntry")
    public void deleteEntry(RegistryEntry deletedEntry, HttpServletResponse response) throws IOException {
        if (!rep.deleteEntry(deletedEntry)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
    }

    //Deleting entire registry
    @PostMapping("delete")
    public void deleteRegistry(HttpServletResponse response) throws IOException {
        if (!rep.deleteRegistry()) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
    }

    @Autowired
    private HttpSession session;

    @GetMapping("login")
    public boolean login(User user) {
        if (rep.checkUsernamePassword(user)) {
            session.setAttribute("Innlogget", user);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isEncrypted = false;

    @PostMapping("encrypt")
    public boolean encrypt(User user) {
        if (!isEncrypted) {
            rep.encrypt(user);
            isEncrypted = true;
            return true;
        }
        else {
            return true;
        }

    }

    @GetMapping("isEncrypted")
    public boolean isEncrypted() {
        if (isEncrypted()) {
            return true;
        }
        else {
            return false;
        }
    }

    @GetMapping("logout")
    public void logout()  {
        session.removeAttribute("Innlogget");
    }
}
