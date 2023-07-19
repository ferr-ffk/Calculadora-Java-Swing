package calculadora.ferr.calc.visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Calculadora extends JFrame {

	public Calculadora() {
		this.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));

		organizarLayout();

		setResizable(false);
		setSize(232, 322);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void organizarLayout() {
		setLayout(new BorderLayout());

		Display display = new Display();
		display.setPreferredSize(new Dimension(231, 60));

		Teclado teclado = new Teclado();

		add(display, BorderLayout.NORTH);
		add(teclado, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		new Calculadora();
	}
}
