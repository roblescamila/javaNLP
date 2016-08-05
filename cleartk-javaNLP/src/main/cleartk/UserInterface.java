package main.cleartk;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTree;

public class UserInterface extends JFrame {

	private JPanel contentPane;
	private static Vector<String> comments;
	private static Vector<String> className;
	private static Vector<String> methodsName;
	private static Vector<String> realVariableName;
	private static Vector<String> imports;
	private static Vector<String> packages;
	private static Vector<String> filteredWords;
	private JTextField textField;

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Vector<String> aux = new Vector<String>();
		aux.add("mati");
		aux.add("mati");
		aux.add("mati");
		loadComments(aux);
		Vector<String> aux2 = new Vector<String>();
		aux2.add("cami");
		aux2.add("cami");
		aux2.add("cami");
		loadImports(aux2);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface frame = new UserInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public boolean isFilteredWord(String word) {
		return filteredWords.contains(word);
	}

	public static void loadComments(Vector<String> aux) {
		comments = aux;
	}

	public static void loadClassname(Vector<String> aux) {
		className = aux;
	}

	public static void loadMethodsName(Vector<String> aux) {
		methodsName = aux;
	}

	public static void loadImports(Vector<String> aux) {
		imports = aux;
	}

	public static void loadPackage(Vector<String> aux) {
		packages = aux;
	}

	public static void loadRealVariableName(Vector<String> aux) {
		realVariableName = aux;
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public UserInterface() throws IOException {

		final JFrame frame2 = new JFrame("cloud word");
		final JFrame frame3 = new JFrame("choice");
		JButton btnNewButton_1 = new JButton("Add");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		final Cloud cloud = new Cloud();
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.JAVA", "*.java");
		fc.setFileFilter(filtro);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		final JPanel panel2 = new JPanel();
		JButton btnNewButton = new JButton("Create word cloud");

		final JRadioButton commentsRadioButton = new JRadioButton("Comments", false);
		final JRadioButton classNameRadioButton = new JRadioButton("Class names", false);
		final JRadioButton methodsNameRadioButton = new JRadioButton("Method names", false);
		final JRadioButton realVariableNameRadioButton = new JRadioButton("Variable names", false);
		final JRadioButton packageRadioButton = new JRadioButton("Packages", false);
		final JRadioButton importsRadioButton = new JRadioButton("Imports", false);

		final JFrame frame = new JFrame("cloud");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblClassList = new JLabel("Class list");
		SpinnerNumberModel model = new SpinnerNumberModel(new Integer(0), // Dato visualizado al inicio en el spinner
				new Integer(0), // Límite inferior
				new Integer(1000), // Límite superior
				new Integer(1) // incremento-decremento
		);

		final JSpinner spinner = new JSpinner(model);

		JLabel lblMinWordCount = new JLabel("Minimun word count");

		JScrollPane scrollPane_1 = new JScrollPane();

		textField = new JTextField();
		textField.setColumns(10);

		JLabel lblFilterWords = new JLabel("Filter words");

		JPanel panel = new JPanel();

		final DefaultMutableTreeNode paquetes = new DefaultMutableTreeNode("Packages");
		final TreeModel packagesmodel = new DefaultTreeModel(paquetes);
		JTree tree = new JTree(packagesmodel);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING, false).addGroup(gl_contentPane
								.createSequentialGroup()
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 333, GroupLayout.PREFERRED_SIZE)
								.addGap(28)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(commentsRadioButton).addComponent(methodsNameRadioButton)
										.addComponent(classNameRadioButton).addComponent(realVariableNameRadioButton)
										.addComponent(packageRadioButton).addComponent(importsRadioButton)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 39,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblMinWordCount, GroupLayout.PREFERRED_SIZE, 129,
														GroupLayout.PREFERRED_SIZE))
										.addComponent(lblFilterWords)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 194,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(textField, GroupLayout.PREFERRED_SIZE, 114,
																GroupLayout.PREFERRED_SIZE)
														.addGap(18).addComponent(btnNewButton_1,
																GroupLayout.PREFERRED_SIZE, 62,
																GroupLayout.PREFERRED_SIZE)))
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 142,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 710, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblClassList)
								.addContainerGap(1301, Short.MAX_VALUE)))));
		gl_contentPane
				.setVerticalGroup(
						gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
										.addComponent(lblClassList)
										.addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane
												.createParallelGroup(Alignment.LEADING)
												.addComponent(panel, GroupLayout.PREFERRED_SIZE, 599,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
														.addGroup(gl_contentPane.createSequentialGroup()
																.addComponent(commentsRadioButton)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(classNameRadioButton)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(methodsNameRadioButton)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(realVariableNameRadioButton)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(packageRadioButton)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(importsRadioButton)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addGroup(gl_contentPane
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(lblMinWordCount).addComponent(
																				spinner, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
																.addGap(18).addComponent(lblFilterWords)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE,
																		208, GroupLayout.PREFERRED_SIZE)
																.addGap(18)
																.addGroup(gl_contentPane
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(textField,
																				GroupLayout.PREFERRED_SIZE, 22,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(btnNewButton_1))
																.addGap(40)
																.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE,
																		75, GroupLayout.PREFERRED_SIZE)
																.addGap(144))
														.addComponent(scrollPane, Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE, 497,
																GroupLayout.PREFERRED_SIZE)))
										.addGap(316)));

		scrollPane.setViewportView(tree);
		final DefaultListModel wordsModel = new DefaultListModel();
		wordsModel.addElement("get");
		wordsModel.addElement("set");
		wordsModel.addElement("java");
		final DefaultListModel modelo = new DefaultListModel();
		contentPane.setLayout(gl_contentPane);

		final JList wordsList = new JList();
		scrollPane_1.setViewportView(wordsList);
		wordsList.setModel(wordsModel);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] aux;
				aux = wordsList.getSelectedIndices();
				for (int a : aux) {
					String palabra = (String) wordsModel.getElementAt(a);
					System.out.println(palabra);
					filteredWords.addElement(palabra);
				}

				System.out.println("empiezo a crear la cloud");
				panel2.removeAll();
				panel2.repaint();
				for (Tag tag : cloud.tags()) {

					if (tag.getScoreInt() > (int) (((SpinnerNumberModel) spinner.getModel()).getNumber())) {
						System.out.println("entro al for de los tag");
						final JLabel label = new JLabel(tag.getName());
						label.setOpaque(false);
						label.setFont(label.getFont().deriveFont((float) tag.getWeight() * 20));
						panel2.add(label);
					}
				}
				// contentPane.add(panel);

				frame2.getContentPane().add(panel2);
				frame2.setSize(400, 400);
				frame2.setVisible(true);
			}

		});
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int seleccion = fc.showOpenDialog(frame3);
				// fc.showOpenDialog(contentPane);
				DefaultMutableTreeNode padre;
				// Si el usuario, pincha en aceptar
				fc.setMultiSelectionEnabled(true);
				if (seleccion == JFileChooser.APPROVE_OPTION) {

					// Seleccionamos el fichero

					File a = fc.getSelectedFile();
					File[] a2 = a.listFiles();
					String name = a.getName();
					System.out.println(name);
					padre = new DefaultMutableTreeNode(name);
					((DefaultTreeModel) packagesmodel).insertNodeInto(padre, paquetes, 0);
					Vector<File> ficheros = new Vector<File>();
					for (File archivo : a2) {
						ficheros.add(archivo);
					}

					// textField.setText(fichero.getAbsolutePath());

					for (int i = 0; i < ficheros.size(); i++) {

						File fichero = ficheros.elementAt(i);

						if (!fichero.isDirectory()) {
							modelo.addElement(fichero.getName());
							DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(fichero.getName());
							((DefaultTreeModel) packagesmodel).insertNodeInto(hijo, padre, 0);

							// try (FileReader fr = new FileReader(fichero)) {
							// String cadena = "";
							//
							// int valor = fr.read();
							// while (valor != -1) {
							//
							// cadena = cadena + (char) valor;
							// valor = fr.read();
							// }
							// System.out.println(cadena);
							// } catch (IOException e1) {
							// e1.printStackTrace();
							// }
						} else {
							name = fichero.getName();
							System.out.println(name);

							DefaultMutableTreeNode padre2 = new DefaultMutableTreeNode(name);
							((DefaultTreeModel) packagesmodel).insertNodeInto(padre2, padre, 0);

							File[] auxiliar = fichero.listFiles();
							for (File aux : auxiliar) {

								ficheros.addElement(aux);

							}
							padre = padre2;

						}
					}
				}
			}
		});

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wordsModel.addElement(textField.getText());
				wordsList.setModel(wordsModel);
				textField.setText("");
			}
		});
	}
}
