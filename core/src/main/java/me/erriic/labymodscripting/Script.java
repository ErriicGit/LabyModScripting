package me.erriic.labymodscripting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.event.Event;
import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

public class Script {

  private static HashMap<String, Script> scripts = new HashMap<>();

  public static final Engine engine = Engine.newBuilder("js").option("engine.WarnInterpreterOnly", "false").build();

  private Context ctx;
  private String script;
  private String name;
  private Path location;
  private Path root;
  private List<CustomCommand> commands = new ArrayList<>();
  private List<CustomSubscribeMethod> events = new ArrayList<>();
  boolean running;

  public static Script create(String name, String script) throws IllegalStateException{
    if(scripts.containsKey(name.toLowerCase())){
      throw new IllegalStateException("A script with the same name already exists");
    }
    Script s = new Script(name.toLowerCase(), script);
    s.createNewContext();
    scripts.put(name.toLowerCase(), s);
    return s;
  }

  public static Script create(String name, String script, Path location) throws IllegalStateException{
    if(scripts.containsKey(name.toLowerCase())){
      throw new IllegalStateException("A script with the same name already exists");
    }
    Script s = new Script(name.toLowerCase(), script, location);
    s.createNewContext();
    scripts.put(name.toLowerCase(), s);
    return s;
  }

  public static Script create(String name, String script, Path location, Path root) throws IllegalStateException{
    if(scripts.containsKey(name.toLowerCase())){
      throw new IllegalStateException("A script with the same name already exists");
    }
    Script s = new Script(name.toLowerCase(), script, location, root);
    s.createNewContext();
    scripts.put(name.toLowerCase(), s);
    return s;
  }

  public static HashMap<String, Script> getScripts(){
    return new HashMap<>(scripts);
  }

  private Script(String name, String script){
    this.name = name;
    this.script = script;
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
      throw new IllegalStateException("Script is already running");
    }
    if(ctx==null){
      createNewContext();
    }
    this.running = true;
    this.ctx.eval("js", this.script);
  }

  private void createNewContext(){
    this.ctx = Context.newBuilder()
        .engine(engine)
        .allowAllAccess(true)
        .option("js.ecmascript-version", "13")
        .option("js.commonjs-require", "true")
        .option("js.commonjs-require-cwd", Constants.Files.CONFIGS.toAbsolutePath().toString())
        .build();
    Value jsBindings = this.ctx.getBindings("js");
    jsBindings.putMember("Script", this);
  }

  public void stop() throws IllegalStateException{
    if(running&&ctx!=null){
      this.unregisterAllCommands();
      this.unregisterAllEvents();
      this.ctx.close(true);
      this.ctx = null;
      running = false;
    }
    else {
      throw new IllegalStateException("Script isn't running");
    }
  }

  private static String readJs(Path path) throws IllegalStateException, IllegalArgumentException, IOException {
    if(!Files.isRegularFile(path)){
      throw new IllegalArgumentException("The provided path is not a regular file");
    }
    if(!path.toString().toLowerCase().endsWith(".js")){
      throw new IllegalArgumentException("The file does not have a .js extension");
    }
    File f = path.toFile();
    FileInputStream fis = new FileInputStream(f);
    return IOUtils.toString(fis, "UTF-8");
  }

  public static Script load(Path path) throws IllegalStateException, IllegalArgumentException, IOException {
    path = path.toAbsolutePath();
    Script script = Script.create(path.getFileName().toString(), readJs(path), path);
    return script;
  }

  public void reload() throws IllegalStateException, IllegalArgumentException, IOException {
    if(running){
      throw new IllegalStateException("Script has to be stopped first");
    }
    if(this.location==null){
      throw new IllegalStateException("This Script is not associated with any File");
    }
    this.script = readJs(this.location);
    this.createNewContext();
  }

  public void unload() throws IllegalStateException{
    if(running){
      throw new IllegalStateException("Script has to be stopped first");
    }
    scripts.remove(this.name);
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

  public void bind(String binding, String className) throws ClassNotFoundException, IllegalStateException {
    if(ctx==null){
      throw new IllegalStateException("Script not loaded");
    }
    //TODO: add all methods with the name newInstance or field_*
    String code = binding + " = class {";
    Class<?> c = Class.forName(className);
    Method[] methods = c.getMethods();
    Constructor[] constructors = c.getConstructors();
    Field[] finalFields = Arrays.stream(c.getFields())
        .filter(field -> Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))
        .toArray(Field[]::new);
    Field[] fields = Arrays.stream(c.getFields())
        .filter(field -> Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()))
        .toArray(Field[]::new);
    Map<String, List<Method>> methodMap = new HashMap<>();
    for (Method method : methods) {
      if(Modifier.isStatic(method.getModifiers())) {
        methodMap.computeIfAbsent(method.getName(), k -> new ArrayList<>()).add(method);
      }
    }
    //finalFields
    for(Field field : finalFields){
      code += " static " + field.getName() + " = Script.getClass('" + field.getType().getName() + "').cast(Script.getClass('" + className + "').getField('" + field.getName() + "').get(null));";
    }
    //Fileds
    for (Field field : fields){
      code += " static field_" + field.getName() + "(...args) { if(args.length==1) { return Script.getClass('" + field.getType().getName() + "').cast(Script.getClass('" + className + "').getField('" + field.getName() + "').set(null, args[0]))}"
          + " else { return Script.getClass('" + field.getType().getName() + "').cast(Script.getClass('" + className + "').getField('" + field.getName() + "').get(null));}}";
    }
    //constructor
    code += " static newInstance(...args){";
    for(int i = 0; i < constructors.length; i++){
      Constructor con = constructors[i];
      if (i == 0) {
        code += "if(";
      } else {
        code += "else if(";
      }
      code += con.getParameters().length==0?"true":"";
      for(int i2 = 0; i2 < con.getParameters().length; i2++){
        Parameter par = con.getParameters()[i2];
        if(i2>0){
          code += "&&";
        }
        code += "args[" + i2 + "] instanceof Script.getPrimitiveClass('" + par.getType().getName() + "')";
      }
      code += "){ return Script.getClass('" + className + "').cast(Script.getClass('" + className + "').getConstructor(";
      for(int i2 = 0; i2 < con.getParameters().length; i2++){
        if(i2>0){code+=", ";}
        Parameter par = con.getParameters()[i2];
        code += "Script.getPrimitiveClass('" + par.getType().getName() + "')";
      }
      code += ").newInstance(";
      for(int i2 = 0; i2 < con.getParameters().length; i2++){
        if(i2>0){code+=", ";}
        code += "args[" + i2 + "]";
      }
      code += "));}";
    }
    code +="}";

    //methods
    for (Map.Entry<String, List<Method>> entry : methodMap.entrySet()) {
      String methodName = entry.getKey();
      List<Method> overloadedMethods = entry.getValue();
      code += " static " + methodName + "(...args){";
      for (int i = 0; i < overloadedMethods.size(); i++) {
        Method meth = overloadedMethods.get(i);
        if (i == 0) {
          code += "if(";
        } else {
          code += "else if(";
        }
        code += meth.getParameters().length==0?"true":"";
        for(int i2 = 0; i2 < meth.getParameters().length; i2++){
          Parameter par = meth.getParameters()[i2];
          if(i2>0){
            code += "&&";
          }
          code += "args[" + i2 + "] instanceof Script.getPrimitiveClass('" + par.getType().getName() + "')";
        }
        code += "){ return Script.getClass('" + meth.getReturnType().getName() + "').cast(Script.getClass('" + className + "').getMethod('" + methodName + "'";
        for(int i2 = 0; i2 < meth.getParameters().length; i2++){
          Parameter par = meth.getParameters()[i2];
          code += ", Script.getPrimitiveClass('" + par.getType().getName() + "')";
        }
        code += ").invoke(null";
        for(int i2 = 0; i2 < meth.getParameters().length; i2++){
          code += ", args[" + i2 + "]";
        }
        code += "));}";
      }
      code += "}";
    }
    code += "}";
    ctx.eval("js", code);
  }

  public void eval(String script) throws IllegalStateException{
    if(ctx==null){
      throw new IllegalStateException("Script not loaded");
    }
    this.ctx.eval("js", script);
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

  public Class getPrimitiveClass(String name) throws ClassNotFoundException {
    switch (name) {
      case "boolean":
        return boolean.class;
      case "byte":
        return byte.class;
      case "short":
        return short.class;
      case "char":
        return char.class;
      case "int":
        return int.class;
      case "long":
        return long.class;
      case "float":
        return float.class;
      case "double":
        return double.class;
      default:
        return Class.forName(name);
    }
  }

  public Class getClass(String name) throws ClassNotFoundException {
    switch (name) {
      case "boolean":
        return Boolean.class;
      case "byte":
        return Byte.class;
      case "short":
        return Short.class;
      case "char":
        return Character.class;
      case "int":
        return Integer.class;
      case "long":
        return Long.class;
      case "float":
        return Float.class;
      case "double":
        return Double.class;
      default:
        return Class.forName(name);
    }
  }
}
