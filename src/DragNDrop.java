import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;

public class DragNDrop extends JFrame
{
    private JPanel panelGrid = new JPanel();
    private JPanel panelColores = new JPanel();
    private JPanel[] panelesDrop = new JPanel[9];
    private JPanel[] panelesDrag = new JPanel[5];
    private Color[] colores = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK};
    public DragNDrop()
    {
        super("DragNDrop");
        setLayout(new BorderLayout());

        panelColores.setLayout(new GridLayout(5, 1, 0, 30));
        panelColores.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

        panelGrid.setLayout(new GridLayout(3, 3, 30, 30));
        panelGrid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelGrid.setPreferredSize(new Dimension(300, 300));

        for (int i = 0; i < panelesDrop.length; i++)
        {
            panelGrid.add(panelesDrop[i] = new JPanel());
            panelesDrop[i].setBackground(Color.WHITE);
            new MyDropTargetListener(panelesDrop[i]);
        }

        for(int i = 0;i < panelesDrag.length; i ++)
        {
            panelColores.add(panelesDrag[i] = new JPanel());
            panelesDrag[i].setBackground(colores[i]);
            panelesDrag[i].setPreferredSize(new Dimension(60, 50));new DragSource();
            DragSource ds= new DragSource();
            ds.createDefaultDragGestureRecognizer(panelesDrag[i], DnDConstants.ACTION_COPY, new DragGestureListener() {
                public void dragGestureRecognized(DragGestureEvent event) {
                    Cursor cursor = null;
                    JPanel panel = (JPanel) event.getComponent();
                    Color color = panel.getBackground();
                    if (event.getDragAction() == DnDConstants.ACTION_COPY) {
                        cursor = DragSource.DefaultCopyDrop;
                    }
                    event.startDrag(cursor, new TransferableColor(color));
                }
            });
        }

        add(panelGrid, BorderLayout.CENTER);
        add(panelColores, BorderLayout.WEST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }

    public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = null;
        JPanel panel = (JPanel) event.getComponent();
        Color color = panel.getBackground();
        if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }
        event.startDrag(cursor, new TransferableColor(color));
    }

    private class MyDropTargetListener extends DropTargetAdapter {
        private DropTarget dropTarget;
        private JPanel panel;
        public MyDropTargetListener(JPanel panel) {
            this.panel = panel;
            dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY, this, true, null);
        }
        public void drop(DropTargetDropEvent event) {
            try {
                Transferable tr = event.getTransferable();
                Color color = (Color) tr.getTransferData(TransferableColor.getColorFlavor());
                if (event.isDataFlavorSupported(TransferableColor.getColorFlavor())) {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    this.panel.setBackground(color);
                    event.dropComplete(true);
                    return;
                }
                event.rejectDrop();
            } catch (Exception e) {
                e.printStackTrace();
                event.rejectDrop();
            }
        }
    }
}
