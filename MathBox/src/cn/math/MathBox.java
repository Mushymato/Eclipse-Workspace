package cn.math;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;

public class MathBox {

	protected Shell shlMathbox;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MathBox window = new MathBox();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlMathbox.open();
		shlMathbox.layout();
		while (!shlMathbox.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlMathbox = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX);
		shlMathbox.setMinimumSize(new Point(600, 600));
		shlMathbox.setSize(600, 600);
		
		shlMathbox.setText("MathBox");

	}

}
