package me.erriic.labymodscripting;

import me.erriic.labymodscripting.commands.ScriptCommand;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class ScriptingAddon extends LabyAddon<ScriptingConfiguration> {
  public static ScriptingAddon INSTANCE;
  public static String VERSION;
  @Override
  protected void enable() {
    INSTANCE = this;
    VERSION = addonInfo().getVersion();
    this.registerSettingCategory();
    //this.labyAPI().navigationService().register("scripting", new ScriptingNavigationElement(this));
    this.registerCommand(new ScriptCommand());
    this.logger().info("LabyModScripitng enabled");
  }
  @Override
  protected Class<ScriptingConfiguration> configurationClass() {
    return ScriptingConfiguration.class;
  }
}
