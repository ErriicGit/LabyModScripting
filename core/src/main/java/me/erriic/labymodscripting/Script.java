package me.erriic.labymodscripting;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import me.erriic.labymodscripting.bridge.ChatBridge;
import me.erriic.labymodscripting.bridge.ComponentBridge;
import me.erriic.labymodscripting.bridge.FileSystemBridge;
import me.erriic.labymodscripting.bridge.HandBridge;
import me.erriic.labymodscripting.bridge.LabyBridge;
import me.erriic.labymodscripting.bridge.MethodBridge;
import me.erriic.labymodscripting.bridge.RequestBridge;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.event.Event;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class Script {

  private static List<Script> scripts = new ArrayList<>();

  private Context ctx;
  private String script;
  private String name;
  private Path location;
  private Path root;
  private List<CustomCommand> commands = new ArrayList<>();
  private List<CustomSubscribeMethod> events = new ArrayList<>();
  boolean running;

  public static Script create(String name, String script){
    Script s = new Script(name, script);
    scripts.add(s);
    return s;
  }

  public static Script create(String name, String script, Path location){
    Script s = new Script(name, script, location);
    scripts.add(s);
    return s;
  }

  public static Script create(String name, String script, Path location, Path root){
    Script s = new Script(name, script, location, root);
    scripts.add(s);
    return s;
  }

  public static List<Script> getScripts(){
    return new ArrayList<>(scripts);
  }

  private Script(String name, String script){
    this.name = name;
    this.script = script;
    this.ctx = Context.newBuilder().allowAllAccess(true).option("js.ecmascript-version", "13").option("js.commonjs-require", "true").option("js.commonjs-require-cwd", Constants.Files.CONFIGS.toAbsolutePath().toString()).build();
    Value jsBindings = this.ctx.getBindings("js");
    jsBindings.putMember("Script", this);
    jsBindings.putMember("Laby", new LabyBridge());
    jsBindings.putMember("Component", new ComponentBridge());
    jsBindings.putMember("Chat", new ChatBridge());
    jsBindings.putMember("Hand", new HandBridge());
    jsBindings.putMember("Request", new RequestBridge());
    jsBindings.putMember("Method", new MethodBridge());
    jsBindings.putMember("FileSystem", new FileSystemBridge());
    Value polyglotBindings = this.ctx.getPolyglotBindings();
  }

  private Script(String name, String script, Path location){
    this(name, script);
    this.location = location;
  }

  private Script(String name, String script, Path location, Path root){
    this(name, script, location);
    this.root = root;
  }

  public void start() throws IllegalStateException{
    if(running){
      throw new IllegalStateException("Script already running");
    }
    this.running = true;
    this.ctx.eval("js", this.script);
  }

  public void stop() throws IllegalStateException{
    if(running){
      this.unregisterAllCommands();
      this.unregisterAllEvents();
      this.ctx.close(true);
      Script.scripts.remove(this);
    }
    else {
      throw new IllegalStateException("Script isn't running");
    }
  }

  public CustomSubscribeMethod registerEvent(String className, Consumer<Event> handler){
    CustomSubscribeMethod csm = new CustomSubscribeMethod(className, handler);
    Laby.labyAPI().eventBus().registry().register(csm);
    this.events.add(csm);
    return csm;
  }

  public CustomCommand registerCommand(String prefix, BiConsumer<String, String[]> handler, String... aliases){
    CustomCommand cmd = new CustomCommand(prefix, handler, aliases);
    Laby.labyAPI().commandService().register(cmd);
    this.commands.add(cmd);
    return cmd;
  }

  public void unregisterCommand(CustomCommand cmd){
    Laby.labyAPI().commandService().unregister(cmd);
    this.commands.remove(cmd);
  }

  public void unregisterEvent(CustomSubscribeMethod csm){
    Laby.labyAPI().eventBus().registry().unregister(csm);
    this.events.remove(csm);
  }

  public void unregisterAllCommands(){
    this.commands.forEach((customCommand -> Laby.labyAPI().commandService().unregister(customCommand)));
    this.commands.clear();
  }

  public void unregisterAllEvents(){
    this.events.forEach((event) -> Laby.labyAPI().eventBus().registry().unregister(event));
    this.events.clear();
  }

  public boolean isRunning() {
    return running;
  }
  public Path getLocation() {
    return location;
  }

  public String getName() {
    return name;
  }

  public Path getRoot() {
    return root;
  }

  public String getScript() {
    return script;
  }

  public String getAddonVersion() {
    return ScriptingAddon.VERSION;
  }
}
