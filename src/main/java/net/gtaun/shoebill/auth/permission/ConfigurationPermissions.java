/**
 * Copyright (C) 2012 JoJLlmAn
 * Copyright (C) 2012 MK124
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.gtaun.shoebill.auth.permission;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.util.config.Configuration;

/**
 * 
 * 
 * @author JoJLlmAn, MK124
 */
public class ConfigurationPermissions implements Permissions
{
	Configuration configuration;
	private HashMap<String, Object> usersPermissions;
	private HashMap<String, Object> groupsPermissions;

	
	@SuppressWarnings("unchecked")
	public ConfigurationPermissions( Configuration configuration )
	{
		this.configuration = configuration;
		
		usersPermissions = (HashMap<String, Object>) configuration.get("user");
		groupsPermissions = (HashMap<String, Object>) configuration.get("groups");
	}
	
	
	public Configuration getConfiguration()
	{
		return configuration;
	}
	
	@SuppressWarnings("unchecked")
	public boolean getPermission(String permission, Player player)
	{
		String playerName = player.getName();
		
		HashMap<String, Object> user = (HashMap<String, Object>) usersPermissions.get(playerName);
		
		if(user == null)
		{
			Collection<Object> groups = groupsPermissions.values();
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
			
			HashMap<String, Object> group = (HashMap<String, Object>) groupsPermissions.get((String)user.get("group"));
			
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
				Collection<Object> groups = groupsPermissions.values();
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
