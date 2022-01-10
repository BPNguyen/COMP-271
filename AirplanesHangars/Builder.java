package AirplanesHangars;

/*
* Brian Nguyen
* COMP 271 - 003, F20
* Asynchronous Lab - 11/04/2020
*/

public interface Builder {
    
    void setTailNumber(String tailNumber);
    void setManufacturer(String manufacturer);
    void setWings(Wings wings);
    void setGear(Landing gear);
    void setTailWheel(boolean tailWheel);
    void setEngines(int engines);
    void setPowerPlant(PowerPlant powerPlant);
    void setPowerPerEngine(int powerPerEngine);
    void setFuel(Fuel fuel);
    void setPassengers(int passengers);
}
