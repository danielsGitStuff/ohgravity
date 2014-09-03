package gui;

import gl.OGLCanvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.List;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextPane;

import main.OSettings;
import threading.OProcessorManager;
import tools.OCreator;
import data.OObject;

public class OGravityWindow implements IStatPresenter {

	private JFrame frmSimulatedMess;
	private OGLCanvas oglCanvas;
	private JPanel OGLCanvasContainer;
	private List<OObject> allObjects;
	private JButton btnStart;
	private JPanel panel;
	private JPanel panel_2;
	private OTextField txtNoOfThreads;
	private JLabel lblNoOfObjects;
	private JLabel lblRoomSize;
	private JLabel lblCreationRoomSize;
	private OTextField txtNoOfObjects;
	private OTextField txtUniverseSize;
	private OTextField txtCreationRoomSize;
	private OProcessorManager processorManager;
	private OTextField txtFps;
	private JLabel lblFps;
	private JLabel lblForceMulti;
	private OTextField txtGravityConstant;
	private JLabel lblNoOfStar;
	private OTextField txtNoOfGalaxies;
	private JLabel lblCreationRoomSize_1;
	private OTextField txtUniverseCreationRoomSize;
	private static String HINT_UNIVERSE_CREATION_ROOMSIZE = "defines the size where the clusters are created in";
	private OSettings settings;
	private JPanel panel_4;
	private JLabel lblNewLabel;
	private JPanel panel_5;
	private JLabel lblSettings;
	private JPanel panel_6;
	private JLabel lblGalaxySettings;
	private JPanel panel_7;
	private JLabel lblStatistics;
	private JLabel lblMergedObjects;
	private JLabel lblObjects;
	private OTextField txtObjects;
	private OTextField txtMergedObjects;
	private JLabel lblTimeFactor;
	private JSlider slider;
	private JLabel lblTargetFps;
	private OTextField txtTargedFps;
	private JLabel lblTimeScale;
	private OTextField txtTimeScale;
	private JLabel lblRunningTime;
	private OTextField txtRunningTime;
	private JLabel lblExplosions;
	private OTextField txtExplosions;
	private JLabel lblObjectsOutOf;
	private JLabel lblMassOutOf;
	private OTextField txtObjectsOob;
	private OTextField txtMassOob;
	private JLabel lblStatus;
	private OTextField txtStatus;
	private JPanel panel_1;
	private JTextPane txtpnNavigationForwardW;

	protected void initOtherStuff() {
		initOpenGl();
	}

	/**
	 * Create the application.
	 */
	public OGravityWindow() {
		initialize();
		this.frmSimulatedMess.setVisible(true);
	}

	private void initOpenGl() {
		OObject.init();
		// setup OpenGL Version 2
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		oglCanvas = new OGLCanvas(capabilities, processorManager,
				OGLCanvasContainer, this, this.settings.getTargetFrameRate());
		settings.bindOGLCanvas(oglCanvas);
		OGLCanvasContainer.removeAll();
		OGLCanvasContainer.add(oglCanvas);

		// shutdown the program on windows close event
		frmSimulatedMess.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		OGravityWindow.this.settings = new OSettings(this);
		frmSimulatedMess = new JFrame();
		frmSimulatedMess.getContentPane().setBackground(Color.GRAY);
		frmSimulatedMess.setTitle("Oh Gravity! - Daniel Decker");
		frmSimulatedMess.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				if (oglCanvas != null)
					oglCanvas.resize();
			}
		});
		frmSimulatedMess.setBounds(100, 100, 679, 705);
		frmSimulatedMess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		frmSimulatedMess.getContentPane().setLayout(gridBagLayout);

		JPanel problematicPanel = new JPanel();
		problematicPanel.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_problematicPanel = new GridBagConstraints();
		gbc_problematicPanel.insets = new Insets(0, 0, 0, 5);
		gbc_problematicPanel.fill = GridBagConstraints.VERTICAL;
		gbc_problematicPanel.anchor = GridBagConstraints.WEST;
		gbc_problematicPanel.gridx = 0;
		gbc_problematicPanel.gridy = 0;
		frmSimulatedMess.getContentPane().add(problematicPanel,
				gbc_problematicPanel);
		GridBagLayout gbl_problematicPanel = new GridBagLayout();
		gbl_problematicPanel.columnWidths = new int[] { 0, 0 };
		gbl_problematicPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_problematicPanel.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_problematicPanel.rowWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		problematicPanel.setLayout(gbl_problematicPanel);

		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		problematicPanel.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[] { 1.0 };
		gbl_panel.rowWeights = new double[] { 0.0 };
		panel.setLayout(gbl_panel);

		panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panel.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 1.0 };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0 };
		panel_2.setLayout(gbl_panel_2);

		panel_4 = new JPanel();
		panel_4.setBackground(Color.GRAY);
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.anchor = GridBagConstraints.NORTH;
		gbc_panel_4.gridwidth = 3;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		panel_2.add(panel_4, gbc_panel_4);

		lblNewLabel = new JLabel("Performance");
		lblNewLabel.setForeground(Color.WHITE);
		panel_4.add(lblNewLabel);

		JLabel lblThreads = new JLabel("threads");
		GridBagConstraints gbc_lblThreads = new GridBagConstraints();
		gbc_lblThreads.insets = new Insets(2, 5, 5, 5);
		gbc_lblThreads.anchor = GridBagConstraints.EAST;
		gbc_lblThreads.gridx = 0;
		gbc_lblThreads.gridy = 1;
		panel_2.add(lblThreads, gbc_lblThreads);

		txtNoOfThreads = new OTextField();
		txtNoOfThreads.setBackground(new Color(204, 204, 204));
		txtNoOfThreads.setText("4");
		GridBagConstraints gbc_txtThreads = new GridBagConstraints();
		gbc_txtThreads.insets = new Insets(2, 0, 5, 0);
		gbc_txtThreads.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtThreads.gridx = 2;
		gbc_txtThreads.gridy = 1;
		panel_2.add(txtNoOfThreads, gbc_txtThreads);
		txtNoOfThreads.setColumns(10);

		lblRoomSize = new JLabel("universe size");
		GridBagConstraints gbc_lblRoomSize = new GridBagConstraints();
		gbc_lblRoomSize.anchor = GridBagConstraints.EAST;
		gbc_lblRoomSize.insets = new Insets(2, 5, 5, 5);
		gbc_lblRoomSize.gridx = 0;
		gbc_lblRoomSize.gridy = 2;
		panel_2.add(lblRoomSize, gbc_lblRoomSize);

		txtUniverseSize = new OTextField();
		txtUniverseSize.setBackground(new Color(204, 204, 204));
		txtUniverseSize.setText("350");
		txtUniverseSize.setColumns(10);
		GridBagConstraints gbc_txtBoundMax = new GridBagConstraints();
		gbc_txtBoundMax.insets = new Insets(2, 0, 5, 0);
		gbc_txtBoundMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBoundMax.gridx = 2;
		gbc_txtBoundMax.gridy = 2;
		panel_2.add(txtUniverseSize, gbc_txtBoundMax);

		lblTimeFactor = new JLabel("time factor");
		GridBagConstraints gbc_lblTimeFactor = new GridBagConstraints();
		gbc_lblTimeFactor.anchor = GridBagConstraints.EAST;
		gbc_lblTimeFactor.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeFactor.gridx = 0;
		gbc_lblTimeFactor.gridy = 3;
		panel_2.add(lblTimeFactor, gbc_lblTimeFactor);

		slider = new JSlider();
		// slider.addChangeListener(new ChangeListener() {
		// public void stateChanged(ChangeEvent e) {
		// int frameRate = slider.getValue() * 25;
		// if (oglCanvas != null)
		// oglCanvas.setFrameRate(frameRate);
		// if (settings != null && statistician != null) {
		// settings.setTargetFrameRate(frameRate);
		// settings.setTimeScale(slider.getValue());
		// statistician.setFrameRate(frameRate);
		// }
		// }
		// });
		slider.setForeground(Color.DARK_GRAY);
		slider.setBackground(Color.LIGHT_GRAY);
		Dimension preferredSize = slider.getPreferredSize();
		slider.setPreferredSize(new Dimension(100, preferredSize.height));
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.insets = new Insets(0, 0, 5, 0);
		gbc_slider.gridx = 2;
		gbc_slider.gridy = 3;
		panel_2.add(slider, gbc_slider);
		settings.bindFrameRateSlider(slider);

		panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.gridwidth = 3;
		gbc_panel_5.anchor = GridBagConstraints.NORTH;
		gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 4;
		panel_2.add(panel_5, gbc_panel_5);

		lblSettings = new JLabel("Universe Settings");
		lblSettings.setForeground(Color.WHITE);
		panel_5.add(lblSettings);

		lblForceMulti = new JLabel("gravity constant");
		GridBagConstraints gbc_lblForceMulti = new GridBagConstraints();
		gbc_lblForceMulti.anchor = GridBagConstraints.EAST;
		gbc_lblForceMulti.insets = new Insets(2, 5, 5, 5);
		gbc_lblForceMulti.gridx = 0;
		gbc_lblForceMulti.gridy = 5;
		panel_2.add(lblForceMulti, gbc_lblForceMulti);

		txtGravityConstant = new OTextField();
		txtGravityConstant.setBackground(new Color(204, 204, 204));
		txtGravityConstant.setText(".0001f");
		GridBagConstraints gbc_txtForceMulti = new GridBagConstraints();
		gbc_txtForceMulti.insets = new Insets(2, 0, 5, 0);
		gbc_txtForceMulti.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtForceMulti.gridx = 2;
		gbc_txtForceMulti.gridy = 5;
		panel_2.add(txtGravityConstant, gbc_txtForceMulti);
		txtGravityConstant.setColumns(10);

		lblNoOfStar = new JLabel("No of Galaxies");
		GridBagConstraints gbc_lblNoOfStar = new GridBagConstraints();
		gbc_lblNoOfStar.insets = new Insets(2, 5, 5, 5);
		gbc_lblNoOfStar.gridx = 0;
		gbc_lblNoOfStar.gridy = 6;
		panel_2.add(lblNoOfStar, gbc_lblNoOfStar);

		txtNoOfGalaxies = new OTextField();
		txtNoOfGalaxies.setBackground(new Color(204, 204, 204));
		txtNoOfGalaxies.setText("4");
		GridBagConstraints gbc_txtNoOfStarClusters = new GridBagConstraints();
		gbc_txtNoOfStarClusters.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNoOfStarClusters.insets = new Insets(2, 0, 5, 0);
		gbc_txtNoOfStarClusters.gridx = 2;
		gbc_txtNoOfStarClusters.gridy = 6;
		panel_2.add(txtNoOfGalaxies, gbc_txtNoOfStarClusters);
		txtNoOfGalaxies.setColumns(10);

		lblCreationRoomSize_1 = new JLabel("creation room size");

		lblCreationRoomSize_1.setToolTipText(HINT_UNIVERSE_CREATION_ROOMSIZE);
		GridBagConstraints gbc_lblCreationRoomSize_1 = new GridBagConstraints();
		gbc_lblCreationRoomSize_1.anchor = GridBagConstraints.EAST;
		gbc_lblCreationRoomSize_1.insets = new Insets(2, 5, 5, 5);
		gbc_lblCreationRoomSize_1.gridx = 0;
		gbc_lblCreationRoomSize_1.gridy = 7;
		panel_2.add(lblCreationRoomSize_1, gbc_lblCreationRoomSize_1);

		txtUniverseCreationRoomSize = new OTextField();
		txtUniverseCreationRoomSize.setBackground(new Color(204, 204, 204));
		txtUniverseCreationRoomSize.setText("150");
		txtUniverseCreationRoomSize.setColumns(10);
		txtUniverseCreationRoomSize
				.setToolTipText(HINT_UNIVERSE_CREATION_ROOMSIZE);
		GridBagConstraints gbc_txtUniverseCreationRoomSize = new GridBagConstraints();
		gbc_txtUniverseCreationRoomSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUniverseCreationRoomSize.insets = new Insets(2, 0, 5, 0);
		gbc_txtUniverseCreationRoomSize.gridx = 2;
		gbc_txtUniverseCreationRoomSize.gridy = 7;
		panel_2.add(txtUniverseCreationRoomSize,
				gbc_txtUniverseCreationRoomSize);

		panel_6 = new JPanel();
		panel_6.setBackground(Color.GRAY);
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 5, 0);
		gbc_panel_6.gridwidth = 3;
		gbc_panel_6.anchor = GridBagConstraints.NORTH;
		gbc_panel_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 8;
		panel_2.add(panel_6, gbc_panel_6);

		lblGalaxySettings = new JLabel("Galaxy Settings");
		lblGalaxySettings.setForeground(Color.WHITE);
		panel_6.add(lblGalaxySettings);

		lblNoOfObjects = new JLabel("no of objects");
		GridBagConstraints gbc_lblNoOfObjects = new GridBagConstraints();
		gbc_lblNoOfObjects.anchor = GridBagConstraints.EAST;
		gbc_lblNoOfObjects.insets = new Insets(2, 5, 5, 5);
		gbc_lblNoOfObjects.gridx = 0;
		gbc_lblNoOfObjects.gridy = 9;
		panel_2.add(lblNoOfObjects, gbc_lblNoOfObjects);

		txtNoOfObjects = new OTextField();
		txtNoOfObjects.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_txtNoOfObjects = new GridBagConstraints();
		gbc_txtNoOfObjects.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNoOfObjects.insets = new Insets(2, 0, 5, 0);
		gbc_txtNoOfObjects.gridx = 2;
		gbc_txtNoOfObjects.gridy = 9;
		panel_2.add(txtNoOfObjects, gbc_txtNoOfObjects);
		txtNoOfObjects.setText("250");
		txtNoOfObjects.setColumns(10);

		lblCreationRoomSize = new JLabel("creation room size");
		GridBagConstraints gbc_lblCreationRoomSize = new GridBagConstraints();
		gbc_lblCreationRoomSize.anchor = GridBagConstraints.EAST;
		gbc_lblCreationRoomSize.insets = new Insets(2, 5, 5, 5);
		gbc_lblCreationRoomSize.gridx = 0;
		gbc_lblCreationRoomSize.gridy = 10;
		panel_2.add(lblCreationRoomSize, gbc_lblCreationRoomSize);

		txtCreationRoomSize = new OTextField();
		txtCreationRoomSize.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_txtCreationRoomSize = new GridBagConstraints();
		gbc_txtCreationRoomSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCreationRoomSize.insets = new Insets(2, 0, 5, 0);
		gbc_txtCreationRoomSize.gridx = 2;
		gbc_txtCreationRoomSize.gridy = 10;
		panel_2.add(txtCreationRoomSize, gbc_txtCreationRoomSize);
		txtCreationRoomSize.setText("20");
		txtCreationRoomSize.setColumns(10);

		panel_7 = new JPanel();
		panel_7.setBackground(Color.GRAY);
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.insets = new Insets(0, 0, 5, 0);
		gbc_panel_7.gridwidth = 3;
		gbc_panel_7.anchor = GridBagConstraints.NORTH;
		gbc_panel_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 11;
		panel_2.add(panel_7, gbc_panel_7);

		lblStatistics = new JLabel("Statistics");
		lblStatistics.setForeground(Color.WHITE);
		panel_7.add(lblStatistics);

		lblTimeScale = new JLabel("time scale");
		GridBagConstraints gbc_lblTimeScale = new GridBagConstraints();
		gbc_lblTimeScale.anchor = GridBagConstraints.EAST;
		gbc_lblTimeScale.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeScale.gridx = 0;
		gbc_lblTimeScale.gridy = 12;
		panel_2.add(lblTimeScale, gbc_lblTimeScale);

		txtTimeScale = new OTextField();
		txtTimeScale.setForeground(Color.BLACK);
		txtTimeScale.setEnabled(true);
		txtTimeScale.setEditable(false);
		txtTimeScale.setColumns(10);
		txtTimeScale.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_txtTimeScale = new GridBagConstraints();
		gbc_txtTimeScale.insets = new Insets(0, 0, 5, 0);
		gbc_txtTimeScale.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTimeScale.gridx = 2;
		gbc_txtTimeScale.gridy = 12;
		panel_2.add(txtTimeScale, gbc_txtTimeScale);

		lblFps = new JLabel("fps");
		GridBagConstraints gbc_lblFps = new GridBagConstraints();
		gbc_lblFps.anchor = GridBagConstraints.EAST;
		gbc_lblFps.insets = new Insets(0, 0, 5, 5);
		gbc_lblFps.gridx = 0;
		gbc_lblFps.gridy = 13;
		panel_2.add(lblFps, gbc_lblFps);

		txtFps = new OTextField();
		txtFps.setEditable(false);
		txtFps.setForeground(Color.BLACK);
		GridBagConstraints gbc_txtFps = new GridBagConstraints();
		gbc_txtFps.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFps.insets = new Insets(0, 0, 5, 0);
		gbc_txtFps.gridx = 2;
		gbc_txtFps.gridy = 13;
		panel_2.add(txtFps, gbc_txtFps);
		txtFps.setBackground(new Color(204, 204, 204));
		txtFps.setEnabled(true);
		txtFps.setColumns(10);

		lblTargetFps = new JLabel("target fps");
		GridBagConstraints gbc_lblTargetFps = new GridBagConstraints();
		gbc_lblTargetFps.anchor = GridBagConstraints.EAST;
		gbc_lblTargetFps.insets = new Insets(0, 0, 5, 5);
		gbc_lblTargetFps.gridx = 0;
		gbc_lblTargetFps.gridy = 14;
		panel_2.add(lblTargetFps, gbc_lblTargetFps);

		txtTargedFps = new OTextField();
		txtTargedFps.setEditable(false);
		txtTargedFps.setForeground(Color.BLACK);
		txtTargedFps.setEnabled(true);
		txtTargedFps.setColumns(10);
		txtTargedFps.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_txtTargedFps = new GridBagConstraints();
		gbc_txtTargedFps.insets = new Insets(0, 0, 5, 0);
		gbc_txtTargedFps.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTargedFps.gridx = 2;
		gbc_txtTargedFps.gridy = 14;
		panel_2.add(txtTargedFps, gbc_txtTargedFps);

		lblRunningTime = new JLabel("running time");
		GridBagConstraints gbc_lblRunningTime = new GridBagConstraints();
		gbc_lblRunningTime.anchor = GridBagConstraints.EAST;
		gbc_lblRunningTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblRunningTime.gridx = 0;
		gbc_lblRunningTime.gridy = 15;
		panel_2.add(lblRunningTime, gbc_lblRunningTime);

		txtRunningTime = new OTextField();
		txtRunningTime.setEditable(false);
		txtRunningTime.setForeground(Color.BLACK);
		txtRunningTime.setEnabled(true);
		txtRunningTime.setColumns(10);
		txtRunningTime.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_txtRunningTime = new GridBagConstraints();
		gbc_txtRunningTime.insets = new Insets(0, 0, 5, 0);
		gbc_txtRunningTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRunningTime.gridx = 2;
		gbc_txtRunningTime.gridy = 15;
		panel_2.add(txtRunningTime, gbc_txtRunningTime);

		lblObjects = new JLabel("objects");
		GridBagConstraints gbc_lblObjects = new GridBagConstraints();
		gbc_lblObjects.anchor = GridBagConstraints.EAST;
		gbc_lblObjects.insets = new Insets(0, 0, 5, 5);
		gbc_lblObjects.gridx = 0;
		gbc_lblObjects.gridy = 16;
		panel_2.add(lblObjects, gbc_lblObjects);

		txtObjects = new OTextField();
		txtObjects.setEditable(false);
		txtObjects.setForeground(Color.BLACK);
		txtObjects.setEnabled(true);
		txtObjects.setColumns(10);
		txtObjects.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_txtObjects = new GridBagConstraints();
		gbc_txtObjects.insets = new Insets(0, 0, 5, 0);
		gbc_txtObjects.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtObjects.gridx = 2;
		gbc_txtObjects.gridy = 16;
		panel_2.add(txtObjects, gbc_txtObjects);

		lblMergedObjects = new JLabel("merged objects");
		GridBagConstraints gbc_lblMergedObjects = new GridBagConstraints();
		gbc_lblMergedObjects.anchor = GridBagConstraints.EAST;
		gbc_lblMergedObjects.insets = new Insets(0, 0, 5, 5);
		gbc_lblMergedObjects.gridx = 0;
		gbc_lblMergedObjects.gridy = 17;
		panel_2.add(lblMergedObjects, gbc_lblMergedObjects);

		txtMergedObjects = new OTextField();
		txtMergedObjects.setEditable(false);
		txtMergedObjects.setForeground(Color.BLACK);
		txtMergedObjects.setEnabled(true);
		txtMergedObjects.setColumns(10);
		txtMergedObjects.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_txtMergedObjects = new GridBagConstraints();
		gbc_txtMergedObjects.insets = new Insets(0, 0, 5, 0);
		gbc_txtMergedObjects.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMergedObjects.gridx = 2;
		gbc_txtMergedObjects.gridy = 17;
		panel_2.add(txtMergedObjects, gbc_txtMergedObjects);

		lblExplosions = new JLabel("explosions");
		GridBagConstraints gbc_lblExplosions = new GridBagConstraints();
		gbc_lblExplosions.anchor = GridBagConstraints.EAST;
		gbc_lblExplosions.insets = new Insets(0, 0, 5, 5);
		gbc_lblExplosions.gridx = 0;
		gbc_lblExplosions.gridy = 18;
		panel_2.add(lblExplosions, gbc_lblExplosions);

		txtExplosions = new OTextField();
		txtExplosions.setForeground(Color.BLACK);
		txtExplosions.setEnabled(true);
		txtExplosions.setEditable(false);
		txtExplosions.setColumns(10);
		txtExplosions.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_txtExplosions = new GridBagConstraints();
		gbc_txtExplosions.insets = new Insets(0, 0, 5, 0);
		gbc_txtExplosions.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtExplosions.gridx = 2;
		gbc_txtExplosions.gridy = 18;
		panel_2.add(txtExplosions, gbc_txtExplosions);

		lblObjectsOutOf = new JLabel("objects out of bounds");
		GridBagConstraints gbc_lblObjectsOutOf = new GridBagConstraints();
		gbc_lblObjectsOutOf.anchor = GridBagConstraints.EAST;
		gbc_lblObjectsOutOf.insets = new Insets(0, 0, 5, 5);
		gbc_lblObjectsOutOf.gridx = 0;
		gbc_lblObjectsOutOf.gridy = 19;
		panel_2.add(lblObjectsOutOf, gbc_lblObjectsOutOf);

		txtObjectsOob = new OTextField();
		txtObjectsOob.setForeground(Color.BLACK);
		txtObjectsOob.setEnabled(true);
		txtObjectsOob.setEditable(false);
		txtObjectsOob.setColumns(10);
		txtObjectsOob.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_txtObjectsOob = new GridBagConstraints();
		gbc_txtObjectsOob.insets = new Insets(0, 0, 5, 0);
		gbc_txtObjectsOob.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtObjectsOob.gridx = 2;
		gbc_txtObjectsOob.gridy = 19;
		panel_2.add(txtObjectsOob, gbc_txtObjectsOob);

		lblMassOutOf = new JLabel("mass out of bounds");
		GridBagConstraints gbc_lblMassOutOf = new GridBagConstraints();
		gbc_lblMassOutOf.insets = new Insets(0, 0, 5, 5);
		gbc_lblMassOutOf.gridx = 0;
		gbc_lblMassOutOf.gridy = 20;
		panel_2.add(lblMassOutOf, gbc_lblMassOutOf);

		txtMassOob = new OTextField();
		txtMassOob.setForeground(Color.BLACK);
		txtMassOob.setEnabled(true);
		txtMassOob.setEditable(false);
		txtMassOob.setColumns(10);
		txtMassOob.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_txtMassOob = new GridBagConstraints();
		gbc_txtMassOob.insets = new Insets(0, 0, 5, 0);
		gbc_txtMassOob.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMassOob.gridx = 2;
		gbc_txtMassOob.gridy = 20;
		panel_2.add(txtMassOob, gbc_txtMassOob);

		lblStatus = new JLabel("status");
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.anchor = GridBagConstraints.EAST;
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 21;
		panel_2.add(lblStatus, gbc_lblStatus);

		txtStatus = new OTextField();
		txtStatus.setForeground(Color.BLACK);
		txtStatus.setEnabled(true);
		txtStatus.setEditable(false);
		txtStatus.setColumns(10);
		txtStatus.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_txtStatus = new GridBagConstraints();
		gbc_txtStatus.insets = new Insets(0, 0, 5, 0);
		gbc_txtStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStatus.gridx = 2;
		gbc_txtStatus.gridy = 21;
		panel_2.add(txtStatus, gbc_txtStatus);

		btnStart = new JButton("start");
		btnStart.setBackground(new Color(204, 204, 204));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				float universeSize = Float.parseFloat(txtUniverseSize.getText());
				float universeCreationRoomSize = Float
						.parseFloat(txtUniverseCreationRoomSize.getText());
				float creationRoomSize = Float.parseFloat(txtCreationRoomSize
						.getText());
				int noOfObjectsPerGalaxy = Integer.parseInt(txtNoOfObjects
						.getText());
				int noOfThreads = Integer.parseInt(txtNoOfThreads.getText());
				int noOfGalaxies = Integer.parseInt(txtNoOfGalaxies.getText());
				float gravityConstant = Float.parseFloat(txtGravityConstant
						.getText());
				allObjects = null;
				OGravityWindow.this.settings
						.setGalaxyCreationRoomSize(creationRoomSize);
				OGravityWindow.this.settings.setNoOfThreads(noOfThreads);
				OGravityWindow.this.settings.setNoOfGalaxies(noOfGalaxies);
				OGravityWindow.this.settings
						.setGravityConstant(gravityConstant);
				OGravityWindow.this.settings
						.setNoOfObjectsPerGalaxy(noOfObjectsPerGalaxy);
				OGravityWindow.this.settings
						.setUniverseCreationRoomSize(universeCreationRoomSize);
				OGravityWindow.this.settings.setUniverseSize(universeSize);
				OGravityWindow.this.settings.reset();
				OGravityWindow.this.txtStatus.setText("running");
				allObjects = OCreator
						.createrandomData(OGravityWindow.this.settings);
				if (processorManager == null)
					processorManager = new OProcessorManager(allObjects,
							OGravityWindow.this.settings);
				else
					processorManager.prepare(allObjects,
							OGravityWindow.this.settings);
				initOpenGl();
			}
		});

		panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		problematicPanel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		txtpnNavigationForwardW = new JTextPane();
		txtpnNavigationForwardW
				.setText("navigation:\r\n   forward: w, mouse up\r\n   backwards: s, mouse down\r\n   left: a\r\n   right: d\r\n   look: mouse");
		txtpnNavigationForwardW.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_txtpnNavigationForwardW = new GridBagConstraints();
		gbc_txtpnNavigationForwardW.anchor = GridBagConstraints.WEST;
		gbc_txtpnNavigationForwardW.fill = GridBagConstraints.VERTICAL;
		gbc_txtpnNavigationForwardW.gridx = 0;
		gbc_txtpnNavigationForwardW.gridy = 0;
		panel_1.add(txtpnNavigationForwardW, gbc_txtpnNavigationForwardW);
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.anchor = GridBagConstraints.WEST;
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 2;
		problematicPanel.add(btnStart, gbc_btnStart);

		OGLCanvasContainer = new JPanel();
		OGLCanvasContainer.setBackground(Color.GRAY);
		GridBagConstraints gbc_OGLCanvasContainer = new GridBagConstraints();
		gbc_OGLCanvasContainer.weightx = 1.0;
		gbc_OGLCanvasContainer.fill = GridBagConstraints.BOTH;
		gbc_OGLCanvasContainer.gridx = 1;
		gbc_OGLCanvasContainer.gridy = 0;
		frmSimulatedMess.getContentPane().add(OGLCanvasContainer,
				gbc_OGLCanvasContainer);
		;
	}

	@Override
	public void showFps(double fps) {
		txtFps.setText(Double.toString(fps));
	}

	@Override
	public void showObjects(int objectCount) {
		txtObjects.setText(Integer.toString(objectCount));
	}

	@Override
	public void showMergedObjects(int mergeCount) {
		txtMergedObjects.setText(Integer.toString(mergeCount));
	}

	@Override
	public void showRunningTime(double runningTime) {
		String text = new DecimalFormat("#.##").format(runningTime);
		txtRunningTime.setText(text);
	}

	@Override
	public void showTimeScale(double timeScale) {
		txtTimeScale.setText(Double.toString(timeScale));
	}

	@Override
	public void showTargetFps(int fps) {
		txtTargedFps.setText(Integer.toString(fps));
	}

	@Override
	public void showExplosions(int explosions) {
		txtExplosions.setText(Integer.toString(explosions));
	}

	@Override
	public void showObjectsOob(int objectsOob) {
		txtObjectsOob.setText(Integer.toString(objectsOob));
	}

	@Override
	public void showMassOob(double massOob) {
		txtMassOob.setText(Double.toString(massOob));
	}

	@Override
	public void stop() {
		oglCanvas.stop();
		txtStatus.setText("stopped");
	}
}
