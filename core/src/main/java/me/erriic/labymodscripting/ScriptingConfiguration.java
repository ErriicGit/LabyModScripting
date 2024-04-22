package me.erriic.labymodscripting;

import net.labymod.api.Laby;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class ScriptingConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true).addChangeListener(e -> {
        Script.getScripts().values().forEach((script) -> {
          if(!script.getName().equals("main")){
            try{
              script.stop();
            }catch (Exception ex){
              Laby.labyAPI().chatProvider().chatController().addMessage(
                  Component.text("§r[§6ScriptingAddon§r] §c" + ex.getMessage()));
              if(Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()){
                ex.printStackTrace();
              }
            }
          }
        });
      });

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }
}
