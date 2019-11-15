public class MockPlayer
{

   private final int _playerNum;
   private static int numPlayers = 0; // class variable, not instance variable
   private String _characterName = "";
   private String _userName;
   private MockServerWorker _serverWorker;

   public MockPlayer()
   {

      _playerNum = ++numPlayers; // set this player number to current player count

      switch (_playerNum)
      {
         case 1:
            _characterName = "Ms._Scarlett";
            break;
         case 2:
            _characterName = "Col._Mustard";
            break;
         case 3:
            _characterName = "Mrs._White";
            break;
         case 4:
            _characterName = "Mrs._Peacock";
            break;
         case 5:
            _characterName = "Prof._Plum";
            break;
         case 6:
            _characterName = "Mr._Green";
            break;
      }
   }


   public MockPlayer(MockServerWorker serverWorker)
   {

      this();
      _userName = serverWorker.getUserName();
      _serverWorker = serverWorker;

      System.out.println("Player successfully created for " + _userName);
   }


   public String getCharacterName()
   {

      return _characterName;
   }


   public void send(String msg)
   {

      _serverWorker.send(msg);
   }


}
