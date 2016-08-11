package main.cleartk;
 import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.Box;
import javax.swing.JRadioButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JSlider;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import java.awt.Dimension;

import javax.swing.JButton;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;
import org.xml.sax.SAXException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

public class UserInterface2 extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JSlider slider;
	private static Vector<String> filteredWords;
	private JTextField textField;
	private WordCloudCreator wcc;
	private Cloud cloud;
	private static Vector<String> files;
	private JTree tree;
	private MyFile projectFile;
	private static UserInterface frame;

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface2 frame = new UserInterface2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private void paintCloud(JPanel panel, JSpinner spinner){
	 panel.removeAll();
     panel.repaint();
		
//		for (String remove :filteredWords )
//		{    System.out.println(remove);
//			cloud.removeTag(remove);
//		}
		for (Tag tag : cloud.tags()) {
			if (tag.getScoreInt() > (int) (((SpinnerNumberModel) spinner.getModel()).getNumber())) {
				System.out.println("Meto :"+tag.getName() +" Esta : "+ tag.getScoreInt());
				JLabel label = new JLabel(tag.getName());
				label.setOpaque(false);
				label.setFont(label.getFont().deriveFont((float) tag.getWeight() * slider.getValue()));
				panel.add(label);
			}
		}
	
		panel.revalidate();
		panel.repaint();

	 }
	 
	 
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
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
	
	public BufferedImage createImage(JPanel panel) {

	    int w = panel.getWidth();
	    int h = panel.getHeight();
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bi.createGraphics();
	    panel.paint(g);
	    return bi;
	}
	

	
	
	/**
	 * Create the frame.
	 */
	public UserInterface2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		final DefaultListModel wordsModel = new DefaultListModel();
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		final JList wordList = new JList();
		JMenu mnFile = new JMenu("Options");
		menuBar.add(mnFile);
		cloud = new Cloud();

		final JFileChooser fc = new JFileChooser();
		final JFileChooser fcs = new JFileChooser();
		final JFrame openFileDialog = new JFrame("Select directory");
		final JFrame saveFileDialog = new JFrame("Select directory");
		JMenuItem mntmOpen = new JMenuItem("Open");
	
		
		mnFile.add(mntmOpen);

		JMenuItem mntmSaveCloud = new JMenuItem("Save cloud");
		
		
		
		
		mnFile.add(mntmSaveCloud);

		JMenuItem mntmResetCloud = new JMenuItem("Reset cloud");
	
		mnFile.add(mntmResetCloud);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		final JPanel panel = new JPanel();

		final JScrollPane scrollPane = new JScrollPane();

		JPanel panel_2 = new JPanel();

		JPanel panel_1 = new JPanel();

		JPanel panel_3 = new JPanel();
		
		
		
		mntmSaveCloud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PNG", "*.png");
				fcs.setFileFilter(filtro);
			//	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				BufferedImage image = createImage(panel);
				String ruta;
				//fc.showSaveDialog(frame3);
				int seleccion=fcs.showSaveDialog(saveFileDialog);
		
				if(seleccion==fcs.APPROVE_OPTION){
					
				File file= new File(fcs.getSelectedFile()+".png");
				try {
					ImageIO.write(image, "png", file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}}
		});
		
		
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.JAVA", "*.java");
				fc.setFileFilter(filtro);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int selection = fc.showOpenDialog(openFileDialog);
				fc.setMultiSelectionEnabled(true);
				if (selection == JFileChooser.APPROVE_OPTION) {
					projectFile = new MyFile(fc.getSelectedFile().getAbsolutePath());
					tree = new JTree(addNodes(null, projectFile));
					scrollPane.setViewportView(tree);
					File a = fc.getSelectedFile();
					// File[] a2 = a.listFiles();
				}
			}
			
			
		});
		
		
		
		
		

		JButton btnNewButton = new JButton("Filtered words");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	FilterWordDialog frame = new FilterWordDialog();
// hacer que no cierre todo
			//	frame.setVisible(true);
				System.out.println("Open filter word dialog");
			}
		});

		JButton btnNewButton_1 = new JButton("Create cloud");
		final JDialog dialog = new JDialog(frame, true); // modal
		dialog.setUndecorated(true);
		dialog.setAlwaysOnTop(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		JProgressBar bar = new JProgressBar();
		bar.setIndeterminate(true);
		bar.setStringPainted(true);
		bar.setString("Creating word cloud");
		dialog.add(bar);
		dialog.pack();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																Alignment.LEADING,
																gl_contentPane
																		.createSequentialGroup()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								scrollPane,
																								GroupLayout.DEFAULT_SIZE,
																								293,
																								Short.MAX_VALUE)
																						.addComponent(
																								panel_2,
																								GroupLayout.DEFAULT_SIZE,
																								285,
																								Short.MAX_VALUE)
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addGap(2)
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																btnNewButton_1,
																																Alignment.TRAILING,
																																GroupLayout.DEFAULT_SIZE,
																																283,
																																Short.MAX_VALUE)
																														.addGroup(
																																gl_contentPane
																																		.createSequentialGroup()
																																		.addComponent(
																																				panel_3,
																																				GroupLayout.PREFERRED_SIZE,
																																				GroupLayout.DEFAULT_SIZE,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				ComponentPlacement.UNRELATED)
																																		.addComponent(
																																				btnNewButton,
																																				GroupLayout.PREFERRED_SIZE,
																																				118,
																																				Short.MAX_VALUE)))))
																		.addGap(10))
														.addGroup(
																Alignment.LEADING,
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				panel_1,
																				GroupLayout.PREFERRED_SIZE,
																				232,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(panel,
												GroupLayout.PREFERRED_SIZE,
												1057,
												GroupLayout.PREFERRED_SIZE)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				scrollPane,
																				GroupLayout.PREFERRED_SIZE,
																				485,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(1)
																		.addComponent(
																				panel_1,
																				GroupLayout.PREFERRED_SIZE,
																				50,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				panel_2,
																				GroupLayout.PREFERRED_SIZE,
																				31,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								btnNewButton,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								panel_3,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnNewButton_1,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addComponent(
																panel,
																GroupLayout.PREFERRED_SIZE,
																674,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

	

		JLabel lblMinimunWordCount = new JLabel("Minimun word count");
		panel_3.add(lblMinimunWordCount);

		final JSpinner spinner = new JSpinner();
		spinner.setPreferredSize(new Dimension(45, 20));
		panel_3.add(spinner);

		Box verticalBox = Box.createVerticalBox();
		panel_1.add(verticalBox);
		final JPanel pnlWordCloud = new JPanel();
		final JRadioButton rdbtnPackages = new JRadioButton("Packages");
		verticalBox.add(rdbtnPackages);
		rdbtnPackages.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnPackages.setVerticalAlignment(SwingConstants.TOP);

		final	JRadioButton rdbtnImports = new JRadioButton("Imports");
		verticalBox.add(rdbtnImports);
		rdbtnImports.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnImports.setVerticalAlignment(SwingConstants.TOP);

		Box verticalBox_1 = Box.createVerticalBox();
		panel_1.add(verticalBox_1);

		final JRadioButton rdbtnVariables = new JRadioButton("Variables");
		verticalBox_1.add(rdbtnVariables);
		rdbtnVariables.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnVariables.setVerticalAlignment(SwingConstants.TOP);

		final JRadioButton rdbtnMethod = new JRadioButton("Methods");
		verticalBox_1.add(rdbtnMethod);
		rdbtnMethod.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnMethod.setVerticalAlignment(SwingConstants.TOP);

		Box verticalBox_2 = Box.createVerticalBox();
		panel_1.add(verticalBox_2);

		final JRadioButton rdbtnComments = new JRadioButton("Comments");
		verticalBox_2.add(rdbtnComments);
		rdbtnComments.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnComments.setVerticalAlignment(SwingConstants.TOP);

		final JRadioButton rdbtnClasses = new JRadioButton("Classes");
		rdbtnClasses.setVerticalAlignment(SwingConstants.TOP);
		rdbtnClasses.setHorizontalAlignment(SwingConstants.LEFT);
		verticalBox_2.add(rdbtnClasses);

		JLabel lblNewLabel = new JLabel("Word size");
		panel_2.add(lblNewLabel);

		slider = new JSlider();
		
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				paintCloud(panel,spinner);
			}
		});
		
		
		slider.setPaintLabels(true);
		slider.setName("Word size");
		slider.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_2.add(slider);
		contentPane.setLayout(gl_contentPane);
		
		
		
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws UIMAException, SAXException {
						try {
//							frame.setVisible(true);
//							int[] aux;
//							aux = wordList.getSelectedIndices();
//							filteredWords = new Vector<String>();
//							for (int a : aux) {
//								String palabra = (String) wordsModel.getElementAt(a);
//								filteredWords.addElement(palabra);
//							}
						
							boolean selected[] = { rdbtnComments.isSelected(), rdbtnClasses.isSelected(),
									rdbtnMethod.isSelected(), rdbtnVariables.isSelected(),
									rdbtnPackages.isSelected(), rdbtnImports.isSelected() };

							TreePath[] tpVector = tree.getSelectionPaths();
					
							if (!this.allFalse(selected))
							{for (int i = 0; i < tpVector.length; i++) {
								String f;	
								System.out.println("ENTRO");
								f = createFilePath(tpVector[i]);
								wcc = new WordCloudCreator(f);
								wcc.updateCloud(selected, cloud);
							}}
							paintCloud(panel,spinner);
						} catch (InvalidXMLException | ResourceInitializationException | IOException e) {
							e.printStackTrace();
						} catch (AnalysisEngineProcessException e) {
							e.printStackTrace();
						} catch (CASException e) {
							e.printStackTrace();
						}
						return null;
					}

					public boolean allFalse(boolean[] selected)
					{ 
						for (Boolean a :selected)
						{
							if (a==true)
							{System.out.print("no son todos falsos");
								
								return false;}
						}
						System.out.print(" son todos falsos");
						return true; 
								
					}

					@Override
					protected void done() {
						dialog.dispose();
					}
				};
				worker.execute();
				dialog.setVisible(true); // will block but with a responsive GUI
			}
		});
		
		mntmResetCloud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cloud.clear();
				panel.removeAll();
	            panel.repaint();
			}
		});

	}
	

	public boolean getSliderPaintLabels() {
		return slider.getPaintLabels();
	}

	public void setSliderPaintLabels(boolean paintLabels) {
		slider.setPaintLabels(paintLabels);
	}
	
	
	
	
	
	
	
}

