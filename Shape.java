import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;


public class Shape implements ShapePainter, ShapeEvaluator {
	
	Vector[] normalizedShape = null;
	Vector[] fullShape = null;
	
	public Shape(Vector[] shape) {
		fullShape = shape;
		normalizedShape = Vector.normalize(fullShape);
	}

	@Override
	public double evaluate(Vector[] derivative) {
		double out = Double.MAX_VALUE;
		for (int m = 0; m < normalizedShape.length; ++m) {
			
			double curr = 0;
			for (int i = 0; i < normalizedShape.length; ++i) {
				curr += normalizedShape[(i + m) % normalizedShape.length].distance(derivative[i]);
			}
			out = Math.min(curr, out);
		}
		return out;
	}

	@Override
	public void paint(Graphics2D g) {
		g.fillRect(0, 0, 1000, 1000);
		Point curr = new Point(300, 100);
		for (Vector vec : fullShape)
		{
			Point next = vec.movePoint(curr, 1);
			g.setColor(Color.RED);
			g.drawLine(next.x, next.y, curr.x, curr.y);
			curr = next;
		}
	}
}
