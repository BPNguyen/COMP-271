package TrainRoute;

/**
 * Brian Nguyen
 * COMP 271 - 003, F20
 * 04 - Train Stations
 * 
 * Interface for managing a train line, allowing for the manipulation of
 * elements in it.
 */
public interface Trains {
    
    /**
     * Method to add a station to the line. Station is added after the
     * last station and becomes the line's last station.
     * @param c Name of city where new station is located.
     */
    void addStation(String c);

    /**
     * Method to determine if a station exists at a specified location
     * @param location Is there a station here?
     * @return True if there is a station object at this location
     */
    boolean stationExists(String location);

    /** Quick method to display a train line */
    void displayRoute();

    /** Quick method to display a train line in reverse order */
    void displayReverse();

    /**
     * Method to insert a new station right after a given station. The method
     * conducts safety checks to determine that the given station exists and
     * that there is no station already at the existing location.
     * @param prior Station to add new station after
     * @param c Name of new station's location
     * @return true if insertion successful, false otherwise;
     */
    boolean insertAfterStation(String prior, String c);

    /**
     * Method to remove a station from a route.
     * @param location Name of location to have station removed from.
     * @return True if removal is successful.
     */
    boolean removeStation(String location);

    /**
     * Method to remove a stations between two indicated stations.
     * @param startingC Name of location to have stations removed after.
     * @param endingC Name of location to have stations removed before.
     * @return True if removal is successful.
     */
    boolean removeBetween(String startingC, String endingC);
}
