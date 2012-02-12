package net.gtaun.JoJLlmAn.Auth;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.gtaun.shoebill.object.IPlayer;
import net.gtaun.shoebill.util.config.YamlConfiguration;

public class MyPermission implements PermissionInterface
{
	YamlConfiguration permissions;
	private HashMap<String, Object> usersInfo;
	private HashMap<String, Object> groupsInfo;

	@SuppressWarnings("unchecked")
	public MyPermission(File path)
	{
		permissions = new YamlConfiguration(path);
		permissions.load();
		
		usersInfo = (HashMap<String, Object>) permissions.get("user");
		groupsInfo = (HashMap<String, Object>) permissions.get("groups");
	}
	
	@SuppressWarnings("unchecked")
	public boolean getPermission(String permission, IPlayer player)
	{
		String playerName = player.getName();
		
		HashMap<String, Object> user = (HashMap<String, Object>) usersInfo.get(playerName);
		
		if(user == null)
		{
			Collection<Object> groups = groupsInfo.values();
			Iterator<Object> it = groups.iterator();
			while(it.hasNext())
			{
				HashMap<String, Object> group = (HashMap<String, Object>) it.next();
				Boolean def = (Boolean) group.get("default");
				if(def != null && def)
				{
					List<String> permissions = (List<String>) group.get("permissions");
					if(permissions != null)
					{
						if(permissions.contains("-" + permission)) return false;
						else if(permissions.contains(permission)) return true;
						else if(permissions.contains("*")) return true;
					}
				}
			}
		}
		else
		{
			List<String> permissions = (List<String>) user.get("permissions");
			if(permissions != null)
			{
				if(permissions.contains("-" + permission)) return false;
				else if(permissions.contains(permission)) return true;
				else if(permissions.contains("*")) return true;
			}
			
			HashMap<String, Object> group = (HashMap<String, Object>) groupsInfo.get((String)user.get("group"));
			
			if(group != null)
			{
				permissions = (List<String>) group.get("permissions");
				if(permissions != null)
				{
					if(permissions.contains("-" + permission)) return false;
					else if(permissions.contains(permission)) return true;
					else if(permissions.contains("*")) return true;
				}
			}
			else
			{
				Collection<Object> groups = groupsInfo.values();
				Iterator<Object> it = groups.iterator();
				while(it.hasNext())
				{
					group = (HashMap<String, Object>) it.next();
					Boolean def = (Boolean) group.get("default");
					if(def != null && def)
					{
						permissions = (List<String>) group.get("permissions");
						if(permissions != null)
						{
							if(permissions.contains("-" + permission)) return false;
							else if(permissions.contains(permission)) return true;
							else if(permissions.contains("*")) return true;
						}
					}
				}
			}
		}
		
		return false;
	}

}
