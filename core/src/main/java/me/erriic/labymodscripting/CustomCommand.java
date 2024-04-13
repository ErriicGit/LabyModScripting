package me.erriic.labymodscripting;

import java.util.function.BiConsumer;
import net.labymod.api.client.chat.command.Command;
import org.jetbrains.annotations.NotNull;

public class CustomCommand extends Command {

  BiConsumer<String, String[]> handler;
  protected CustomCommand(@NotNull String prefix, BiConsumer<String, String[]> handler, String... aliases) {
    super(prefix, aliases);
    this.handler = handler;
  }

  @Override
  public boolean execute(String prefix, String[] arguments) {
    handler.accept(prefix, arguments);
    return true;
  }

  public BiConsumer<String, String[]> getHandler() {
    return handler;
  }
}
