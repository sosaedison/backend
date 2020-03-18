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

    public TimeRange(int start, int stop ){

        this.startTime = start; 
        this.endTime = stop; 
    }
    
    public static TimeRange defaultRange() {
        return new TimeRange( 0, 2400 ); 
    }

    private boolean isInRange( int time ){
        boolean result = false; 
        if( time > this.startTime && time < this.endTime){
            result = true; 
        }
        return result; 
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Start: " + Integer.toString(this.startTime) + "\n");
        str.append("End: " +   Integer.toString(this.endTime) + "\n"); 
        return str.toString(); 
    }
}
