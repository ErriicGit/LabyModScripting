package me.erriic.labymodscripting;

import java.lang.reflect.Method;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.method.SubscribeMethod;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomSubscribeMethod implements SubscribeMethod {

  String className;
  Consumer<Event> handler;

  public CustomSubscribeMethod(String className, Consumer<Event> handler){
    this.className = className;
    this.handler = handler;
  }

  @Override
  public void invoke(Event event) {
    try {
      handler.accept(event);
    } catch (Exception e){
      Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §c" + e.getMessage()));
      if(Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()){
        e.printStackTrace();
      }
    }
  }

  @Override
  public @Nullable ClassLoader getClassLoader() {
    return null;
  }

  @Override
  public @Nullable InstalledAddonInfo getAddon() {
    return ScriptingAddon.INSTANCE.addonInfo();
  }

  @Override
  public @Nullable Object getListener() {
    return null;
  }

  @Override
  public byte getPriority() {
    return 0;
  }

  @Override
  public @Nullable Method getMethod() {
    return null;
  }

  @Override
  public @NotNull Class<?> getEventType() {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      Laby.labyAPI().chatProvider().chatController().addMessage(Component.text("§r[§6ScriptingAddon§r] §c" + e.getMessage()));
      if(Laby.labyAPI().labyModLoader().isDevelopmentEnvironment()){
        e.printStackTrace();
      }
      return Event.class;
    }
  }

  @Override
  public @Nullable LabyEvent getLabyEvent() {
    return null;
  }

  @Override
  public boolean isInClassLoader(ClassLoader other) {
    return false;
  }

  @Override
  public SubscribeMethod copy(Object newListener) {
    return null;
  }

  public Consumer<Event> getHandler() {
    return handler;
  }

  public String getClassName() {
    return className;
  }
}
