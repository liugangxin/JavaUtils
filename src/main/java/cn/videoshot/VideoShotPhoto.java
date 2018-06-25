package cn.videoshot;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * 最主要是参数问题，声音转码，可ffmpeg.getAbsolutePath() + " -i " + wav.getAbsolutePath()+ " -ab 128 -ar 44100 " + mp3.getAbsolutePath();
 * @author liugangxin
 *
 */
public class VideoShotPhoto {

	public static void main(String[] args) {
		String ffmpegPath = "D:\\JAVA\\IDE\\workspace64\\JavaStudy\\tools\\ffmpeg.exe";
		String videoRealPath = "D:\\JAVA\\IMG_1358.MOV";// 视频文件
		String imageRealPath = "D:\\JAVA\\IDE\\workspace64\\JavaStudy\\tools\\atest.jpg";// 截图的路径（输出路径）
		// 第一种方式
		processImg1(videoRealPath, ffmpegPath, imageRealPath);
		// 第二种方式
		processImg2(videoRealPath, ffmpegPath, imageRealPath);
	}

	public static void processImg1(String veido_path, String ffmpeg_path, String imageRealPath) {
		// 文件命名
		Calendar c = Calendar.getInstance();
		String savename = String.valueOf(c.getTimeInMillis()) + Math.round(Math.random() * 100000);
		List<String> commend = new ArrayList<String>();
		commend.add(ffmpeg_path);
		commend.add("-i");
		commend.add(veido_path);
		commend.add("-ab");
		commend.add("56");
		commend.add("-ar");
		commend.add("22050");
		commend.add("-qscale");
		commend.add("8");
		commend.add("-r");
		commend.add("15");
		commend.add("-s");
		commend.add("600x500");
		commend.add(imageRealPath);

		try {
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			builder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean processImg2(String veido_path, String ffmpeg_path, String imageRealPath) {
		File file = new File(veido_path);
		if (!file.exists()) {
			System.err.println("路径[" + veido_path + "]对应的视频文件不存在!");
			return false;
		}
		try {
			Runtime.getRuntime().exec(
					ffmpeg_path + " -i " + veido_path + " -ss 3 -vframes 1 -r 1 -ac 1 -ab 2 -s 600x500 -f image2 "
							+ imageRealPath);
			System.out.println("截取成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
