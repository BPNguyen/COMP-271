package AirplanesHangars;

/*
* Brian Nguyen
* COMP 271 - 003, F20
* Asynchronous Lab - 11/04/2020
*/

public class Airplane {

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

    // full constructor only
    public Airplane(String tailNumber, String manufacturer,
                    Wings wings, Landing gear, boolean tailWheel,
                    int engines, PowerPlant powerPlant, int powerPerEngine,
                    Fuel fuel, int passengers)
    {
        this.tailNumber = tailNumber;
        this.manufacturer = manufacturer;
        this.wings = wings;
        this.gear = gear;
        this.tailWheel = tailWheel;
        this.engines = engines;
        this.powerPlant = powerPlant;
        this.powerPerEngine = powerPerEngine;
        this.fuel = fuel;
        this.passengers = passengers;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Wings getWings() {
        return wings;
    }

    public Landing getGear() {
        return gear;
    }

    public boolean getTailWheel() {
        return tailWheel;
    }

    public int getEngines() {
        return engines;
    }

    public PowerPlant getPowerPlant() {
        return powerPlant;
    }

    public int getPowerPerEngine() {
        return powerPerEngine;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public int getPassengers() {
        return passengers;
    }
}