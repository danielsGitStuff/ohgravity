package gl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JPanel;

import threading.OProcessorManager;

import com.jogamp.opengl.util.FPSAnimator;

public class OGLCanvas extends GLCanvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6252562931829146408L;
	private FPSAnimator animator;
	private OGLView oglView;
	private JPanel embeddedIn;

	class OMouseListener implements MouseListener, MouseWheelListener, MouseMotionListener, KeyListener {
		private int startX, startY;

		@Override
		public void mouseDragged(MouseEvent e) {
			int x, y;
			float dx, dy;
			x = e.getX();
			y = e.getY();
			dx = x - startX;
			dy = y - startY;
			oglView.rotateY(dx / 2);
			oglView.rotateXZ(dy / 2);

			startX = e.getX();
			startY = e.getY();
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int d = e.getWheelRotation();
			float amount = d * -13;

			float alpha = (float) Math.toRadians(oglView.getrAngleY());
			float beta = (float) Math.toRadians(oglView.getrAngleXZ());

			float sinAlpha = (float) Math.sin(alpha);
			float cosAlpha = (float) Math.cos(alpha);
			float sinBeta = (float) Math.sin(beta);

			// rotate horizontally
			float dx = sinAlpha * -amount;
			float dz = cosAlpha * amount;

			// rotation vertically
			float dy = sinBeta * amount;
			oglView.translate(dx, dy, dz);
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
			startX = e.getX();
			startY = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			float alpha = (float) Math.toRadians(oglView.getrAngleY());
			float beta = (float) Math.toRadians(oglView.getrAngleXZ());

			float sinAlpha = (float) Math.sin(alpha);
			float cosAlpha = (float) Math.cos(alpha);
			float sinBeta = (float) Math.sin(beta);

			float dx;
			float dy = 0;
			float dz;

			float multi = 1.5f;

			switch (code) {
			case KeyEvent.VK_W:
				// rotate horizontally
				dx = sinAlpha * -multi;
				dz = cosAlpha * multi;

				// rotation vertically
				dy = sinBeta * multi;
				oglView.translate(dx, dy, dz);
				break;
			case KeyEvent.VK_S:
				// rotate horizontally
				dx = sinAlpha * multi;
				dz = cosAlpha * -multi;

				// rotation vertically
				dy = sinBeta * -multi;
				oglView.translate(dx, dy, dz);
				break;
			case KeyEvent.VK_D:
				// rotate horizontally
				dz = sinAlpha * -multi;
				dx = cosAlpha * -multi;

				// rotation vertically
				oglView.translate(dx, dy, dz);
				break;
			case KeyEvent.VK_A:
				// rotate horizontally
				dz = sinAlpha * multi;
				dx = cosAlpha * multi;

				// rotation vertically
				oglView.translate(dx, dy, dz);
				break;
			default:
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

	}

	public void resize() {
		int x = embeddedIn.getWidth();
		int y = embeddedIn.getHeight();

		if (x == 0 || y == 0) {
			x = 600;
			y = 600;
		}
		setLocation(0, 0);
		setSize(x, y);
	}

	public void setFrameRate(int frameRate) {
		if (this.animator != null) {
			animator.stop();

			animator.remove(this);
			animator = new FPSAnimator(this, frameRate);
			animator.start();
		}
	}

	public OGLCanvas(GLCapabilities capabilities, OProcessorManager processorManager, JPanel embeddedIn,
					 int frameRate) {
		super(capabilities);
		this.embeddedIn = embeddedIn;
		oglView = new OGLView(processorManager);

		addGLEventListener(oglView);
		animator = new FPSAnimator(this, frameRate);
		animator.start();
		resize();
		OMouseListener inputListener = new OMouseListener();
		addMouseListener(inputListener);
		addMouseMotionListener(inputListener);
		addMouseWheelListener(inputListener);
		addKeyListener(inputListener);
	}

	public void stop() {
		animator.stop();
	}
}
