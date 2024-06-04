import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private Main mainFrame;

    public MenuPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new GridLayout(4, 1));
        this.setPreferredSize(new Dimension(700, 600));

        JLabel title = new JLabel("Brick Breaker", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 30));
        this.add(title);

        JButton level1Button = new JButton("Level 1");
        level1Button.addActionListener(new LevelButtonListener(1));
        this.add(level1Button);

        JButton level2Button = new JButton("Level 2");
        level2Button.addActionListener(new LevelButtonListener(2));
        this.add(level2Button);

        JButton level3Button = new JButton("Level 3");
        level3Button.addActionListener(new LevelButtonListener(3));
        this.add(level3Button);
    }

    private class LevelButtonListener implements ActionListener {
        private int level;

        public LevelButtonListener(int level) {
            this.level = level;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.startGame(level);
        }
    }
}
