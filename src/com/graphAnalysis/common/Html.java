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
//		String source = null;// �洢��ҳԴ�ļ�
//		source = getSource(url);
//		source = HtmlToTextGb2312(source);
//		// ��ȡÿ����ҳ����������
//		System.out.println(source);
//	}
//
//	// ��ȡ��ҳ��Դ�ļ�
//	private String getSource(String link) {
//		String charset = "GBK";// ��ҳĬ�ϱ�������ΪGBK
//		URLConnection connection = null;
//		try {
//			URL url = new URL(link);
//			// ������
//			connection = url.openConnection();
//			// �����ҳ�޷���
//			if (null == connection)
//				return null;
//
//			// ������Դ�ļ�
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
//			// ��ȡ��ҳ�ı����ʽ
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
//		String htmlStr = inputString; // ��html��ǩ���ַ���
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
//			// ����script��������ʽ.
//			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
//			// ����style��������ʽ.
//			String regEx_html = "<[^>]+>";
//			// ����HTML��ǩ��������ʽ
//			String regEx_houhtml = "/[^>]+>";
//			// ����HTML��ǩ��������ʽ
//			String regEx_spe = "\\&[^;]+;";
//			// ����������ŵ�������ʽ
//			String regEx_blank = " +";
//			// �������ո��������ʽ
//			String regEx_table = "\t+";
//			// �������Ʊ����������ʽ
//			String regEx_enter = "\n+";
//			// �������س���������ʽ
//
//			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
//			m_script = p_script.matcher(htmlStr);
//			htmlStr = m_script.replaceAll(""); // ����script��ǩ
//
//			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
//			m_style = p_style.matcher(htmlStr);
//			htmlStr = m_style.replaceAll(""); // ����style��ǩ
//
//			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
//			m_html = p_html.matcher(htmlStr);
//			htmlStr = m_html.replaceAll(""); // ����html��ǩ
//
//			p_houhtml = Pattern.compile(regEx_houhtml, Pattern.CASE_INSENSITIVE);
//			m_houhtml = p_houhtml.matcher(htmlStr);
//			htmlStr = m_houhtml.replaceAll(""); // ����html��ǩ
//
//			p_spe = Pattern.compile(regEx_spe, Pattern.CASE_INSENSITIVE);
//			m_spe = p_spe.matcher(htmlStr);
//			htmlStr = m_spe.replaceAll(""); // �����������
//
//			p_blank = Pattern.compile(regEx_blank, Pattern.CASE_INSENSITIVE);
//			m_blank = p_blank.matcher(htmlStr);
//			htmlStr = m_blank.replaceAll(" "); // ���˹���Ŀո�
//
//			p_table = Pattern.compile(regEx_table, Pattern.CASE_INSENSITIVE);
//			m_table = p_table.matcher(htmlStr);
//			htmlStr = m_table.replaceAll(" "); // ���˹�����Ʊ��
//
//			p_enter = Pattern.compile(regEx_enter, Pattern.CASE_INSENSITIVE);
//			m_enter = p_enter.matcher(htmlStr);
//			htmlStr = m_enter.replaceAll(" "); // ���˹�����Ʊ��
//
//			textStr = htmlStr;
//
//		} catch (Exception e) {
//			System.err.println("Html2Text: " + e.getMessage());
//		}
//
//		return textStr;// �����ı��ַ���
//	}
//
//	public void html(String url) {
//		try {
//			/*
//			 * ����������ʹ��HttpRequester���HttpRespons����һ��HTTP�����е����ݣ�HTML�ĵ�����
//			 * ���Դ�(http://download.csdn.net/source/321516)������htmlloader���ÿ����������ࣻ
//			 * ����ҵġ�JAVA����HTTP���󣬷���HTTP��Ӧ���ݣ�ʵ����Ӧ�á�һ����ժȡ������JAVA��Ĵ��롣
//			 * htmlparse���Դ�(http://download.csdn.net/source/321507)������
//			 */
//			HttpRequester request = new HttpRequester();
//			HttpRespons hr = request.sendGet(url);
//
//			Parser parser = Parser.createParser(hr.getContent(), hr.getContentEncoding());
//			parser.setEncoding("utf-8");
//			try {
//				// ͨ�����������˳�<A>��ǩ
//				NodeList nodeList = parser.extractAllNodesThatMatch(new NodeFilter() {
//					private static final long serialVersionUID = 1L;
//					// ʵ�ָ÷���,���Թ��˱�ǩ
//					public boolean accept(Node node) {
//						if (node instanceof LinkTag)// <A>���
//							return true;
//						return false;
//					}
//				});
//				// ��ӡ
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
