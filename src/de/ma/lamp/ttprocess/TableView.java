/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.ma.lamp.ttprocess;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.View;

// ----------------------------------------------------------------------

public class TableView extends Activity {
	DrawView drawView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		drawView = new DrawView(this);
		drawView.setBackgroundColor(Color.WHITE);
		setContentView(drawView);

	}
}

// ----------------------------------------------------------------------

class DrawView extends View {
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

	public DrawView(Context context) {
		super(context);

		disableHardwareRendering(this);

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

	public static void disableHardwareRendering(View v) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		float view_height = canvas.getHeight();
		float view_width = canvas.getWidth();

		// get scales
		float scale = Math.min(view_width / _width, view_height / _height);

		float width = _width * scale - 2 * border;		//border substracted
		float height = _height * scale - 2 * border;	//border substracted
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

	}
}