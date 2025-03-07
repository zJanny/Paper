package io.papermc.paper.event.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Holds information for living entity movement events
 * <p>
 * Does not fire for players; use {@link PlayerMoveEvent} for player movement.
 */
@NullMarked
public class EntityMoveEvent extends EntityEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private Location from;
    private Location to;

    private boolean cancelled;

    @ApiStatus.Internal
    public EntityMoveEvent(final LivingEntity entity, final Location from, final Location to) {
        super(entity);
        this.from = from;
        this.to = to;
    }

    @Override
    public LivingEntity getEntity() {
        return (LivingEntity) super.getEntity();
    }

    /**
     * Gets the location this entity moved from
     *
     * @return Location the entity moved from
     */
    public Location getFrom() {
        return this.from;
    }

    /**
     * Sets the location to mark as where the entity moved from
     *
     * @param from New location to mark as the entity's previous location
     */
    public void setFrom(final Location from) {
        this.validateLocation(from);
        this.from = from.clone();
    }

    /**
     * Gets the location this entity moved to
     *
     * @return Location the entity moved to
     */
    public Location getTo() {
        return this.to;
    }

    /**
     * Sets the location that this entity will move to
     *
     * @param to New Location this entity will move to
     */
    public void setTo(final Location to) {
        this.validateLocation(to);
        this.to = to.clone();
    }

    /**
     * Check if the entity has changed position (even within the same block) in the event
     *
     * @return whether the entity has changed position or not
     */
    public boolean hasChangedPosition() {
        return this.hasExplicitlyChangedPosition() || !this.from.getWorld().equals(this.to.getWorld());
    }

    /**
     * Check if the entity has changed position (even within the same block) in the event, disregarding a possible world change
     *
     * @return whether the entity has changed position or not
     */
    public boolean hasExplicitlyChangedPosition() {
        return this.from.getX() != this.to.getX() || this.from.getY() != this.to.getY() || this.from.getZ() != this.to.getZ();
    }

    /**
     * Check if the entity has moved to a new block in the event
     *
     * @return whether the entity has moved to a new block or not
     */
    public boolean hasChangedBlock() {
        return this.hasExplicitlyChangedBlock() || !this.from.getWorld().equals(this.to.getWorld());
    }

    /**
     * Check if the entity has moved to a new block in the event, disregarding a possible world change
     *
     * @return whether the entity has moved to a new block or not
     */
    public boolean hasExplicitlyChangedBlock() {
        return this.from.getBlockX() != this.to.getBlockX() || this.from.getBlockY() != this.to.getBlockY() || this.from.getBlockZ() != this.to.getBlockZ();
    }

    /**
     * Check if the entity has changed orientation in the event
     *
     * @return whether the entity has changed orientation or not
     */
    public boolean hasChangedOrientation() {
        return this.from.getPitch() != this.to.getPitch() || this.from.getYaw() != this.to.getYaw();
    }

    private void validateLocation(final Location loc) {
        Preconditions.checkArgument(loc != null, "Cannot use null location!");
        Preconditions.checkArgument(loc.getWorld() != null, "Cannot use null location with null world!");
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
