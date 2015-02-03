package de.ma.lamp.ttprocess.execution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.questionsys.questionnaire.engine.execute.ExecutionManager;
import com.questionsys.questionnaire.engine.xmlinterface.NodeDTO;
import com.questionsys.questionnaire.engine.xmlinterface.NodesDTO;
import com.questionsys.questionnaire.engine.xmlinterface.TemplateDTO;
import com.questionsys.questionnaire.engine.xmlinterface.XmlInterface;

public class Processor {

	String templatePath = "templates";
	ExecutionManager executioner;
	String instanceId;
	Context context;

	// Use CopyOnWriteArrayList to avoid ConcurrentModificationExceptions if a
	// listener attempts to remove itself during event notification.
	private final CopyOnWriteArrayList<ExecutionEventChangeListener> listeners;

	public Processor(Context context) {
		this.context = context;
		this.listeners = new CopyOnWriteArrayList<ExecutionEventChangeListener>();
	}

	public void addExecutionEventChangeListener(ExecutionEventChangeListener l) {
		this.listeners.add(l);
	}

	public void removeExecutionEventChangeListener(
			ExecutionEventChangeListener l) {
		this.listeners.remove(l);
	}

	// Event firing method. Called internally by other class methods.
	protected void fireChangeEvent() {
		ExecutionEvent evt = new ExecutionEvent(this);

		for (ExecutionEventChangeListener l : listeners) {
			l.changeEventReceived(evt);
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////
	public void startTemplate() {
		try {
			final String[] assetList = context.getAssets().list(templatePath);
			OnClickListener listener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					try {
						// get path
						String path = String.format("%s/%s", templatePath,
								assetList[which]);

						// read file
						BufferedReader r = new BufferedReader(
								new InputStreamReader(context.getAssets().open(
										path)));
						StringBuilder template = new StringBuilder();
						String line;
						while ((line = r.readLine()) != null) {
							template.append(line);
						}
						
//						IOUtils.copy(inputStream, writer, encoding)

						// store
						String templateID = ((TemplateDTO) XmlInterface
								.deserializeToObject(TemplateDTO.class,
										template.toString())).getName();
						executioner.storeTemplate(template.toString());

						// start
						instanceId = executioner.startInstance(templateID);
						next();

					}  catch (Exception e) {
						e.printStackTrace();
					}

				}
			};

			showSelection("Template auswaehlen", assetList, listener);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showSelection(String title, String[] items,
			OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title).setItems(items, listener);
		builder.create().show();
	}

	// show next steps
	private void next() {
		String _nodes = executioner.getStartableNode(instanceId);
		List<NodeDTO> nodes = null;
		try {
			nodes = ((NodesDTO) XmlInterface.deserializeToObject(
					NodesDTO.class, _nodes)).getNodeList();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				//start node

			}

		};
		
		List<String> names = new ArrayList<String>();
		for(NodeDTO node:nodes){
			names.add(node.getNodeName());
		}
		
		showSelection("Select Path", (String[]) names.toArray(), listener);
	}
}
