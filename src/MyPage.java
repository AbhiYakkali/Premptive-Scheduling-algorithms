import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.swing.table.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author saisu
 */
public class MyPage extends javax.swing.JFrame {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
    int size=0;
 
    int MAX_VALUE=999;
    /**
     * Creates new form MyPage
     */
    public MyPage() {
        super("Home Page");
        initComponents();
        conn=Connecting.ConnerDb();
        //Calender();
        //table1();
        
    }
    
    public void tablerr_srtf() throws SQLException{
        try{
            String sql="select * from rr_srtf";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            try{
                rs.close();
                pst.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        try{
            String sql2="delete from rr_srtf";
           pst=conn.prepareStatement(sql2);
            pst.executeUpdate();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            try{
                rs.close();
                pst.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
        
    }
     public void tablepri() throws SQLException{
        try{
            String sql="select * from pri";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            try{
                rs.close();
                pst.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        try{
            String sql2="delete from pri";
           pst=conn.prepareStatement(sql2);
            pst.executeUpdate();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            try{
                rs.close();
                pst.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
        
    } 
    
    
 public void prepri(int[] pt,int[] at,int[] bt,int[] pro,int n){
    // int[] at = new int[20];
      //  int[] bt = new int[20];
        int[] ct = new int[20];
        int[] tat = new int[20];
        int[] wt = new int[20];
        //int n=pt.length;
        System.out.println("----"+n);
       // int[] pro=new int[20];
       // int[] pt=new int[20];
        int[] bt1=new int[20];
        int[] pt1=new int[20];
      prepri_sort(at,bt,pt,pro,n);
    for(int i=0; i<n; i++)
{
bt1[i]=bt[i];
System.out.println(bt[i]+"bt");
pt1[i]=pt[i];
System.out.println("at"+pt1[i]);
}// calculating complete time
    
//printf("\nGantt Chart: ");
int prev_i,c,i,loop_count=n;
i=prepri_findLeastPriority(pt,at,at[0],n);
//printf("\tp%d",pro[i]);
c=at[i]+1;
ct[i]=c;
bt[i]--;

if(bt[i]==0)
{
pt[i]=MAX_VALUE;
loop_count--;
}

while(loop_count>0)
{
//printf("lc=%d\n",loop_count);
prev_i=i;
i=prepri_findLeastPriority(pt,at,c,n);
//printf("i=%d\n",i);
if(prev_i!=i)
System.out.println("->p"+pro[i]);
c++;
ct[i]=c;
bt[i]--;
if(bt[i]==0)
{
pt[i]=MAX_VALUE;
loop_count--;
}
}

// calculating waiting and turn around time
float sum_tat=0,sum_wt=0;
for(i=0; i<n; i++){
tat[i]=ct[i]-at[i];
wt[i]=tat[i]-bt1[i];
sum_tat=sum_tat+tat[i];
sum_wt=sum_wt+wt[i];
}
sum_tat=sum_tat/n;
sum_wt=sum_wt/n;

System.out.println("\nidiot\n");
//printf("\t");
System.out.println("\t---------------------------------------------------");
//printf("\n\tPRO     PT      AT      BT      CT      TAT     WT\n");
//printf("\t---------------------------------------------------\n");
for(i=0; i<n; i++){
    //sleep(1);
    System.out.println("i=ehdsbjkfsjhkh-");
//printf("\tp%d\t%d\t%d\t%d\t%d\t%d\t%d\n",pro[i],pt1[i],at[i],bt1[i],ct[i],tat[i],wt[i]);
 String sql="insert into pri(pri,arr,bt,ct,tat,wt) values(?,?,?,?,?,?)";
            try{
                pst=conn.prepareStatement(sql);
                pst.setString(1,String.valueOf(pt1[i]));
                pst.setString(2,String.valueOf(at[i]));
                pst.setString(3,String.valueOf(bt1[i]));
                pst.setString(4,String.valueOf(ct[i]));
                pst.setString(5,String.valueOf(tat[i]));
                pst.setString(6,String.valueOf(wt[i]));
                pst.execute();
                
            } 
            catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }

}
try{
    tablepri();
}
catch(Exception e){
    JOptionPane.showMessageDialog(null, e);
}
jLabel2.setText("Result");
String sumtata="The Average Turn Around Time is "+sum_tat;
String sumwt="The Average Waiting Time is "+sum_wt;
jLabel3.setText(sumwt);
jLabel4.setText(sumtata);
 }
 public void prepri_sort(int[] a, int[] b,int[] pt,int[] pro,int n){
int j,key1,key2,key3,key4;
for(int i=1; i<n; i++){
j=i-1;
key1=a[i];
key2=b[i];
key3=pt[i];
key4=pro[i];
while(j>=0 && key1<a[j]){
a[j+1]=a[j];
b[j+1]=b[j];
pro[j+1]=pro[j];
pt[j+1]=pt[j];
j--;
}
a[j+1]=key1;
b[j+1]=key2;
pt[j+1]=key3;
pro[j+1]=key4;
}
}
 
public int prepri_findLeastPriority(int[] priority, int[] at, int ct,int n){
int min_value=priority[0];
int min_index=0;
for(int i=0; i<n && at[i]<=ct; i++){
if(priority[i]<min_value){
min_value=priority[i];
min_index=i;
}
}
return min_index;
}

 public void rr_sort(int[] at_, int[] bt_,int no){
int k,j,key1_,key2_;
for(int i=0; i<no; i++){
j=i-1;
k=i-1;
key1_=at_[i];
key2_=bt_[i];
while(j>=0 && key1_<at_[j]){
at_[j+1]=at_[j];
bt_[k+1]=bt_[k];
j--;
k--;
}
at_[j+1]=key1_;
bt_[k+1]=key2_;
}
}
 public void srtf_sort(int [] a,int [] b,int [] pro,int n){
        int k,j,key1,key2,key3;
for(int i=1; i<n; i++){
j=i-1;
key1=a[i];
key2=b[i];
key3=pro[i];
while(j>=0 && key1<a[j]){
a[j+1]=a[j];
b[j+1]=b[j];
pro[j+1]=pro[j];
j--;
}
a[j+1]=key1;
b[j+1]=key2;
pro[j+1]=key3;
}
}
    
public int srtf_findLeastBurstTime(int[] bt, int[] at, int ct,int n){
int min_value=bt[0];
int min_index=0;
for(int i=0; i<n && at[i]<=ct; i++){
if(bt[i]<min_value){
min_value=bt[i];
min_index=i;
}
}
return min_index;
}





                
        
  

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jTextField8 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Time-Quantum");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Arrival time");

        jButton4.setText("Get Result");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel19.setText("Burst time");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(258, 258, 258)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(152, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("  Round Robin                       ", jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("Arrival time");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setText("Burst time");

        jButton6.setText("Get Result");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(157, 157, 157)
                                .addComponent(jLabel29)
                                .addGap(46, 46, 46))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(jTextField2)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(111, 111, 111)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(182, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(" SRTF                            ", jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 0))));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Priority");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("Burst time");

        jButton7.setText("Get Result");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Arrival time");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(214, 214, 214)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(93, 93, 93)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );

        jTabbedPane1.addTab("   Priority Preemptive                          ", jPanel4);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton3.setText("Logout");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(81, 81, 81))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("                                                   Table of contents                                    ", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(47, 47, 47))
        );

        setSize(new java.awt.Dimension(1212, 686));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        Welcome ob=new Welcome();
        ob.setVisible(true);
            
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        int[] at = new int[20];
        int[] bt = new int[20];
        int[] ct = new int[20];
        int[] tat = new int[20];
        int[] wt = new int[20];
        int[] pro=new int[20];
        int[] bt1=new int[20];
        String ar_=jTextField2.getText();
        String[] ar=(ar_.split(","));
        int n=ar.length;
        String br_=jTextField8.getText();
        String[] br=(br_.split(","));
        for(int i=0; i<n; i++) {
            at[i] = Integer.parseInt(ar[i]); 
           System.out.println(at[i]);
            bt[i] = Integer.parseInt(br[i]);
           // bt1[i]=bt[i];
            System.out.println(bt[i]);
            pro[i]=i+1;
        }
        srtf_sort(at,bt,pro,n);
        // System.arraycopy(bt, 0, bt1, 0, n);
        for(int i=0; i<n; i++)
            bt1[i]=bt[i];
        // calculating complete time

//printf("\nGantt Chart:-");
        int prev_i,c,i,loop_count=n,max=MAX_VALUE;
        i=srtf_findLeastBurstTime(bt,at,at[0],n);
        //printf("i=%d\n",i);
        //printf("\tp%d",pro[i]);
        c=at[i]+1;
        ct[i]=c;
        bt[i]--;

        if(bt[i]==0)
        {
            bt[i]=MAX_VALUE;
            loop_count--;
        }

        while(loop_count>0)
        {
            prev_i=i;
            i=srtf_findLeastBurstTime(bt,at,c,n);
            if(prev_i!=i)
            System.out.println(pro[i]+"howla");
            c++;
            if(bt[i]!=max)
            {
                ct[i]=c;
                bt[i]--;
                if(bt[i]==0)
                {
                    bt[i]=MAX_VALUE;
                    loop_count--;
                }
            }
        }
        //printf("\n");

        // calculating waiting and turn around time
        float sum_tat=0,sum_wt=0;
        for(i=0; i<n; i++){
            tat[i]=ct[i]-at[i];
            wt[i]=tat[i]-bt1[i];
            sum_tat+=tat[i];
            sum_wt+=wt[i];
            System.out.println(wt[i]+" wt");
        }
        //printf("\n");
        //printf("\t----------------------------------------------");
        //printf("\n\tPRO\tAT\tBT\tCT\tTAT\tWT  \n\t---------------------------------------------\n");
        for(i=0; i<n; i++){
            //printf("\tp%d \t%d \t%d \t%d \t%d  \t%d   \n",pro[i],at[i],bt1[i],ct[i],tat[i],wt[i]);
            String sql="insert into rr_srtf(arr,bt,ct,tat,wt) values(?,?,?,?,?)";
            try{
                pst=conn.prepareStatement(sql);
                pst.setString(1,String.valueOf(at[i]));
                pst.setString(2,String.valueOf(bt1[i]));
                pst.setString(3,String.valueOf(ct[i]));
                pst.setString(4,String.valueOf(tat[i]));
                pst.setString(5,String.valueOf(wt[i]));
                pst.execute();
                
            } 
            catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
           // value += ct[i]+",";
            //sleep(1);
        }
//        jLabel18.setText(value);
        try{
            tablerr_srtf();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        sum_wt=sum_wt/n;
        sum_tat=sum_tat/n;
        jLabel2.setText("Result: ");
        String sumwt="The Average Waiting Time is "+sum_wt;
        String sumtat="The Average Turn Around Time is "+sum_tat;
        jLabel3.setText(sumwt);
        jLabel4.setText(sumtat);
        /*setVisible(false);
        Result ob=new Result(at,bt,pro,n);
        ob.setVisible(true);*/
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        int[] at_ = new int[20];
        int[] bt_ = new int[20];
        int[] bt1_=new int[20];
        int[][] rq_=new int[100][2];
        int[] wt_=new int[20];
        int[] tat_= new int[20];
        int[]  ct_  = new int[20];
        int index_, c_, r_=0, f_=0;
        //Sint rq_[100][2];
        String tq_=jTextField1.getText();
        int tq=Integer.parseInt(tq_);
        String ar_=jTextField9.getText();
        String[] ar=(ar_.split(","));
        int no_of_processes=ar.length;
        String br_=jTextField10.getText();
        String[] br=(br_.split(","));
        for(int i=0; i<no_of_processes; i++) {
            at_[i] = Integer.parseInt(ar[i]);
            bt_[i] = Integer.parseInt(br[i]);
            bt1_[i]=bt_[i];
        }
        rr_sort(at_,bt_,no_of_processes);
        for(int i=0; i<no_of_processes; i++)
        bt1_[i]=bt_[i];
        if(bt_[0]>tq){
            c_=at_[0]+tq;
            bt_[0]=bt_[0]-tq;
            ct_[0]=c_;
            rq_[r_][0]=c_;
            rq_[r_][1]=0;
            r_++;
        }
        else{
            c_=at_[0]+bt_[0];
            ct_[0]=c_;
        }
        int i=1;
        while(i<no_of_processes){
            if(r_!=0 && at_[i]<=rq_[f_][0]){ 
                if(bt_[i]>tq){
                    c_=c_+tq;
                    ct_[i]=c_;
                    bt_[i]=bt_[i]-tq;
                    rq_[r_][0]=c_;
                    rq_[r_][1]=i;
                    r_++;
                }
                else{
                    c_=c_+bt_[i];
                    ct_[i]=c_;
                }
                i++;
            }
            else if(r_==0){
                if(bt_[i]>tq){
                    c_=c_+tq;
                    bt_[i]=bt_[i]-tq;
                    rq_[r_][0]=ct_[i];
                    rq_[r_][1]=i;
                    r_++;
                }
                else{
                    c_=c_+bt_[i];
                    ct_[i]=c_;
                }
                i++;
            }
            else{
                index_=rq_[f_][1];
                f_++;
                if(bt_[index_]>tq){
                    c_=c_+tq;
                    ct_[index_]=c_;
                    bt_[index_]=bt_[index_]-tq;
                    rq_[r_][0]=ct_[index_];
                    rq_[r_][1]=index_;
                    r_++;
                }
                else{
                    c_=c_+bt_[index_];
                    ct_[index_]=c_;
                }
            }
        }
        while(f_<r_){
            index_=rq_[f_][1];
            f_++;
            if(bt_[index_]>tq){
                c_=c_+tq;
                ct_[index_]=c_;
                bt_[index_]=bt_[index_]-tq;
                rq_[r_][0]=ct_[index_];
                rq_[r_][1]=index_;
                r_++;
            }
            else{
                c_=c_+bt_[index_];
                ct_[index_]=c_;
            }
        }

        for(i=0; i<no_of_processes; i++){
            tat_[i]=ct_[i]-at_[i];
        }

        for( i=0; i<no_of_processes; i++){
            wt_[i]=tat_[i]-bt1_[i];
        }
        float awt_=0,atat_=0;
        //printf("\t---------------------------------\n");
        //printf("\tAT     BT      CT       TAT    WT\n");
        //printf("\t---------------------------------\n");
        String value="";
        for( i=0; i<no_of_processes; i++){
            //sprintf("\t%d\t%d\t%d\t%d\t%d\n",at_[i],bt1_[i],ct_[i],tat_[i],wt_[i]);
            value +=wt_[i]+",";
            awt_=awt_+wt_[i];
            atat_=atat_+tat_[i];
            String sql="insert into rr_srtf(arr,bt,ct,tat,wt) values(?,?,?,?,?)";
            try{
                pst=conn.prepareStatement(sql);
                pst.setString(1,String.valueOf(at_[i]));
                pst.setString(2,String.valueOf(bt1_[i]));
                pst.setString(3,String.valueOf(ct_[i]));
                pst.setString(4,String.valueOf(tat_[i]));
                pst.setString(5,String.valueOf(wt_[i]));
                pst.execute();
                
            } 
            catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            }
        }
        try{
           tablerr_srtf(); 
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        awt_=awt_/no_of_processes;
        atat_=atat_/no_of_processes;
        String awtt="The Average Waiting Time is "+awt_;
        jLabel3.setText(awtt);
        String atatt="The Average Turn around Time is"+atat_;
        jLabel4.setText(atatt);
        jLabel2.setText("Result:");
        //printf("\t---------------------------------\n");

        //printf("\nAverage Waiting Time: %.2f",awt_);

        //printf("\nAverage Turnaround Time: %.2f",atat_);
        //printf("\n\n");
        //jLabel22.setText(value);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        int[] at = new int[20];
        int[] bt = new int[20];
        int[] ct = new int[20];
        int[] tat = new int[20];
        int[] wt = new int[20];
        int[] pro=new int[20];
        int[] pt=new int[20];
        int[] bt1=new int[20];
        int[] pt1=new int[20];
        String pr_=jTextField5.getText();
        String[] pr=(pr_.split(","));
        String ar_=jTextField6.getText();
        String[] ar=(ar_.split(","));
        int n=ar.length;
        String br_=jTextField7.getText();
        String[] br=(br_.split(","));
        for(int i=0; i<n; i++) {
            pt[i]=Integer.parseInt(pr[i]);
            at[i] = Integer.parseInt(ar[i]);
            System.out.println(at[i]);
            bt[i] = Integer.parseInt(br[i]);
            bt1[i]=bt[i];
            System.out.println(bt[i]);
            pro[i]=i+1;
        }
        prepri(pt,at,bt,pro,n);
        
        
    }//GEN-LAST:event_jButton7ActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
       // String name;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
