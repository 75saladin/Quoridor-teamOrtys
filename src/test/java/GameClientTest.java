
import Client.GameClient;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Ignore;

public class GameClientTest{

  // testing GameClient parameter processing
  @Ignore
  @Test
  public void GameClientParameterProcessingTest() throws Exception{
  
    String[] testArgs = {"localhost:5555","localhost:5554"};

    String[] testProcess = GameClient.processParams(testArgs);

    String[] expectedArray = {"localhost","5555","localhost","5554"};

    assertNotNull("Array should contain values",testProcess);
  
    assertArrayEquals("Failure - arrays do not match",expectedArray,testProcess);

  }

}
