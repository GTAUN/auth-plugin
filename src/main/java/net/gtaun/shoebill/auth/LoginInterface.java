package net.gtaun.shoebill.auth;

import net.gtaun.shoebill.object.IPlayer;

public interface LoginInterface
{
	boolean login(IPlayer player);
	void logout(IPlayer player);
	void setVerifier(VerifyInterface verifier);
}
