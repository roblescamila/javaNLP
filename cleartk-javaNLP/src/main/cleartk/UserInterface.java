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

import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
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
	private WordCloudCreator wcc;
	private Cloud cloud;
	private static Vector<File> files;

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

	public static void loadImports(Vector<String> aux) {
		imports = aux;
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public UserInterface() throws IOException {

//		for (File file : directory) {
			files.add(new File("C:\\Users\\Cami\\Documents\\Faca\\Materias\\4to\\Diseño\\javanlp\\cleartk-javaNLP\\input\\test.txt.xmi"));
//		} //unir con lo de abajo
		
		for (File file : files) {
			wcc = new WordCloudCreator(file);
		}

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(gl_contentPane);
		setContentPane(contentPane);

		textField = new JTextField();
		textField.setColumns(10);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		SpinnerNumberModel model = new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(1000),
				new Integer(1));
		DefaultMutableTreeNode paquetes = new DefaultMutableTreeNode("Packages");
		TreeModel packagesmodel = new DefaultTreeModel(paquetes);
		DefaultListModel wordsModel = new DefaultListModel();
		DefaultListModel modelo = new DefaultListModel();

		JMenu mnFile = new JMenu("File");
		JMenuBar menuBar = new JMenuBar();
		JMenuItem mntmOpen = new JMenuItem("Open");
		JMenuItem mntmSave = new JMenuItem("Save");
		JFileChooser fc = new JFileChooser();
		JFrame openFileDialog = new JFrame("Select directory");

		JButton btnCreateWordCloud = new JButton("Create word cloud");
		JButton btnAddWord = new JButton("Add");

		JLabel lblMinWordCount = new JLabel("Minimun word count");
		JLabel lblClassList = new JLabel("Class list");
		JLabel lblFilterWords = new JLabel("Filter words");
		JPanel pnlWordCloud = new JPanel();
		JScrollPane scrollPane = new JScrollPane();
		JScrollPane scrollPane_1 = new JScrollPane();
		JSpinner spinner = new JSpinner(model);
		JTree tree = new JTree(packagesmodel);
		JList wordList = new JList();

		JRadioButton commentsRadioButton = new JRadioButton("Comments", false);
		JRadioButton classNameRadioButton = new JRadioButton("Class names", false);
		JRadioButton methodsNameRadioButton = new JRadioButton("Method names", false);
		JRadioButton variableNameRadioButton = new JRadioButton("Variable names", false);
		JRadioButton packageRadioButton = new JRadioButton("Packages", false);
		JRadioButton importsRadioButton = new JRadioButton("Imports", false);

		setJMenuBar(menuBar);

		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.JAVA", "*.java");
		fc.setFileFilter(filtro);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		menuBar.add(mnFile);
		mnFile.add(mntmOpen);
		mnFile.add(mntmSave);

		scrollPane.setViewportView(tree);
		scrollPane_1.setViewportView(wordList);

		wordsModel.addElement("get");
		wordsModel.addElement("set");
		wordsModel.addElement("java");

		wordList.setModel(wordsModel);

		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING, false).addGroup(gl_contentPane
								.createSequentialGroup()
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 333, GroupLayout.PREFERRED_SIZE)
								.addGap(28)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(commentsRadioButton).addComponent(methodsNameRadioButton)
										.addComponent(classNameRadioButton).addComponent(variableNameRadioButton)
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
														.addGap(18).addComponent(btnAddWord, GroupLayout.PREFERRED_SIZE,
																62, GroupLayout.PREFERRED_SIZE)))
										.addComponent(btnCreateWordCloud, GroupLayout.PREFERRED_SIZE, 142,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 77, Short.MAX_VALUE).addComponent(
										pnlWordCloud, GroupLayout.PREFERRED_SIZE, 710, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblClassList)
								.addContainerGap(1301, Short.MAX_VALUE)))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap().addComponent(lblClassList)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlWordCloud, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
								.createSequentialGroup().addComponent(commentsRadioButton)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(classNameRadioButton)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(methodsNameRadioButton)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(variableNameRadioButton)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(packageRadioButton)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(importsRadioButton)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblMinWordCount).addComponent(spinner, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18).addComponent(lblFilterWords).addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, 22,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnAddWord))
								.addGap(40)
								.addComponent(btnCreateWordCloud, GroupLayout.PREFERRED_SIZE, 75,
										GroupLayout.PREFERRED_SIZE)
								.addGap(144)).addComponent(scrollPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
										497, GroupLayout.PREFERRED_SIZE)))
				.addGap(316)));

		btnCreateWordCloud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] aux;
				aux = wordList.getSelectedIndices();
				for (int a : aux) {
					String palabra = (String) wordsModel.getElementAt(a);
					filteredWords.addElement(palabra);
				}

				try {
					boolean selected[] = { commentsRadioButton.isSelected(), classNameRadioButton.isSelected(),
							methodsNameRadioButton.isSelected(), variableNameRadioButton.isSelected(),
							packageRadioButton.isSelected(), importsRadioButton.isSelected() };

					cloud = wcc.CreateCloud(selected);

					pnlWordCloud.removeAll();
					pnlWordCloud.repaint();
					// cloud.addTag("a");
					for (Tag tag : cloud.tags()) {
						if (tag.getScoreInt() > (int) (((SpinnerNumberModel) spinner.getModel()).getNumber())) {
							// System.out.println("entro al for de los tag");
							JLabel label = new JLabel(tag.getName());
							label.setOpaque(false);
							label.setFont(label.getFont().deriveFont((float) tag.getWeight() * 20)); //hacerlo dinamico, elegido por el usuario
							pnlWordCloud.add(label);
						}
					}

					pnlWordCloud.revalidate();
					pnlWordCloud.repaint();
				} catch (InvalidXMLException | ResourceInitializationException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int seleccion = fc.showOpenDialog(openFileDialog);
				DefaultMutableTreeNode padre;
				fc.setMultiSelectionEnabled(true);
				if (seleccion == JFileChooser.APPROVE_OPTION) {
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
//							System.out.println(name);
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

		btnAddWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wordsModel.addElement(textField.getText());
				wordList.setModel(wordsModel);
				textField.setText("");
			}
		});
	}
}
