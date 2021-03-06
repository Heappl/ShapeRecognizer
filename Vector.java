import java.awt.Point;
import java.util.LinkedList;


public class Vector {
	public final static int NORM_SIZE = 50;
	
	double xdiff;
	double ydiff;
	
	public static Vector average(Vector previousAvg, int vecsSoFar, Vector newVector) {
		double avgXDiff = previousAvg.xdiff * vecsSoFar + newVector.xdiff;
		double avgYDiff = previousAvg.ydiff * vecsSoFar + newVector.ydiff;
		return new Vector(avgXDiff / (vecsSoFar + 1), avgYDiff / (vecsSoFar + 1));
	}
	
	private static double signum(double value) {
		if (value == 0) return 0;
		return (value > 0) ? 1 : -1;
	}
	
	public static Vector[] normalize(Vector vec) {
		if (Math.abs(vec.ydiff) > Math.abs(vec.xdiff))
		{
			double newXDiff = vec.xdiff / Math.abs(vec.ydiff);
			int newVecs = (int)Math.round(Math.abs(vec.ydiff));
			Vector[] normalized = new Vector[newVecs];
			
			for (int i = 0; i < (int)Math.abs(Math.round(vec.xdiff)); ++i) {
				normalized[i] = new Vector(signum(newXDiff), signum(vec.ydiff));
			}
			for (int i = (int)Math.abs(Math.round(vec.xdiff)); i < newVecs; ++i) {
				normalized[i] = new Vector(0, signum(vec.ydiff));
			}
			return normalized;
		}
		double newYDiff = vec.ydiff / Math.abs(vec.xdiff);
		int newVecs = (int)Math.round(Math.abs(vec.xdiff));
		Vector[] normalized = new Vector[newVecs];
		
		for (int i = 0; i < (int)Math.abs(Math.round(vec.ydiff)); ++i) {
			normalized[i] = new Vector(signum(vec.xdiff), signum(newYDiff));
		}
		for (int i = (int)Math.abs(Math.round(vec.ydiff)); i < newVecs; ++i) {
			normalized[i] = new Vector(signum(vec.xdiff), 0);
		}
		return normalized;
	}
	
	public static Vector[] normalize(Vector[] vecs) {
		Vector[] normalized = new Vector[NORM_SIZE];
		double aux = (double)vecs.length / (double)NORM_SIZE;
		for (int i = 0; i < NORM_SIZE; ++i)
		{
			double begin = aux * (double)i;
			double end = aux * (double)(i + 1);
			int first = (int)Math.ceil(begin);
			int last = (int)Math.floor(end);
			Vector avgVector = new Vector(0, 0);
			for (int j = first; j < last; ++j) avgVector.add(vecs[j]);
			
			if (first > 0)
			{
				Vector auxVec = vecs[first - 1];
				auxVec.multiply((double)first - begin);
				avgVector.add(auxVec);
			}
			if (last < vecs.length)
			{
				Vector auxVec = vecs[last];
				auxVec.multiply(end - (double)last);
				avgVector.add(auxVec);
			}
			avgVector.divide(aux);
			normalized[i] = avgVector;
		}
		return normalized;
	}
	
	public Vector(double x, double y) {
		xdiff = x;
		ydiff = y;
	}
	
	public Vector(Point prev, Point next) {
		xdiff = next.x - prev.x;
		ydiff = next.y - prev.y;
	}

	public double squaredDistance(Vector other) {
		double aux1 = xdiff - other.xdiff;
		double aux2 = ydiff - other.ydiff;
		return aux1 * aux1 + aux2 * aux2;
	}
	
	private double convertAngle(boolean xPositive, boolean yPositive, double angle) {
		if (xPositive && yPositive) return angle;
		if (!xPositive && yPositive) return Math.PI - angle;
		if (!xPositive && !yPositive) return Math.PI + angle;
		return Math.PI * 2.0 - angle;
	}
	
	public double distance(Vector other) {
		return Math.sqrt(squaredDistance(other));
	}

	public double angleDistance(Vector other) {
		double otherHypotenuse = Math.sqrt(other.xdiff * other.xdiff + other.ydiff * other.ydiff);
		double myHypotenuse = Math.sqrt(xdiff * xdiff + ydiff * ydiff);
		double myAngle = convertAngle(xdiff >= 0, ydiff >= 0, Math.acos(Math.abs(xdiff) / myHypotenuse));
		double otherAngle = convertAngle(other.xdiff >= 0, other.ydiff >= 0, Math.acos(Math.abs(other.xdiff) / otherHypotenuse));
		double out = Math.min(Math.abs(myAngle - otherAngle),
							  Math.abs(Math.min(myAngle, otherAngle) + Math.PI * 2.0 - Math.max(myAngle, otherAngle)));
//		System.err.println("DISTANCE: ");
//		System.err.println(other.xdiff + " " + other.ydiff + " " + otherHypotenuse + " " +  Math.acos(other.xdiff / otherHypotenuse)
//						   + " " +  otherAngle + " " + Math.toDegrees(otherAngle));
//		System.err.println(xdiff + " " + ydiff + " " + myHypotenuse + " " + Math.acos(xdiff / myHypotenuse)
//						   + " " + myAngle + " " + Math.toDegrees(myAngle));
//		System.err.println(Math.abs(myAngle - otherAngle) + " " + Math.abs(Math.min(myAngle, otherAngle) + Math.PI * 2.0 - Math.max(myAngle, otherAngle)) + " " + out);
//		System.err.println(Math.toDegrees(Math.abs(myAngle - otherAngle)) + " " +
//						   Math.toDegrees(Math.abs(Math.min(myAngle, otherAngle) + Math.PI * 2.0 - Math.max(myAngle, otherAngle))) + " " + Math.toDegrees(out));
		return out;
	}
	
	public Point movePoint(Point p, int times) {
		return new Point((int)Math.round(p.x + times * xdiff),
						  (int)Math.round(p.y + times * ydiff));
	}
	
	public void add(Vector other) {
		xdiff += other.xdiff;
		ydiff += other.ydiff;
	}
	
	public Vector subtract(Vector other) {
		Vector out = new Vector(xdiff, ydiff);
		out.xdiff = xdiff - other.xdiff;
		out.ydiff = ydiff - other.ydiff;
		return out;
	}
	
	public void divide(double arg) {
		xdiff /= arg;
		ydiff /= arg;
	}
	
	public void multiply(double arg) {
		xdiff *= arg;
		ydiff *= arg;
	}
	
	public String toString() {
		return "Vector{xdiff: " + xdiff + ", ydiff: " + ydiff + "}"; 
	}
}

