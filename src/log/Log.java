package log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JScrollBar;
import javax.swing.JTextArea;

public abstract class Log {

	private static ArrayList<JTextArea> outputAreas;
	private static HashMap<JTextArea, JScrollBar> scrollbars;

	public static void log(String text) {
		if (text == null) {
			text = "<null>";
		}

		text = "[" + formatNow() + "] " + text.trim();
		System.out.println(text);
		if (outputAreas != null) {
			for (JTextArea textArea : outputAreas) {
				JScrollBar scrollBar = scrollbars.get(textArea);
				textArea.append(text + "\n");
				scrollBar.setValue(scrollBar.getMaximum());
			}
		}
	}

	public static void clear() {
		log("Clearing the visible log.");
		if (outputAreas != null) {
			for (JTextArea textArea : outputAreas) {
				textArea.setText("");
			}
		}
	}

	public static void log(Object o) {
		if (o == null) {
			log(null);
			return;
		}
		log(o.toString());
	}

	public static String formatNow() {
		Date date = new Date();
		return new SimpleDateFormat("dd.MM.yyyy - hh:mm:ss").format(date);
	}

	public static void addOutputArea(JTextArea outputArea, JScrollBar scrollBar) {
		if (outputAreas == null) {
			outputAreas = new ArrayList<>();
			scrollbars = new HashMap<>();
		}
		outputAreas.add(outputArea);
		scrollbars.put(outputArea, scrollBar);
	}

}
