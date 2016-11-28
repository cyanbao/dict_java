package Database;
import Database.DBHelper;
public class test extends DBHelper {
    public static void main(String []args){
        DBHelper db = new DBHelper();
       /* boolean res = db.exists("Simon");
        System.out.print(res);
        res = db.exists("yoyo");
        System.out.print(res);
        res = db.check("Simon","aaa");
        System.out.print(res);
        res = db.check("Simon","xxxxx");
        System.out.print(res);*/
        User user = new User("hhh","hdjsdhjk","jamiel@126.com");
        db.save(user);


    }
}
