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

	public double evaluate1(Vector[] derivative) {
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

	public double evaluate3(Vector[] derivative) {
		double out = Double.MAX_VALUE;
		for (int m = 0; m < normalizedShape.length; ++m) {
			
			double curr = 0;
			for (int i = 0; i < normalizedShape.length; ++i) {
				curr += normalizedShape[(i + m) % normalizedShape.length].angleDistance(derivative[i]);
			}
			out = Math.min(curr, out);
		}
		return out;
	}

	public double evaluate2(Vector[] derivative) {
		double out = Double.MAX_VALUE;
		
		Vector[] secondDerivative = new Vector[derivative.length - 1];
		Vector[] secondNormalized = new Vector[derivative.length - 1];
		for (int i = 1; i < derivative.length; ++i) {
			secondDerivative[i - 1] = derivative[i].subtract(derivative[i - 1]);
			secondNormalized[i - 1] = normalizedShape[i].subtract(normalizedShape[i - 1]);
		}
		
		for (int m = 0; m < secondNormalized.length; ++m) {
			
			double curr = 0;
			for (int i = 0; i < secondDerivative.length; ++i) {
				curr += secondNormalized[(i + m) % secondNormalized.length].distance(secondDerivative[i]);
			}
			out = Math.min(curr, out);
		}
		return out;
	}

	@Override
	public double evaluate(Vector[] derivative) {
		return evaluate1(derivative) + evaluate3(derivative);
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
