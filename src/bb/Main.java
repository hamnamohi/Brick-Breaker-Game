import javax.swing.*;
import java.awt.*;
//21b-189-se
public class Main extends JFrame {
    private MenuPanel menuPanel;
    private Gameplay gameplayPanel;

    public Main() {
        setTitle("Brick Breaker");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        menuPanel = new MenuPanel(this);
        add(menuPanel);
        
        setVisible(true);
    }

    public void startGame(int level) {
        remove(menuPanel);
        gameplayPanel = new Gameplay(level);
        add(gameplayPanel);
        gameplayPanel.requestFocusInWindow();
        revalidate();
    }

    public static void main(String[] args) {
        new Main();
    }}
