package kjellwiggo.vehicleregisterdb2;

public class RegistryEntry {
    private int id;
    private String personalNumber;
    private String name;
    private String address;
    private String licenceNumber;
    private String carMake;
    private String carModel;

    public RegistryEntry(int id, String personalNumber, String name, String address, String licenceNumber, String carMake, String carModel) {
        this.id = id;
        this.personalNumber = personalNumber;
        this.name = name;
        this.address = address;
        this.licenceNumber = licenceNumber;
        this.carMake = carMake;
        this.carModel = carModel;
    }

    public RegistryEntry() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
}
