package es.add.ae4;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;

public class Vista {

	private JFrame frame;

	private JButton btnCargarCSV;
	private JButton btnMostrar;
	private JButton btnConsultar;
	private JButton btnAnyadir;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnSQL;
	
	private JTextField textField_Mostrar;
	private JTextField textField_Consulta;
	
	private JTextArea textArea_Texto;

	public Vista() {
		inicialize();
	}

	public void inicialize() {
		frame = new JFrame();
		frame.setBounds(300, 300, 950, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(238, 238, 238));
		frame.setTitle("Biblioteca Pública de Arrakeen");

		JScrollPane scrollPane_Original = new JScrollPane();
		scrollPane_Original.setBounds(10, 10, 920, 220);
		frame.getContentPane().add(scrollPane_Original);

		textArea_Texto = new JTextArea();
		textArea_Texto.setLineWrap(true);
		textArea_Texto.setRows(30);
		scrollPane_Original.setColumnHeaderView(textArea_Texto);
		scrollPane_Original.getViewport().setView(textArea_Texto);

		btnCargarCSV = new JButton("Cargar CSV");
		btnCargarCSV.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnCargarCSV.setBounds(450, 252, 120, 27);
		frame.getContentPane().add(btnCargarCSV);
		
		btnMostrar = new JButton("Mostrar BD");
		btnMostrar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnMostrar.setBounds(10, 252, 120, 27);
		frame.getContentPane().add(btnMostrar);

		btnConsultar = new JButton("Consultar libro");
		btnConsultar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnConsultar.setBounds(10, 300, 120, 27);
		frame.getContentPane().add(btnConsultar);
		
		btnAnyadir = new JButton("Añadir libro");
		btnAnyadir.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnAnyadir.setBounds(150, 252, 120, 27);
		frame.getContentPane().add(btnAnyadir);
		
		btnModificar = new JButton("Modificar libro");
		btnModificar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnModificar.setBounds(150, 300, 120, 27);
		frame.getContentPane().add(btnModificar);
		
		btnBorrar = new JButton("Borrar libro");
		btnBorrar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnBorrar.setBounds(300, 252, 120, 27);
		frame.getContentPane().add(btnBorrar);

		btnSQL = new JButton("Consulta SQL");
		btnSQL.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnSQL.setBounds(300, 300, 120, 27);
		frame.getContentPane().add(btnSQL);

		textField_Consulta = new JTextField("Inserta ID o sentencia SQL");
		textField_Consulta.setBounds(10, 350, 220, 27);
		frame.getContentPane().add(textField_Consulta);
		textField_Consulta.setColumns(100);

		this.frame.setVisible(true);
	}

	// Metodos getters JButton, JTextField, JTextArea
	// Descipcion:
	// Parametros entrada: no
	// Parametros salida: no

	public JButton getbtnCargarCSV() {
		return btnCargarCSV;
	}

	public JButton getbtnMostrar() {
		return btnMostrar;
	}

	public JButton getbtnConsultar() {
		return btnConsultar;
	}

	public JButton getbtnAnyadir() {
		return btnAnyadir;
	}
	
	public JButton getbtnModificar() {
		return btnModificar;
	}
	
	public JButton getbtnBorrar() {
		return btnBorrar;
	}
	
	public JButton getbtnSQL() {
		return btnSQL;
	}

	public JTextField gettextField_Mostrar() {
		return textField_Mostrar;
	}

	public JTextField gettextField_Consulta() {
		return textField_Consulta;
	}
	
	public JTextArea gettextArea_Texto() {
		return textArea_Texto;
	}

	
}