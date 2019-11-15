public class MockServerWorker extends Thread
{

   private final MockServer _server;
   private String _userName;
   private static int currentMessageCount = 0;
   private final int _sleepTime;


   public MockServerWorker(MockServer mockServer, String userName)
   {

      _server = mockServer;
      _userName = userName;
      _sleepTime = 1000;
      System.out.println("ServerWorker " + _userName + " has been constructed...");
   }


   public void run()
   {

      System.out.println("ServerWorker " + _userName + " has started running...");
   }


   public void send(String toString)
   {

      _server.getLock().lock();

      System.out.println("Message received in " + _userName);
      try
      {
         Thread.sleep(_sleepTime);
      } catch (InterruptedException e)
      {
         e.printStackTrace();
      }
      _server.setLastMessageContent(_userName + " replied with number " + ++currentMessageCount);

      _server.getLock().unlock();

   }


   public String getUserName()
   {

      return _userName;
   }


}
