package me.erriic.labymodscripting.bridge;

import java.util.Collection;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.api.client.world.food.FoodData;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;

public class PlayerBridge implements ClientPlayer {

  private ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();

  @Override
  public boolean isAbilitiesFlying() {
    return player.isAbilitiesFlying();
  }

  @Override
  public float getAbilitiesWalkingSpeed() {
    return player.getAbilitiesWalkingSpeed();
  }

  @Override
  public boolean isHandActive() {
    return player.isHandActive();
  }

  @Override
  public boolean isUsingBow() {
    return player.isUsingBow();
  }

  @Override
  public void swingArm(Hand hand) {
    player.swingArm(hand);
  }

  @Override
  public void swingArm(Hand hand, boolean cancelPacket) {
    player.swingArm(hand, cancelPacket);
  }

  @Override
  public Inventory inventory() {
    return player.inventory();
  }

  @Override
  public void setDistanceWalked(float distance) {
    player.setDistanceWalked(distance);
  }

  @Override
  public float getForwardMovingSpeed() {
    return player.getForwardMovingSpeed();
  }

  @Override
  public float getStrafeMovingSpeed() {
    return player.getStrafeMovingSpeed();
  }

  @Override
  public boolean isSwingingHand() {
    return player.isSwingingHand();
  }

  @Override
  public float getArmSwingProgress() {
    return player.getArmSwingProgress();
  }

  @Override
  public float getPreviousArmSwingProgress() {
    return player.getPreviousArmSwingProgress();
  }

  @Override
  public boolean isDead() {
    return player.isDead();
  }

  @Override
  public String getName() {
    return player.getName();
  }

  @Override
  public float getChasingPosX() {
    return player.getChasingPosX();
  }

  @Override
  public float getPreviousChasingPosX() {
    return player.getPreviousChasingPosX();
  }

  @Override
  public float getChasingPosY() {
    return player.getChasingPosY();
  }

  @Override
  public float getPreviousChasingPosY() {
    return player.getPreviousChasingPosY();
  }

  @Override
  public float getChasingPosZ() {
    return player.getChasingPosZ();
  }

  @Override
  public float getPreviousChasingPosZ() {
    return player.getPreviousChasingPosZ();
  }

  @Override
  public float getRenderYawOffset() {
    return player.getRenderYawOffset();
  }

  @Override
  public float getPreviousRenderYawOffset() {
    return player.getPreviousRenderYawOffset();
  }

  @Override
  public float getCameraYaw() {
    return player.getCameraYaw();
  }

  @Override
  public float getPreviousCameraYaw() {
    return player.getPreviousCameraYaw();
  }

  @Override
  public float getPreviousWalkDistance() {
    return player.getPreviousWalkDistance();
  }

  @Override
  public float getWalkDistance() {
    return player.getWalkDistance();
  }

  @Override
  public float getPreviousRotationHeadYaw() {
    return player.getPreviousRotationHeadYaw();
  }

  @Override
  public float getRotationHeadYaw() {
    return player.getRotationHeadYaw();
  }

  @Override
  public float getPreviousLimbSwingStrength() {
    return player.getPreviousLimbSwingStrength();
  }

  @Override
  public float getLimbSwingStrength() {
    return player.getLimbSwingStrength();
  }

  @Override
  public float getLimbSwing() {
    return player.getLimbSwing();
  }

  @Override
  public float getRenderTick(float partialTicks) {
    return player.getRenderTick(partialTicks);
  }

  @Override
  public boolean isShownModelPart(PlayerClothes part) {
    return player.isShownModelPart(part);
  }

  @Override
  public double getSprintingSpeed() {
    return player.getSprintingSpeed();
  }

  @Override
  public ResourceLocation skinTexture() {
    return player.skinTexture();
  }

  @Override
  public @Nullable ResourceLocation getCloakTexture() {
    return player.getCloakTexture();
  }

  @Override
  public boolean isSlim() {
    return player.isSlim();
  }

  @Override
  public GameMode gameMode() {
    return player.gameMode();
  }

  @Override
  public boolean isWearingTop() {
    return player.isWearingTop();
  }

  @Override
  public GameProfile profile() {
    return player.profile();
  }

  @Override
  public FoodData foodData() {
    return player.foodData();
  }

  @Override
  public PlayerAbilities abilities() {
    return player.abilities();
  }

  @Override
  public int getExperienceLevel() {
    return player.getExperienceLevel();
  }

  @Override
  public int getTotalExperience() {
    return player.getTotalExperience();
  }

  @Override
  public float getExperienceProgress() {
    return player.getExperienceProgress();
  }

  @Override
  public int getExperienceNeededForNextLevel() {
    return player.getExperienceNeededForNextLevel();
  }

  @Override
  public PlayerModel playerModel() {
    return player.playerModel();
  }

  @Override
  public GameUser gameUser() {
    return player.gameUser();
  }

  @Override
  public float getHealth() {
    return player.getHealth();
  }

  @Override
  public float getMaximalHealth() {
    return player.getMaximalHealth();
  }

  @Override
  public float getAbsorptionHealth() {
    return player.getAbsorptionHealth();
  }

  @Override
  public int getItemUseDurationTicks() {
    return player.getItemUseDurationTicks();
  }

  @Override
  public Hand getUsedItemHand() {
    return player.getUsedItemHand();
  }

  @Override
  public ItemStack getMainHandItemStack() {
    return player.getMainHandItemStack();
  }

  @Override
  public ItemStack getOffHandItemStack() {
    return player.getOffHandItemStack();
  }

  @Override
  public ItemStack getRightHandItemStack() {
    return player.getRightHandItemStack();
  }

  @Override
  public ItemStack getLeftHandItemStack() {
    return player.getLeftHandItemStack();
  }

  @Override
  public ItemStack getEquipmentItemStack(EquipmentSpot equipmentSpot) {
    return player.getEquipmentItemStack(equipmentSpot);
  }

  @Override
  public float getBodyRotationY() {
    return 0;
  }

  @Override
  public float getPreviousBodyRotationY() {
    return 0;
  }

  @Override
  public void setBodyRotationY(float rotationY) {

  }

  @Override
  public void setPreviousBodyRotationY(float rotationY) {

  }

  @Override
  public float getHeadRotationY() {
    return 0;
  }

  @Override
  public float getPreviousHeadRotationY() {
    return 0;
  }

  @Override
  public void setHeadRotationY(float rotationY) {

  }

  @Override
  public void setPreviousHeadRotationY(float rotationY) {

  }

  @Override
  public boolean isSleeping() {
    return player.isSleeping();
  }

  @Override
  public int getHurtTime() {
    return player.getHurtTime();
  }

  @Override
  public int getDeathTime() {
    return player.getDeathTime();
  }

  @Override
  public boolean isHostile() {
    return player.isHostile();
  }

  @Override
  public Collection<PotionEffect> getActivePotionEffects() {
    return player.getActivePotionEffects();
  }

  @Override
  public float getPosX() {
    return player.getPosX();
  }

  @Override
  public float getPreviousPosX() {
    return player.getPreviousPosX();
  }

  @Override
  public float getPosY() {
    return player.getPosY();
  }

  @Override
  public float getPreviousPosY() {
    return player.getPreviousPosY();
  }

  @Override
  public float getPosZ() {
    return player.getPosZ();
  }

  @Override
  public float getPreviousPosZ() {
    return player.getPreviousPosZ();
  }

  @Override
  public boolean isCrouching() {
    return player.isCrouching();
  }

  @Override
  public boolean isInvisible() {
    return player.isInvisible();
  }

  @Override
  public boolean isSprinting() {
    return player.isSprinting();
  }

  @Override
  public UUID getUniqueId() {
    return player.getUniqueId();
  }

  @Override
  public AxisAlignedBoundingBox axisAlignedBoundingBox() {
    return player.axisAlignedBoundingBox();
  }

  @Override
  public FloatVector3 perspectiveVector(float partialTicks) {
    return player.perspectiveVector(partialTicks);
  }

  @Override
  public EntityPose entityPose() {
    return player.entityPose();
  }

  @Override
  public boolean canEnterEntityPose(EntityPose pose) {
    return player.canEnterEntityPose(pose);
  }

  @Override
  public float getEyeHeight() {
    return player.getEyeHeight();
  }

  @Override
  public DataWatcher dataWatcher() {
    return player.dataWatcher();
  }

  @Override
  public TagType nameTagType() {
    return player.nameTagType();
  }

  @Override
  public void setNameTagType(TagType type) {
    player.setNameTagType(type);
  }

  @Override
  public void setRendered(boolean rendered) {
    player.setRendered(rendered);
  }

  @Override
  public boolean isRendered() {
    return player.isRendered();
  }

  @Override
  public boolean isInWater() {
    return player.isInWater();
  }

  @Override
  public boolean isInLava() {
    return player.isInLava();
  }

  @Override
  public boolean isUnderWater() {
    return player.isUnderWater();
  }

  @Override
  public boolean isOnFire() {
    return player.isOnFire();
  }

  @Override
  public boolean isOnGround() {
    return player.isOnGround();
  }

  @Override
  public Entity getVehicle() {
    return player.getVehicle();
  }
}
