import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class EdgeTest {

    Edge e = new Edge();
    
    @Test
    public void testGetSource() {
	assertEquals("Testing getSource()", null, e.getSource());
    }

    @Test
    public void testGetTarget() {
	assertEquals("Testing getTarget()", null, e.getTarget());
    }

}
