package cn.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * @author liugangxin
 * @date 2018年6月13日 上午9:35:56
 * @Description 合成图片
 */
public class ImageUtils {

	public static boolean compose(File target, String poster, String headimgStr, String codeUrl, Integer userId, String userName) {
		Font font = new Font("微软雅黑", Font.PLAIN, 30);// 添加字体的属性设置
		try {
			// 加载背景图片
			BufferedImage imageLocal = ImageIO.read(new URL(poster));
			// 加载用户的二维码
			BufferedImage imageCode = ImageIO.read(new URL(codeUrl));
			// 加载用户的头像
			BufferedImage headimg = ImageIO.read(new URL(headimgStr));
			// 以本地图片为模板
			Graphics2D g = imageLocal.createGraphics();
			int height = imageLocal.getHeight();
			int width = imageLocal.getWidth();
			
			// 在模板上添加用户二维码(地址,左边距,上边距,图片宽度,图片高度,未知)
			int finalCodeWidth = 120;
			g.drawImage(imageCode, 42, height - (20 + finalCodeWidth), finalCodeWidth, finalCodeWidth, null);
			
			//先将头像裁剪成椭圆，再合成
			int offsetHeadimgWidth = 230;//(width - 150) / 2
			int offsetHeadimgHeight = 165;
			int finalHeadimgWidth = 105;
			int finalHeadimgHeight = 105;
	        Ellipse2D.Double shape = new Ellipse2D.Double(offsetHeadimgWidth, offsetHeadimgHeight, finalHeadimgWidth, finalHeadimgHeight);
	        g.setClip(shape);
	        // 在模板上添加用户头像(地址,左边距,上边距,图片宽度,图片高度,未知)
			g.drawImage(headimg, offsetHeadimgWidth, offsetHeadimgHeight, finalHeadimgWidth, finalHeadimgHeight, null);
			
			// 设置文本样式
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			// 计算文字长度，计算居中的x点坐标
			FontMetrics fm = g.getFontMetrics(font);
			int textWidth = fm.stringWidth(userName);
			int widthX = (width - textWidth) / 2;
			// 添加用户名称,表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
			g.drawString(userName, widthX, 400);
			
			// 完成模板修改
			g.dispose();
			// 获取新文件的地址
			File outputfile = target;
			// 生成新的合成过的用户二维码并写入新图片,formatName必须是png，不然合成图片会有红色蒙层
			ImageIO.write(imageLocal, "png", outputfile);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean compose2(File target, String poster, String headimgStr, String codeUrl, Integer userId, String userName) {
		Font font = new Font("微软雅黑", Font.PLAIN, 30);// 添加字体的属性设置
		try {
			// 加载背景图片
			BufferedImage imageLocal = ImageIO.read(new URL(poster));
//			BufferedImage imageLocal = ImageIO.read(new File(poster));
			
			// 加载用户的二维码
			BufferedImage imageCode = ImageIO.read(new URL(codeUrl));
//			BufferedImage imageCode = ImageIO.read(new File(codeUrl));
			
			// 加载用户的头像
			BufferedImage headimg = ImageIO.read(new URL(headimgStr));
//			BufferedImage headimg = ImageIO.read(new File(headimgStr));
			
			// 以本地图片为模板
			Graphics2D g = imageLocal.createGraphics();
			int height = imageLocal.getHeight();
			int width = imageLocal.getWidth();
			
			// 在模板上添加用户二维码(地址,左边距,上边距,图片宽度,图片高度,未知)
//			g.drawImage(imageCode, 575, imageLocal.getHeight() - 500, 350, 350, null);
			g.drawImage(imageCode, 52, 1170, 134, 134, null);
			
			//先将头像裁剪成椭圆，再合成
			int offsetHeadimgWidth = 310;//(width - 150) / 2
			int offsetHeadimgHeight = 220;
			int finalHeadimgWidth = 130;
			int finalHeadimgHeight = 130;
	        Ellipse2D.Double shape = new Ellipse2D.Double(offsetHeadimgWidth, offsetHeadimgHeight, finalHeadimgWidth, finalHeadimgHeight);
	        g.setClip(shape);
	        // 在模板上添加用户头像(地址,左边距,上边距,图片宽度,图片高度,未知)
			g.drawImage(headimg, offsetHeadimgWidth, offsetHeadimgHeight, finalHeadimgWidth, finalHeadimgHeight, null);
			
			// 设置文本样式
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			// 计算文字长度，计算居中的x点坐标
			FontMetrics fm = g.getFontMetrics(font);
			int textWidth = fm.stringWidth(userName);
			int widthX = (width - textWidth) / 2;
			// 添加用户名称,表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
//			g.drawString(newUserName, 620, imageLocal.getHeight() - 530);
			g.drawString(userName, widthX, 400);
			
			// 完成模板修改
			g.dispose();
			// 获取新文件的地址
			File outputfile = target;
			// 生成新的合成过的用户二维码并写入新图片,formatName必须是png，不然合成图片会有红色蒙层
			ImageIO.write(imageLocal, "png", outputfile);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		// 返回给页面的图片地址(因为绝对路径无法访问)
		// imgName = Constants.PROJECT_URL + "codeImg/" + userId + ".png";
	}

	public static void main(String[] args) {
		String imgName = "C:/Users/Administrator/Desktop/常用/666.jpg";
		String poster = "C:/Users/Administrator/Desktop/常用/poster.png";
		String headimg = "C:/Users/Administrator/Desktop/常用/headimg.png";
		String codeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQEf8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluL66gfdU0RldhVF8xYXhMUTFyMUwAAgQhdStbAwSAOgkA";
		compose(new File(imgName), poster, headimg, codeUrl, 0, "哈哈哈哈哈哈哈哈哈哈哈哈");
		
//		System.out.println(getFileType("dfsf.s"));
//		System.out.println(getFileType("dfsf.jpg"));
//		System.out.println(getFileType("dfsf.jp"));
//		System.out.println(getFileType("dfsf.jpG"));
//		System.out.println(getFileType("dfsf.gif"));

	}
}
