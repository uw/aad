package aad.app.exercise.drawing;

public class Point {
	
	public float x;
	public float y;
	public float pressure;
	public float size;

	public Point(float x, float y, float pressure, float size) {
		this.x = x;
		this.y = y;
		this.pressure = pressure;
		this.size = size;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", pressure=" + pressure + ", size=" + size + "]";
	}
	
}
