package net.gtaun.shoebill.auth;

import java.io.File;
import java.util.ArrayList;

import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.event.listener.PlayerEventListener;
import net.gtaun.shoebill.event.player.PlayerConnectEvent;
import net.gtaun.shoebill.event.player.PlayerDisconnectEvent;
import net.gtaun.shoebill.object.IPlayer;
import net.gtaun.shoebill.plugin.Plugin;
import net.gtaun.shoebill.util.event.EventListenerPriority;

public class SimpleAuthentication extends Plugin
{
	private ArrayList<IPlayer> loggedIn = new ArrayList<IPlayer>();
	private LoginInterface loginer;
	private VerifyInterface verifier;
	private PermissionInterface permissioner;
	
	private PlayerEventListener listener = new PlayerEventListener()
	{
		public void onPlayerConnect(net.gtaun.shoebill.event.player.PlayerConnectEvent event)
		{
			loginer.login(event.getPlayer());
		};
		
		public void onPlayerDisconnect(net.gtaun.shoebill.event.player.PlayerDisconnectEvent event)
		{
			loginer.logout(event.getPlayer());
		};
	};

	@Override
	protected void onDisable()
	{
		getShoebill().getEventManager().removeListener(PlayerConnectEvent.class, listener);
		getShoebill().getEventManager().removeListener(PlayerDisconnectEvent.class, listener);
	}

	@Override
	protected void onEnable()
	{
		try
		{
			File configIn = new File(getDataFolder(), "users.yml");
			verifier = new MyVerifier(configIn);
			
			configIn = new File(getDataFolder(), "permissions.yml");
			permissioner = new MyPermission(configIn);
			
			loginer = new MyLogin(verifier, loggedIn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		getShoebill().getEventManager().addListener(PlayerConnectEvent.class, listener, EventListenerPriority.NORMAL);
		getShoebill().getEventManager().addListener(PlayerDisconnectEvent.class, listener, EventListenerPriority.NORMAL);
		
		for(IPlayer player : this.getShoebill().getManagedObjectPool().getPlayers())
		{
			if(verifier.isRegistered(player))
				player.sendMessage(Color.RED, "You are registered, please type /login [password] to login.");
			else
				player.sendMessage(Color.RED, "You are not registered, please type /register [password] to register.");
		}
	}
	
	public void setLoginer(LoginInterface loginer)
	{
		this.loginer = loginer;
	}
	public void setVerifier(VerifyInterface verifier)
	{
		this.verifier = verifier;
		this.loginer.setVerifier(verifier);
	}
	public void setPermissioner(PermissionInterface permissioner)
	{
		this.permissioner = permissioner;
	}
	
	public boolean getPermission(String permission, IPlayer player)
	{
		if(!loggedIn.contains(player)) return false;
		
		return this.permissioner.getPermission(permission, player);
	}
}
