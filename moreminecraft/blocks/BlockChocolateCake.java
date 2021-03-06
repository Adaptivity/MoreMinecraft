package moreminecraft.blocks;

import moreminecraft.MoreMinecraft;
import net.minecraft.block.BlockCake;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChocolateCake extends BlockCake {
	@SideOnly(Side.CLIENT)
	private IIcon cakeTopIcon;
	@SideOnly(Side.CLIENT)
	private IIcon cakeBottomIcon;
	@SideOnly(Side.CLIENT)
	private IIcon cakeSliceSide;

	public BlockChocolateCake() {
		super();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return par1 == 1 ? this.cakeTopIcon : (par1 == 0 ? this.cakeBottomIcon : (par2 > 0 && par1 == 4 ? this.cakeSliceSide : this.blockIcon));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4) {
		return MoreMinecraft.itemChocolateCake;
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		this.eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
		return true;
	}

	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		this.eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
		this.cakeSliceSide = par1IconRegister.registerIcon(this.getTextureName() + "_inner");
		this.cakeTopIcon = par1IconRegister.registerIcon(this.getTextureName() + "_top");
		this.cakeBottomIcon = par1IconRegister.registerIcon(this.getTextureName() + "_bottom");
	}

	/**
	 * Heals the player and removes a slice from the cake.
	 */
	private void eatCakeSlice(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		if (par5EntityPlayer.canEat(false)) {
			par5EntityPlayer.getFoodStats().addStats(3, 0.2F);
			int l = par1World.getBlockMetadata(par2, par3, par4) + 1;
			if (l >= 6) {
				par1World.setBlockToAir(par2, par3, par4);
			} else {
				par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
			}
		}
	}
}
