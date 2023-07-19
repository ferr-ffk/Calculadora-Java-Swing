package calculadora.ferr.calc.visao;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Botao extends JButton {

	public Botao(String label, Color cor) {

		setText(label);
		setOpaque(true);
		setBackground(cor);
		setFont(new Font("arial", Font.PLAIN, 15));
		setBorder(null);
		setForeground(Color.WHITE);
	}
}
