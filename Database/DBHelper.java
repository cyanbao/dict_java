package Database;

import Database.DBConfig;

import java.sql.*;

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
    public boolean exists(String username) {
        String sql = "select id from user_info where username = '" + username + "';";// 定义查询语句
        Connection conn = getConnection();// 获得连接\
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

    public boolean check(String username, String password) {
        String sql = "select password from user_info where username ='" + username + "'and password = '"+ password +"';";// 定义查询语句
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

    public boolean save(User user) {
        String sql = "insert into user_info(username,password,email) values (?,?,?);";// 定义查询语句
        Connection conn = getConnection();// 获得连接
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            //pst.setInt(1,user.getId());
            pst.setString(1,user.getUsername());
            pst.setString(2,user.getPassword());
            pst.setString(3,user.getEmail());
            pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;// 如果发生异常返回false
    }
}