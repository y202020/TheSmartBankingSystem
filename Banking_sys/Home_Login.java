/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banking_sys;

import java.util.Scanner;

public class Home_Login {
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       int k=1;
      while(k!=0){
       System.out.println("\t\t\t\t\t\t\t\t 1.CREATE ACCOUNT ?");
       System.out.println("\t\t\t\t\t\t\t\t 2.LOG-IN");
       System.out.println("\t\t\t\t\t\t\t\t 3.Exit !");
       System.out.println("\t\t\t\t\t\t\t\t What do you Want to do ? ");
       int ch=sc.nextInt();
       switch(ch){
           case 1:
            new BackEnd_Oprtns().crt_Acc();
            break;
           case 2 : 
            new BackEnd_Oprtns().lg_window();
            break;
           case 3:
               System.out.println("\t\t\t\t\t\t\t\t Thanks ! , Have a Nice Day (:  ");   
            break;   
        }
       System.out.println("\n\t\t\t\t\t\t\t\t GO TO HOME ! --> 'PRESS-1' / 0-Exit ");
       k=sc.nextInt();
     }
    }
    
}
