package me.erriic.labymodscripting;

import java.util.function.BiConsumer;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;

public class CustomCommand extends Command {

  BiConsumer<String, String[]> handler;
  protected CustomCommand(@NotNull String prefix, BiConsumer<String, String[]> handler, String... aliases) {
    super(prefix, aliases);
    this.handler = handler;
  }

  @Override
  public boolean execute(String prefix, String[] arguments) {
    try {
      handler.accept(prefix, arguments);
    } catch (Exception e){
      Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §c" + e.getMessage()));
      if(Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()){
        e.printStackTrace();
      }
    }
    return true;
  }

  public BiConsumer<String, String[]> getHandler() {
    return handler;
  }
}
