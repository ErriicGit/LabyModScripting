package me.erriic.labymodscripting.bridge;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatController;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.chat.ChatMessage.Builder;
import net.labymod.api.client.component.Component;

public class ChatBridge implements ChatController {

  private ChatController chatController = Laby.labyAPI().chatProvider().chatController();

  public String log(String message){
    chatController.addMessage(Component.text(message));
    return message;
  }
  public Component log(Component message){
    chatController.addMessage(message);
    return message;
  }

  @Override
  public int getMaxHistorySize() {
    return chatController.getMaxHistorySize();
  }

  @Override
  public List<ChatMessage> getMessages() {
    return chatController.getMessages();
  }

  @Override
  public ChatMessage addMessage(Component component) {
    return chatController.addMessage(component);
  }

  @Override
  public ChatMessage addMessage(Builder builder) {
    return chatController.addMessage(builder);
  }

  @Override
  public ChatMessage addMessage(ChatMessage chatMessage) {
    return chatController.addMessage(chatMessage);
  }

  @Override
  public void clear() {
    chatController.clear();
  }

  @Override
  public ChatMessage messageAt(int index) {
    return chatController.messageAt(index);
  }
}
