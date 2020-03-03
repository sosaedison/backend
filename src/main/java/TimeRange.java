package AutoGarcon; 


/*
 * @author Tyler Beverley 
 * Represents a range of time of day.
 *
 * Times are formatted as 24hour time integers, 
 * and are not associated with a particular day.  
 * i.e. midnight = 2400, noon = 1200.
 *
 * 
 */
public class TimeRange {
    private int startTime; 
    private int endTime; 

    private boolean isInRange( int time ){
        return false; 
    }
}
