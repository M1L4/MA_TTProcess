package de.ma.lamp.ttprocess.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;

public class TableView extends View {
	// table bounds
	public static RectF TABLE_BOUNDS;

	// table measurements
	private final float _net = 15.25f;
	private final float _width = 152.5f + 2 * _net;
	private final float _height = 274f; // length

	// view entries
	float border = 20;

	Paint paint;
	Paint table_body;
	Paint table_frame;
	Paint table_frame_middle;

	ShapeDrawable table;

	public TableView(Context context) {
		super(context);

		setBackgroundColor(Color.WHITE);
		Renderer.disableHardwareRendering(this);

		TABLE_BOUNDS = new RectF();

		paint = new Paint();
		paint.setColor(Color.BLACK);

		table_body = new Paint();
		table_body.setColor(Color.GREEN);
		table_body.setStyle(Style.FILL);

		table_frame = new Paint(paint);
		table_frame.setStyle(Style.STROKE);

		table_frame_middle = new Paint(table_frame);
		table_frame_middle.setPathEffect(new DashPathEffect(new float[] { 40,
				20 }, 0));
	}

	@Override
	public void onDraw(Canvas canvas) {
		float view_height = canvas.getHeight();
		float view_width = canvas.getWidth();

		// get scales
		float scale = Math.min(view_width / _width, view_height / _height);

		float width = _width * scale - 2 * border; // border substracted
		float height = _height * scale - 2 * border; // border substracted
		float net = _net * scale;

		// center table
		float x0 = Math.abs(view_width - width) / 2;
		float y0 = Math.abs(view_height - height) / 2;

		// table
		canvas.drawRect(x0 + net, y0, x0 + width - net, y0 + height, table_body);
		canvas.drawRect(x0 + net, y0, x0 + width - net, y0 + height,
				table_frame);

		// net
		float middle_height = height / 2;
		canvas.drawLine(x0, y0 + middle_height, x0 + width, y0 + middle_height,
				paint);

		// dashed middle line
		float middle_width = width / 2;
		canvas.drawLine(x0 + middle_width, y0, x0 + middle_width, y0 + height,
				table_frame_middle);

		// set table bounds for other views (like arrows etc...)
		TABLE_BOUNDS.set(x0 + net, y0, x0 + width - net, y0 + height);

	}
}