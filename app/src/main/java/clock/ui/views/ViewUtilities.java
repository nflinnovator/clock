package clock.ui.views;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

class ViewUtilities {
	
	static Label createLabel(String text, String id) {
		final var newLabel = new Label(text);
		newLabel.setId(id);
		return newLabel;
	}

	static Button createButton(String text, String id) {
		final var newButton = new Button(text);
		newButton.setId(id);
		return newButton;
	}
	

	static boolean addToRoot(Pane container,Node... nodes) {
		return container.getChildren().addAll(nodes);
	}

}
