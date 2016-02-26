
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class GameClientTest{

  // testing GameClient constructor
  @Test
  public void GameClientConstructorTest() throws Exception{
    
    GameClient testClient = new GameClient();

    assertNotNull("Client created",testClient);

  }

  // testing GameClient connection
  @Test
  public void GameClientConnectionTest() throws Exception{
  
    GameClient testClient = new GameClient();
    testClient.connect();
    

  } 

}
