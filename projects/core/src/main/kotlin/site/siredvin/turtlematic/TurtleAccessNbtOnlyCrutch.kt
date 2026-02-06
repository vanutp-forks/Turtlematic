package site.siredvin.turtlematic

import dan200.computercraft.api.turtle.*
import dan200.computercraft.api.upgrades.UpgradeData
import dan200.computercraft.shared.turtle.items.TurtleItem
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

@Suppress("NonExtendableApiUsage")
class TurtleAccessNbtOnlyCrutch(private val stack: ItemStack) : ITurtleAccess {
    private val turtle = stack.item as TurtleItem

    private fun panic(): Nothing = throw NotImplementedError("Tried to call a method on TurtleAccessNbtOnlyCrutch")

    override fun getLevel() = panic()
    override fun getPosition() = panic()
    override fun isRemoved() = panic()
    override fun teleportTo(world: Level?, pos: BlockPos?) = panic()
    override fun getDirection() = panic()
    override fun setDirection(dir: Direction?) = panic()
    override fun getSelectedSlot() = panic()
    override fun setSelectedSlot(slot: Int) = panic()
    override fun setColour(colour: Int) = panic()
    override fun getColour() = panic()
    override fun getOwningPlayer() = panic()
    override fun getInventory() = panic()
    override fun isFuelNeeded() = panic()
    override fun getFuelLevel() = panic()
    override fun setFuelLevel(fuel: Int) = panic()
    override fun getFuelLimit() = panic()
    override fun consumeFuel(fuel: Int) = panic()
    override fun addFuel(fuel: Int) = panic()
    override fun executeCommand(command: TurtleCommand?) = panic()
    override fun playAnimation(animation: TurtleAnimation?) = panic()
    override fun getUpgrade(side: TurtleSide?) = panic()
    override fun setUpgradeWithData(side: TurtleSide?, upgrade: UpgradeData<ITurtleUpgrade?>?) = panic()
    override fun getPeripheral(side: TurtleSide?) = panic()

    override fun getUpgradeNBTData(side: TurtleSide) = turtle.getUpgradeWithData(stack, side)?.data
    override fun updateUpgradeNBTData(side: TurtleSide?) = panic()
}
