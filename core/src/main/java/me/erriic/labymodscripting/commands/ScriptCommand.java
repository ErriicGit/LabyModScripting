package me.erriic.labymodscripting.commands;

import java.nio.file.Path;
import java.util.Arrays;
import me.erriic.labymodscripting.Script;
import me.erriic.labymodscripting.ScriptingAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;

public class ScriptCommand extends Command {

  public ScriptCommand() {
    super("script", "s");
  }

  @Override
  public boolean execute(String prefix, String[] arguments) {
    if(arguments.length == 1&&arguments[0].equalsIgnoreCase("list")){
      if(Script.getScripts().isEmpty()){
        send(" §cThere are currently no scripts loaded");
        return true;
      }
      String msg = " §rThe following scripts are available: ";
      for(Script script : Script.getScripts().values()){
        msg += "§b" + script.getName() + " " + (script.isRunning()? "§a[Running]§r" : "§c[Idle]§r") + ", ";
      }
      msg = msg.substring(0, msg.length()-2);
      send(msg);
      return true;
    }
    else if(arguments.length==2){
      if(arguments[0].equalsIgnoreCase("load")){
        new Thread(() -> {
          try {
            Script script = Script.load(Path.of(arguments[1]));
            send(" §b" + script.getName() + " §aloaded");
          }catch (Exception e){
            send(" §c" + e.getMessage());
            if(Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()){
              e.printStackTrace();
            }
          }
        }).start();
        return true;
      }
      else if(arguments[0].equalsIgnoreCase("reload")){
        Script script = Script.getScripts().get(arguments[1].toLowerCase());
        if(script==null){
          send(" §cNo script named " + arguments[1] + " found");
          return true;
        }
        new Thread(() -> {
          try {
            script.reload();
            send(" §b" + script.getName() + " §areloaded");
          } catch (Exception e) {
            send(" §c" + e.getMessage());
            if(Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()){
              e.printStackTrace();
            }
          }
        }).start();
        return true;
      }
      else if(arguments[0].equalsIgnoreCase("start")){
        Script script = Script.getScripts().get(arguments[1].toLowerCase());
        if(script==null){
          send(" §cNo script named " + arguments[1] + " found");
          return true;
        }
        new Thread(() -> {
          try {
            script.start();
            send(" §b" + script.getName() + " §astarted");
          } catch (Exception e) {
            send(" §c" + e.getMessage());
            if(Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()){
              e.printStackTrace();
            }
          }
        }).start();
        return true;
      }
      else if(arguments[0].equalsIgnoreCase("stop")){
        Script script = Script.getScripts().get(arguments[1].toLowerCase());
        if(script==null){
          send(" §cNo script named " + arguments[1] + " found");
          return true;
        }
        new Thread(() -> {
          try {
            script.stop();
            send(" §b" + script.getName() + " §cstopped");
          } catch (Exception e) {
            send(" §c" + e.getMessage());
            if(Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()){
              e.printStackTrace();
            }
          }
        }).start();
        return true;
      }
      else if(arguments[0].equalsIgnoreCase("unload")){
        Script script = Script.getScripts().get(arguments[1].toLowerCase());
        if(script==null){
          send(" §cNo script named " + arguments[1] + " found");
          return true;
        }
        try {
          script.unload();
          send(" §b" + script.getName() + " §cunloaded");
        } catch (Exception e) {
          send(" §c" + e.getMessage());
          if(Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()){
            e.printStackTrace();
          }
        }
        return true;
      }
    }
    else if(arguments.length>2){
      if(arguments[0].equalsIgnoreCase("run")){
        Script script = Script.getScripts().get(arguments[1].toLowerCase());
        if(script==null){
          send(" §cNo script named " + arguments[1] + " found");
          return true;
        }
        new Thread(() -> {
          try {
            script.eval(String.join(" ", Arrays.copyOfRange(arguments, 2, arguments.length)));
            send(" §aSuccessfully executed in §b" + script.getName());
          } catch (Exception e) {
            send(" §c" + e.getMessage());
            if(Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()){
              e.printStackTrace();
            }
          }
        }).start();
        return true;
      }
      else if(arguments[0].equalsIgnoreCase("create")){
        new Thread(() -> {
          try {
            Script script = Script.create(arguments[1], String.join(" ", Arrays.copyOfRange(arguments, 2, arguments.length)));
            send(" §b" + script.getName() + " §acreated");
          } catch (Exception e) {
            send(" §c" + e.getMessage());
            if(Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()){
              e.printStackTrace();
            }
          }
        }).start();
        return true;
      }
    }
    sendUsage();
    return true;
  }

  public void sendUsage(){
    Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §cUsage is:\n/script <load/start/stop/reload/unload> <name>\n/script list\n/script <create/run> <name> <script>"));
  }

  public void send(String s){
    Laby.labyAPI().chatProvider().chatController().addMessage(Component.text(ScriptingAddon.PREFIX + s));
  }
}
