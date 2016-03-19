
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class GameClientTest{

  // testing default GameClient constructor
  @Test
  public void GameClientDefaultConstructorTest() throws Exception{
    
    GameClient testClient = new GameClient();

    assertNotNull("Client created",testClient);

  }

  // testing machine/port pair GameClientConstructor
  @Test
  public void GameClientMachinePortPairConstructor() throws Exception{
    
    GameClient testClient = new GameClient("localhost",5555);

    assertNotNull("Client created",testClient);
  
  }

  // testing GameClient connection
  @Test
  public void GameClientConnectionTest() throws Exception{
  
    GameClient testClient = new GameClient();
    testClient.connect();
    

  } 

}
