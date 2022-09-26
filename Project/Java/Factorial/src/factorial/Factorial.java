/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorial;

import java.util.Scanner;

/**
 *
 * @author Hossein Fani
 */
public class Factorial {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Scanner console = new Scanner(System.in);
       Integer n = Integer.parseInt(console.nextLine());
       Integer r = 1;
       for(int i = n; i > 0; i--){
           r = r * i;
       }
       System.out.println(r);
    }
    
}
