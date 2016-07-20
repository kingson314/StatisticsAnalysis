//package test.http;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Calendar;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import org.htmlparser.Node;
//import org.htmlparser.NodeFilter;
//import org.htmlparser.Parser;
//import org.htmlparser.Tag;
//import org.htmlparser.filters.AndFilter;
//import org.htmlparser.filters.HasAttributeFilter;
//import org.htmlparser.filters.TagNameFilter;
//import org.htmlparser.util.NodeList;
//import org.htmlparser.util.ParserException;
//import org.htmlparser.visitors.NodeVisitor;
//import common.util.conver.UtilConver;
//import common.util.file.UtilFile;
//
//public class Test {
//	// public static void main(String[] args) throws ParserException,
//	// MalformedURLException, IOException {
//	// String url = "http://www.dailyfx.com.hk/calendar/index.html";
//	// Parser parser = new Parser((URLConnection) (new URL(url))
//	// .openConnection());
//	//
//	// for (NodeIterator i = parser.elements(); i.hasMoreNodes();) {
//	// Node node = i.nextNode();
//	// System.out.println(node.toHtml());
//	// System.err.println("mhj------------------>");
//	// }
//	//
//	// }
//	public static Map<String, String> parseList(String url) {
//		Map<String, String> rlt = new LinkedHashMap<String, String>();
//		// NodeFilter filter = new CssSelectorNodeFilter(".className tr");
//		// filter = new AndFilter(filter, new NotFilter(new HasChildFilter(
//		// new CssSelectorNodeFilter("table"))));
//		NodeFilter filter = new TagNameFilter("div_currency");
//		Parser parser;
//		try {
//			parser = new Parser(url);
//			parser.setEncoding("GB2312");
//			NodeList list = parser.extractAllNodesThatMatch(filter);
//			for (int i = 0; i < list.size(); i++) {
//				Node tr = list.elementAt(i);
//				parser = new Parser(tr.toHtml());
//				// NodeList tds = parser
//				// .extractAllNodesThatMatch(new CssSelectorNodeFilter(
//				// "table"));
//
//				String key = list.elementAt(0).toPlainTextString();
//				String value = list.elementAt(1).toPlainTextString();
//				System.out.println("[" + key.trim() + "]" + "   " + value.trim());
//				rlt.put(key, value);
//			}
//		} catch (ParserException e) {
//			e.printStackTrace();
//		}
//		return rlt;
//	}
//
//	private static void gethtml(String url) {
//		try {
//			Parser parser = new Parser(url);
//			parser.setEncoding("GB2312");
//			NodeFilter[] nodeFilter = new NodeFilter[2];
//			nodeFilter[0] = new TagNameFilter("div");
//			// nodeFilter[0]=new NodeClassFilter(Span.class);
//
//			nodeFilter[1] = new HasAttributeFilter("class", "TableBlock");
//			AndFilter andFilter = new AndFilter(nodeFilter);
//			// NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
//
//			NodeList nodeList = parser.parse(andFilter);
//			Node[] nodes = nodeList.toNodeArray();
//			StringBuffer buftext = new StringBuffer();
//			String line = null;
//			for (int i = 0; i < nodes.length; i++) {
//				line = nodes[i].toPlainTextString();
//				if (line != null) {
//					buftext.append(line);
//					System.out.println(line);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void getBeanHtml(String url) {
//		url = "http://baidu.com";
//		try {
//			Parser parser = new Parser();
//			parser.setEncoding(parser.getEncoding());
//			parser.setURL(url);
//			NodeVisitor visitor = new NodeVisitor() {
//				public void visitTag(Tag tag) {
//					System.out.println("testVisitorAll() Tag name is :" + tag.getTagName() + "\n Class is :" + tag.getClass());
//				}
//			};
//			parser.visitAllNodesWith(visitor);
//		} catch (ParserException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public static String getHTML(String pageURL, String encoding) throws IOException {
//		StringBuilder pageHTML = new StringBuilder();
//		URL url = new URL(pageURL);
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.setRequestProperty("User-Agent", "MSIE 7.0");
//		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
//		String line = null;
//		while ((line = br.readLine()) != null) {
//			pageHTML.append(line);
//			pageHTML.append("\r\n");
//		}
//		connection.disconnect();
//		return pageHTML.toString();
//	}
//
//	public static void getHtmlFile() {
//		String baseDir = "E:\\我的工程\\汇通网\\";
//		String url = "http://www.fx678.com/indexs/html/20131217.shtml";
//		Calendar dayStart = Calendar.getInstance();
//		dayStart.set(2012, 0, 0);
//		Calendar dayEnd = Calendar.getInstance();
//		dayEnd.set(2013, 11, 21);
//		while (dayStart.before(dayEnd)) {
//			dayStart.add(Calendar.DAY_OF_MONTH, 1);
//
//			url = "http://www.fx678.com/indexs/html/" + UtilConver.dateToStr(dayStart.getTime(), "yyyyMMdd") + ".shtml";
//			String html;
//			try {
//				html = getHTML(url, "GB2312");
//				UtilFile.writeFile(baseDir + UtilConver.dateToStr(dayStart.getTime(), "yyyy") + "/" + UtilConver.dateToStr(dayStart.getTime(), "yyyyMMdd") + ".shtml", html);
//			} catch (IOException e) {
//				System.out.println(UtilConver.dateToStr(dayStart.getTime(), "yyyyMMdd") + ".shtml �ļ�������");
//			}
//
//		}
//	}
//
//	public static void main(String[] args) throws IOException {
//		// String url = "http://www.fx678.com/indexs/html/20131219.shtml";
//		String url = "E:\\我的工程\\汇通网\\20131219.shtml";
//		gethtml(url);
//		// getHtmlFile()
//		;
//	}
//}
