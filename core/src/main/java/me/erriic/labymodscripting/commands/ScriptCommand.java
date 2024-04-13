package me.erriic.labymodscripting.commands;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import me.erriic.labymodscripting.Script;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import org.apache.commons.io.IOUtils;

public class ScriptCommand extends Command {

  public ScriptCommand() {
    super("script", "s");
  }

  @Override
  public boolean execute(String prefix, String[] arguments) {
    if(arguments.length == 1&&arguments[0].equalsIgnoreCase("list")){
      if(Script.getScripts().isEmpty()){
        Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §rThere are currently no scripts loaded"));
        return true;
      }
      String msg = "§r[§6ScriptingAddon§r] §rThe following scripts are available: ";
      for(Script script : Script.getScripts()){
        msg += "§b" + script.getName() + " " + (script.isRunning()? "§a[Running]§r" : "§c[Idle]§r") + ", ";
      }
      msg = msg.substring(0, msg.length()-2);
      Laby.labyAPI().chatProvider().chatController().addMessage(Component.text(msg));
      return true;
    }
    else if(arguments.length>=2){
      if(arguments[0].equalsIgnoreCase("load")){
        try {
          File f = new File(arguments[1]);
          FileInputStream fis = new FileInputStream(f);
          String data = IOUtils.toString(fis, "UTF-8");
          for(Script s: Script.getScripts()){
            if(s.getName().equals(f.getName())){
              Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §b" + f.getName() + " §c is already loaded"));
              return true;
            }
          }
          Script script = Script.create(f.getName(), data, Path.of(f.getPath()));
          Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §b" + script.getName() + " §aloaded"));
        } catch (Exception e) {
          Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §c" + e.getMessage()));
          e.printStackTrace();
        }
        return true;
      }
      else if(arguments[0].equalsIgnoreCase("start")){
        Script.getScripts().stream().filter((script -> script.getName().equals(arguments[1]))).forEach((script -> {
          try {
            script.start();
            Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §b" + script.getName() + " §astarted"));
          } catch (Exception e) {
            Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §c" + e.getMessage()));
            e.printStackTrace();
          }
        }));
        return true;
      }
      else if(arguments[0].equalsIgnoreCase("stop")){
        Script.getScripts().stream().filter((script -> script.getName().equals(arguments[1]))).forEach((script -> {
          try {
            script.stop();
            Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §a" + script.getName() + " §cstopped"));
          } catch (Exception e) {
            Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §c" + e.getMessage()));
            e.printStackTrace();
          }
        }));
        return true;
      }
    }
    sendUsage();
    return true;
  }

  public void sendUsage(){
    Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §cUsage is /script <load/start/stop> <script>"));
  }
}
