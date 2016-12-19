package Database;
import java.lang.String;

import Messages.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper implements DBConfig {
    /*
    * 使用MySQL数据源获得数据库连接对象
    * @return：MySQL连接对象，如果获得失败返回null
    */
    public static Connection getConnection() {
        try {
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        } catch (ClassNotFoundException e1) {
            System.out.println("找不到MySQL驱动!");
            e1.printStackTrace();
        }
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection conn;
        try {
            conn = DriverManager.getConnection(url,username,password);
            System.out.print("成功连接到数据库！");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    * 判断指定用户名的用户是否存在
    *
    * @return：如果存在返回true，不存在或者查询失败返回false
    */
    public boolean UserExists(String username) {
        String sql = "select USERNAME from User where USERNAME = '" + username + "';";// 定义查询语句
        //System.out.println(username+"hhhhhhh\n");
        Connection conn = getConnection();// 获得连接
        try {
            Statement stmt = conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);//创建数据对象

            //   List<Object> result = runner.query(conn, sql, rsh);// 获得查询结果
            if (rs.next()) {// 如果列表中存在数据
                rs.close();
                stmt.close();
                conn.close();
                return true;// 返回true
            } else {// 如果列表中不存在数据
                rs.close();
                stmt.close();
                conn.close();
                return false;// 返false
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;// 如果发生异常返回false
    }
    /*
    * 验证用户名和密码是否正确 使用Commons Lang组件转义字符串避免SQL注入
    *
    * @return：如果正确返回true，错误返回false
    */

    public boolean PasswordCheck(String username, String password) {
        String sql = "select PASSWORD from User where USERNAME ='" + username + "'and PASSWORD = '"+ password +"';";// 定义查询语句
        Connection conn = getConnection();// 获得连接
        try {
            Statement stmt = conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);//创建数据对象
            if (rs.next()) {// 如果列表中存在数据
                rs.close();
                stmt.close();
                conn.close();
                return true;// 返回true
            } else {// 如果列表中不存在数据
                rs.close();
                stmt.close();
                conn.close();
                return false;// 返false
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;// 如果发生异常返回false
    }
    /*
    * 保存用户输入的注册信息
    *
    * @return：如果保存成功返回true，保存失败返回false
    */

    public boolean UserRegister(User user) {
        String sql = "insert into User(USERNAME,PASSWORD,EMAIL) values (?,?,?);";// 定义查询语句
        String sql1 = "insert into `likenum` (USERNAME,JINSHAN,BIYING,YOUDAO) values (?,?,?,?);";
        Connection conn = getConnection();// 获得连接
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            //pst.setInt(1,user.getId());
            pst.setString(1,user.getUsername());
            pst.setString(2,user.getPassword());
            pst.setString(3,user.getEmail());
            pst.executeUpdate();
            pst.close();
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setString(1,user.getUsername());
            pst1.setInt(2,0);
            pst1.setInt(3,0);
            pst1.setInt(4,0);
            pst1.executeUpdate();
            pst1.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;// 如果发生异常返回false
    }

    public boolean UserState(String username,boolean state){
        String sql = "update User set ISLOGIN = ? where USERNAME=?";
        Connection conn = getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            if(state==true){
                pst.setInt(1,1);
            }
            else{
                pst.setInt(1,0);
            }
            pst.setString(2,username);
            pst.executeUpdate();
            pst.close();
            conn.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean UserLogout(String username){
        return true;
    }

    public  boolean AddLike(List<String>list){

        String sql = "insert into `like`(USERNAME,WORD,TRANS) values (?,?,?);";
        String sql1;
        if(list.get(2).equals("金山")){
            sql1 = "update `likenum` set JINSHAN = JINSHAN+1 where USERNAME ='"+list.get(0)+"';";
        }
        else if(list.get(2).equals("必应")){
            sql1 =  "update `likenum` set BIYING = BIYING+1 where USERNAME ='"+list.get(0)+"';";
        }
        else{
            sql1 = "update `likenum` set YOUDAO = BIYING+1 where USERNAME ='"+list.get(0)+"';";
        }

        Connection conn = getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst.setString(1,list.get(0));
            pst.setString(2,list.get(1));
            pst.setString(3,list.get(2));
            pst.executeUpdate();
            pst1.executeUpdate();
            pst.close();
            pst1.close();
            conn.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean LikeExists(List<String>list){
        String sql = "select USERNAME from `like` where USERNAME ='"+list.get(0)+"'AND WORD = '"+list.get(1)+"' AND TRANS='"+list.get(2)+"';";// 定义查询语句
        Connection conn = getConnection();// 获得连接
        try {
            Statement stmt = conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);//创建数据对象

            if (rs.next()) {// 如果列表中存在数据
                rs.close();
                stmt.close();
                conn.close();
                return true;// 返回true
            } else {// 如果列表中不存在数据
                rs.close();
                stmt.close();
                conn.close();
                return false;// 返false
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;// 如果发生异常返回false
    }

    public String GetLikeNum(String name,String trans){
        String sql;
        String num;
        if(trans.equals("金山")){
            sql = "select JINSHAN from `likenum` where USERNAME ='"+name+"';";
        }
        else if(trans.equals("必应")){
            sql = "select BIYING from `likenum` where USERNAME ='"+name+"';";
        }
        else{
            sql = "select YOUDAO from `likenum` where USERNAME ='"+name+"';";
        }

        Connection conn = getConnection();
        try {
            Statement stmt = conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);//创建数据对象
            if (rs.next()) {// 如果列表中存在数据
                num=rs.getString(1);
                rs.close();
                stmt.close();
                conn.close();
                return num;
            } else {// 如果列表中不存在数据
                rs.close();
                stmt.close();
                conn.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;// 如果发生异常返回fals

    }

    public List<String> GetOnUser(){
        List<String> list = null;
        String sql = "select USERNAME from User where ISLOGIN = 1";
        Connection conn = getConnection();
        try {
            list = new ArrayList<String>();
            Statement stmt = conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {// 如果列表中存在数据
                list.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> GetOffUser(){
        List<String> list = null;
        String sql = "select USERNAME from User where ISLOGIN = 0";
        Connection conn = getConnection();
        try {
            list = new ArrayList<String>();
            Statement stmt = conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {// 如果列表中存在数据
                list.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> GetMessage(String username){
        List<String> list = null;
        String sql = "Select * from `Message` where RNAME = '"+username+"';";
        Connection conn = getConnection();
        try {
            list = new ArrayList<String>();
            Statement stmt = conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {// 如果列表中存在数据
                list.add(rs.getString(1));
                list.add(rs.getString(3));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean SendMessage(String sname,String rname,String content){
        String sql = "insert into `message`(SNAME,RNAME,CONTENT) values (?,?,?);";
        Connection conn = getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,sname);
            pst.setString(2,rname);
            pst.setString(3,content);
            pst.executeUpdate();
            pst.close();
            conn.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean AddWord(String name,String word,String BE,String AE,String Content){
        String sql = "insert into `book`(USERNAME,WORD,BE,AE,TRANS) values (?,?,?,?,?);";
        Connection conn = getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setString(2,word);
            pst.setString(3,BE);
            pst.setString(4,AE);
            pst.setString(5,Content);
            pst.executeUpdate();
            pst.close();
            conn.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean DeleteWord(String name,String word){
        String sql = "delete from book where username ='"+name+"'AND word ='"+word+"';";
        Connection conn = getConnection();
        try {
            Statement stmt=conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<String> GetBook(String name){
        List<String> list = null;
        Connection conn = getConnection();
        String sql = "select * from `book` where USERNAME ='"+name+"';";
        try{
            list = new ArrayList<String>();
            Statement stmt = conn.createStatement(); //创建Statement对象
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {// 如果列表中存在数据
                list.add(rs.getString(2));
                list.add(rs.getString(3));
                list.add(rs.getString(4));
                list.add(rs.getString(5));
                list.add("|");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public void TestInsert(){
        String sql = "insert into `like`(USERNAME,WORD,TRANS) values (?,?,?);";
        Connection conn = getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,"Bobo");
            pst.setString(2,"good");
            pst.setString(3,"金山");
            pst.executeUpdate();
            pst.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



}