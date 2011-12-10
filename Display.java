import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileFilter;


public class Display extends JFrame implements MouseMotionListener, MouseInputListener {
	JPanel drawPanel = new JPanel();
	CurvePreparer curvePreparer = new CurvePreparer();
	ShapeRecognizer shapeEvaluator = new ShapeRecognizer();
	JFileChooser fileChooser = new JFileChooser();
	
	
	
	public Display() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		add(menuBar);
		menuBar.add(menu);

		JMenuItem openFileMenuItem = new JMenuItem("Load shapes");
		menu.add(openFileMenuItem);
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return ".shs";
			}
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getAbsolutePath().endsWith(".shs");
			}
		});
		openFileMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showOpenDialog(Display.this) == JFileChooser.APPROVE_OPTION) {
					new ShapeLoader().loadShapes(shapeEvaluator, fileChooser.getSelectedFile());
				}
			}
		});
		setJMenuBar(menuBar);
		
		setMinimumSize(new Dimension(800, 600));
		drawPanel.setBackground(Color.BLACK);
		add(drawPanel);
		drawPanel.addMouseMotionListener(this);
		drawPanel.addMouseListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		curvePreparer.addPoint(new Point(e.getX(), e.getY()));
		curvePreparer.paintSegment((Graphics2D)drawPanel.getGraphics());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Vector[] normalized = curvePreparer.getNormalizedDerivative();
		ShapePainter shape = shapeEvaluator.getShape(normalized);
		shape.paint((Graphics2D)drawPanel.getGraphics());
		curvePreparer = new CurvePreparer();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}
}
