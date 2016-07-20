package test.swing;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestJSlider {

	JFrame mainWin = new JFrame("滑动条示范");
	ChangeListener listener;
	JTextField showVal = new JTextField();
	Box sliderBox = new Box(BoxLayout.Y_AXIS);

	public void init() {
		listener = new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				JSlider source = (JSlider) event.getSource();
				showVal.setText("当前滑动条的值为：" + source.getValue());
			}
		};

		JSlider slider = new JSlider(0,300); 
		 slider.setValue(10);
		// 设置绘制刻度
		slider.setPaintTicks(true); 
		// 设置主、次刻度的间距
		slider.setMajorTickSpacing(50);
		slider.setMinorTickSpacing(10);
		// 设置绘制刻度标签，默认绘制数值刻度标签
		slider.setPaintLabels(true);
		addSlider(slider, "数值刻度标签");

		mainWin.add(sliderBox, BorderLayout.CENTER);
		mainWin.add(showVal, BorderLayout.SOUTH);
		mainWin.pack();
		mainWin.setVisible(true);
	}

	public void addSlider(JSlider slider, String description) {
		slider.addChangeListener(listener);
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(new JLabel(description + "："));
		box.add(slider);
		sliderBox.add(box);
	}

	public static void main(String[] args) {
		new TestJSlider().init();
	}
}