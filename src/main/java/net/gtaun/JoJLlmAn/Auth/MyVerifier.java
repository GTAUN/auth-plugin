package net.gtaun.JoJLlmAn.Auth;

import java.io.File;
import java.security.MessageDigest;

import net.gtaun.shoebill.object.IPlayer;
import net.gtaun.shoebill.util.config.YamlConfiguration;

public class MyVerifier implements VerifyInterface
{
	private MessageDigest md;
	private YamlConfiguration users;
	
	public MyVerifier(File path)
	{
		try
		{
			md = MessageDigest.getInstance("MD5");
			users = new YamlConfiguration(path);
			users.load();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean verify(IPlayer player, String password)
	{
		if(!isRegistered(player)) return false;
		
		String md5 = encrypt(password);
		
		return (users.getString(player.getName().toLowerCase()).compareTo(md5) == 0);
	}

	@Override
	public boolean register(IPlayer player, String password)
	{
		if(isRegistered(player)) return false;
		
		String md5 = encrypt(password);
		
		users.set(player.getName().toLowerCase(), md5);
		users.save();
		return true;
	}

	@Override
	public boolean isRegistered(IPlayer player)
	{
		return (users.get(player.getName().toLowerCase()) != null);
	}
	
	private String encrypt(String str)
	{
		md.update(str.getBytes());
		byte[] bytes = md.digest();
		for(int i=0;i<bytes.length;i++)
			bytes[i] = (byte) (bytes[i] ^ i);
		md.update(bytes);
		bytes = md.digest();
		return Integer.toHexString(Integer.valueOf(bytes.toString()));
	}
}
