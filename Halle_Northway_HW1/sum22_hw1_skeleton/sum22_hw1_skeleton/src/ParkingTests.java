import hw1.ExitMachine;
import hw1.PaymentMachine;
import hw1.SimpleClock;
import hw1.Ticket;
import hw1.TicketDispenser;

public class ParkingTests
{
  public static void main(String[] args)
  {
    
//    SimpleClock c = new SimpleClock();
//    ExitMachine em = new ExitMachine(c);
//    Ticket t = new Ticket(c.getTime());
//    t.setPaymentTime(10);
//    c.timePasses(20);
//    boolean canExit = em.insertTicket(t); 
//    System.out.println(canExit);            // expected true
//    Ticket t2 = new Ticket(0);
//    t2.setPaymentTime(30);
//    c.timePasses(60);
//    canExit = em.insertTicket(t2); 
//    System.out.println(canExit);           // expected false
//    System.out.println(em.getExitCount()); // expected 1

//    SimpleClock c = new SimpleClock();
//    System.out.println(c.getTime());        // Expected 0
//    c.timePasses(10);
//    System.out.println(c.getTime());        // Expected 10
//    Ticket t = new Ticket(c.getTime());
//    System.out.println(t.getStartTime());   // Expected 10
//    System.out.println(t.getPaymentTime()); // Expected 0
	  
//	  SimpleClock c = new SimpleClock();
//	  TicketDispenser td = new TicketDispenser(c);
//	  c.timePasses(10);
//	  Ticket t = td.takeTicket();
//	  System.out.println(t.getStartTime());   // Expected 10
//	  System.out.println(t.getPaymentTime()); // Expected 0


//    SimpleClock c = new SimpleClock();
//    ExitMachine em = new ExitMachine(c);
//    Ticket t = new Ticket(c.getTime());
//    t.setPaymentTime(10);
//    c.timePasses(20);
//    boolean canExit = em.insertTicket(t); 
//    System.out.println(canExit);            // expected true
//    Ticket t2 = new Ticket(c.getTime());
//    c.timePasses(30);
//    canExit = em.insertTicket(t2); 
//    System.out.println(canExit);            // expected false
//    System.out.println(em.getExitCount());  // expected 1

//    SimpleClock c = new SimpleClock();
//    PaymentMachine pm = new PaymentMachine(c);
//    Ticket t = new Ticket(0);
//    pm.insertTicket(t);
//    System.out.println(pm.inProgress());   // expected true
//    Ticket current = pm.getCurrentTicket();
//    System.out.println(current == t);      // expected true
//    pm.ejectTicket();
//    System.out.println(pm.inProgress());   // expected false
//    current = pm.getCurrentTicket();
//    System.out.println(current == null);   // expected true
    
//    SimpleClock c = new SimpleClock();
//    TicketDispenser td = new TicketDispenser(c);
//    c.timePasses(10);
//    Ticket t = td.takeTicket();
//    System.out.println(t.getStartTime());   // Expected 10
//    System.out.println(t.getPaymentTime()); // Expected 0
 
//    SimpleClock c = new SimpleClock();
//    c.timePasses(10);
//    PaymentMachine pm = new PaymentMachine(c);
//    Ticket t = new Ticket(c.getTime());
//    c.timePasses(60); 
//    pm.insertTicket(t);
//    System.out.println(pm.getPaymentDue());  // expected 2.00
//    pm.ejectTicket();
//    c.timePasses(75);
//    pm.insertTicket(t);
//    System.out.println(pm.getPaymentDue());  // expected 5.00
//    double cost = pm.getPaymentDue();
//    System.out.println(pm.inProgress());   // expected true
//    Ticket current = pm.getCurrentTicket();
//    System.out.println(current == t);      // expected true
//    pm.ejectTicket();
//    System.out.println(pm.inProgress());   // expected false
//    current = pm.getCurrentTicket();
//    System.out.println(current == null);   // expected true

  }
}
