package Database;

/**
 * Created by Cyan on 2016/11/25.
 */
public interface DBConfig{
    //String databaseName = "dict_db";// 数据库名称
    String username = "root";// 数据库用户名
    String password = "";// 数据库密码
    String url="jdbc:mysql://localhost:3306/dict_db";    //JDBC的URL
}
