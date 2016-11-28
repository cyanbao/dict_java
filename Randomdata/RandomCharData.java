package Randomdata;
import java.util.Random;
/**
 * Created by Cyan on 2016/11/26.
 */
public class RandomCharData {
   /* public static void main(String[] args){
        for(int i=0;i<100;++i)
            createRandomCharData(4);
    }*/
    public String createRandomCharData(int length){
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//随机用以下三个随机生成器
        Random rand_data=new Random();
        int data=0;
        for(int i=0;i<length;++i) {
            int index=rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch(index) {
                case 0:
                    data=rand_data.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data=rand_data.nextInt(26)+65;//保证只会产生ASCII65~90之间的整数
                    sb.append((char)data);
                    break;
                case 2:
                    data=rand_data.nextInt(26)+97;//保证只会产生97~122之间的整数
                    sb.append((char)data);
                    break;
            }
        }
        String result=sb.toString();
        return result;
        //System.out.println(result);
    }

}
