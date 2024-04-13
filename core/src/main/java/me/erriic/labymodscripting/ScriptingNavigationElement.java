package me.erriic.labymodscripting;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;

public class ScriptingNavigationElement extends ScreenNavigationElement {
  ScriptingAddon addon;

  protected ScriptingNavigationElement(ScriptingAddon addon) {
    super(new ScriptingActivity());
    this.addon = addon;
  }

  @Override
  public Component getDisplayName() {
    return Component.text("Scripting");
  }

  @Override
  public Icon getIcon() {
    return null;
  }

  @Override
  public String getWidgetId() {
    return "scripting";
  }

  @Override
  public boolean isVisible() {
    return this.addon.configuration().enabled().get();
  }
}