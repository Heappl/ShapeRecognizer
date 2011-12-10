import java.util.LinkedList;


public class ShapeRecognizer {
	
	LinkedList<Shape> shapes = new LinkedList<>();
	
	void addShape(Shape shape) {
		shapes.add(shape);
	}
	
	public ShapePainter getShape(Vector[] normalizedDerivative) {
		
		double bestScore = 99999999;
		Shape bestShape = shapes.getFirst();
		int i = 0;
		for (Shape shape : shapes) {
			double score = shape.evaluate(normalizedDerivative);
			System.err.println("score[" + i++ + "]: " + score);
			if (score < bestScore) {
				bestShape = shape;
				bestScore = score;
			}
		}
		
		return bestShape;
	}

}
