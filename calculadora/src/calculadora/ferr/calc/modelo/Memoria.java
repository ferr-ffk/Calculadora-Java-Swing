package calculadora.ferr.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

	private enum TipoComando {
		ZERAR, OPOSTO, PORC, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA;
	};

	private static TipoComando ultimaOperacao = null;
	/*
	 * a ultima tecla digitada, necessária para verificar se é necessário limpar a
	 * tela
	 */

	private static boolean substituir = false;
	/* se a tecla deve substituir a ultima operacao */

	private static final Memoria m = new Memoria();
	/* uma instancia privada da classe, para uso unico dela */

	private static String textoAtual = "";
	/* o texto a ser feita a operação */

	private static String textoBuffer = "";
	/*
	 * a ultima operacao feita ou o ultimo texto digitado, dependendo do caso
	 */

	private final List<MemoriaObservador> observadores = new ArrayList<>();

	private Memoria() {
	}

	public static Memoria get() {
		return m;
	}

	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}

	public void adicionarObservador(MemoriaObservador o) {
		observadores.add(o);
	}

	public void processarComando(String texto) {
		TipoComando tipo = getTipoComando(texto);

		if (tipo == null) {
			return;
		} else if (tipo == TipoComando.ZERAR) {
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
		} else if (tipo == TipoComando.OPOSTO && textoAtual.contains("-")) {
			textoAtual = textoAtual.substring(1);
		} else if (tipo == TipoComando.OPOSTO && !getTextoAtual().equals("0")) {
			textoAtual = "-" + textoAtual;
		} else if (tipo == TipoComando.OPOSTO && textoAtual.equals("0")) {
			textoAtual = "0";
		} else if (tipo == TipoComando.NUMERO || tipo == TipoComando.VIRGULA) {
			textoAtual = substituir ? texto : textoAtual + texto;
		} else {
			substituir = true;
			textoAtual = obterResultadoOperacao();
			textoBuffer = textoAtual;
			ultimaOperacao = tipo;
		}

		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
	}

	private String obterResultadoOperacao() {

		if (ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}

		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));

		double numeroAtual = Double.parseDouble(textoAtual.replace(",", "."));

		double resultado = 0;

		if (ultimaOperacao == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		} else if (ultimaOperacao == TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		} else if (ultimaOperacao == TipoComando.DIV) {
			resultado = numeroBuffer / numeroAtual;
		} else if (ultimaOperacao == TipoComando.MULT) {
			resultado = numeroBuffer * numeroAtual;
		} else if (ultimaOperacao == TipoComando.PORC) {
			resultado = numeroBuffer % numeroAtual;
		}

		String texto = Double.toString(resultado).replace(".", ",");

		boolean isInteiro = texto.endsWith(",0");

		return isInteiro ? texto.replace(",0", "") : texto;
	}

	private TipoComando getTipoComando(String texto) {
		if (textoAtual.isEmpty() && texto == "0") {
			return null;
		}

		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {
			if ("AC".equals(texto)) {
				return TipoComando.ZERAR;
			} else if ("+/-".equals(texto)) {
				return TipoComando.OPOSTO;
			} else if ("%".equals(texto)) {
				return TipoComando.PORC;
			} else if ("/".equals(texto)) {
				return TipoComando.DIV;
			} else if ("*".equals(texto)) {
				return TipoComando.MULT;
			} else if ("-".equals(texto)) {
				return TipoComando.SUB;
			} else if ("+".equals(texto)) {
				return TipoComando.SOMA;
			} else if ("=".equals(texto)) {
				return TipoComando.IGUAL;
			} else if (",".equals(texto) && !textoAtual.contains(",")) {
				return TipoComando.VIRGULA;
			}
		}

		return null;
	}
}
