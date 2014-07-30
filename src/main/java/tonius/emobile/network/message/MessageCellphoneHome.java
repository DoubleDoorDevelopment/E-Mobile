package tonius.emobile.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import tonius.emobile.config.EMConfig;
import tonius.emobile.item.ItemCellphone;
import tonius.emobile.session.CellphoneSessionLocation;
import tonius.emobile.session.CellphoneSessionsHandler;
import tonius.emobile.util.ServerUtils;
import tonius.emobile.util.StringUtils;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageCellphoneHome implements IMessage, IMessageHandler<MessageCellphoneHome, IMessage> {

    private String player;

    public MessageCellphoneHome() {
    }

    public MessageCellphoneHome(String player) {
        this.player = player;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.player);
    }

    @Override
    public IMessage onMessage(MessageCellphoneHome msg, MessageContext ctx) {
        if (EMConfig.allowTeleportHome) {
            EntityPlayerMP player = ServerUtils.getPlayerOnServer(msg.player);
            if (player == null) {
                return null;
            } else if (player.worldObj.provider.dimensionId != 0) {
                ServerUtils.sendChatToPlayer(player.getCommandSenderName(), StringUtils.LIGHT_RED + StringUtils.translate("chat.cellphone.tryStart.overworld"));
                return null;
            } else {
                ChunkCoordinates bed = player.getBedLocation(0);
                if (bed != null)
                    bed = player.worldObj.getBlock(bed.posX, bed.posY, bed.posZ).getBedSpawnPosition(player.worldObj, bed.posX, bed.posY, bed.posZ, player);
                if (bed != null) {
                    if (!CellphoneSessionsHandler.isPlayerInSession(player)) {
                        ItemStack heldItem = player.getCurrentEquippedItem();
                        if (heldItem != null && heldItem.getItem() instanceof ItemCellphone) {
                            if (((ItemCellphone) heldItem.getItem()).usePearl(heldItem, player)) {
                                player.worldObj.playSoundAtEntity(player, "emobile:phonecountdown", 1.0F, 1.0F);
                                new CellphoneSessionLocation(8, "chat.cellphone.location.home", player, 0, bed.posX, bed.posY, bed.posZ);
                            }
                        }
                    }
                } else {
                    ServerUtils.sendChatToPlayer(player.getCommandSenderName(), StringUtils.LIGHT_RED + StringUtils.translate("chat.cellphone.tryStart.bedmissing"));
                }
            }
        }

        return null;
    }
}
