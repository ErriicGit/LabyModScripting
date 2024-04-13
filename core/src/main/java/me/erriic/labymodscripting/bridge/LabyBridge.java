package me.erriic.labymodscripting.bridge;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.LivingEntity.Hand;

public class LabyBridge {
  public LabyAPI labyAPI(){
    Laby.labyAPI().minecraft().getClientPlayer().swingArm(Hand.MAIN_HAND);
    return Laby.labyAPI();
  }
}
