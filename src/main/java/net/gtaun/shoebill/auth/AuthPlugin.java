/**
 * Copyright (C) 2012 MK124
 * Copyright (C) 2012 JoJLlmAn
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

package net.gtaun.shoebill.auth;

import net.gtaun.shoebill.auth.authorize.Authorizer;
import net.gtaun.shoebill.auth.permission.Permissions;
import net.gtaun.shoebill.auth.ui.LoginInterface;
import net.gtaun.shoebill.resource.Plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author MK124, JoJLlmAn
 */

public class AuthPlugin extends Plugin
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthPlugin.class);
	
	
	private Authorizer authorizer;
	private Permissions permissions;
	private LoginInterface loginInterface;
	
	
	public AuthPlugin()
	{
		
	}
	
	@Override
	protected void onEnable()
	{
		String startupMessage = getDescription().getName() + " " + getDescription().getVersion();
		if( getDescription().getBuildNumber() != 0 ) startupMessage += " Build " + getDescription().getBuildNumber();
		startupMessage += " Enabled.";
		
		LOGGER.info( startupMessage );
	}

	@Override
	protected void onDisable()
	{
		LOGGER.info( getDescription().getName() + " " + getDescription().getVersion() + " Disabled." );
	}
}
