package test.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ClientAbortMethod {
	// 链表list用来存储相关的网页链接

	public static void downLoad(String url) {
		String source = null;// 存储网页源文件
		source = getSource(url);
		// 抽取每个网页的正文内容
		System.out.println(source);
	}

	// 抽取网页的源文件
	private static String getSource(String link) {
		String charset = "GBK";// 网页默认编码设置为GBK
		URLConnection connection = null;
		try {
			URL url = new URL(link);
			// 打开连接
			connection = url.openConnection();
			// 如果网页无法打开
			if (null == connection)
				return null;

			// 下载裸源文件
			byte[] buf = new byte[2048];
			InputStream is = null;
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			int count = 0;

			try {
				is = connection.getInputStream();
				while ((count = is.read(buf)) >= 0) {
					os.write(buf, 0, count);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (os.size() == 0) {
					return null;
				}
			} finally {
				try {
					is.close();
				} catch (Exception e) {
				}
			}

			// 获取网页的编码格式
			String content = os.toString();
			int fromIndex = content.indexOf("charset=");
			charset = content.substring(fromIndex + 8, content.indexOf("\"",
					fromIndex));

			return new String(os.toByteArray(), charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
  public static void main(String[] args) {
	  ClientAbortMethod.downLoad("http://www.dailyfx.com.hk/calendar/index.html");
}
}