package net.gtaun.shoebill.auth;

import java.util.ArrayList;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.event.listener.PlayerEventListener;
import net.gtaun.shoebill.event.player.PlayerCommandEvent;
import net.gtaun.shoebill.object.IPlayer;
import net.gtaun.shoebill.util.event.EventListenerPriority;

public class MyLogin implements LoginInterface
{
	private VerifyInterface verifier;
	private ArrayList<IPlayer> loggedIn;
	
	private PlayerEventListener listener = new PlayerEventListener()
	{	
		public void onPlayerCommand(net.gtaun.shoebill.event.player.PlayerCommandEvent event)
		{
			IPlayer player = event.getPlayer();
			
			String cmdtext = event.getCommand();
			
			if(cmdtext.substring(0, 8).compareToIgnoreCase("/register") == 0)
			{
				String[] parts = cmdtext.split(" ");
				if(parts.length == 0)
				{
					player.sendMessage(Color.RED, "Usage : /register [password]");
				}
				else if(verifier.isRegistered(player))
				{
					player.sendMessage(Color.RED, "You're already registered.");
				}
				else
				{
					if(verifier.register(player, parts[1]))
					{
						loggedIn.add(player);
						player.sendMessage(Color.RED, "Register and log in sucessfully.");
					}
					else
						player.sendMessage(Color.RED, "Error occured when Register.");
				}
			}
			
			if(cmdtext.substring(0, 5).compareToIgnoreCase("/login") == 0)
			{
				String[] parts = cmdtext.split(" ");
				if(parts.length == 0)
					player.sendMessage(Color.RED, "Usage : /login [password]");
				else if(!verifier.isRegistered(player))
					player.sendMessage(Color.RED, "You are not registered, please type /register [password] to register.");
				else if(loggedIn.contains(player))
					player.sendMessage(Color.RED, "You are already logged in.");
				else
				{
					if(verifier.verify(player, parts[1]))
					{
						loggedIn.add(player);
						player.sendMessage(Color.RED, "Log in sucessfully.");
					}
					else
						player.sendMessage(Color.RED, "Wrong password.");
				}
			}
		};
	};
	
	public MyLogin(VerifyInterface verifier, ArrayList<IPlayer> loggedIn)
	{
		this.verifier = verifier;
		this.loggedIn = loggedIn;
		
		Shoebill.getInstance().getEventManager().addListener(PlayerCommandEvent.class, listener, EventListenerPriority.NORMAL);
	}
	
	protected void finalize() throws Throwable
	{
	    try
	    {
	    	Shoebill.getInstance().getEventManager().removeListener(PlayerCommandEvent.class, listener);
	    }
	    finally
	    {
	        super.finalize();
	    }
	}

	public boolean login(IPlayer player)
	{
		if(verifier.isRegistered(player))
			player.sendMessage(Color.RED, "You are registered, please type /login [password] to login.");
		else
			player.sendMessage(Color.RED, "You are not registered, please type /register [password] to register.");
		return false;
	}

	public void logout(IPlayer player)
	{
		loggedIn.remove(player);
	}

	public void setVerifier(VerifyInterface verifier)
	{
		this.verifier = verifier;
	}
	
}
