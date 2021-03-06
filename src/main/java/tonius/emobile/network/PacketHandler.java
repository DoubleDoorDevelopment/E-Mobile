package tonius.emobile.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import tonius.emobile.EMobile;
import tonius.emobile.network.message.toclient.MessageConfigSync;
import tonius.emobile.network.message.toclient.MessageDiallingParticles;
import tonius.emobile.network.message.toclient.MessageDiallingSound;
import tonius.emobile.network.message.toclient.MessageTeleportParticles;
import tonius.emobile.network.message.toserver.*;

public class PacketHandler {

    public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(EMobile.MODID);

    public static void preInit() {
        EMobile.logger.info("Registering network messages");
        instance.registerMessage(MessageCellphonePlayer.class, MessageCellphonePlayer.class, 0, Side.SERVER);
        instance.registerMessage(MessageCellphoneAuthorize.class, MessageCellphoneAuthorize.class, 1, Side.SERVER);
        instance.registerMessage(MessageCellphoneSpawn.class, MessageCellphoneSpawn.class, 2, Side.SERVER);
        instance.registerMessage(MessageCellphoneHome.class, MessageCellphoneHome.class, 3, Side.SERVER);
        instance.registerMessage(MessageCellphoneCancel.class, MessageCellphoneCancel.class, 4, Side.SERVER);
        instance.registerMessage(MessageConfigSync.class, MessageConfigSync.class, 5, Side.CLIENT);
        instance.registerMessage(MessageDiallingSound.class, MessageDiallingSound.class, 6, Side.CLIENT);
        instance.registerMessage(MessageDiallingParticles.class, MessageDiallingParticles.class, 7, Side.CLIENT);
        instance.registerMessage(MessageTeleportParticles.class, MessageTeleportParticles.class, 8, Side.CLIENT);
    }
}
