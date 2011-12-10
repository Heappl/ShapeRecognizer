import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;


public class CurvePreparer {
	LinkedList<Vector> diffs = new LinkedList<>();
	Point previousPoint = null;
	Point currentPoint = null;
	
	void addPoint(Point p)
	{
		previousPoint = currentPoint;
		currentPoint = p;
		if (previousPoint != null)
		{
			Vector diff = new Vector(currentPoint.x - previousPoint.x, currentPoint.y - previousPoint.y);
			Vector[] normDiff = Vector.normalize(diff);
			for (Vector vec : normDiff) diffs.add(vec);
		}
	}
	
	Vector[] getNormalizedDerivative()
	{
		return Vector.normalize(diffs.toArray(new Vector[0]));
	}

	public void paintSegment(Graphics2D graphics) {
		if ((previousPoint != null) && (currentPoint != null))
		{
			graphics.setColor(Color.GREEN);
			graphics.drawLine(currentPoint.x, currentPoint.y, previousPoint.x, previousPoint.y);
		}
	}
}
