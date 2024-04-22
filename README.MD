# LabyModScripting

This Addon for LabyMod 4 allows you to run JavaScript inside of Minecraft.
***
## Command
There is only one simple command for loading, starting and stopping the scripts.
```
/script <load/start/stop/list> <name>
```
You can download the test.js from above and load it with
```
/script load full/path/to/test.js
```
You can then view the current Status of it by running
```
/script list
```
Now you can start it using
```
/script start test.js
```
If it says ```TEST``` in chat it successfully ran.

You can now stop it with
```
/script stop test.js
```
***
## Classes
There is a handful of Classes that allow you to interact with the Game.  
So far there is:
>### Script
> For interaction with the own script and adding Commands as well as Listeners
> - ``bind(String binding, String className)`` This method is used to bind Classes. It creates a javascript interface for the java class using reflections
> - ``start()`` To start the Script. Throws ``IllegalStateException``
> - ``stop()`` To stop the Script. Throws ``IllegalStateException``
> - ``registerEvent(String className, Consumer<Event> handler)`` To register a new Event-Listener. Returns ``CustomSubscribeMethod``
> - ``registerCommand(String prefix, BiConsumer<String, String[]> handler, String... aliases)`` To register a new Command. Returns ``CustomCommand``
> - ``unregisterCommand(CustomCommand cmd)`` To unregister a Command. This only works for Commands that were registered from this script
> - ``unregisterEvent(CustomSubscribeMethod csm)`` To unregister an Event-Listener. This only works for Listeners that were registered form this script
> - ``unregisterAllCommands()`` To unregister all Commands that were registered from this script
> - ``unregisterAllEvents()`` To unregister all Event-Listeners that were registered from this script
> - ``isRunning()`` To check whether the script is running or not. Returns ``boolean``
> - ``getLocation()`` To get the file location of the script. Returns ``Path``
> - ``getName()`` To get the name of the script. Returns ``String``
> - ``getRoot()`` To get the root path of the script. Returns ``Path``
> - ``getScript()`` To get the whole script as one string. Returns ``String``
> - ``getAddonVersion()`` To get the Version of the LabyModScripting-Addon. Can be used to check compatibility. Returns ``String``
> - ``getClass(String className)`` To get the class associated with the given name. For primitives this will return the corresponding wrapper class
> - ``getPrimitiveClass(String className)`` Works similar to ``getClass(String className)`` but returns the actual primitive class
> #### Examples
> ##### Bindings
> ```
> Script.bind("Laby", "net.labymod.api.Laby");
> ```
> ##### Events
> ```
> worldEvent = Script.registerEvent("net.labymod.api.event.client.world.WorldEnterEvent", (event) -> {
>   console.log("Hello World");
> });
> Script.unregisterEvent(worldEvent);
> ```
> ##### Commands
> ```
> customCommand = Script.registerCommand("customcommand", (prefix, args) => {
>     console.log("You entered the custom command");
> });
> Script.unregisterCommand(customCommand);
> ```

## Planed Features
- A text-editor with syntax-highlighting and suggestions
- Installation of scripts
- An easy way to add and access settings fom scripts