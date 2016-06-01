/**
 *
 * @author Forsythe
 */
package rubik;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import rubik.Node.Move;

class MyCanvas extends JComponent {

    Node n;
    Color[] colors = new Color[]{new Color(0, 154, 68), new Color(254, 80, 0), new Color(186, 12, 47), Color.WHITE, new Color(0, 61, 165), new Color(255, 215, 0)};
    int gridSize = 50, gridGap = 5, faceGap = 8;

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

    @Override
    public void paint(Graphics g) {
        drawFace(g, n.up, 2 * faceGap + 3 * (gridSize + gridGap), faceGap);
        drawFace(g, n.left, faceGap, 2 * faceGap + 3 * (gridSize + gridGap));
        drawFace(g, n.front, 2 * faceGap + 3 * (gridSize + gridGap), 2 * faceGap + 3 * (gridSize + gridGap));
        drawFace(g, n.right, 3 * faceGap + 6 * (gridSize + gridGap), 2 * faceGap + 3 * (gridSize + gridGap));
        drawFace(g, n.back, 4 * faceGap + 9 * (gridSize + gridGap), 2 * faceGap + 3 * (gridSize + gridGap));
        drawFace(g, n.down, 2 * faceGap + 3 * (gridSize + gridGap), 3 * faceGap + 6 * (gridSize + gridGap));
    }

}

public class RubikRunner {

    static final int MAX_SEARCH_DEPTH = 30;

    public static void main(String[] args) {

        Node n = new Node();

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setBounds(30, 30, 700, 560);
        window.getContentPane().setBackground(new Color(50, 50, 50));

        MyCanvas cvs = new MyCanvas();
        cvs.setNode(n);
        window.getContentPane().add(cvs);
        window.setVisible(true);

        RubikRunner runner = new RubikRunner();

        runner.scramble(n, 7);
        Node pristineCube = new Node();
        Node solution = runner.IDS(n, pristineCube);

        System.out.println("Solution sequence: ");
        ArrayList<Move> solveSequence = new ArrayList<>();

        while (solution.parent != null) {
            solveSequence.add(0, solution.curMove);
            solution = solution.parent;
        }

        for (Move m : solveSequence) {
            System.out.print(m + " ");
        }
    }

    public void scramble(Node n, int level) {
        System.out.println("Scrambling depth: " + level);
        ArrayList<Move> scrambleSequence = new ArrayList<>();

        int pos = (int) (Math.random() * Node.moves.length);
        scrambleSequence.add(Node.moves[pos]);

        while (scrambleSequence.size() < level) {
            pos = (int) (Math.random() * Node.moves.length);
            while (Node.areCounterproductiveMoves(scrambleSequence.get(scrambleSequence.size() - 1), Node.moves[pos])) {
                pos = (int) (Math.random() * Node.moves.length);
            }
            scrambleSequence.add(Node.moves[pos]);
        }

        for (Move m : scrambleSequence) {
            n.doMove(m);
            System.out.print(" " + m);
        }
        System.out.println();
    }

    public Node IDS(Node root, Node goal) {
        Node result;
        for (int depth = 0; depth < MAX_SEARCH_DEPTH; depth++) {
            //System.out.println("cur depth: " + depth);
            result = depthLimitedSearch(root, goal, depth);

            if (result != null) {
                System.out.println("Solution found at depth: " + depth);
                return result;
            }
        }
        return null;
    }

    public Node depthLimitedSearch(Node n, Node goal, int depth) {

        if (depth == 0 && n.equals(goal)) {
            return n;
        } else if (depth > 0) {
            for (Node child : n.getChildren()) {
                if (child == null) //necessary, because one will be null ("undo" moves not included in children)
                    continue;
                child.parent = n;
                Node result = depthLimitedSearch(child, goal, depth - 1);
                if (result != null)
                    return result;
            }
        }
        return null;
    }

    public int heuristic(Node n) {
        return 0;
    }

}
