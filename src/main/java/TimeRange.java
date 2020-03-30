package AutoGarcon; 
import java.util.ArrayList; 
import java.sql.ResultSet; 
import java.sql.SQLException; 


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

    /**
     * TimeRange: 
     * Construct a time range object with a start and stop time. 
     * 24 hour time ints are used, i.e 0 = start of day, 2400 = end of the day.  
     */
    public TimeRange(int start, int stop ){
        this.startTime = start; 
        this.endTime = stop; 
    }

    public TimeRange( ResultSet rs ){
        try{ 
            this.startTime = rs.getInt("startTime"); 
            this.endTime = rs.getInt("endTime"); 
        }
        catch( SQLException e){
            System.out.printf("Failed to get the required fields while creating a TimeRange Object.\n" + 
                   "Exception: %s.\n", e.toString() );
        }

    }


    public static TimeRange[] timeRanges( int menuID ){
        ResultSet times = DBUtil.getMenuTimes( menuID ); 
        ArrayList<TimeRange> list = new ArrayList<TimeRange>(); 
        boolean hasResult; 

        try{ 
            hasResult = times.next();

            while(hasResult){
                TimeRange tr = new TimeRange( times );  
                list.add( tr ); 
                hasResult = times.next(); 
            }
        }
        catch( SQLException e){
            System.out.printf("Failed to get next row in result set.\n" + 
                    "Exception: %s\n", e.toString() );
        }
        return list.toArray( new TimeRange[ list.size() ] ); 
    }


    /**
     * defaultRange: 
     * Construct a time range object with a default start/stop time 
     * that represents 24 hours in a day. 
     */
    public static TimeRange defaultRange() {
        return new TimeRange( 0, 2400 ); 
    }


    /**
     * isInRange:
     * Check if a 24 hour time int is in the range of this time range. 
     */
    private boolean isInRange( int time ){
        boolean result = false; 
        if( time > this.startTime && time < this.endTime){
            result = true; 
        }
        return result; 
    }

    public int getStartTime() {
        return this.startTime; 
    }
    
    public int getEndTime(){
        return this.endTime;  
    }

    /**
     * getStartTimesString: Get a bar delimted string of the start times. 
     * @param timeRanges - an array of time range objects to get a start time from.
     * @return a bar delimted string of start times. 
     */
    @Deprecated
    public static String getStartTimeString( TimeRange[] timeRanges ){
        StringBuilder str = new StringBuilder(); 
        for( TimeRange r :  timeRanges ){
            str.append( r.startTimeString() ); 
        }
        return str.toString(); 
    }

    /**
     * getStartTimesString: Get a bar delimted string of the start times. 
     * @param timeRanges - an array of time range objects to get an end time from.
     * @return a bar delimted string of end times. 
     */
    @Deprecated
    public static String getEndTimeString( TimeRange[] timeRanges ){
        StringBuilder str = new StringBuilder(); 
        for( TimeRange r :  timeRanges ){
            str.append( r.endTimeString() ); 
        }
        return str.toString(); 
    }

    /**
     * Get the start time String with a bar delimiter.  
     * For use in database queries. 
     */
    public String startTimeString() {
        return this.startTime + "|"; 
    }
    

    /**
     * Get the end time String with a bar delimiter.  
     * For use in database queries. 
     */
    public String endTimeString() { 
        return this.endTime + "|"; 
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\tStart: " + Integer.toString(this.startTime) + "\n");
        str.append("\tEnd: " +   Integer.toString(this.endTime) + "\n"); 
        return str.toString(); 
    }
}
