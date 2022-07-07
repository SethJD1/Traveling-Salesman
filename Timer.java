import java.util.concurrent.TimeUnit;
import java.text.DecimalFormat;

/**
 * Creates a Timer with start, stop and current lap time functionality.
 *
 * @author Seth Dovgan
 * @version 18FEB18
 */
public class Timer {

    private long startTime;
    private long stopTime;
    private long elapsedTime;
    private boolean hasTimerStopped;

    /**
     * Constructor. Initializes all the times to zero.
     */
    public Timer(){

        this.startTime = 0;
        this.stopTime = 0;
        this.elapsedTime = 0;
        this.hasTimerStopped = false;
    }

    /**
     * Starts the current timer with a start time derived from the system clock.
     */
    public void startTimer(){

        startTime = System.nanoTime();
        hasTimerStopped = false;
    }

    /**
     * Stops the current timer with a stop time derived from the system clock.
     */
    public void stopTimer(){

        stopTime = System.nanoTime();
        elapsedTime = stopTime - startTime;
        hasTimerStopped = true;
    }

    /**
     * Sets the current elapsed time lap. This method must be called before
     * getting getting the current elapsed time.
     */
    private void setCurrentElapsedTime(){

        if(!hasTimerStopped){
            elapsedTime = System.nanoTime() - startTime;
        }
    }

    /**
     * Returns the current elapsed time in the format specified in the parameter.
     * @param unit to get the current elapsed time in.
     * @return value of the elapsed time in the specified unit.
     */
    public long getElapsedTime(UnitOfTime unit){

        setCurrentElapsedTime();

        switch(unit){

            case MILLISECONDS:
                return TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
            case SECONDS:
                return TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
            case MINUTES:
                return TimeUnit.MINUTES.convert(elapsedTime, TimeUnit.NANOSECONDS);
            case HOURS:
                return TimeUnit.HOURS.convert(elapsedTime, TimeUnit.NANOSECONDS);
            default:
                return elapsedTime;
        }
    }

    /**
     * Returns the current elapsed time in a proper format. If the unit of
     * time is great than the its next higher time unit, it convert to the next
     * higher time unit and returns as a string.
     * @return string representation of the current elapsed time.
     */
    public String getElapsedTime(){

        setCurrentElapsedTime();

        DecimalFormat f = new DecimalFormat("#.###");

        if(TimeUnit.MILLISECONDS.convert(elapsedTime , TimeUnit.NANOSECONDS) < 1){

            return f.format(elapsedTime) + " nanoseconds";

        } else if(TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS) < 1){

            return f.format(TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS))
                    + " milliseconds";

        } else if(TimeUnit.MINUTES.convert(elapsedTime, TimeUnit.NANOSECONDS) < 1){

            return f.format(TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS))
                    + " seconds";

        } else if(TimeUnit.HOURS.convert(elapsedTime, TimeUnit.NANOSECONDS) < 1){

            return f.format(TimeUnit.MINUTES.convert(elapsedTime, TimeUnit.NANOSECONDS))
                    + " minutes";
        } else {

            return f.format(TimeUnit.HOURS.convert(elapsedTime, TimeUnit.NANOSECONDS))
                    + " hours";
        }
    }
}

