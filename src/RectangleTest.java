import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RectangleTest {
  Rectangle myRectangle = new Rectangle(6,7);

  @Test
  public void testGetArea() {
    assertEquals (myRectangle.getArea(),42);
  }

  @Test
  public void testGetPerimeter() {
    assertEquals (myRectangle.getPerimeter(),26);
  }

  @Test
  public void testLength() {
    assertEquals (myRectangle.length,6);
  }

  @Test
  public void testWidth() {
    assertEquals (myRectangle.width,7);
  }


}
