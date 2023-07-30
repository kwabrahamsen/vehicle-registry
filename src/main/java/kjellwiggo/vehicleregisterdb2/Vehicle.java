package kjellwiggo.vehicleregisterdb2;

public class Vehicle {
    private int id;
    private String carMake;
    private String carModel;

    public Vehicle(int id, String carMake, String carModel) {
        this.id = id;
        this.carMake = carMake;
        this.carModel = carModel;
    }

    public Vehicle() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
