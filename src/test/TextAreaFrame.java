package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextAreaFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7936536208053061249L;

	private JButton insertButton = new JButton("Insert");

	private JButton wrapButton = new JButton("Wrap");

	private JButton noWrapButton = new JButton("No wrap");

	private JTextArea textArea = new JTextArea(8, 40);

	private JScrollPane scrollPane = new JScrollPane(textArea);

	public TextAreaFrame() {
		JPanel p = new JPanel();

		p.add(insertButton);
		insertButton.addActionListener(this);

		p.add(wrapButton);
		wrapButton.addActionListener(this);

		p.add(noWrapButton);
		noWrapButton.addActionListener(this);

		getContentPane().add(p, "South");

		getContentPane().add(scrollPane, "Center");

		setTitle("TextAreaTest");
		setSize(300, 300);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == insertButton)
			textArea
					.append("textArea The quick brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog.");
		else if (source == wrapButton) {
			// ���÷����ж�������������
			textArea.setWrapStyleWord(true);
			textArea.setLineWrap(true);
			scrollPane.validate();
		} else if (source == noWrapButton) {
			textArea.setLineWrap(false);
			textArea.setWrapStyleWord(false);
			scrollPane.validate();
		}
	}

	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		JFrame f = new TextAreaFrame();
		f.show();
	}

}
