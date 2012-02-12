package net.gtaun.shoebill.auth;

import net.gtaun.shoebill.object.IPlayer;

public interface PermissionInterface
{
	boolean getPermission(String permission, IPlayer player);
}
