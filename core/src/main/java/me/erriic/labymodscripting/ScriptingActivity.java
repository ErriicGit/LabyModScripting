package me.erriic.labymodscripting;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;

@AutoActivity
public class ScriptingActivity extends Activity {

  @Override
  public void initialize(Parent parent) {
    super.initialize(parent);
    ComponentWidget textWidget = ComponentWidget.text("This is where the text-editor is added");
    this.document().addChild(textWidget);
  }
}