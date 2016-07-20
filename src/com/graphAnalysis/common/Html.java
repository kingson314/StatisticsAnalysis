//package com.graphAnalysis.common;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.htmlparser.Node;
//import org.htmlparser.NodeFilter;
//import org.htmlparser.Parser;
//import org.htmlparser.tags.LinkTag;
//import org.htmlparser.util.NodeList;
//import org.htmlparser.util.ParserException;
//
//import com.yao.http.HttpRequester;
//import com.yao.http.HttpRespons;
//
//public class Html {
//	public static void main(String[] args) throws ParserException {
//		// String url="http://www.dailyfx.com.hk/calendar/index.html";
//		// url="http://www.baidu.com";
//		// new Html().downLoad(url);
//		// new Html().html(url);
//	}
//
//	public void downLoad(String url) {
//		String source = null;// 存储网页源文件
//		source = getSource(url);
//		source = HtmlToTextGb2312(source);
//		// 抽取每个网页的正文内容
//		System.out.println(source);
//	}
//
//	// 抽取网页的源文件
//	private String getSource(String link) {
//		String charset = "GBK";// 网页默认编码设置为GBK
//		URLConnection connection = null;
//		try {
//			URL url = new URL(link);
//			// 打开连接
//			connection = url.openConnection();
//			// 如果网页无法打开
//			if (null == connection)
//				return null;
//
//			// 下载裸源文件
//			byte[] buf = new byte[2048];
//			InputStream is = null;
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			int count = 0;
//
//			try {
//				is = connection.getInputStream();
//				while ((count = is.read(buf)) >= 0) {
//					os.write(buf, 0, count);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				if (os.size() == 0) {
//					return null;
//				}
//			} finally {
//				try {
//					is.close();
//				} catch (Exception e) {
//				}
//			}
//
//			// 获取网页的编码格式
//			String content = os.toString();
//			int fromIndex = content.indexOf("charset=");
//			charset = content.substring(fromIndex + 8, content.indexOf("\"", fromIndex));
//
//			return new String(os.toByteArray(), charset);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public String HtmlToTextGb2312(String inputString) {
//		String htmlStr = inputString; // 含html标签的字符串
//		String textStr = "";
//		Pattern p_script;
//		Matcher m_script;
//		Pattern p_style;
//		Matcher m_style;
//		Pattern p_html;
//		Matcher m_html;
//		Pattern p_houhtml;
//		Matcher m_houhtml;
//		Pattern p_spe;
//		Matcher m_spe;
//		Pattern p_blank;
//		Matcher m_blank;
//		Pattern p_table;
//		Matcher m_table;
//		Pattern p_enter;
//		Matcher m_enter;
//
//		try {
//			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
//			// 定义script的正则表达式.
//			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
//			// 定义style的正则表达式.
//			String regEx_html = "<[^>]+>";
//			// 定义HTML标签的正则表达式
//			String regEx_houhtml = "/[^>]+>";
//			// 定义HTML标签的正则表达式
//			String regEx_spe = "\\&[^;]+;";
//			// 定义特殊符号的正则表达式
//			String regEx_blank = " +";
//			// 定义多个空格的正则表达式
//			String regEx_table = "\t+";
//			// 定义多个制表符的正则表达式
//			String regEx_enter = "\n+";
//			// 定义多个回车的正则表达式
//
//			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
//			m_script = p_script.matcher(htmlStr);
//			htmlStr = m_script.replaceAll(""); // 过滤script标签
//
//			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
//			m_style = p_style.matcher(htmlStr);
//			htmlStr = m_style.replaceAll(""); // 过滤style标签
//
//			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
//			m_html = p_html.matcher(htmlStr);
//			htmlStr = m_html.replaceAll(""); // 过滤html标签
//
//			p_houhtml = Pattern.compile(regEx_houhtml, Pattern.CASE_INSENSITIVE);
//			m_houhtml = p_houhtml.matcher(htmlStr);
//			htmlStr = m_houhtml.replaceAll(""); // 过滤html标签
//
//			p_spe = Pattern.compile(regEx_spe, Pattern.CASE_INSENSITIVE);
//			m_spe = p_spe.matcher(htmlStr);
//			htmlStr = m_spe.replaceAll(""); // 过滤特殊符号
//
//			p_blank = Pattern.compile(regEx_blank, Pattern.CASE_INSENSITIVE);
//			m_blank = p_blank.matcher(htmlStr);
//			htmlStr = m_blank.replaceAll(" "); // 过滤过多的空格
//
//			p_table = Pattern.compile(regEx_table, Pattern.CASE_INSENSITIVE);
//			m_table = p_table.matcher(htmlStr);
//			htmlStr = m_table.replaceAll(" "); // 过滤过多的制表符
//
//			p_enter = Pattern.compile(regEx_enter, Pattern.CASE_INSENSITIVE);
//			m_enter = p_enter.matcher(htmlStr);
//			htmlStr = m_enter.replaceAll(" "); // 过滤过多的制表符
//
//			textStr = htmlStr;
//
//		} catch (Exception e) {
//			System.err.println("Html2Text: " + e.getMessage());
//		}
//
//		return textStr;// 返回文本字符串
//	}
//
//	public void html(String url) {
//		try {
//			/*
//			 * 首先我们先使用HttpRequester类和HttpRespons类获得一个HTTP请求中的数据（HTML文档）。
//			 * 可以从(http://download.csdn.net/source/321516)中下载htmlloader，该库中有上述类；
//			 * 或从我的《JAVA发送HTTP请求，返回HTTP响应内容，实例及应用》一文中摘取上述两JAVA类的代码。
//			 * htmlparse可以从(http://download.csdn.net/source/321507)中下载
//			 */
//			HttpRequester request = new HttpRequester();
//			HttpRespons hr = request.sendGet(url);
//
//			Parser parser = Parser.createParser(hr.getContent(), hr.getContentEncoding());
//			parser.setEncoding("utf-8");
//			try {
//				// 通过过滤器过滤出<A>标签
//				NodeList nodeList = parser.extractAllNodesThatMatch(new NodeFilter() {
//					private static final long serialVersionUID = 1L;
//					// 实现该方法,用以过滤标签
//					public boolean accept(Node node) {
//						if (node instanceof LinkTag)// <A>标记
//							return true;
//						return false;
//					}
//				});
//				// 打印
//				for (int i = 0; i < nodeList.size(); i++) {
//					LinkTag n = (LinkTag) nodeList.elementAt(i);
//					System.out.print((n.getStringText()) + "\n");
//					// System.out.println(n.extractLink());
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
