package hw1;

/**
 * This class models a payment machine in a parking garage that
 * an existing ticket is inserted into and calculates the amount
 * owed for parking, accepts payment, and then ejects the ticket.
 * Time is assumed to be represented in minutes as a single integer. 
 * 
 * @author Halle N
 */
public class PaymentMachine
{
  
	/**
	 * Clock used to tell time.
	 */
	private SimpleClock clock;
	
	/**
	 * Total payments made for parking time.
	 */
	private double totalPayment; 
	
	/**
	 * Ticket inserted into this machine.
	 */
	private Ticket ticket;

	/**
	 * Constructs a payment machine with an internal clock and
	 * with total payments beginning at zero.
	 * @param givenClock
	 *    the clock
	 */
	public PaymentMachine(SimpleClock givenClock)
	{
		clock = givenClock;
		totalPayment = 0.0;
	}
	
	/**
	 * Inserts a ticket into this machine.
	 * @param t
	 *    the ticket
	 */
	public void insertTicket(Ticket t)
	{
		ticket = t;
	}
	
	/**
	 * Returns a reference to the ticket.
	 * If none exists, returns null.
	 * @return
	 *   the ticket reference
	 */
	public Ticket getCurrentTicket()
	{
		Ticket currentTicket = ticket;
		
		if (currentTicket == ticket)
		{
			return currentTicket;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Checks if a ticket is currently in the machine.
	 * If no ticket is in the machine, returns false.
	 * @return
	 *   if a ticket is in progress
	 */
	public boolean inProgress()
	{
		if (ticket == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Returns the payment due for the ticket currently in the machine.
	 * If no transaction is in progress, returns zero.
	 * @return
	 *   the payment due
	 */
	public double getPaymentDue()
	{	
		double initialAmount;
		double paidAmount;
		
		if (inProgress() == false)
		{
			return 0.0;
		}
		else
		{
			int currentTime = clock.getTime();
			int startTime = ticket.getStartTime();
			int paymentTime = ticket.getPaymentTime();
			int minutesParked = (currentTime - startTime);
			
			initialAmount = ParkingRateUtil.calculateCost(minutesParked);
			
			if (ticket.getPaymentTime() != 0.0)
			{
				minutesParked = (paymentTime - startTime);
				paidAmount = ParkingRateUtil.calculateCost(minutesParked);
				
				return initialAmount - paidAmount;
			}
			else
			{		
				return initialAmount;
			}
		}
	}

	/**
	 * Updates current ticket with payment time and adds payment
	 * amount to this machine's total.
	 */
	public void makePayment()
	{
		totalPayment = totalPayment + getPaymentDue();
		ticket.setPaymentTime(clock.getTime());
	}
	
	/**
	 * Ejects a ticket from this machine.
	 */
	public void ejectTicket()
	{
		ticket = null;
	}

	/**
	 * Returns the total payments that have been made at this machine.
	 * @return
	 *    the total payments made
	 */
	public double getTotalPayments()
	{
		return totalPayment;
	}
}
