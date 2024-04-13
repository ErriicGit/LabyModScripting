package me.erriic.labymodscripting.bridge;

import net.labymod.api.client.entity.LivingEntity.Hand;

public class HandBridge {
  public Hand MAIN_HAND = Hand.MAIN_HAND;
  public Hand OFF_HAND = Hand.OFF_HAND;

  public Hand valueOf(String value){
    return Hand.valueOf(value);
  }

  public Hand[] values(){
    return Hand.values();
  }

}
