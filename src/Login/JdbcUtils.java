package Login;

import java.sql.*;

public class JdbcUtils {
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    public static void Jief(){
        Connection conn=getConnection();
        String sql="select * from paperdone where paperid=1";
        try {
            PreparedStatement ps1=conn.prepareStatement(sql);
            ResultSet rs1=ps1.executeQuery();
            rs1.next();
            int score=0;
            for(int i=1;i<=10;i++){
                score+=rs1.getInt(i*2+2);
            }
            String str="update paperdone set scores=? where paperid=1";
            ps1=conn.prepareStatement(str);
            ps1.setInt(1,score);
            ps1.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void getMultiplechoice(){
        String str1="select * from paperreleased where paperid=1";
        String str2="select * from multiplechoice_db where qid=?";
        String str3="select * from paperdone where paperid=1";
        Connection conn=getConnection();
        try {
            PreparedStatement ps1=conn.prepareStatement(str1);
            PreparedStatement ps2=conn.prepareStatement(str2);
            PreparedStatement ps3=conn.prepareStatement(str3);
            for(int i=1;i<=8;i++){
                ResultSet rs1=ps1.executeQuery();
                while(rs1.next()){
                    String st=rs1.getString(i+1);
                    ps2.setString(1,st);
                    ResultSet rs2=ps2.executeQuery();
                    ResultSet rs3=ps3.executeQuery();
                    rs2.next();rs3.next();
                    if(rs3.getString(i*2+1)==rs2.getString("correction")){
                        addScores(i,"5");
                    }else addScores(i,"0");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private static Connection getConnection(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/olexsys?characterEncoding=UTF-8";
            String user="root";
            String pwd="123456";
            conn=DriverManager.getConnection(url,user,pwd);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static void release(Connection conn,PreparedStatement st,ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            rs=null;
        }
        if(st!=null){
            try{
                st.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try{
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void addScores(int qid,String scores){
        Connection conn=getConnection();
        String sql="update paperdone set point?=? where paperid=1";
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,qid);
            ps.setString(2, scores);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            release(conn,ps,rs);
        }
    }
    public static String getStuAns(int id){
        Connection conn=getConnection();
        String sql="select * from paperdone where paperid=1";
        String str = null;
        try{
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                str=rs.getString("ans"+String.valueOf(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            release(conn,ps,rs);
        }
        return str;
    }
    public static int getQid2(String id) throws SQLException {
        Connection conn=getConnection();
        String sql="select * from paperreleased where paperid=?";
        ps=conn.prepareStatement(sql);
        ps.setString(1,id);
        rs=ps.executeQuery();
        rs.next();
        return rs.getInt("qid9");
    }
    public static int getQid3(String id) throws SQLException {
        Connection conn=getConnection();
        String sql="select * from paperreleased where paperid=?";
        ps=conn.prepareStatement(sql);
        ps.setString(1,id);
        rs=ps.executeQuery();
        rs.next();
        return rs.getInt("qid10");
    }
    public static String getStaAns(int id){
        Connection conn=getConnection();
        String sql="select * from shortanswer_db where qid=?";
        String str1="select * from paperreleased where paperid=1";
        String str = null;
        try{
            ps=conn.prepareStatement(sql);
            PreparedStatement ps1=conn.prepareStatement(str1);
            ResultSet rs1=ps1.executeQuery();
            rs1.next();
            ps.setString(1, rs1.getString(id+1));
            rs=ps.executeQuery();
            while(rs.next()){
                str=rs.getString("qans");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(conn,ps,rs);
        }
        return str;
    }
}