package view;

import android.graphics.Path;

public class Table extends Path {

	// meashurements in cm
	// 2D
	private final static float _net = 15.25f;
	private final static float _width = 152.5f;
	
	public final static float WIDTH = _width + 2 * _net;
	public final static float HEIGHT = 274f; // length

	// 3D
	// private static float height = 76.2f;

	public Table() {
		super();
		createTable();
	}

	private void createTable() {

		setFillType(FillType.EVEN_ODD);
		
		// table
		addRect(_net, 0, _width + _net, HEIGHT, Direction.CCW);

		// net
		float middle_height = HEIGHT / 2;

		moveTo(0, middle_height);
		lineTo(_width + 2 * _net, middle_height);

		// dashed middle line
		float middle_width = WIDTH / 2;

		float number_of_parts = 20;
		float part_length = HEIGHT / (number_of_parts * 2);

		float current_pos = 0;
		for (int i = 0; i < number_of_parts; i++) {
			moveTo(middle_width, current_pos);
			lineTo(middle_width, current_pos + part_length);

			current_pos += 2 * part_length;
		}
	}
}
