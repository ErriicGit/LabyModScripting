Script.bind("Laby", "net.labymod.api.Laby");
Chat = Laby.labyAPI().chatProvider().chatController();
Script.bind("Component", "net.labymod.api.client.component.Component");
function log(msg) {
  Chat.addMessage(Component.text(msg));
}
log('§aTEST');