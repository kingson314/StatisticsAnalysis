//package test.http;
//
//import org.htmlparser.Node;
//import org.htmlparser.NodeFilter;
//import org.htmlparser.Parser;
//import org.htmlparser.filters.AndFilter;
//import org.htmlparser.filters.HasAttributeFilter;
//import org.htmlparser.filters.HasChildFilter;
//import org.htmlparser.filters.TagNameFilter;
//import org.htmlparser.util.NodeList;
//import org.htmlparser.util.ParserException;
//import org.htmlparser.visitors.TextExtractingVisitor;
//
///** */
///**
// * @author 作者 Eric yang
// * @version 创建时间：2007-7-16 下午02:49:55 类说明
// */
//public class AstroExtractorTest {
//
//	/** */
//	/**
//	 * @param args
//	 * @throws ParserException
//	 */
//	public static void main(String[] args) throws ParserException {
//		// TODO Auto-generated method stub
//		@SuppressWarnings("unused")
//		String title;
//		@SuppressWarnings("unused")
//		String constellation;
//		@SuppressWarnings("unused")
//		String body;
//		String summary;
//		String url = "http://www.dailyfx.com.hk/calendar/index.html";
//		Parser parser = new Parser(url);
//		parser.setEncoding("utf-8");
//
//		NodeFilter filter_constellation_summart = new AndFilter(
//				(new TagNameFilter(" td ")), (new HasChildFilter(
//						new TagNameFilter(" b "))));
//
//		NodeFilter filter_title = new AndFilter(new TagNameFilter("font"),
//				new HasAttributeFilter("class", "f1491"));
//
//		NodeFilter filter_body = new AndFilter(new TagNameFilter("td"),
//				new HasAttributeFilter("width", "30%"));
//
//		NodeList nodelist = parser.parse(filter_constellation_summart);
//		Node node_constellation = nodelist.elementAt(0);
//		constellation = node_constellation.getFirstChild().getNextSibling()
//				.toHtml();
//
//		Node node_summary = nodelist.elementAt(1);
//		NodeList summary_nodelist = node_summary.getChildren();
//		summary = summary_nodelist.elementAt(3).toHtml()
//				+ summary_nodelist.elementAt(5).toHtml();
//
//		parser.reset();
//
//		nodelist = parser.parse(filter_title);
//		Node node_title = nodelist.elementAt(0);
//		title = node_title.getNextSibling().getNextSibling().toHtml();
//		// title = node_title.getNextSibling().getNextSibling().toHtml() ;
//
//		parser.reset();
//
//		nodelist = parser.parse(filter_body);
//		Node node_body = nodelist.elementAt(0);
//		Parser body_parser = new Parser(node_body.toHtml());
//		TextExtractingVisitor visitor = new TextExtractingVisitor();
//		body_parser.visitAllNodesWith(visitor);
//		body = visitor.getExtractedText();
//
//		// System.out.println(node_summary.getChildren().toHtml()) ;
//		// System.out.println(node_body.toHtml()) ;
//		// System.out.println(title.trim()) ;
//		// System.out.println(constellation.trim()) ;
//		// System.out.println(body.trim()) ;
//		System.out.println(summary.trim());
//
//	}
//
//}