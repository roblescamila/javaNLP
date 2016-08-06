package main.cleartk;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
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
	private static Vector<String> files;
	private JTree tree;

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

	// private void crearArbol() {

	/* Construimos los nodos del arbol que seran ramas u hojas */
	// DefaultMutableTreeNode carpetaRaiz = new DefaultMutableTreeNode("root");
	// /* Definimos el modelo donde se agregaran los nodos */
	// DefaultTreeModel modelo = new DefaultTreeModel(carpetaRaiz);
	// /*
	// * agregamos el modelo al arbol, donde previamente establecimos la raiz
	// */
	// tree = new JTree(modelo);
	// /* definimos los eventos */
	//// tree.getSelectionModel().addTreeSelectionListener(this);
	//
	// /* Definimos mas nodos del arbol y se lo agregamos al modelo */
	// DefaultMutableTreeNode carpeta2 = new
	// DefaultMutableTreeNode("SubCarpeta");
	// DefaultMutableTreeNode archivo1 = new DefaultMutableTreeNode("Archivo1");
	// DefaultMutableTreeNode archivo2 = new DefaultMutableTreeNode("Archivo2");
	// DefaultMutableTreeNode archivo3 = new DefaultMutableTreeNode("Archivo3");
	// /*
	// * Definimos donde se agrega el nodo, dentro de que rama y que posicion
	// */
	// modelo.insertNodeInto(carpeta2, carpetaRaiz, 0);
	// modelo.insertNodeInto(archivo1, carpetaRaiz, 1);
	// modelo.insertNodeInto(archivo2, carpetaRaiz, 2);
	//
	// /* Creamos las hojas del arbol */
	// DefaultMutableTreeNode archivo4 = new DefaultMutableTreeNode("Archivo4");
	// DefaultMutableTreeNode archivo5 = new DefaultMutableTreeNode("Archivo5");
	// DefaultMutableTreeNode archivo6 = new DefaultMutableTreeNode("Archivo6");
	//
	// modelo.insertNodeInto(archivo3, carpeta2, 0);
	// modelo.insertNodeInto(archivo4, carpeta2, 1);
	// modelo.insertNodeInto(archivo5, carpeta2, 2);
	// modelo.insertNodeInto(archivo6, carpeta2, 3);
	// }

	private DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, MyFile dir) {
		String curPath = dir.getPath();
		DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
		if (curTop != null) { // should only be null at root
			curTop.add(curDir);
		}
		Vector<String> ol = new Vector<String>();
		String[] tmp = dir.list();
		for (int i = 0; i < tmp.length; i++)
			ol.addElement(tmp[i]);
		Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
		MyFile f;
		Vector<String> files = new Vector<String>();
		// Make two passes, one for Dirs and one for Files. This is #1.
		for (int i = 0; i < ol.size(); i++) {
			String thisObject = (String) ol.elementAt(i);
			String newPath;
			if (curPath.equals("."))
				newPath = thisObject;
			else
				newPath = curPath + MyFile.separator + thisObject;
			if ((f = new MyFile(newPath)).isDirectory())
				addNodes(curDir, f);
			else
				files.addElement(thisObject);
		}
		// Pass two: for files.
		for (int fnum = 0; fnum < files.size(); fnum++)
			curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
		return curDir;
	}

	public static String createFilePath(TreePath treePath) {
		StringBuilder sb = new StringBuilder();
		Object[] nodes = treePath.getPath();
		for (int i = 0; i < nodes.length; i++) {
			sb.append(nodes[i].toString()).append(File.separatorChar);
		}
		return sb.toString();
	}

	public boolean isFilteredWord(String word) {
		return filteredWords.contains(word);
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * @throws ResourceInitializationException
	 * @throws InvalidXMLException
	 */
	public UserInterface() throws IOException, InvalidXMLException, ResourceInitializationException {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
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

		// treefile.addTreeSelectionListener(new TreeSelectionListener() {
		// public void valueChanged(TreeSelectionEvent e) {
		// DefaultMutableTreeNode node = (DefaultMutableTreeNode)
		// treefile.getLastSelectedPathComponent();
		// /* if nothing is selected */
		// if (node == null)
		// return;
		// /* retrieve the node that was selected */
		//// Object nodeInfo = node.getUserObject();
		// }
		// });

		btnCreateWordCloud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// files = new Vector<File>();
				// String input =
				// "c:/Users/Cami/Documents/Faca/Materias/4to/Diseño/javanlp/cleartk-javaNLP/input/test.java";
				// File file = new File(input);
				// for (File file : directory) {

				String f = createFilePath(tree.getSelectionPath());
				
				// for (String f : files) {
				try {
					wcc = new WordCloudCreator(f);
				} catch (InvalidXMLException | ResourceInitializationException | IOException e) {
					e.printStackTrace();
				}
				// }
				catch (AnalysisEngineProcessException e) {
					e.printStackTrace();
				}

				int[] aux;
				aux = wordList.getSelectedIndices();
				for (int a : aux) {
					String palabra = (String) wordsModel.getElementAt(a);
					filteredWords.addElement(palabra);
				}

				boolean selected[] = { commentsRadioButton.isSelected(), classNameRadioButton.isSelected(),
						methodsNameRadioButton.isSelected(), variableNameRadioButton.isSelected(),
						packageRadioButton.isSelected(), importsRadioButton.isSelected() };

				try {
					cloud = wcc.CreateCloud(selected);
				} catch (CASException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				pnlWordCloud.removeAll();
				pnlWordCloud.repaint();
				cloud.addTag("a");
				for (Tag tag : cloud.tags()) {
					if (tag.getScoreInt() > (int) (((SpinnerNumberModel) spinner.getModel()).getNumber())) {
						JLabel label = new JLabel(tag.getName());
						label.setOpaque(false);
						label.setFont(label.getFont().deriveFont((float) tag.getWeight() * 20)); // hacerlo
																									// dinamico,
																									// elegido
																									// por
																									// el
																									// usuario
						pnlWordCloud.add(label);
					}
				}

				pnlWordCloud.revalidate();
				pnlWordCloud.repaint();
			}
		});

		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int seleccion = fc.showOpenDialog(openFileDialog);

				fc.setMultiSelectionEnabled(true);
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					final MyFile projectFile;
					projectFile = new MyFile(fc.getSelectedFile().getAbsolutePath());
					tree = new JTree(addNodes(null, projectFile));
					scrollPane.setViewportView(tree);
					File a = fc.getSelectedFile();
					File[] a2 = a.listFiles();
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
