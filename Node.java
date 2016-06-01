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

    public static final String[] COLORS = {"G", "O", "R", "W", "B", "Y"};  //green, orange, red, white, blue, yellow

    public enum Move {
        U, Ui, U2, L, Li, L2, F, Fi, F2, R, Ri, R2, B, Bi, B2, D, Di, D2;
    }

    Move cur; //Move to achieve current node

    int[][] up, down, left, right, front, back;

    public Node(int[][] up_rhs, int[][] down_rhs, int[][] left_rhs, int[][] right_rhs, int[][] front_rhs, int[][] back_rhs) {
        up = up_rhs;
        down = down_rhs;
        left = left_rhs;
        right = right_rhs;
        front = front_rhs;
        back = back_rhs;
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
    
    public void rotate180(int[][] face){
        
    }
    
    public void doMove(Move m) {
        int[] temp;
        switch (m) {
            case U:
                rotateCW(up);
                temp = left[1];
                left[1] = front[1];
                front[1] = right[1];
                right[1] = temp;
                break;
            case Ui:
                rotateCCW(up);
                temp = back[1];
                back[1] = right[1];
                right[1] = front[1];
                front[1] = left[1];
                left[1] = temp;
                break;
            case U2:
                
                break;
            case L:
                break;
            case Li:
                break;
            case L2:
                break;
            case F:
                break;
            case Fi:
                break;
            case F2:
                break;
            case R:
                break;
            case Ri:
                break;
            case R2:
                break;
            case B:
                break;
            case Bi:
                break;
            case B2:
                break;
            case D:
                break;
            case Di:
                break;
            case D2:
                break;
        }

    }

    public void print(int[][] n) {
        for (int[] k : n) {
            for (int z : k) {
                System.out.print(" " + COLORS[z]);
            }
            System.out.println();
        }
    }
}
