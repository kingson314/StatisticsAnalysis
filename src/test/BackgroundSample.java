package test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BackgroundSample {
	public static void main(String args[]) {
		JFrame frame = new JFrame("Background Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final ImageIcon imageIcon = new ImageIcon("draft.gif");
		JTextArea textArea = new JTextArea() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6028337469561305501L;

			Image image = imageIcon.getImage();

			Image grayImage = GrayFilter.createDisabledImage(image);
			{
				setOpaque(false);
			}

			public void paint(Graphics g) {
				g.drawImage(grayImage, 0, 0, this);
				super.paint(g);
			}
		};
		JScrollPane scrollPane = new JScrollPane(textArea);
		Container content = frame.getContentPane();
		content.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(250, 250);
		frame.setVisible(true);
	}
}
