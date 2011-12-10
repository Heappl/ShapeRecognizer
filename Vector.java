import java.awt.Point;
import java.util.LinkedList;


public class Vector {
	public final static int NORM_SIZE = 15;
	
	double xdiff;
	double ydiff;
	
	public static Vector average(Vector previousAvg, int vecsSoFar, Vector newVector) {
		double avgXDiff = previousAvg.xdiff * vecsSoFar + newVector.xdiff;
		double avgYDiff = previousAvg.ydiff * vecsSoFar + newVector.ydiff;
		return new Vector(avgXDiff / (vecsSoFar + 1), avgYDiff / (vecsSoFar + 1));
	}
	
	public static Vector[] normalize(Vector vec) {
		if (vec.ydiff > vec.xdiff)
		{
			double newXDiff = vec.xdiff / Math.abs(vec.ydiff);
			int newVecs = (int)Math.round(Math.abs(vec.ydiff));
			Vector[] normalized = new Vector[newVecs];
			for (int i = 0; i < newVecs; ++i) normalized[i] = new Vector(newXDiff, vec.ydiff / Math.abs(vec.ydiff));
			return normalized;
			
		}
		double newYDiff = vec.ydiff / Math.abs(vec.xdiff);
		int newVecs = (int)Math.round(Math.abs(vec.xdiff));
		Vector[] normalized = new Vector[newVecs];
		for (int i = 0; i < newVecs; ++i) normalized[i] = new Vector(vec.xdiff / Math.abs(vec.xdiff), newYDiff);
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

	public double distance(Vector other) {
		return Math.sqrt(squaredDistance(other));
	}
	
	public Point movePoint(Point p, int times) {
		return new Point((int)Math.round(p.x + times * xdiff),
						  (int)Math.round(p.y + times * ydiff));
	}
	
	public void add(Vector other) {
		xdiff += other.xdiff;
		ydiff += other.ydiff;
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

