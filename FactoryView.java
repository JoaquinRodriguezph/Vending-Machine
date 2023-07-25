import javax.swing.*;
import java.awt.*;

public class FactoryView {
    private JFrame mainframe;
    public FactoryView(){
        this.mainframe = new JFrame("Vending Machine Factory Simulator");
        this.mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainframe.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.mainframe.setSize(900,900);


        this.mainframe.setVisible(true);
    }
}
