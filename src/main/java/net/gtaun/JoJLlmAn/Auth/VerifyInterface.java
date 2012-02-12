package net.gtaun.JoJLlmAn.Auth;

import net.gtaun.shoebill.object.IPlayer;

public interface VerifyInterface
{
	boolean verify(IPlayer player, String password);
	boolean register(IPlayer player, String password);
	boolean isRegistered(IPlayer player);
}
