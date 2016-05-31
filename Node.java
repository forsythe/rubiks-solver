/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubik;

/**
 *
 * @author Forsythe
 */
public class Node {

    public static final int WHITE = 4, ORANGE = 1, GREEN = 2, RED = 3, BLUE = 4, YELLOW = 5;

    int[][] up, down, left, right, front, back;

    public Node() {
        up = new int[][]{{WHITE, WHITE, WHITE}, {WHITE, WHITE, WHITE}, {WHITE, WHITE, WHITE}};

        for (int[] n : up)
            for (int k : n)
                System.out.println(k);
    }

}
