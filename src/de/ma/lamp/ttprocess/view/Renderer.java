package de.ma.lamp.ttprocess.view;

import android.view.View;

public class Renderer {
	/**
	 * Added since dashed path effect in TableView had no effect
	 */
	public static void disableHardwareRendering(View v) {
		//from: https://code.google.com/p/android/issues/detail?id=29944
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}
}
