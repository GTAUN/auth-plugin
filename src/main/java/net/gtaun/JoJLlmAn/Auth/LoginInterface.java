package net.gtaun.JoJLlmAn.Auth;

import net.gtaun.shoebill.object.IPlayer;

public interface LoginInterface
{
	boolean login(IPlayer player);
	void logout(IPlayer player);
	void setVerifier(VerifyInterface verifier);
}
