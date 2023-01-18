package hw1;

/**
 * This class models an exit machine in a parking garage that
 * uses the timestamps encoded on a ticket to determine whether or
 * not it has been 15 minutes or less since a payment for parking
 * was made.  If it has been less than 15 minutes, the machine
 * counts it as a successful exit.  Time is assumed to be represented
 * in minutes as a single integer.
 * 
 * @author Halle N
 */
public class ExitMachine
{
 
	/**
	 * Clock used to tell time.
	 */
	private SimpleClock clock;
	
	/**
	 * Counter that keeps track of each successful exit.
	 */
	private int exitCounter;
	
	/**
	 * Constructs an exit machine with an internal clock and
	 * with an exit count of zero.
	 * @param givenClock
	 *    the clock
	 */
	public ExitMachine(SimpleClock givenClock)
	{
		clock = givenClock;
		exitCounter = 0;
	}

	/**
	 * Simulates inserting a ticket in this machine.
	 * Returns true if successful exit, false otherwise.
	 * @param t
	 *   the ticket
	 * @return
	 *   if exit is successful
	 */
	public boolean insertTicket(Ticket t)
	{
		
		if (t.getPaymentTime() > 0)
		{
			int currentTime = clock.getTime();
			int paymentTime = t.getPaymentTime();
			
			if ((currentTime - paymentTime) < ParkingRateUtil.EXIT_TIME_LIMIT)
			{
				exitCounter += 1;
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns a count of the total number of successful exits.
	 * @return
	 *    the total number of successful exits
	 */
	public int getExitCount()
	{
		return exitCounter;
	}
}
