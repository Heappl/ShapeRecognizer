import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class ShapeLoader {
	
	final Vector[] neighbours = new Vector[]{new Vector(1, -1), new Vector(1, 0), new Vector(1, 1),
										  	 new Vector(0, 1), new Vector(-1, 1), new Vector(-1, 0),
										  	 new Vector(-1, -1), new Vector(0, -1)};
	
	private boolean isPointOK(int height, int width, Point p) {
		return ((p.x >= 0) && (p.x < width) && (p.y >= 0) && (p.y < height));
	}
	
	private Shape readShape(BufferedImage image, Point startPoint) {
		Point prev = startPoint;
		HashSet<Point> visited = new HashSet<>();
		LinkedList<Vector> diffs = new LinkedList<>();
		while (true) {
			Point curr = null;
			for (Vector vec : neighbours) {
				Point temp = vec.movePoint(prev, 1);
				if (!isPointOK(image.getHeight(), image.getWidth(), temp)) continue;
				if (image.getRGB(temp.x, temp.y) != Color.BLACK.getRGB()) continue;
				if (visited.contains(temp)) continue;
				curr = temp;
				break;
			}
			
			if (curr == null) {
				break;
			}
			System.err.println(curr + " " + new Vector(prev, curr));
			visited.add(curr);
			diffs.add(new Vector(prev, curr));
			prev = curr;
		}
		return new Shape(diffs.toArray(new Vector[0]));
	}
	
	void loadShapes(ShapeRecognizer recognizer,
					File shapesFile)
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader(shapesFile));
			
			for (String line = reader.readLine(); line != null; line = reader.readLine())
			{
				String[] tokens = line.split(" ");
				File imageFile = new File(shapesFile.getParentFile() + "/" + tokens[0]);
				System.err.println("reading " + tokens[0]);
				recognizer.addShape(readShape(ImageIO.read(imageFile),
											  new Point(Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]))));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
