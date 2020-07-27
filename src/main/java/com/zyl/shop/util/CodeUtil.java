package com.zyl.shop.util;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
@Component("codeUtil")
public class CodeUtil {
    public String getRamdomCode() {
        Random rd = new Random();
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int flag = rd.nextInt(3);
            switch (flag) {
                case 0:
                    sbf.append(rd.nextInt(10));
                    break;
                case 1:
                    sbf.append((char) (rd.nextInt(26) + 65));
                    break;
                case 2:
                    sbf.append((char) (rd.nextInt(26) + 97));
                    break;
                default:
                    break;
            }
        }
        return sbf.toString();
    }

    private Color getRandomColor(int start, int bound) {
        Random rd = new Random();
        int r = start + rd.nextInt(bound);
        int g = start + rd.nextInt(bound);
        int b = start + rd.nextInt(bound);
        return new Color(r, g, b);
    }

    public BufferedImage getCodeImage(String code, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        // 背景色
        g.setColor(getRandomColor(225, 30));
        g.fillRect(0, 0, width, height);

        // 随机的干扰线
        Random rd = new Random();
        for (int i = 0; i < 20; i++) {
            g.setColor(getRandomColor(150, 40));
            int x1 = rd.nextInt(width);
            int x2 = rd.nextInt(width);
            int y1 = rd.nextInt(height);
            int y2 = rd.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
        // 文字
        g.setFont(new Font("Courier New", Font.ITALIC, 20));
        char[] codes = code.toCharArray();
        for (int i = 0; i < codes.length; i++) {
            // 每个字的颜色
            g.setColor(getRandomColor(50, 80));
            g.drawString(String.valueOf(codes[i]), 10 + 24 * i, 20);
        }
        g.dispose();
        return image;
    }
}
