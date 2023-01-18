package hw1;

/**
 * This class models a ticket dispenser in a parking garage that sets
 * a new ticket with a timestamp based on its internal clock.
 * Time is assumed to be represented in minutes as a single integer.
 * 
 * @author Halle N
 */
public class TicketDispenser
{
 
	/**
	 * Clock used to tell time.
	 */
	private SimpleClock clock;

	/**
	 * Constructs a ticket dispenser with an internal clock.
	 * @param givenClock
	 *   the clock
	 */
	public TicketDispenser(SimpleClock givenClock)
	{
		clock = givenClock;
	}

	/**
	 * Constructs a new ticket with the given time on the
	 * time dispenser's clock and with a payment time of zero.
	 * @return
	 *   the ticket
	 */
	public Ticket takeTicket()
	{
		Ticket t = new Ticket(clock.getTime());
		t.setPaymentTime(0);
		
		return t;
	}
}

