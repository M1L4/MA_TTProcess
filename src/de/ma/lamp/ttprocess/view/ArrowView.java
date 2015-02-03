package de.ma.lamp.ttprocess.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ArrowView extends View {
	Paint arrow_paint;
	List<float[]> arrows;

	public ArrowView(Context context) {
		super(context);

		setBackgroundColor(Color.TRANSPARENT);
		Renderer.disableHardwareRendering(this);

		arrow_paint = new Paint();
		arrow_paint.setColor(Color.BLACK);
		arrow_paint.setStrokeWidth(3f);

		arrows = new ArrayList<float[]>();
	}

	@Override
	public void onDraw(Canvas canvas) {

		float x0 = TableView.TABLE_BOUNDS.left;
		float y0 = TableView.TABLE_BOUNDS.top;
		float width = TableView.TABLE_BOUNDS.width();
		float height = TableView.TABLE_BOUNDS.height();

		if (arrows.isEmpty()) {
			arrows.add(new float[] { x0, y0, x0 + width, y0 + height }); // diagonal_to_bottom_right
			arrows.add(new float[] { x0 + width, y0, x0, y0 + height }); // diagonal_to_bottom_left
			arrows.add(new float[] { x0, y0, x0, y0 + height }); // parallel_to_bottom_left
			arrows.add(new float[] { x0 + width, y0, x0 + width, y0 + height }); // parallel_to_bottom_right
			arrows.add(new float[] { x0 + width, y0 + height, x0, y0, }); // diagonal_to_top_right
			arrows.add(new float[] { x0, y0 + height, x0 + width, y0 }); // diagonal_to_top_left
			arrows.add(new float[] { x0, y0 + height, x0, y0 }); // parallel_to_top_left
			arrows.add(new float[] { x0 + width, y0 + height, x0 + width, y0 }); // parallel_to_top_right
		}

		for (float[] arrow : arrows) {
			fillArrow(canvas, arrow[0], arrow[1], arrow[2], arrow[3]);
			// drawArrow(canvas, arrow[0], arrow[1], arrow[2], arrow[3]);
		}
	}

	private void fillArrow(Canvas canvas, float x0, float y0, float x1, float y1) {
		// from:
		// http://stackoverflow.com/questions/11975636/how-to-draw-an-arrow-using-android-graphic-class

		// arrow line
		canvas.drawLine(x0, y0, x1, y1, arrow_paint);

		arrow_paint.setStyle(Paint.Style.FILL);

		float deltaX = x1 - x0;
		float deltaY = y1 - y0;
		float frac = 0.05f;

		float p1_x = x0 + (float) ((1 - frac) * deltaX + frac * deltaY);
		float p1_y = y0 + (float) ((1 - frac) * deltaY - frac * deltaX);

		float p2_x = x0 + (float) ((1 - frac) * deltaX - frac * deltaY);
		float p2_y = y0 + (float) ((1 - frac) * deltaY + frac * deltaX);

		canvas.drawLine(p1_x, p1_y, x1, y1, arrow_paint);
		canvas.drawLine(p2_x, p2_y, x1, y1, arrow_paint);
		
	}

	private void drawArrow(Canvas canvas, float x0, float y0, float x1, float y1) {

		// arrow line
		canvas.drawLine(x0, y0, x1, y1, arrow_paint);

		// Arrow
		float dx = x1 - x0, dy = y1 - y0;
		double angle = Math.atan2(dy, dx);
		float len = (float) Math.sqrt(dx * dx + dy * dy);
		// AffineTransform at = AffineTransform.getTranslateInstance(x0, y0);
		// at.concatenate(AffineTransform.getRotateInstance(angle));

		// Arrow Head
		float ARR_SIZE = 5;
		canvas.drawLine(len - ARR_SIZE, -ARR_SIZE * 0.8f, x1, y1, arrow_paint);
		canvas.drawLine(len - ARR_SIZE, ARR_SIZE * 0.8f, x1, y1, arrow_paint);
	}

}