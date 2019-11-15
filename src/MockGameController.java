import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MockGameController extends Thread
{

   private MockServer _server;
   private ArrayList<MockPlayer> _players;
   private int _turn;
   private int _iterations;
   private final int _maxIterations;


   public MockGameController(MockServer server)
   {

      _server = server;
      _players = new ArrayList<>();
      _turn = 0;
      _iterations = 0;
      _maxIterations = 15;
   }


   public void run()
   {

      System.out.println("MockGameController has started running...");

      startGame();

   }


   public void startGame()
   {

      System.out.println("Running startGame() method...");

      ArrayList<MockServerWorker> list = _server.getWorkerList();

      for (MockServerWorker worker : list)
      {
         addPlayer(worker);
      }

      _turn = getFirstTurn();

      runGame();

   }


   private void runGame()
   {

      MockPlayer currentPlayer;

      while (!gameIsOver())
      {
         currentPlayer = _players.get(_turn);
         executeTurn(currentPlayer);
         _turn = getNextTurn(_turn);
      }

      System.out.println("This concludes the test!");
   }


   private void executeTurn(MockPlayer currentPlayer)
   {

      currentPlayer.send("Test");

      try
      {
         boolean lockAcquired = _server.getLock().tryLock(2, TimeUnit.SECONDS);

         if (lockAcquired)
         {
            try
            {
               System.out.println(
                       "GameController successfully retrieved client reply : " + _server.getLastMessageContent());
            } finally
            {
               _server.getLock().unlock();
            }
         }
      } catch (InterruptedException e)
      {
         e.printStackTrace();
      }

   }


   private boolean gameIsOver()
   {

      if (_iterations < _maxIterations)
      {
         _iterations++;
         return false;
      }

      return true;
   }


   public void addPlayer(MockServerWorker worker)
   {

      MockPlayer player = new MockPlayer(worker);
      System.out.println(player.toString());
      _players.add(player);
   }


   private int getFirstTurn()
   {

      int index = 0;
      for (MockPlayer player : _players)
      {
         String name = player.getCharacterName();
         if (name == "Ms._Scarlett")
         {
            index = _players.indexOf(player);
         }
      }
      return index;
   }


   private int getNextTurn(int counter)
   {

      counter++;
      return counter % _players.size();

   }


}
