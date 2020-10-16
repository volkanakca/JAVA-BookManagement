
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Operation {
    Connection con=null;
    Statement sta=null;
    PreparedStatement psta=null;
    public int bookCount(){
    int label=0;
    String sorgu ="SELECT COUNT(*) FROM books_database";
        try {
            sta=con.createStatement();
            ResultSet rs=sta.executeQuery(sorgu);
            rs.next();
            label=rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } return label;
    }
    
    public void bookDelete(int id){
    String sorgu="DELETE FROM books_database Where id=?";
        try {
            psta=con.prepareCall(sorgu);
            psta.setInt(1,id);
            psta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void bookUpdate(int id,String new_name,String new_writer,String new_type,String new_publisher){
        String sorgu="UPDATE books_database SET book_name=?,book_writer=?,book_type=?,book_publisher=? WHERE id=?";
        try {
            psta=con.prepareStatement(sorgu);
            psta.setString(1, new_name);
            psta.setString(2, new_writer);
            psta.setString(3, new_type);
            psta.setString(4, new_publisher);
            psta.setInt(5,id);
            psta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void bookAdd(String name,String writer,String type,String publisher){
    String sorgu="Insert Into books_database( book_name, book_writer, book_type, book_publisher) VALUES (?,?,?,?)";
        try {
            psta=con.prepareStatement(sorgu);
            psta.setString(1, name);
            psta.setString(2, writer);
            psta.setString(3, type);
            psta.setString(4, publisher);
            psta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Book> BookCome(){
        ArrayList<Book> list=new ArrayList<Book>();
        String sorgu="Select * from books_database";
        try {
            sta=con.createStatement();
            ResultSet rs=sta.executeQuery(sorgu);
            while(rs.next()){
            int id=rs.getInt("id");
            String book_name=rs.getString("book_name");
            String book_writer=rs.getString("book_writer");
            String book_type=rs.getString("book_type");
            String book_publisher=rs.getString("book_publisher");
            list.add(new Book(id,book_name,book_writer,book_type,book_publisher));
            
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    
    }
    //database bilgileri
    public boolean Login(String id,String password){
       String sorgu="Select * from admin where id=? and password=?";
        try {
            psta=con.prepareStatement(sorgu);
            psta.setString(1,id);
            psta.setString(2, password);
            ResultSet rs=psta.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
    }
   

    public Operation() {
        String url="jdbc:mysql://"+DataBase.host+":"+DataBase.port+"/"+DataBase.db_name;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(url,DataBase.id,DataBase.password);
            System.out.println("Veritabanı bağlandı.");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver çalışmadı");
        } catch (SQLException ex) {
            Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("bağlantı olmadı");
        }
        
    }
    public static void main(String[] args) {
        Operation op=new Operation();
    }
    
    
}
