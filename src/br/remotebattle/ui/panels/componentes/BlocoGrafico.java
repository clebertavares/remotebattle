package br.remotebattle.ui.panels.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import br.remotebattle.ui.Janela;
import br.remotebattle.ui.UIMain;
import br.remotebattle.ui.panels.MapaJogo;

@SuppressWarnings("serial")
public class BlocoGrafico extends JButton{
	private MapaJogo mapaJogo;
	private int x;
	private int y;

	private boolean selecionando;
	private boolean marcado;

	public BlocoGrafico(MapaJogo mapaJogo, int x, int y){
		this.mapaJogo = mapaJogo;
		this.x = x;
		this.y = y;

		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(25,25));
		this.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				click(e);
			}

		});
	}

	private void click(ActionEvent e){
		//atirando====================================================
		if (mapaJogo.isModoJogo()){
			Janela.getInstance().getGlassPane().setVisible(true);
			
			int x = this.getCoordX();
			int y = this.getCoordY();
			
			System.out.println("atirou! ("+x+", "+y+")");
			mapaJogo.getAtiradorController().definirAlvo(x, y);
			mapaJogo.getAtiradorController().execute();
			boolean atingido = mapaJogo.getAtiradorController().isAtingido();
			
			UIMain.getMudarTurnoController().execute();
			
			Border border = new LineBorder(Color.BLACK);
			this.setBorder(border);
			
			if (atingido){
				this.setBackground(Color.RED);				
			}else{
				this.setBackground(Color.BLUE);
			}
			
			marcado = true;
			this.setEnabled(false);
			
			this.validate();
			
		}else{
		//Colocando barcos============================================
			if (!isMarcado()){
				if (isSelecionando() && mapaJogo.isRoot(this)){
					mapaJogo.marcarArea(this, false);
				}else{

					if (isSelecionando()){
						mapaJogo.preencherBarco(this);
					}else{
						mapaJogo.marcarArea(this, true);
					}
				}
			}
		//============================================================
		}
	}

	public int getCoordX(){
		return x;
	}

	public int getCoordY(){
		return y;
	}

	public void setSelecionando(boolean selecionando) {
		this.selecionando = selecionando;
	}

	public boolean isSelecionando() {
		return selecionando;
	}

	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}

	public boolean isMarcado() {
		return marcado;
	}
}














