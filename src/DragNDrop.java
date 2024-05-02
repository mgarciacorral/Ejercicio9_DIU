import javax.swing.*;
import java.awt.*;

public class DragNDrop extends JFrame
{
    private JPanel panelGrid = new JPanel(new GridLayout(3, 3));
    private JPanel[] panelesDrop = new JPanel[9];
    private JPanel[] panelesDrag = new JPanel[5];
    public DragNDrop()
    {
        super("DragNDrop");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }
}
