package mods.juxs;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;

import com.jcraft.jorbis.Block;


import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import mods.juxs.block.ModBlocks;
import mods.juxs.client.audio.SoundHandler;
import mods.juxs.core.radio.RadioInit;
import mods.juxs.core.radio.RadioStation;
import mods.juxs.item.ModItems;
import mods.juxs.juxbox.JuxBox;
import mods.juxs.juxbox.TileEntityJux;
import mods.juxs.lib.Reference;
import mods.juxs.lib.Sounds;
import mods.juxs.network.CommonProxy;
import mods.juxs.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION)
@NetworkMod(channels={Reference.CHANNEL+"MESS",Reference.CHANNEL+"REQUEST",Reference.CHANNEL+"CHANGEP",Reference.CHANNEL+"CHANGEN",Reference.CHANNEL+"CHECK",Reference.CHANNEL+"PLAY",Reference.CHANNEL+"STOP",Reference.CHANNEL+"TIMEUNTIL",Reference.CHANNEL+"REMOVE",Reference.CHANNEL+"NEXT"},clientSideRequired=true,serverSideRequired=true,packetHandler=PacketHandler.class)
public class Juxs {
    @Instance(Reference.MOD_ID)
    public static Juxs instance;
    
    @SidedProxy(clientSide="mods.juxs.network.ClientProxy",serverSide="mods.juxs.network.CommonProxy")
    public static CommonProxy proxy;
    public static CreativeTabs juxTab;
    public static net.minecraft.block.Block juxBox;
    public static ArrayList<RadioStation> stations;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	GameRegistry.registerTileEntity(TileEntityJux.class, "juxboxtile");
    	Reference.MOD_DIR=event.getModConfigurationDirectory().getAbsolutePath();
        System.out.println(Reference.MOD_DIR);
        juxTab = new CreativeTabs("juxTab") {
			@Override public ItemStack getIconItemStack() {
				return new ItemStack(juxBox,1,0);
			}
			@Override public String getTranslatedTabLabel() {
				return "Juxs";
			}
		};
		stations= new ArrayList<RadioStation>();
		juxBox= new JuxBox(2451);
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        proxy.registerSoundHandler();
        proxy.registerWorldHandler();
        TickRegistry.registerTickHandler(new JuxsTickHandler(EnumSet.of(TickType.SERVER)), Side.SERVER);
        //ModBlocks.init();
        //ModItems.init();
    }
    
    @EventHandler public void init(FMLInitializationEvent Event) {ModBlocks.init();
    ModItems.init(); }
}
