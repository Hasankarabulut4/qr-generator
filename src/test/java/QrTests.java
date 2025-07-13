import org.api.QrGenerator;
import org.frame.Window;
import org.junit.Test;

import javax.swing.*;

public class QrTests {

    @Test
    public void dimensionTest() {
        Window window = new Window();
        System.out.println(window.getScreenDimension());
    }

    @Test
    public void urlTest() {
        System.out.println(QrGenerator.getQrcode("Hello wrold",150));
    }


    @Test
    public void imageTest() {

    }
}
