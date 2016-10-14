package com.yimayhd.sellerAdmin.validate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 
 * @author wzf
 *
 */
public class ValidateCode {

    // 图片的宽度。
    private int width = 160;
    // 图片的高度。
    private int height = 40;
    // 验证码字符个数
    private int codeCount = 5;
    // 验证码干扰线数
    private int lineCount = 100;
    // 验证码图片Buffer
    private BufferedImage buffImg = null;

    public ValidateCode(String code) {
        this.createCode(code);
    }

    /**
     * @param width     图片宽
     * @param height    图片高
     * @param codeCount 字符个数
     * @param lineCount 干扰线条数
     */
    public ValidateCode(String code, int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.createCode(code);
    }

    public void createCode(String code) {
    	if( code == null ){
    		return ;
    	}
        int charWidth = 0;
        // 字体的高度
        int fontWeight = 30;
        int red = 0, green = 0, blue = 0;

        charWidth = width / (codeCount + 2);// 每个字符的宽度

        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体
        g.setFont(new Font("Dialog", Font.PLAIN, fontWeight));

        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width);
            int ye = ys + random.nextInt(height);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        int length = code.length() ;
        int charFontColor = 96 ;
        int colorStart = 24 ;
        int charMargin = 16 ;
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < length; i++) {
            String strRand = code.substring(i, i+1) ;
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = colorStart + random.nextInt(charFontColor);
            green = colorStart +  random.nextInt(charFontColor);
            blue =  colorStart + random.nextInt(charFontColor);
            int positionX = 32+ i * charWidth + random.nextInt(charMargin) ;
            int positionY = height - random.nextInt(height/4) ;
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, positionX, positionY);
        }
    }
    
    public void write(String path) throws IOException {
        OutputStream sos = new FileOutputStream(path);
        this.write(sos);
    }

    public void write(OutputStream sos) throws IOException {
        ImageIO.write(buffImg, "png", sos);
        sos.flush();
        sos.close();
    }

    public BufferedImage getBuffImg() {
        return buffImg;
    }

}
