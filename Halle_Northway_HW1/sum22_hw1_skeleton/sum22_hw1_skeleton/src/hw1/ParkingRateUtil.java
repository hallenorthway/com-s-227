package hw1;

/**
 * This is a utility class that calculates the rate for parking
 * based on the total number of minutes parked.  Time is assumed
 * to be represented in minutes as a single integer.
 * 
 * @author Halle N
 */
public class ParkingRateUtil
{

	/**
	 * The cost of parking beginning at zero.
	 */
	private static double cost = 0;
	
	/**
	 * Constructs the container for this class.
	 */
	private ParkingRateUtil()
	{
		// Empty constructor.
	}
	
	/**
	 * Constant representing the exit time limit.
	 */
	public static final int EXIT_TIME_LIMIT = 15;
	
	/**
	 * Returns the cost of parking for the given total number of minutes
	 * based on the rates for the parking garage.
	 * @param minutes
	 *   the total number of minutes
	 * @return
	 *   the cost of parking
	 */
	public static double calculateCost(int minutes)
	{
		double fee1 = 1.00;
		double fee2 = 1.50;
		double fee3 = 1.25;
		
		double hoursRoundedUp = ((minutes + 59) / 60);
		int days = (int) (hoursRoundedUp / 24);
		int daysInMinutes = (1440 * days);
		
		// Assigning these new values is what allows calculations
		// for parking times that are over 24 hours.
		minutes = minutes - daysInMinutes;
		hoursRoundedUp = ((minutes + 59) / 60);
				
		if ((minutes > 0) && (minutes <= 30))
		{
			cost = (days * 13.0) + fee1;
		}
		else if((minutes > 30) && (minutes <= 60))
		{
			cost = (days * 13.0) + (fee1 * 2);
		}
		else if ((hoursRoundedUp > 1.0) && (hoursRoundedUp <= 5.0))
		{
			cost = (days * 13.0) + ((fee1 * 2) + (fee2 * (hoursRoundedUp - 1)));
		}
		else if ((hoursRoundedUp > 5.0) && (hoursRoundedUp <= 8.0))
		{
			cost = (days * 13.0) + (fee2 * 2) + (fee3 * (hoursRoundedUp - 1));
		}
		else if ((hoursRoundedUp > 8.0) && (hoursRoundedUp <= 24.0))
		{
			cost = (days * 13.0) + 13;
		}
		
		return cost;
	}
}

