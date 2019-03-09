import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class HiloLlenado implements Runnable {
	String[] arreglo = new String[10000];

	@Override
	public void run() {
		VentanaPrincipal.areaSi.setText("");
		VentanaPrincipal.areaNo.setText("");
		int cont1 = 0, cont2 = 0, cont3 = 0;
		String respuestas[] = { "SI", "NO" };
		for (int i = 0; i < arreglo.length; i++) {
			arreglo[i] = respuestas[new Random().nextInt(respuestas.length)];

			if (arreglo[i].equals("SI")) {
				VentanaPrincipal.areaSi.append((cont1 + 1) + ".- " + arreglo[i] + "\n");
				cont1++;
			} else if (arreglo[i].equals("NO")) {
				VentanaPrincipal.areaNo.append((cont2 + 1) + ".- " + arreglo[i] + "\n");
				cont2++;
			}
			System.out.println(cont3);
			cont3++;
		}
	}
}

class HiloHistograma implements Runnable {
	String[] arreglo;
	public HiloHistograma() {
		HiloLlenado hi = new HiloLlenado();
		arreglo = hi.arreglo;
	}
	
	@Override
	public void run() {
	
	VentanaPrincipal ventana;
		for (int i = 0; i < arreglo.length; i++) {
			System.out.println("entra"+arreglo[i]);
			
			if (arreglo[i].equals("SI")) {
				VentanaPrincipal.barraSi.setValue(i);
				VentanaPrincipal.barraSi.repaint();
				VentanaPrincipal.barraSi.setForeground(new Color(8, 106, 211));
			} else {
				VentanaPrincipal.barraNo.setValue(i);
				VentanaPrincipal.barraNo.repaint();
				VentanaPrincipal.barraNo.setForeground(new Color(211, 8, 8));
			}
		}

	}
}

class VentanaPrincipal extends JFrame {
	static JTextArea areaSi, areaNo;
	static JProgressBar barraSi, barraNo;
	JButton boton1, boton2;
	int contadorSi = 0, contadorNo = 0;

	public void VentanaInicio() {

		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(209, 209, 209));
		setSize(400, 450);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Resultados Encuesta");
		setVisible(true);

		// Componentes
		JLabel labelSi = new JLabel("Resultados SI");
		labelSi.setBounds(65, 10, 100, 30);
		labelSi.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		add(labelSi);

		areaSi = new JTextArea();
		areaSi.setLineWrap(true);
		areaSi.setBounds(30, 40, 150, 200);
		areaSi.setEditable(false);
		areaSi.setBorder(null);
		add(areaSi);

		JScrollPane scroll1 = new JScrollPane(areaSi);
		scroll1.setBounds(30, 40, 150, 200);
		add(scroll1);

		JLabel labelNo = new JLabel("Resultados NO");
		labelNo.setBounds(245, 10, 100, 30);
		labelNo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		add(labelNo);

		areaNo = new JTextArea();
		areaNo.setLineWrap(true);
		areaNo.setBounds(210, 40, 150, 200);
		areaNo.setEditable(false);
		areaNo.setBorder(null);
		add(areaNo);

		JScrollPane scroll2 = new JScrollPane(areaNo);
		scroll2.setBounds(210, 40, 150, 200);
		add(scroll2);

		JLabel si = new JLabel("Si.");
		si.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		si.setBounds(30, 270, 100, 30);
		add(si);

		barraSi = new JProgressBar(0, 10000);
		barraSi.setBounds(80, 270, 260, 30);
		barraSi.setStringPainted(true);
		add(barraSi);

		JLabel no = new JLabel("No.");
		no.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		no.setBounds(30, 310, 100, 30);
		add(no);

		barraNo = new JProgressBar(0, 10000);
		barraNo.setBounds(80, 310, 260, 30);
		barraNo.setStringPainted(true);
		add(barraNo);

		boton1 = new JButton("Generar");
		boton1.setBounds(60, 360, 120, 30);
		add(boton1);

		boton1.addActionListener(new ActionListener() {
			HiloLlenado hl;
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread hilo1 = new Thread(new HiloLlenado());
				hilo1.start();
				try {
					hilo1.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
System.out.println("Hilo 1 fin");
				Thread hilo2 = new Thread(new HiloHistograma());
				hilo2.start();

			}
		});

		boton2 = new JButton("Limpiar");
		boton2.setBounds(210, 360, 120, 30);
		add(boton2);

		boton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				areaSi.setText("");
				areaNo.setText("");
				barraSi.setValue(0);
				barraNo.setValue(0);
			}
		});
	}
}

public class Prueba {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new VentanaPrincipal().VentanaInicio();
				;
			}
		});

	}

}