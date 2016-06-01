/**
 *
 * @author Forsythe
 */
package rubik;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;

class MyCanvas extends JComponent {

    Node n;

    Color[] colors = new Color[]{Color.GREEN, Color.ORANGE, Color.RED, Color.WHITE, Color.BLUE, Color.YELLOW};
    int gridSize = 50;
    int gridGap = 3;

    public void setNode(Node n_rhs) {
        n = n_rhs;
    }

    public void drawFace(Graphics g, int[][] face, int x, int y) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int val = face[r][c];
                g.setColor(colors[val]);
                g.fillRect(x + c * (gridSize + gridGap), y + r * (gridSize + gridGap), gridSize, gridSize);
            }
        }
    }

    public void paint(Graphics g) {
        drawFace(g, n.up, 3 * (gridSize + gridGap), 0);
        drawFace(g, n.left, 0, 3 * (gridSize + gridGap));
        drawFace(g, n.front, 3 * (gridSize + gridGap), 3 * (gridSize + gridGap));
        drawFace(g, n.right, 6 * (gridSize + gridGap), 3 * (gridSize + gridGap));
        drawFace(g, n.back, 9 * (gridSize + gridGap), 3 * (gridSize + gridGap));
        drawFace(g, n.down, 3 * (gridSize + gridGap), 6 * (gridSize + gridGap));
    }

}

public class Rubik {

    public static void main(String[] args) {

        Node n = new Node();
        n.doMove(Node.Move.U);
        n.doMove(Node.Move.U);

        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 650, 525);

        MyCanvas cvs = new MyCanvas();
        cvs.setNode(n);
        window.getContentPane().add(cvs);
        window.setVisible(true);

    }

}
