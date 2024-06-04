import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private static final long serialVersionUID = -1175726918742874762L;
    private boolean play = false; 
    private int score = 0;
    private int TotalBricks;
    private Timer time;
    private int delay = 6;
    private int playerX = 210;
    private int ballposX = 120;
    private int ballposY = 350;
    private int balldirX = -1;
    private int balldirY = -2;
    private MapGenerator map;
    private int level;
    private int i2, j2;
    private int life = 2;
    private boolean k = false;

    public Gameplay(int level) {
        this.level = level;
        this.i2 = level * 2;
        this.j2 = level * 3 + 1;
        this.TotalBricks = i2 * j2;
        map = new MapGenerator(i2, j2);
        this.setPreferredSize(new Dimension(700, 600));
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false); 
        time = new Timer(delay, this);
        time.start();
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        map.draw((Graphics2D)g);
        g.setColor(Color.blue);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // Lives
        if (life == 2) {
            g.setColor(Color.red);
            g.fillArc(260, 10, 14, 14, 0, 180);
            g.fillArc(274, 10, 14, 14, 0, 180);
            g.fillPolygon(new int[] { 260, 274, 288 }, new int[] { 17, 29, 17 }, 3);
            g.setColor(Color.red);
            g.fillArc(291, 10, 14, 14, 0, 180);
            g.fillArc(305, 10, 14, 14, 0, 180);
            g.fillPolygon(new int[] { 291, 305, 319 }, new int[] { 17, 29, 17 }, 3);
            g.fillArc(322, 10, 14, 14, 0, 180);
            g.fillArc(336, 10, 14, 14, 0, 180);
            g.fillPolygon(new int[] { 322, 336, 350 }, new int[] { 17, 29, 17 }, 3);
        } else if (life == 1) {
            g.setColor(Color.red);
            g.fillArc(260, 10, 14, 14, 0, 180);
            g.fillArc(274, 10, 14, 14, 0, 180);
            g.fillPolygon(new int[] { 260, 274, 288 }, new int[] { 17, 29, 17 }, 3);
            g.fillArc(291, 10, 14, 14, 0, 180);
            g.fillArc(305, 10, 14, 14, 0, 180);
            g.fillPolygon(new int[] { 291, 305, 319 }, new int[] { 17, 29, 17 }, 3);
        } else if (life == 0) {
            g.setColor(Color.red);
            g.fillArc(260, 10, 14, 14, 0, 180);
            g.fillArc(274, 10, 14, 14, 0, 180);
            g.fillPolygon(new int[] { 260, 274, 288 }, new int[] { 17, 29, 17 }, 3);
        }

        // Display Score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("" + score, 590, 30);

        // Display level
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Level " + level, 3, 30);

        // Player's Bar
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // Ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        // For Next Level
        if (TotalBricks <= 0) {
            if (level < 14) {
                play = true;
                balldirX = 0;
                balldirY = 0;
                g.setColor(Color.red);
                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("LEVEL " + level + " Cleared   Score: " + score, 260, 300);
                g.setFont(new Font("serif", Font.BOLD, 20));
                g.drawString("Press enter to continue", 290, 350);
                k = true;
            } else {
                play = false;
                balldirX = 0;
                balldirY = 0;
                g.setColor(Color.red);
                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("YOU WON!  Score: " + score, 260, 300);
                g.setFont(new Font("serif", Font.BOLD, 20));
                g.drawString("Press enter to play again", 290, 350);
                k = true;
                time.stop();
                // Input in = new Input();
                // in.button();
            }
        }

        // For Game End
        if (ballposY > 570) {
            if (life == 0) {
                play = false;
                balldirX = 0;
                balldirY = 0;

                g.setColor(Color.red);
                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("GAME OVER   scores: " + score, 190, 300);
                g.setFont(new Font("serif", Font.BOLD, 20));
                g.drawString("Press enter to restart", 190, 350);
                k = true;
                time.stop();
                // Input in = new Input();
                // in.button();
            } else {
                life--;
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (level == 1) {
                    score = 0;
                } else {
                    score = 5 * (i2 - 1) * 5;
                }
                ballposX = 120;
                ballposY = 350;
                balldirX = -1;
                balldirY = -2;
                playerX = 310;
                TotalBricks = i2 * j2;
                map = new MapGenerator(i2, j2);
                k = false;
            }
        }
        g.dispose();
    }

    public void actionPerformed(ActionEvent ae) {
        time.start();
        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                balldirY = -balldirY;
            }
            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickwidth + 80;
                        int brickY = i * map.brickheight + 50;
                        int brickwidth = map.brickwidth;
                        int brickheight = map.brickheight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;

                        if (ballrect.intersects(brickrect)) {
                            map.setBrickvalue(0, i, j);
                            TotalBricks--;
                            score += 5;
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + brickrect.width) {
                                balldirX = -balldirX;
                            } else {
                                balldirY = -balldirY;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX += balldirX;
            ballposY += balldirY;

            if (ballposX < 0) {
                balldirX = -balldirX;
            }
            if (ballposY < 0) {
                balldirY = -balldirY;
            }
            if (ballposX > 670) {
                balldirX = -balldirX;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyReleased(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            if (k == true) {
                if (life == 0) {
                    score = 0;
                }
                ballposX = 120;
                ballposY = 350;
                balldirX = -1;
                balldirY = -2;
                playerX = 310;
                if (TotalBricks <= 0) {
                    level++;
                    if (level <= 14) {
                        i2 = level * 2;
                        j2 = level * 3 + 1;
                    }
                    TotalBricks = i2 * j2;
                    map = new MapGenerator(i2, j2);
                } else {
                    TotalBricks = i2 * j2;
                    map = new MapGenerator(i2, j2);
                }
                k = false;
                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
}
