
package Banking_sys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class after_loginWindow {
   public final String url ="com.mysql.cj.jdbc.Driver";
   public final String db_name ="bankingsystem";
   public final String connction_url ="jdbc:mysql://localhost:3306/"+db_name;
   public final String user_name ="root";
   public final String passwpord ="";
   int account_NO =0;
   Scanner sc = new Scanner(System.in);

    public after_loginWindow(int account_NO) {
     this.account_NO = account_NO;
     menuBar();
    }
   
   public void menuBar(){
        int k=1;
        while(k!=0){
         System.out.println("\t\t\t\t\t\t\t\t 1.Deposite Balance  ");
         System.out.println("\t\t\t\t\t\t\t\t 2.Withdraw Balance  ");
         System.out.println("\t\t\t\t\t\t\t\t 3.Show Passbook  ");
         System.out.println("\t\t\t\t\t\t\t\t 4.Transfer Amount  ");
         System.out.println("\t\t\t\t\t\t\t\t 5.Delet Account  ");
         System.out.println("\t\t\t\t\t\t\t\t 6.Log-Out ! ");
         System.out.println("\t\t\t\t\t\t\t\t Enter choice ?"); 
         int ch =sc.nextInt();
         
            switch(ch){
                case 1:
                    dep_Money();
                    break;
                case 2:
                    withdr_money();
                    break;
                case 3:
                    pssbk();
                    break;
                case 4:
                    trnsfr_amt();
                    break;
                    
                default:
                    System.out.println("\n\t\t\t\t\t\t\t\t Please Select Correct Option !!");
                 
            }
            
            
         System.out.println("\t\t\t\t\t\t\t\t PRESS 0- MAIN MENU / 1-SAME PAGE ");
         k=sc.nextInt();
        }
    }
   
   protected void pssbk(){
       try {
          Class.forName(url);
          Connection cont = DriverManager.getConnection(connction_url,user_name, passwpord);
          int idu=0;
          String ui = "Select id from account where accountnumber = ?";
          PreparedStatement p = cont.prepareStatement(ui);
          p.setInt(1, account_NO);
          ResultSet r =p.executeQuery();
          if(r.next()){
           idu=r.getInt("id");
          }  
         String pbk = "Select * from passbook where uid = ? ";
         PreparedStatement ps = cont.prepareStatement(pbk);
         ps.setInt(1,idu);
         
         ResultSet rs = ps.executeQuery();
          System.out.println("\t\t\t\t\t\t\t\t Transaction\t  Date      Time \t\t Debit   \t Credit    \t Balance");
         while(rs.next()){
            System.out.println("\t\t\t\t\t\t\t\t "+rs.getString("transaction")+" \t "+rs.getString("date")+" \t "+rs.getInt("debit")+" \t\t"+rs.getInt("credit")+"  \t "+rs.getInt("balance"));   
         }
       } catch (ClassNotFoundException | SQLException e) {
           System.out.println("\t\t\t\t\t\t\t\t "+e.getMessage());
       }
    }
   
   protected int getuid(int ac_no){
       int uid = 0;
       try {
          Class.forName(url);
          Connection c = DriverManager.getConnection(connction_url,user_name, passwpord);
            
            String ui = "Select id from account where accountnumber = ?";
            PreparedStatement p = c.prepareStatement(ui);
            p.setInt(1,ac_no);
            ResultSet r =p.executeQuery();
            if(r.next()){
              uid=r.getInt("id");
            }   
          } catch (Exception e) {
             System.out.println(""+e.getMessage());
          }
       return uid;
   }
   
   protected int getIdBal(int ac_no){
    int rtrn_val =0;  
       try {
       Class.forName(url);
       Connection cont = DriverManager.getConnection(connction_url,user_name, passwpord);
       
       String getId ="Select id from account where accountnumber = ?";
       PreparedStatement preSt = cont.prepareStatement(getId);
       preSt.setInt(1, ac_no);
       
       ResultSet rs = preSt.executeQuery();
       if(rs.next()){
        int idu = rs.getInt("id");
         String ids = "Select id from accinfo where uid = ? ";
         PreparedStatement pst = cont.prepareStatement(ids);
         pst.setInt(1,idu);
         
         ResultSet r = pst.executeQuery();
         if(r.next()){
           int id = r.getInt("id");
           rtrn_val = id;
         }else{
             System.out.println("\t\t\t\t\t\t\t\t Id Misssing !!");
         }
       }else{
           System.out.println("\t\t\t\t\t\t\t\t Uid MISSING !!");
       }
       } catch (ClassNotFoundException | SQLException e) {
           System.out.println("\t\t\t\t\t\t\t\t "+e.getMessage());
       }
     return rtrn_val;
   }
   
   protected void dep_Money(){
       try {
           int idu=0;
           Class.forName(url);
           Connection cont = DriverManager.getConnection(connction_url,user_name, passwpord);
            idu=getuid(account_NO);
            System.out.println("\t\t\t\t\t\t\t\t Hello ! , Enter Amount = ");
            int dAmt = sc.nextInt();
            int bal_id = getIdBal(account_NO);
                String uidchk = "Select balance from accinfo where id = ? ";
                PreparedStatement pst = cont.prepareStatement(uidchk);
                pst.setInt(1, bal_id);
                ResultSet rSet = pst.executeQuery();
            if(rSet.next()){
              int bal_amnt = rSet.getInt("balance");
              bal_amnt = dAmt+ bal_amnt;
              String typ = "'Deposited'";
              String dp = "UPDATE accinfo set balance=? where id = ? and uid = ? ";
              PreparedStatement dpst = cont.prepareStatement(dp);
              dpst.setInt(1,bal_amnt);
              dpst.setInt(2,bal_id);
              dpst.setInt(3,idu);
              
              int updt = dpst.executeUpdate();
                shw_trsc(idu,typ,dAmt,0,bal_amnt);
              if(updt ==1){
                  System.out.println("\t\t\t\t\t\t\t\t AMOUNT DEPOSITED !");
              }else{
                  System.out.println("\t\t\t\t\t\t\t\t TRANSACATION FAILED !!");
              }
            }
            else {
               System.out.println("\t\t\t\t\t\t\t\t SOMETHING IS WRONG - 111 !!"); 
            }
         } catch (ClassNotFoundException | SQLException e) {
           System.out.println("\t\t\t\t\t\t\t\t Error ==> "+e.getMessage());
         }
    }
   protected int chk_bal(int Amt){
       
        int return_val = 0;
       try {
        Class.forName(url);
        Connection con = DriverManager.getConnection(connction_url,user_name, passwpord);
        
           int idu = getuid(account_NO);
            String getinf = "SELECT id,balance from accinfo where uid = ? ";
            PreparedStatement getb = con.prepareStatement(getinf);
            getb.setInt(1, idu);
            ResultSet rs = getb.executeQuery();
           if(rs.next()){
            int usr_bal = rs.getInt("balance");
            if(usr_bal>=Amt){
             return_val =  usr_bal;
            }else{
             return_val =  -1;
            }
          }  
        }catch (ClassNotFoundException | SQLException e) {
           System.out.println("\t\t\t\t\t\t\t\t "+e.getMessage());
         }
       return return_val;
   }
   
   protected void withdr_money(){
       try {
         int idu = 0;  
        Class.forName(url);
        Connection con = DriverManager.getConnection(connction_url,user_name, passwpord);
        
        System.out.println("Enter amount to withdraw ?");
        int w_amt =sc.nextInt();
        int act_bal = chk_bal(w_amt);
        
        if(act_bal != -1){
            
            int rg_bal = act_bal - w_amt;  
            int b_id = getIdBal(account_NO);
             String udt = "UPDATE accinfo  set balance = ? where id = ? ";
             PreparedStatement ps = con.prepareStatement(udt);
              ps.setInt(1,rg_bal);
              ps.setInt(2,b_id);
              int i = ps.executeUpdate();
               if(i==1){
                 System.out.println("\t\t\t\t\t\t\t\t Transaction completed !! ");
               }else{
                 System.out.println("\t\t\t\t\t\t\t\t    Transaction FAILED !!");
               }
              String typ = "'Withdrawn'"; 
              idu=getuid(account_NO);
              shw_trsc(idu,typ,0,w_amt,rg_bal);
             }else{
               System.out.println("\t\t\t\t\t\t\t\t TRANSACTION FAILED --> LOW BALANCE !");
             }
           
           
          
       } catch (ClassNotFoundException | SQLException e) {
           System.out.println("\t\t\t\t\t\t\t\t Error --> "+e.getMessage());
       }
    }
   
   protected void shw_trsc(int uid,String trnsc,int dp,int crd,int bal){
        try {
            
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Calendar cal = Calendar.getInstance();
            String date_complete = df.format(cal.getTime());
            
           Class.forName(url);
           Connection c = DriverManager.getConnection(connction_url,user_name, passwpord);
           String s = "Insert into passbook(uid,transaction,date,debit,credit,balance) values (?,?,?,?,?,?)";
           PreparedStatement ps = c.prepareStatement(s);
           ps.setInt(1,uid);
           ps.setString(2,trnsc);
           ps.setString(3,date_complete);
           ps.setInt(4,dp);
           ps.setInt(5,crd);
           ps.setInt(6,bal);
           
           int pbk = ps.executeUpdate();
           if(pbk==1){
               System.out.println("\t\t\t\t\t\t\t\t  PASSBOOK UPDATED !! ");
            }else{
              System.out.println("\t\t\t\t\t\t\t\t  AN ERROR OCCURED,Plz TRY AGAIN !! ");
            }
          }catch (ClassNotFoundException | SQLException e) {
              System.out.println("\t\t\t\t\t\t\t\t  "+e.getMessage());
           }
   }
   
  protected void trnsfr_amt(){
      try {
          Class.forName(url);
          Connection c = DriverManager.getConnection(connction_url,user_name, passwpord);
            int ruid=0,idu = 0;
            
            int wt_id = getIdBal(account_NO);
            String b = "Select balance from accinfo where id = ? ";
            PreparedStatement ps = c.prepareStatement(b);
            ps.setInt(1, wt_id);
            
            ResultSet st = ps.executeQuery();
            if(st.next()){
              int bal = st.getInt("balance");
               System.out.println("\t\t\t\t\t\t\t\t Enter Recipent's Name ?");
               String nam = sc.next();
               System.out.println("\t\t\t\t\t\t\t\t ENTER RECIPENT's ACCOUNT NUMBER ?");
               int tfac = sc.nextInt();
               
                int tf_uid = getIdBal(tfac);
                String rcp = "Select balance from accinfo where id = ? ";
                PreparedStatement q = c.prepareStatement(rcp);
                q.setInt(1,tf_uid);
                ResultSet rc = q.executeQuery();
                if(rc.next()){
                  
                    
                 int rc_id = getIdBal(tfac);
                 int rcp_bal=rc.getInt("balance");
                 System.out.println("\t\t\t\t\t\t\t\t ENTER AMOUNT ? ");
                 int tf_amt=sc.nextInt();
                  if(tf_amt<= chk_bal(tf_amt)){
                    rcp_bal += tf_amt;  
                     String tf = "UPDATE accinfo set balance = ? where uid = ? ";
                     PreparedStatement updbl = c.prepareStatement(tf);
                     updbl.setInt(1,rcp_bal);
                     updbl.setInt(2,rc_id);
                     int i = updbl.executeUpdate();
                    if(i==1){
                      System.out.println("\t\t\t\t\t\t\t\t AMOUNT TRANSFERED !!");
                     
                      String bal_upd = "UPDATE accinfo set balance = ? where id = ?";
                      int new_bal = bal - tf_amt;
                      PreparedStatement bal_updt = c.prepareStatement(bal_upd);
                      bal_updt.setInt(1,new_bal);
                      bal_updt.setInt(2,wt_id);
                      int nbl = bal_updt.executeUpdate();
                        String trf = "'Transfer'";
                        idu=getuid(account_NO);
                       shw_trsc(idu,trf,0,tf_amt,new_bal);
                        ruid=getuid(tfac);
                       shw_trsc(ruid,trf,tf_amt,0,rcp_bal);
                    }
                  }else{
                      System.out.println("\t\t\t\t\t\t\t\t TRANSACTION FAILED!:- DUE TO LOW Balance");
                    }
                }else{
                     System.out.println("\t\t\t\t\t\t\t\t AN ERROR OCCURED !!"); 
                }   
               
            }else{
                System.out.println("\t\t\t\t\t\t\t\t SOME ERROR OCCURED !");
            }
            
       } catch (ClassNotFoundException | SQLException e) {
          System.out.println("\t\t\t\t\t\t\t\t "+e.getMessage());
        }
   } 
}
