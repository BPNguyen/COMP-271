package AirplanesHangars;

/*
* Brian Nguyen
* COMP 271 - 003, F20
* Asynchronous Lab - 11/04/2020
*/

public class AirplaneBuilder implements Builder {

    private String tailNumber;
    private String manufacturer;
    private Wings wings;
    private Landing gear;
    private boolean tailWheel;
    private int engines;
    private PowerPlant powerPlant;
    private int powerPerEngine;
    private Fuel fuel;
    private int passengers;

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setWings(Wings wings) {
        this.wings = wings;
    }

    public void setGear(Landing gear) {
        this.gear = gear;
    }

    public void setTailWheel(boolean tailWheel) {
        this.tailWheel = tailWheel;
    }

    public void setEngines(int engines) {
        this.engines = engines;
    }

    public void setPowerPlant(PowerPlant powerPlant) {
        this.powerPlant = powerPlant;
    }

    public void setPowerPerEngine(int powerPerEngine) {
        this.powerPerEngine = powerPerEngine;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public Airplane fetch() {
        return new Airplane(tailNumber, manufacturer, wings, gear, 
                            tailWheel, engines, powerPlant, powerPerEngine, 
                            fuel, passengers);
    }
}
