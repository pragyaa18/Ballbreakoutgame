package demogame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
public class MapGenerator {
    public int[][] map;
    public int brickwidth;
    public int brickheight;

    public MapGenerator(int row, int col) {
        this.map = new int[row][col];

        for(int i = 0; i < this.map.length; ++i) {
            for(int j = 0; j < this.map[0].length; ++j) {
                this.map[i][j] = 1;
            }
        }

        this.brickwidth = 540 / col;
        this.brickheight = 150 / row;
    }

    public void draw(Graphics2D g) {
        for(int i = 0; i < this.map.length; ++i) {
            for(int j = 0; j < this.map[0].length; ++j) {
                if (this.map[i][j] > 0) {
                    g.setColor(Color.white);
                    g.fillRect(j * this.brickwidth + 80, i * this.brickheight + 50, this.brickwidth, this.brickheight);
                    g.setStroke(new BasicStroke(3.0F));
                    g.setColor(Color.black);
                    g.drawRect(j * this.brickwidth + 80, i * this.brickheight + 50, this.brickwidth, this.brickheight);
                }
            }
        }

    }

    public void setBrickval(int val, int row, int col) {
        this.map[row][col] = val;
    }
}
