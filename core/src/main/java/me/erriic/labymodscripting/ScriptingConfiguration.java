package me.erriic.labymodscripting;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class ScriptingConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true).addChangeListener(e -> {
        Script.getScripts().forEach((script) -> {
          try{
            script.stop();
          }catch (Exception ex){
            ex.printStackTrace();
          }
        });
      });

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }
}
