package test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Hashtable;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultEditorKit;

public class CutPasteSample {
	public static void main(String args[]) {
		JFrame frame = new JFrame("Cut/Paste Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = frame.getContentPane();

		JTextField textField = new JTextField();
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);

		content.add(textField, BorderLayout.NORTH);
		content.add(scrollPane, BorderLayout.CENTER);

		Action actions[] = textField.getActions();

		Action cutAction = TextUtilities.findAction(actions,
				DefaultEditorKit.cutAction);
		Action copyAction = TextUtilities.findAction(actions,
				DefaultEditorKit.copyAction);
		Action pasteAction = TextUtilities.findAction(actions,
				DefaultEditorKit.pasteAction);

		JPanel panel = new JPanel();
		content.add(panel, BorderLayout.SOUTH);

		JButton cutButton = new JButton(cutAction);
		cutButton.setText("Cut");
		panel.add(cutButton);

		JButton copyButton = new JButton(copyAction);
		copyButton.setText("Copy");
		panel.add(copyButton);

		JButton pasteButton = new JButton(pasteAction);
		pasteButton.setText("Paste");
		panel.add(pasteButton);

		frame.setSize(250, 250);
		frame.setVisible(true);
	}
}

class TextUtilities {
	private TextUtilities() {
	}

	public static Action findAction(Action actions[], String key) {
		Hashtable<Object, Action> commands = new Hashtable<Object, Action>();
		for (int i = 0; i < actions.length; i++) {
			Action action = actions[i];
			commands.put(action.getValue(Action.NAME), action);
		}
		return (Action) commands.get(key);
	}
}
