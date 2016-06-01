/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubik;

import java.util.Arrays;

/**
 *
 * @author Forsythe
 */
public class Node {

    public static final String[] COLORS = {"G", "O", "R", "W", "B", "Y"};
    public static final int GREEN = 0, ORANGE = 1, RED = 2, WHITE = 3, BLUE = 4, YELLOW = 5;

    public enum Move {
        U, Ui, U2, L, Li, L2, F, Fi, F2, R, Ri, R2, B, Bi, B2, D, Di, D2;

        public String ToString() {
            return this.name();
        }
    }

    public static Move[] moves = {Move.U, Move.Ui, Move.U2, Move.L, Move.Li,
        Move.L2, Move.F, Move.Fi, Move.F2, Move.R, Move.Ri, Move.R2, Move.B,
        Move.Bi, Move.B2, Move.D, Move.Di, Move.D2};

    Move curMove; //Move to achieve current node
    Node parent = null;

    public int[][] up, down, left, right, front, back;

    public Node() {
        up = filled3x3Array(WHITE);
        down = filled3x3Array(YELLOW);
        left = filled3x3Array(ORANGE);
        right = filled3x3Array(RED);
        front = filled3x3Array(GREEN);
        back = filled3x3Array(BLUE);
    }

    public static int[][] filled3x3Array(int val) {
        return new int[][]{{val, val, val}, {val, val, val}, {val, val, val}};
    }

    public static int[][] copy(int[][] input) {
        int[][] target = new int[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                target[r][c] = input[r][c];
            }
        }
        return target;
    }

    public Node(int[][] up_rhs, int[][] down_rhs, int[][] left_rhs, int[][] right_rhs, int[][] front_rhs, int[][] back_rhs) {
        up = copy(up_rhs);
        down = copy(down_rhs);
        left = copy(left_rhs);
        right = copy(right_rhs);
        front = copy(front_rhs);
        back = copy(back_rhs);
    }

    @Override
    public boolean equals(Object other) {
        Node b = (Node) other;
        return (Arrays.deepEquals(up, b.up)
                && Arrays.deepEquals(down, b.down)
                && Arrays.deepEquals(left, b.left)
                && Arrays.deepEquals(right, b.right)
                && Arrays.deepEquals(front, b.front)
                && Arrays.deepEquals(back, b.back));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Arrays.deepHashCode(this.up);
        hash = 31 * hash + Arrays.deepHashCode(this.down);
        hash = 31 * hash + Arrays.deepHashCode(this.left);
        hash = 31 * hash + Arrays.deepHashCode(this.right);
        hash = 31 * hash + Arrays.deepHashCode(this.front);
        hash = 31 * hash + Arrays.deepHashCode(this.back);
        return hash;
    }

    public boolean isDone() {
        return (faceOnlyContainsValue(up, WHITE)
                && faceOnlyContainsValue(down, YELLOW)
                && faceOnlyContainsValue(left, ORANGE)
                && faceOnlyContainsValue(right, RED)
                && faceOnlyContainsValue(front, GREEN)
                && faceOnlyContainsValue(back, BLUE));
    }

    public boolean faceOnlyContainsValue(int[][] n, int val) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (n[r][c] != val)
                    return false;
            }
        }
        return true;
    }

    public Node[] getChildren() {
        Node[] ans = new Node[18];
        for (int k = 0; k < 18; k++) {
            ans[k] = (new Node(up, down, left, right, front, back));
            ans[k].doMove(moves[k]);
        }
        return ans;
    }

    public void rotateCW(int[][] face) { //clockwise
        int temp = face[0][0];
        face[0][0] = face[2][0];
        face[2][0] = face[2][2];
        face[2][2] = face[0][2];
        face[0][2] = temp;
        temp = face[0][1];
        face[0][1] = face[1][0];
        face[1][0] = face[2][1];
        face[2][1] = face[1][2];
        face[1][2] = temp;
    }

    public void rotateCCW(int[][] face) {
        int temp = face[0][0];
        face[0][0] = face[0][2];
        face[0][2] = face[2][2];
        face[2][2] = face[2][0];
        face[2][0] = temp;
        temp = face[0][1];
        face[0][1] = face[1][2];
        face[1][2] = face[2][1];
        face[2][1] = face[1][0];
        face[1][0] = temp;
    }

    public void rotate180(int[][] face) {
        int temp = face[0][0];
        face[0][0] = face[2][2];
        face[2][2] = temp;
        temp = face[0][2];
        face[0][2] = face[2][0];
        face[2][0] = temp;
        temp = face[0][1];
        face[0][1] = face[2][1];
        face[2][1] = temp;
        temp = face[1][0];
        face[1][0] = face[1][2];
        face[1][2] = temp;
    }

    public void swapRow(int[][] a, int[][] b, int rowA, int rowB) {
        int[] temp = a[rowA];
        a[rowA] = b[rowB];
        b[rowB] = temp;
    }

    public void swapCol(int[][] a, int[][] b, int colA, int colB) {
        int[] temp = {a[0][colA], a[1][colA], a[2][colA]};
        a[0][colA] = b[0][colB];
        a[1][colA] = b[1][colB];
        a[2][colA] = b[2][colB];

        b[0][colB] = temp[0];
        b[1][colB] = temp[1];
        b[2][colB] = temp[2];
    }

    public void doMove(Move m) {
        int[] temp;
        curMove = m;
        switch (m) {
            case U:
                rotateCW(up);
                temp = back[0];
                back[0] = left[0];
                left[0] = front[0];
                front[0] = right[0];
                right[0] = temp;
                break;
            case Ui:
                rotateCCW(up);
                temp = back[0];
                back[0] = right[0];
                right[0] = front[0];
                front[0] = left[0];
                left[0] = temp;
                break;
            case U2:
                rotate180(up);
                swapRow(left, right, 0, 0);
                swapRow(front, back, 0, 0);
                break;
            case L:
                rotateCW(left);
                temp = new int[]{up[0][0], up[1][0], up[2][0]};
                up[0][0] = back[2][2];
                up[1][0] = back[1][2];
                up[2][0] = back[0][2];

                back[0][2] = down[2][0];
                back[1][2] = down[1][0];
                back[2][2] = down[0][0];

                down[0][0] = front[0][0];
                down[1][0] = front[1][0];
                down[2][0] = front[2][0];

                front[0][0] = temp[0];
                front[1][0] = temp[1];
                front[2][0] = temp[2];
                break;
            case Li:
                rotateCCW(left);
                temp = new int[]{up[0][0], up[1][0], up[2][0]};
                up[0][0] = front[0][0];
                up[1][0] = front[1][0];
                up[2][0] = front[2][0];

                front[0][0] = down[0][0];
                front[1][0] = down[1][0];
                front[2][0] = down[2][0];

                down[0][0] = back[2][2];
                down[1][0] = back[1][2];
                down[2][0] = back[0][2];

                back[0][2] = temp[2];
                back[1][2] = temp[1];
                back[2][2] = temp[0];
                break;
            case L2:
                rotate180(left);
                swapCol(up, down, 0, 0);
                temp = new int[]{front[0][0], front[1][0], front[2][0]};
                front[0][0] = back[2][2];
                front[1][0] = back[1][2];
                front[2][0] = back[0][2];

                back[0][2] = temp[2];
                back[1][2] = temp[1];
                back[2][2] = temp[0];
                break;
            case F:
                rotateCW(front);
                temp = new int[]{up[2][0], up[2][1], up[2][2]};
                up[2][0] = left[2][2];
                up[2][1] = left[1][2];
                up[2][2] = left[0][2];

                left[0][2] = down[0][0];
                left[1][2] = down[0][1];
                left[2][2] = down[0][2];

                down[0][0] = right[2][0];
                down[0][1] = right[1][0];
                down[0][2] = right[0][0];

                right[0][0] = temp[0];
                right[1][0] = temp[1];
                right[2][0] = temp[2];
                break;
            case Fi:
                rotateCCW(front);
                temp = new int[]{up[2][0], up[2][1], up[2][2]};
                up[2][0] = right[0][0];
                up[2][1] = right[1][0];
                up[2][2] = right[2][0];

                right[0][0] = down[0][2];
                right[1][0] = down[0][1];
                right[2][0] = down[0][0];

                down[0][0] = left[0][2];
                down[0][1] = left[1][2];
                down[0][2] = left[2][2];

                left[0][2] = temp[2];
                left[1][2] = temp[1];
                left[2][2] = temp[0];
                break;
            case F2:
                rotate180(front);
                temp = new int[]{up[2][0], up[2][1], up[2][2]};
                up[2][0] = down[0][2];
                up[2][1] = down[0][1];
                up[2][2] = down[0][0];

                down[0][0] = temp[2];
                down[0][1] = temp[1];
                down[0][2] = temp[0];

                temp = new int[]{left[0][2], left[1][2], left[2][2]};
                left[0][2] = right[2][0];
                left[1][2] = right[1][0];
                left[2][2] = right[0][0];

                right[0][0] = temp[2];
                right[1][0] = temp[1];
                right[2][0] = temp[0];
                break;
            case R:
                rotateCW(right);
                temp = new int[]{up[0][2], up[1][2], up[2][2]};
                up[0][2] = front[0][2];
                up[1][2] = front[1][2];
                up[2][2] = front[2][2];

                front[0][2] = down[0][2];
                front[1][2] = down[1][2];
                front[2][2] = down[2][2];

                down[0][2] = back[2][0];
                down[1][2] = back[1][0];
                down[2][2] = back[0][0];

                back[0][0] = temp[2];
                back[1][0] = temp[1];
                back[2][0] = temp[0];
                break;
            case Ri:
                rotateCCW(right);
                temp = new int[]{up[0][2], up[1][2], up[2][2]};
                up[0][2] = back[2][0];
                up[1][2] = back[1][0];
                up[2][2] = back[0][0];

                back[0][0] = down[2][2];
                back[1][0] = down[1][2];
                back[2][0] = down[0][2];

                down[0][2] = front[0][2];
                down[1][2] = front[1][2];
                down[2][2] = front[2][2];

                front[0][2] = temp[0];
                front[1][2] = temp[1];
                front[2][2] = temp[2];
                break;
            case R2:
                rotate180(right);
                swapCol(up, down, 2, 2);
                temp = new int[]{front[0][2], front[1][2], front[2][2]};
                front[0][2] = back[2][0];
                front[1][2] = back[1][0];
                front[2][2] = back[0][0];

                back[0][0] = temp[2];
                back[1][0] = temp[1];
                back[2][0] = temp[0];
                break;
            case B:
                rotateCW(back);
                temp = new int[]{up[0][0], up[0][1], up[0][2]};

                up[0][0] = right[0][2];
                up[0][1] = right[1][2];
                up[0][2] = right[2][2];

                right[0][2] = down[2][2];
                right[1][2] = down[2][1];
                right[2][2] = down[2][0];

                down[2][0] = left[0][0];
                down[2][1] = left[1][0];
                down[2][2] = left[2][0];

                left[0][0] = temp[2];
                left[1][0] = temp[1];
                left[2][0] = temp[0];
                break;
            case Bi:
                rotateCCW(back);
                temp = new int[]{up[0][0], up[0][1], up[0][2]};

                up[0][0] = left[2][0];
                up[0][1] = left[1][0];
                up[0][2] = left[0][0];

                left[0][0] = down[2][0];
                left[1][0] = down[2][1];
                left[2][0] = down[2][2];

                down[2][0] = right[2][2];
                down[2][1] = right[1][2];
                down[2][2] = right[0][2];

                right[0][2] = temp[0];
                right[1][2] = temp[1];
                right[2][2] = temp[2];
                break;
            case B2:
                rotate180(back);
                temp = new int[]{up[0][0], up[0][1], up[0][2]};

                up[0][0] = down[2][2];
                up[0][1] = down[2][1];
                up[0][2] = down[2][0];

                down[2][0] = temp[2];
                down[2][1] = temp[1];
                down[2][2] = temp[0];

                temp = new int[]{left[0][0], left[1][0], left[2][0]};

                left[0][0] = right[2][2];
                left[1][0] = right[1][2];
                left[2][0] = right[0][2];

                right[0][2] = temp[2];
                right[1][2] = temp[1];
                right[2][2] = temp[0];

                break;
            case D:
                rotateCW(down);
                temp = back[2];
                back[2] = right[2];
                right[2] = front[2];
                front[2] = left[2];
                left[2] = temp;
                break;
            case Di:
                rotateCCW(down);
                temp = back[2];
                back[2] = left[2];
                left[2] = front[2];
                front[2] = right[2];
                right[2] = temp;
                break;
            case D2:
                rotate180(down);
                swapRow(left, right, 2, 2);
                swapRow(front, back, 2, 2);
                break;
        }
    }

    public static void print(int[][] n) {
        for (int[] k : n) {
            for (int z : k) {
                System.out.print(" " + COLORS[z]);
            }
            System.out.println();
        }
    }

    public String toString() {
        print(up);
        print(down);
        print(left);
        print(right);
        print(front);
        print(back);
        return "";
    }
}
