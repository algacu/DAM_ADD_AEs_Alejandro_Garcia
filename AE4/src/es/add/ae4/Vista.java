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
	private JButton btnAutores;
	private JButton btnEditoriales;
	private JTextField textField_Mostrar;
	private JTextArea textArea_Texto;

	
	public Vista() {
		inicialize();
	}

	
	public void inicialize() {
		frame = new JFrame();
		frame.setBounds(300, 300, 950, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(238, 238, 238));
		frame.setTitle("Biblioteca P?blica de Arrakeen");

		JScrollPane scrollPane_Original = new JScrollPane();
		scrollPane_Original.setBounds(10, 10, 920, 230);
		frame.getContentPane().add(scrollPane_Original);

		textArea_Texto = new JTextArea();
		textArea_Texto.setLineWrap(true);
		textArea_Texto.setRows(30);
		scrollPane_Original.setColumnHeaderView(textArea_Texto);
		scrollPane_Original.getViewport().setView(textArea_Texto);

		btnCargarCSV = new JButton("Cargar CSV");
		btnCargarCSV.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnCargarCSV.setBounds(450, 285, 120, 27);
		frame.getContentPane().add(btnCargarCSV);
		
		btnMostrar = new JButton("Mostrar BD");
		btnMostrar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnMostrar.setBounds(10, 262, 120, 27);
		frame.getContentPane().add(btnMostrar);

		btnConsultar = new JButton("Consultar libro");
		btnConsultar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnConsultar.setBounds(10, 310, 120, 27);
		frame.getContentPane().add(btnConsultar);
		
		btnAnyadir = new JButton("A?adir libro");
		btnAnyadir.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnAnyadir.setBounds(150, 262, 120, 27);
		frame.getContentPane().add(btnAnyadir);
		
		btnModificar = new JButton("Modificar libro");
		btnModificar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnModificar.setBounds(150, 310, 120, 27);
		frame.getContentPane().add(btnModificar);
		
		btnBorrar = new JButton("Borrar libro");
		btnBorrar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnBorrar.setBounds(300, 262, 120, 27);
		frame.getContentPane().add(btnBorrar);

		btnSQL = new JButton("Consulta SQL");
		btnSQL.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnSQL.setBounds(300, 310, 120, 27);
		frame.getContentPane().add(btnSQL);
		
		btnAutores = new JButton("Autores < 1950");
		btnAutores.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnAutores.setBounds(600, 262, 120, 27);
		frame.getContentPane().add(btnAutores);
		
		btnEditoriales = new JButton("Editoriales S.XXI");
		btnEditoriales.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnEditoriales.setBounds(600, 310, 120, 27);
		frame.getContentPane().add(btnEditoriales);

		this.frame.setVisible(true);
	}

	
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
	
	public JButton getbtnAutores() {
		return btnAutores;
	}
	
	public JButton getbtnEditoriales() {
		return btnEditoriales;
	}

	public JTextField gettextField_Mostrar() {
		return textField_Mostrar;
	}
	
	public JTextArea gettextArea_Texto() {
		return textArea_Texto;
	}

	
}