package demogame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;
public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;
    private Timer time;// for setting the speed of the ball
    private int delay = 5;
    private int player1 = 310;
    private int ballposx = 120;
    private int ballposy = 350;
    private int ballxdir = -1;
    private int ballydir = -2;
    private MapGenerator map = new MapGenerator(3, 7);

    public GamePlay() {
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.time = new Timer(this.delay, this);
        this.time.start();
    }

    public void paint(Graphics g) {
    	//canvas
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        this.map.draw((Graphics2D)g);
        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        g.setColor(Color.white);
        g.setFont(new Font("Verdana", 1, 25));
        g.drawString("" + this.score, 590, 30);
        
        // paddle
        g.setColor(Color.green);
        g.fillRect(this.player1, 550, 100, 8);
        //ball
        g.setColor(Color.blue);
        g.fillOval(this.ballposx, this.ballposy, 20, 20);
        if (this.totalbricks <= 0) {
            this.play = false;
            this.ballxdir = 0;
            this.ballydir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("Verdana", 1, 35));
            g.drawString("YOU WON!", 190, 300);
            g.setFont(new Font("Verdana", 1, 30));
            g.drawString("Press ENTER to restart", 230, 350);
        }

        if (this.ballposy > 570) {
            this.play = false;
            this.ballxdir = 0;
            this.ballydir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("Verdana", 1, 35));
            g.drawString("GAME OVER!", 190, 300);
            g.setFont(new Font("Verdana", 1, 30));
            g.drawString("Press ENTER to restart", 230, 350);
        }

        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        this.time.start();
        if (this.play) {
            if ((new Rectangle(this.ballposx, this.ballposy, 20, 20)).intersects(new Rectangle(this.player1, 550, 100, 8))) {
                this.ballydir = -this.ballydir;
            }

            label48:
            for(int i = 0; i < this.map.map.length; ++i) {
                for(int j = 0; j < this.map.map[0].length; ++j) {
                    if (this.map.map[i][j] > 0) {
                        int brickx = j * this.map.brickwidth + 80;
                        int bricky = i * this.map.brickheight + 50;
                        int brickwidth = this.map.brickwidth;
                        int brickheight = this.map.brickheight;
                        Rectangle rect = new Rectangle(brickx, bricky, brickwidth, brickheight);
                        Rectangle ballrect = new Rectangle(this.ballposx, this.ballposy, 20, 20);
                        if (ballrect.intersects(rect)) {
                            this.map.setBrickval(0, i, j);
                            --this.totalbricks;
                            this.score += 10;
                            if (this.ballposx + 19 > rect.x && this.ballposx + 1 < rect.x + rect.width) {
                                this.ballydir = -this.ballydir;
                                break label48;
                            }

                            this.ballxdir = -this.ballxdir;
                            break label48;
                        }
                    }
                }
            }

            this.ballposx += this.ballxdir;
            this.ballposy += this.ballydir;
            if (this.ballposx < 0) {
                this.ballxdir = -this.ballxdir;
            }

            if (this.ballposy < 0) {
                this.ballydir = -this.ballydir;
            }

            if (this.ballposx > 670) {
                this.ballxdir = -this.ballxdir;
            }
        }

        this.repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 39) {
            if (this.player1 >= 600) {
                this.player1 = 600;
            } else {
                this.moveright();
            }
        }

        if (e.getKeyCode() == 37) {
            if (this.player1 < 10) {
                this.player1 = 10;
            } else {
                this.moveleft();
            }
        }

        if (e.getKeyCode() == 10 && !this.play) {
            this.play = true;
            this.ballposx = 120;
            this.ballposy = 350;
            this.ballxdir = -1;
            this.ballydir = -2;
            this.player1 = 310;
            this.score = 0;
            this.totalbricks = 21;
            this.map = new MapGenerator(3, 7);
            this.repaint();
        }

    }

    public void moveright() {
        this.play = true;
        this.player1 += 20;
    }

    public void moveleft() {
        this.play = true;
        this.player1 -= 20;
    }
}
