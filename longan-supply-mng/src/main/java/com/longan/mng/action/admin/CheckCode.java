package com.longan.mng.action.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.mng.action.common.BaseController;
import com.longan.mng.utils.CheckCodeContext;

/**
 * 图片验证码的切换
 */
@Controller
@RequestMapping("admin/checkCode")
public class CheckCode extends BaseController {
    private static int WIDTH = 60;
    private static int HEIGHT = 20;

    @RequestMapping(method = RequestMethod.GET)
    public void getCode(HttpServletRequest request, HttpServletResponse response) {
	response.setContentType("image/jpeg");
	ServletOutputStream sos = null;
	char[] rands = null;
	ByteArrayOutputStream bos = null;
	try {
	    sos = response.getOutputStream();
	    response.setHeader("Pragma", "No-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = image.createGraphics();
	    rands = generateCheckCode();
	    drawBackground(g);
	    drawRands(g, rands);
	    g.dispose();
	    bos = new ByteArrayOutputStream();
	    ImageIO.write(image, "JPEG", bos);
	    byte[] buf = bos.toByteArray();
	    response.setContentLength(buf.length);
	    sos.write(buf);
	} catch (Exception e) {
	    logger.error("checkCode create error", e);
	} finally {
	    try {
		if (bos != null) {
		    bos.close();
		}
		if (sos != null) {
		    sos.close();
		}
	    } catch (IOException e) {
		logger.error("checkCode close stream error", e);
	    }
	}
	CheckCodeContext.map.put(getRemoteIp(request), new String(rands));
    }

    private char[] generateCheckCode() {
	String chars = "abcdefghijklmnopqrstuvwxyz";
	char[] rands = new char[4];
	for (int i = 0; i < 4; i++) {
	    int rand = (int) (Math.random() * 26);
	    rands[i] = chars.charAt(rand);
	}
	return rands;
    }

    private void drawRands(Graphics graphics, char[] rands) {
	int fc = 100;
	int bc = 200;
	int r = fc + (int) (Math.random() * (bc - fc));
	int g = fc + (int) (Math.random() * (bc - fc));
	int b = fc + (int) (Math.random() * (bc - fc));
	graphics.setColor(new Color(r, g, b));
	graphics.setFont(new Font(null, Font.CENTER_BASELINE, 18));
	graphics.drawString("" + rands[0], 1 + (int) (Math.random() * 5), 17);
	graphics.drawString("" + rands[1], 16 + (int) (Math.random() * 5), 15);
	graphics.drawString("" + rands[2], 31 + (int) (Math.random() * 5), 18);
	graphics.drawString("" + rands[3], 46 + (int) (Math.random() * 5), 16);
    }

    private void drawBackground(Graphics2D g) {
	g.setColor(new Color(231, 252, 253));
	g.fillRect(0, 0, WIDTH, HEIGHT);
	for (int i = 0; i < 120; i++) {
	    int x = (int) (Math.random() * WIDTH);
	    int y = (int) (Math.random() * HEIGHT);
	    int red = (int) (Math.random() * 255);
	    int green = (int) (Math.random() * 255);
	    int blue = (int) (Math.random() * 255);
	    g.setColor(new Color(red, green, blue));
	    g.drawOval(x, y, 1, 0);
	}
    }
}
