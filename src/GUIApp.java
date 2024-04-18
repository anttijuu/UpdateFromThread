import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GUIApp {
    public static void main(String[] args) throws Exception {
        new GUIApp().launch();
    }

    private JFrame mainFrame;
    private DotModel model;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;
    
    private void launch() {
        model = new DotModel(WIDTH, HEIGHT);
        GraphicsPanel panel = new GraphicsPanel(model);
        panel.setSize(WIDTH, HEIGHT);
        model.setObserver(panel);
        mainFrame = new JFrame("Update GUI from Thread demo");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container rootPane = mainFrame.getContentPane();
        rootPane.add(panel);
        mainFrame.setPreferredSize(new Dimension(WIDTH + 20, HEIGHT + 20));
        mainFrame.pack();
        mainFrame.setVisible(true);
        model.launch();
    }
}
