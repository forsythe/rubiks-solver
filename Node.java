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

    public static final String[] COLORS = {"G", "O", "R", "W", "B", "Y"};
    //green, orange, red, white, blue, yellow

    int[][] up, down, left, right, front, back;

    public Node(int[][] up_rhs, int[][] down_rhs, int[][] left_rhs, int[][] right_rhs, int[][] front_rhs, int[][] back_rhs) {
        up = up_rhs;
        down = down_rhs;
        left = left_rhs;
        right = right_rhs;
        front = front_rhs;
        back = back_rhs;
    }
    
    public Node(int[][] p){
        rotateCCW(p);
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

    public void u() { //rotate upper clockwise

    }

    public void ui() { //rotate upper anticlockwise

    }

    public void u2() { //rotate upper 180

    }

    public void l() {

    }

    public void li() {

    }

    public void l2() {

    }

    public void f() {

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
