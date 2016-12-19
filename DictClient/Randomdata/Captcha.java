package Randomdata;

/**
 * Created by Cyan on 2016/11/26.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 绘制验证码图像
 *
 */
public class Captcha {
    private int width = 80; // 图像宽度
    private int height = 30;// 图像高度
    private int length = 4;// 验证码字符长度
    // 随机生成验证码基础字符串
    private final String baseCharacters = "abcdefghijkmnpqrstuvwxyABCDEFGHIJKLMNPQRSTUVWXYZ23456789";
    private String ValidateStr;

    /**
     * 获取验证码字符串
     *
     *@return 验证码字符串
     **/
    public String getValidateStr(){
        return ValidateStr;
    }
    /**
     * 获取验证码图像
     *
     * @return 验证码图像
     */
    public BufferedImage getCaptchaImage() {
        BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);// 创建图像缓冲区
        Graphics g = img.getGraphics(); // 获取图像上下文（画笔）
        g.setColor(getRandomColor(200, 255));// 设定图像背景色，填充背景矩形
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK); // 画边框
        g.drawRect(0, 0, width - 1, height - 1);

        /* 生成随机验证码 */
        int len = baseCharacters.length(); // 基础字符串长度
        g.setFont(new Font("楷体", Font.HANGING_BASELINE, 24)); // 设置验证码字体
        Random rand = new Random(); // 循环生成验证码各字符
        for (int i = 0; i < length; i++) {
            // 随机生成验证码中单个字符
            String randStr = String.valueOf(baseCharacters.charAt(rand.nextInt(len)));
            if(ValidateStr==null)
                ValidateStr=randStr;
            else
                ValidateStr+=randStr;
            // 单个字符绘制宽度
            int width = this.width / this.length;
            // 当前字符绘制原点
            int x = width * i;
            int y = this.height / 2 + rand.nextInt(this.height / 3);
            /* 将该字符画到图像中 */
            drawString(g, x, y, randStr);
        }
        // 画干扰线
        drawLine(g, 10);
        // 释放图像上下文（画笔）
        g.dispose();
        return img;
    }

    /**
     * 画验证码字符串中单个字符
     *
     * @param g
     *            图像上下文
     * @param width
     *            字符所占宽度
     * @param height
     *            字符所占高度
     * @param str
     *            待绘制字符串
     */
    private void drawString(Graphics g, int width, int height, String str) {
        Random rand = new Random();
        // 随机生成字符旋转角度(-30~30度)
        int degree = rand.nextInt(60);
        if (degree > 30)
            degree = 30 - degree;
        // 设置字体颜色
        g.setColor(getRandomColor(0, 80));
        // 转换 Graphics2D
        Graphics2D g2 = (Graphics2D) g.create();
        // 平移原点到图形环境的中心 ,这个方法的作用实际上就是将字符串移动到某一个位置
        g2.translate(width + rand.nextInt(5), height + rand.nextInt(5));
        // 旋转文本
        g2.rotate(degree * Math.PI / 180);
        // 画文本，特别需要注意的是,这里的画笔已经具有了上次指定的一个位置,所以这里指定的其实是一个相对位置
        g2.drawString(str, 0, 0);
    }

    /**
     * 画随机干扰线
     *
     * @param g
     *            画笔（图像上下文）
     * @param count
     *            干扰线条数
     */
    private void drawLine(Graphics g, int count) {
        Random rand = new Random();
        // 循环绘制每条干扰线
        for (int i = 0; i < count; i++) {
            // 设置线条随机颜色
            g.setColor(getRandomColor(180, 200));
            // 生成随机线条起点终点坐标点
            int x1 = rand.nextInt(this.width);
            int y1 = rand.nextInt(this.height);
            int x2 = rand.nextInt(this.width);
            int y2 = rand.nextInt(this.height);
            // 画线条
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 获取随机颜色
     *
     * @param minimum
     *            颜色下限值
     * @param maximum
     *            颜色上限值
     * @return 随机颜色对象
     */
    private Color getRandomColor(int minimum, int maximum) {
        if (minimum > maximum) {
            int tmp = minimum;
            minimum = maximum;
            maximum = tmp;
        }
        if (maximum > 255)
            maximum = 255;
        if (minimum < 0)
            minimum = 0;

        int r = minimum + (int) (Math.random() * (maximum - minimum));
        int g = minimum + (int) (Math.random() * (maximum - minimum));
        int b = minimum + (int) (Math.random() * (maximum - minimum));

        return new Color(r, g, b);
    }


    /*public static void main(String[] args) {
        Captcha captcha = new Captcha();
        JFrame frame = new JFrame("验证码");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lbl = new JLabel(new ImageIcon(captcha.getCaptchaImage()));
        frame.add(lbl);
        frame.setVisible(true);
    }*/
}