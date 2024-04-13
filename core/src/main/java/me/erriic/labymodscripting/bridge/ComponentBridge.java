package me.erriic.labymodscripting.bridge;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;

public class ComponentBridge {
  public TextComponent text(String text){
    return Component.text(text);
  }
}
