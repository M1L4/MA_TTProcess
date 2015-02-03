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
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import de.ma.lamp.ttprocess.execution.ExecutionEvent;
import de.ma.lamp.ttprocess.execution.ExecutionEventChangeListener;
import de.ma.lamp.ttprocess.execution.Processor;
import de.ma.lamp.ttprocess.view.ArrowView;
import de.ma.lamp.ttprocess.view.TableView;

// ----------------------------------------------------------------------

public class Presenter extends Activity implements ExecutionEventChangeListener {
	Processor processor;
	TableView tableView;
	ArrowView arrowView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tableView = new TableView(this);
		setContentView(tableView);
		
		arrowView = new ArrowView(this);
		addContentView(arrowView, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
		processor = new Processor(this);
		processor.addExecutionEventChangeListener(this);
		processor.startTemplate();
	}

	@Override
	public void changeEventReceived(ExecutionEvent evt) {
		
//		arrowView.setArrow()
		arrowView.invalidate();
	}

}

// ----------------------------------------------------------------------

