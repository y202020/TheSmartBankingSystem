/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banking_sys;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class BackEnd_Oprtns {
   Scanner sc = new Scanner(System.in);
   public final String url ="com.mysql.cj.jdbc.Driver";
   public final String db_name ="bankingsystem";
   public final String connction_url ="jdbc:mysql://localhost:3306/"+db_name;
   public final String user_name ="root";
   public final String passwpord ="";
    Random rn = new Random();
    int  ac_No= rn.nextInt(9999,9999999);
   public void crt_Acc(){
       System.out.println("\t\t\t\t\t\t\t\t Make an USER-NAME ?");
       String usr_nm=sc.next();
       System.out.println("\t\t\t\t\t\t\t\t Build PASSWORD ?");
       String psd=sc.next();
       System.out.println("\t\t\t\t\t\t\t\t Enter EMAIL ?");
       String eml=sc.next();
       try {
           
           Class.forName(url);
           Connection conn = DriverManager.getConnection(connction_url,user_name, passwpord);
           
           String checkAcc ="SELECT * from account where username = ? or email = ?";
           PreparedStatement pSt =conn.prepareStatement(checkAcc);
           pSt.setString(1,usr_nm);
           pSt.setString(2,eml);
           ResultSet rs = pSt.executeQuery();
           if(rs.next()){
             System.out.println("\t\t\t\t\t\t\t\t Account already Existed !! ");
           }else{
             String crt = "INSERT into account (username,password,email,accountnumber) values (?,?,?,?)";  
             PreparedStatement prStmt =conn.prepareStatement(crt);
             prStmt.setString(1, usr_nm);
             prStmt.setString(2, psd);
             prStmt.setString(3,eml);
             prStmt.setInt(4,ac_No);
             int i = prStmt.executeUpdate();
             if(i==1){
                 System.out.println("EMAIL = "+eml);
                String info ="SELECT * FROM account WHERE email=?";
                PreparedStatement ainfo = conn.prepareStatement(info);
                ainfo.setString(1,eml);
                
                ResultSet getid = ainfo.executeQuery();
                
                if(getid.next()){ 
                  int uid = getid.getInt("id");
                  String k = "INSERT into accinfo (balance,uid) values(?,?)";
                  PreparedStatement ps =conn.prepareStatement(k);
                  ps.setInt(1,0);
                  ps.setInt(2,uid);
                  int upd =ps.executeUpdate();
                   if(upd==1){
                       System.out.println("\t\t\t\t\t\t\t\t ACCOUNT SUCCESSFULLLY CREATED ! ");
                       System.out.println("\t\t\t\t\t\t\t\t Your Account number --> "+ac_No);
                   } else {
                       System.out.println("\t\t\t\t\t\t\t\t Not Created ");
                   }
                } else {
                    System.out.println("USER NOT FOUND");
                } 
            
             }else{
             System.out.println("\t\t\t\t\t\t\t\t Some Error occured !!");
             }
           }
       }catch (ClassNotFoundException | SQLException e) {
            System.err.println("\t\t\t\t\t\t\t\t Excptn ==>> "+e);
       }
    } 
   
   public void lg_window(){
       System.out.println("\t\t\t\t\t\t\t\t Enter Email ?");
       String emal = sc.next();
       System.out.println("\t\t\t\t\t\t\t\t Enter Password ?");
       String pasd =sc.next();
       try {
         Class.forName(url);
         Connection cnc = DriverManager.getConnection(connction_url, user_name, passwpord);
         String check ="Select * from account where email = ? and password = ?";
         PreparedStatement stmt = cnc.prepareStatement(check);
         stmt.setString(1, emal);
         stmt.setString(2, pasd);
         ResultSet rs = stmt.executeQuery();
         if(rs.next()){
          System.out.println("\t\t\t\t\t\t\t\t Enter account no ? ");
          int ac =sc.nextInt(); 
          String accheck="SELECT accountnumber from account where email = ? and password = ? ";
          PreparedStatement stm = cnc.prepareStatement(accheck);
          stm.setString(1,emal);
          stm.setString(2,pasd);
          
          ResultSet r_set = stm.executeQuery();
            if(r_set.next()){
                if(ac==r_set.getInt("accountnumber")){
                 after_loginWindow afw = new after_loginWindow(ac);
                }else{
                 System.out.println("\t\t\t\t\t\t\t\t SYSTEM NOT RESPONDING !");
                }
            }
         
          }else{
             System.out.println("\t\t\t\t\t\t\t\t INVALID !! USERNAME/PASSWORD ");
          }
        } catch (ClassNotFoundException | SQLException e) {
           System.out.println("\t\t\t\t\t\t\t\t excp = "+e);
       }
    }
}
