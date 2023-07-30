package kjellwiggo.vehicleregisterdb2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleRegistryRepository {
    @Autowired
    public JdbcTemplate db;

    Logger logger = LoggerFactory.getLogger(VehicleRegistryRepository.class);

    //Encrypting password
    private String encryptPassword(String password)  {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(14);
        String hashedPassword = bCrypt.encode(password);
        return hashedPassword;
    }

    //Checking password
    private boolean checkPassword(String password, String hashPassword) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        if (bCrypt.matches(hashPassword, password)) {
            return true;
        }
        return false;
    }

    //Fetching entire registry
    public List<RegistryEntry> getRegistry() {
        String sql = "SELECT* FROM RegistryEntries";
        try {
            List<RegistryEntry> allEntries = db.query(sql, BeanPropertyRowMapper.newInstance(RegistryEntry.class));
            return allEntries;
        }
        catch (Exception e) {
            logger.error("Feil i getRegistry : "+e);
            return null;
        }
    }

    //Loading list  of vehicles  to populate drop down  menus
    public List<Vehicle> loadCarMakeList() {
        String sql = "SELECT* FROM Vehicles";
        try {
            List<Vehicle> allVehicles = db.query(sql, BeanPropertyRowMapper.newInstance(Vehicle.class));
            return allVehicles;
        }
        catch (Exception e) {
            logger.error("Feil i loadCarMakeList : "+e);
            return null;
        }
    }

    //Registering new entry
    public boolean registerEntry(RegistryEntry entry) {
        String sql = "INSERT INTO RegistryEntries (personalNumber, name, address, licenceNumber, carMake, carModel) VALUES (?,?,?,?,?,?)";
        try {
            db.update(sql, entry.getPersonalNumber(), entry.getName(), entry.getAddress(), entry.getLicenceNumber(), entry.getCarMake(), entry.getCarModel());
            return true;
        }
        catch (Exception  e) {
            logger.error("Feil i registerEntry : "+e);
            return false;
        }
    }

    //Fetching specific registry entry
    public RegistryEntry getRegistryEntry(int id) {
        Object[] param = new Object[1];
        param[0] = id;
        String sql = "SELECT* FROM RegistryEntries WHERE id=?";
        try {
            RegistryEntry entry = db.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(RegistryEntry.class));
            return entry;
        }
        catch (Exception e) {
            logger.error("Feil i getRegistryEntry : "+e);
            return null;
        }
    }

    //Updating existing entry
    public boolean editEntry(RegistryEntry entry) {
        String sql = "UPDATE RegistryEntries SET personalNumber=?, name=?, address=?, licenceNumber=?, carMake=?, carModel=? WHERE id=?";
        try {
            db.update(sql, entry.getPersonalNumber(), entry.getName(), entry.getAddress(), entry.getLicenceNumber(), entry.getCarMake(), entry.getCarModel(), entry.getId());
            return true;
        }
        catch (Exception e) {
            logger.error("Feil i editEntry : "+e);
            return false;
        }
    }

    //Deleting existing entry
    public boolean deleteEntry(RegistryEntry deletedEntry) {
        String sql = "DELETE FROM RegistryEntries WHERE id=?";
        try {
            db.update(sql, deletedEntry.getId());
            return true;
        }
        catch (Exception e) {
            logger.error("Feil i deleteEntry : "+e);
            return false;
        }
    }

    //Deleting entire registry
    public boolean deleteRegistry() {
        String sql = "DELETE FROM RegistryEntries";
        try {
            db.update(sql);
            return true;
        }
        catch (Exception e) {
            logger.error("Feil i deleteRegistry : "+e);
            return false;
        }
    }

    public boolean checkUsernamePassword(User user) {
        String sql = "SELECT* FROM Users WHERE username=?";
        try {
            User dbUser = db.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class), new Object[]{user.getUsername()});
            return checkPassword(dbUser.getPassword(), user.getPassword());
        }
        catch (Exception e) {
            logger.error("Feil i checkUsernamePassword : "+e);
            return false;
        }
    }

    // Encrypting all users
    public void encrypt(User user) {
        String sql = "SELECT* FROM Users";
        try {
            List<User> allUsers = db.query(sql, BeanPropertyRowMapper.newInstance(User.class));
            for (User i : allUsers) {
                String sqlUpdate = "UPDATE Users SET password=? WHERE id=?";
                String hashPassword = encryptPassword(i.getPassword());
                db.update(sqlUpdate, hashPassword, i.getId());
            }
        }
        catch (Exception e) {
            logger.error("Feil i kryptering :  "+e);
        }
    }
}
