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
>### Chat
> For accessing the Chat
> - ``log(String message)`` To display a simple string in chat
> - ``log(Component message)`` To display a component in chat
> - ``getMaxHistorySize()`` To get the maximum size of the chat history. Returns ``int``
> - ``getMessages()`` To get a list of all messages. Returns ``List<ChatMessage>``
> - ``addMessage(Compontent message)`` To display a component in chat. Returns ``ChatMessage``
> - ``addMessage(Builder builder)`` To display a message in chat. Returns ``ChatMessage``
> - ``addMessage(ChatMessage message)`` To display a message in chat. Returns ``ChatMessage``
> - ``clear()`` To clear the chat.
> - ``messageAt(int index)`` To get the message at a certain position. Returns ``ChatMessage``
> #### Example
> ```
> Chat.log("Hello World");
> ```

>### Component
> For creating Components
> - ``text(String text)`` To get a component from a String. Returns TextComponent
> #### Example
> ```
> Chat.log(Component.text("Hello World"));
> ```

>### FileSystem
> For accessing the FileSystem
> - ``writeString(Path path, String string, OpenOption option)`` To write a string to a text file. Returns ``Path``

>### Laby
> For accessing the LabyMod API
> - ``labyAPI()`` To get access to the LabyMod-API. Returns ``LabyAPI``

>### Script
> For interaction with the own script and adding Commands as well as Listeners
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
> #### Example
> ```
> worldEvent = Script.registerEvent("net.labymod.api.event.client.world.WorldEnterEvent", (event) -> {
>   Chat.log("Hello World");
> });
> Script.unregisterEvent(worldEvent);
> ```

>### Hand
> Hand selection
> - ``MAIN_HAND`` Is ``Hand``
> - ``OFF_HAND`` Is ``Hand``
> - ``valueOf(String hand)`` Returns ``Hand``
> - ``values()`` Returns ``Hand[]``

>### Request
> For making http requests. It uses the LabyMod Requests, thus having the same syntax.
> - **TODO**
>#### Example
> ```
> Request.ofString().method(Method.GET).async().url("https://example.com").readTimeout(3000).connectTimeout(3000).execute((response) => {
>   Chat.log(response.get());
> });
> ```

>### Method
> A method for a Request like GET or POST
> - ``GET``
> - ``HEAD``
> - ``CONNECT``
> - ``DELETE``
> - ``OPTIONS``
> - ``PATCH``
> - ``POST``
> - ``PUT``
> - ``TRACE``

>### Player
> For accessing the Player. A shortcut for ``Laby.labyAPI().minecraft().getClientPlayer()``
> - **TODO**
> - >#### Example
> ```
> Player.swingArm(Hand.MAIN_HAND);
> ```

## Planed Features
- Async script execution
- A text-editor with syntax-highlighting and suggestions
- Add more methods to ``Component``
- Add more methods to ``FileSystem``