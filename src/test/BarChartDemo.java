//package test;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Toolkit;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.io.File;
//import java.io.IOException;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartFrame;
//import org.jfree.chart.ChartUtilities;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.AxisLocation;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;
//
//public class BarChartDemo {
//	public BarChartDemo() {
//		super();
//	}
//
//	public static void main(String[] args) throws IOException {
//		CategoryDataset dataset = getDataSet();
//		JFreeChart chart = ChartFactory.createBarChart3D("招生信息总览", // 图表标题
//				"应报与实报对照", // 目录轴的显示标签
//				"人数", // 数值轴的显示标签
//				dataset, // 数据集
//				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
//				true, // 是否显示图例(对于简单的柱状图必须是false)
//				true, // 是否生成工具
//				true // 是否生成URL链接
//				);
//		CategoryPlot plot = chart.getCategoryPlot();// 获得图表区域对象
//		// 设置图表的纵轴和横轴org.jfree.chart.axis.CategoryAxis
//		org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
//		domainAxis.setLowerMargin(0.1);// 设置距离图片左端距离此时为10%
//		domainAxis.setUpperMargin(0.1);// 设置距离图片右端距离此时为百分之10
//		domainAxis.setCategoryLabelPositionOffset(10);// 图表横轴与标签的距离(10像素)
//		domainAxis.setCategoryMargin(0.2);// 横轴标签之间的距离20%
//		// domainAxis.setMaximumCategoryLabelLines(1);
//		// domainAxis.setMaximumCategoryLabelWidthRatio(0);
//
//		// 设定柱子的属性
//		org.jfree.chart.axis.ValueAxis rangeAxis = plot.getRangeAxis();
//		rangeAxis.setUpperMargin(0.1);// 设置最高的一个柱与图片顶端的距离(最高柱的10%)
//
//		// 设置图表的颜色
//		org.jfree.chart.renderer.category.BarRenderer3D renderer;
//		renderer = new org.jfree.chart.renderer.category.BarRenderer3D();
//		renderer.setBaseOutlinePaint(Color.red);
//		renderer.setSeriesPaint(0, new Color(0, 255, 255));// 计划柱子的颜色为青色
//		renderer.setSeriesOutlinePaint(0, Color.BLACK);// 边框为黑色
//		renderer.setSeriesPaint(1, new Color(0, 255, 0));// 实报柱子的颜色为绿色
//		renderer.setSeriesOutlinePaint(1, Color.red);// 边框为红色
//		renderer.setItemMargin(0.1);// 组内柱子间隔为组宽的10%
//		// 显示每个柱的数值，并修改该数值的字体属性
////		renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
////		renderer.setItemLabelFont(new Font("黑体", Font.BOLD, 12));// 12号黑体加粗
////		renderer.setItemLabelPaint(Color.black);// 字体为黑色
////		renderer.setItemLabelsVisible(true);
////		plot.setRenderer(renderer);// 使用我们设计的效果
//
//		// 设置纵横坐标的显示位置
//		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);// 学校显示在下端(柱子竖直)或左侧(柱子水平)
//		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT); // 人数显示在下端(柱子水平)或左侧(柱子竖直)
//
//		try {
//			File file = new File("c:/student.png");
//			ChartUtilities.saveChartAsPNG(file, chart, 400, 300);// 把报表保存为文件
//		} catch (Exception e) {
//			String s = e.getLocalizedMessage();
//			s = e.getMessage();
//			s = e.toString();
//		}
//		// 将生成的报表放到预览窗口中
//		final ChartFrame preview = new ChartFrame("招生信息", chart);
//		preview.addWindowListener(new WindowAdapter() {
//			public void windowClosing(final WindowEvent event) {
//				preview.dispose();
//			}
//		});
//		preview.pack();
//		// 调整预览窗口的大小和位置,适合屏幕，并且居中
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		preview.setSize(screenSize.width, screenSize.height - 50);// 适合屏幕，50表示把工具栏要考虑在内
//		Dimension frameSize = preview.getSize();
//		if (frameSize.height > screenSize.height) {
//			frameSize.height = screenSize.height;
//		}
//		if (frameSize.width > screenSize.width) {
//			frameSize.width = screenSize.width;
//		}
//		preview.setLocation((screenSize.width - frameSize.width) / 2,
//				(screenSize.height - frameSize.height - 50) / 2);
//
//		// 显示报表预览窗口
//		preview.setVisible(true);
//	}
//
//	/**
//	 * 获取一个演示用的组合数据集对象
//	 * 
//	 * @return
//	 */
//	private static CategoryDataset getDataSet() {
//		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//		dataset.addValue(200, "计划", "清华大学");
//		dataset.addValue(400, "实报", "清华大学");
//		dataset.addValue(100, "计划", "天津大学");
//		dataset.addValue(205, "实报", "天津大学");
//		dataset.addValue(200, "计划", "郑州大学");
//		dataset.addValue(285, "实报", "郑州大学");
//		return dataset;
//	}
//}