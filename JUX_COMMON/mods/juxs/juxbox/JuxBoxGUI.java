package mods.juxs.juxbox;


import java.io.IOException;
import java.util.ArrayList;

import mods.juxs.client.audio.JuxsSoundManager;
import mods.juxs.core.radio.Location;
import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import mods.juxs.network.RadioUpdatePacket;
import mods.juxs.network.RequestPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class JuxBoxGUI extends GuiContainer {
		ArrayList<GuiButton> controlList= new ArrayList<GuiButton>();
		private TileEntityJux jux;
		
        public JuxBoxGUI (InventoryPlayer inventoryPlayer,
                        TileEntityJux tileEntity) {
                //the container is instanciated and passed to the superclass for handling
        		
                super(new JuxBoxContainer(inventoryPlayer, tileEntity));
                jux=tileEntity;
        }
        public void initGui(){
        	super.initGui();
                                         //id, x, y, width, height, text
            buttonList.add(new GuiButton(1, ((width) /2)  , height/2-55, 80, 20, "Next Station"));
            buttonList.add(new GuiButton(2, ((width)/2)-80, height/2-55, 80, 20, "Prev Station"));
            buttonList.add(new GuiButton(3, ((width)/2)-80, height/2-35,160, 20, "Next Track (OPS ONLY)"));
            
        }
        @Override
        protected void actionPerformed(GuiButton guibutton) {
        	switch(guibutton.id){
        		case 1:{
        			new RadioUpdatePacket(Reference.CHANNEL+"CHANGEN",jux.getStation(),jux.xCoord,jux.yCoord,jux.zCoord);
        			new RequestPacket(Reference.CHANNEL+"REQUEST",jux.xCoord,jux.yCoord,jux.zCoord);
        			JuxsSoundManager.stop(jux.xCoord, jux.yCoord, jux.zCoord);
        			break;
        		}
        		case 2:{
        			new RadioUpdatePacket(Reference.CHANNEL+"CHANGEP",jux.getStation(),jux.xCoord,jux.yCoord,jux.zCoord);
        			new RequestPacket(Reference.CHANNEL+"REQUEST",jux.xCoord,jux.yCoord,jux.zCoord);
        			JuxsSoundManager.stop(jux.xCoord, jux.yCoord, jux.zCoord);
        			break;
        		}
        		case 3:{
					new RadioUpdatePacket(Reference.CHANNEL+"NEXT",jux.getStation(),jux.xCoord,jux.yCoord,jux.zCoord);
        			//new RequestPacket(Reference.CHANNEL+"REQUEST",jux.xCoord,jux.yCoord,jux.zCoord);
					//JuxsSoundManager.stop(jux.xCoord, jux.yCoord, jux.zCoord);
					
        		}
        	
        	}
        	this.updateScreen();
            
    }
        @Override
        protected void drawGuiContainerForegroundLayer(int param1, int param2) {
                //draw text and stuff here
                //the parameters for drawString are: string, x, y, color
                fontRenderer.drawString("Current station: "+jux.getStation(), 8, 6, 4210752);
                fontRenderer.drawString(jux.getPlaying(),8,18,4210752);
                fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                        int par3) {
                //draw your Gui here, only thing you need to change is the path
        	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        	this.mc.func_110434_K().func_110577_a(new net.minecraft.util.ResourceLocation("juxs","/textures/gui/juxbox_gui.png"));
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        }

}
