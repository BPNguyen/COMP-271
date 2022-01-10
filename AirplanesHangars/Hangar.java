package AirplanesHangars;

/*
* Brian Nguyen
* COMP 271 - 003, F20
* Asynchronous Lab - 11/04/2020
*/

/**
 * USE THIS JAVADOC SECTION TO TYPE YOUR ANSWERS FOR THE FIRST DELIVERABLE
 * 
 * I've essentially created multiple templates of various degrees of relativity in
 * regards to creating an airplane based on user-defined specifications/parts. 
 *      - The 'Builder' interface essentially creates a template for what MUST be 
 *        included in the broadest sense without actually defining how they are 
 *        created and specified. 
 *      - The 'AirplaneBuilder' class implements the 'Builder' interface and 
 *        essentially details how each specification is created. Although the 
 *        'AirplaneBuilder' class we've created is quite bland, each field could be 
 *        modified to manipulate user-defined values further (e.g. formatting a tail 
 *        number to standardize it). 
 *      - The 'Airplane' class is essentially the "construction" stage of the airplane, 
 *        using the information gathered from the 'AirplaneBuilder' class and allows 
 *        for accessing of each specification included in the airplane.
 * 
 * Several of the fields are of enum type, which essentially only allows for the value 
 * of a field to be set to a set of predefined constants, meaning the field must be 
 * equal to one of the values that have been predefined for it.
 * 
 * The 'Hangar' class is the driver class:
 *      1) An instance of the Hangar class 'ourHangar' is created.
 *      2) An instance of the AirplaneBuilder class 'builder' is created.
 *      3) 'ourHangar' sets some values in the 'builder' instance, but not all of them.
 *      4) An instance of the Airplane class 'mySkyHawk' is created using the values 
 *         from the 'builder' instance, some of which are set by the C172 method but 
 *         not all of them.
 * 
 * In addition, the airplane's manufacturer is null because the manufacturer was never 
 * set to any specified value, defaulting it to null (default value for an undefined 
 * String). The manufacturer field is also not of enum type, which doesn't require it 
 * to be defined to a set of predefined values.
 */
public class Hangar {
    public void C172(Builder builder) {
        builder.setTailNumber("N969RR");
        builder.setWings(Wings.HIGH);
        builder.setFuel(Fuel.AVGAS);
        builder.setEngines(1);
    }

    public void displayInfo(Airplane airplane) {
        System.out.println("* AIRPLANE INFO *\n"
        + "Tail number: " + airplane.getTailNumber() + "\n"
        + "Manufacturer: " + airplane.getManufacturer() + "\n"
        + "Wings: " + airplane.getWings() + "\n"
        + "Gear: " + airplane.getGear() + "\n"
        + "Tail wheel: " + airplane.getTailWheel() + "\n"
        + "Engines: " + airplane.getEngines() + "\n"
        + "Power plant: " + airplane.getPowerPlant() + "\n"
        + "Power per engine: " + airplane.getPowerPerEngine() + "\n"    
        + "Fuel: " + airplane.getFuel() + "\n"
        + "Passengers: " + airplane.getPassengers() + "\n");
    }
    public static void main(String[] args){
        Hangar ourHangar = new Hangar();
        AirplaneBuilder builder = new AirplaneBuilder();
        ourHangar.C172(builder);
        Airplane mySkyhawk = builder.fetch();
        ourHangar.displayInfo(mySkyhawk);
        
        String m = mySkyhawk.getManufacturer();
        System.out.println("Can you explain this value for the airplane\'s manufacturer? "+m);
    }
}