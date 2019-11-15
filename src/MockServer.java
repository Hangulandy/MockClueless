
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MockServer extends Thread
{

   private MockGameController _gc; // TODO make into a singleton class
   private String _lastMessageContent;
   private ReentrantLock _lock;

   private ArrayList<MockServerWorker> _workerList = new ArrayList<>();


   public MockServer()
   {

      _gc = new MockGameController(this);
      _lock = new ReentrantLock();
   }


   public ArrayList<MockServerWorker> getWorkerList()
   {

      return _workerList;
   }


   @Override
   public void run()
   {

      for (int i = 0; i < 6; i++)
      {
         addWorker(new MockServerWorker(this, "ServerWorker " + (i + 1)));
      }

      _gc.start();
   }


   public void addWorker(MockServerWorker serverWorker)
   {

      _workerList.add(serverWorker);
      serverWorker.start();
   }


   public String getLastMessageContent()
   {

      return _lastMessageContent;
   }


   public void setLastMessageContent(String string)
   {
      _lastMessageContent = string;
      System.out.println("Message in server : " + _lastMessageContent);
   }

   public ReentrantLock getLock()
   {

      return _lock;
   }

}
