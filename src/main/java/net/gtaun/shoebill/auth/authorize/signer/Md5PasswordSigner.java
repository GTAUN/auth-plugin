/**
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

package net.gtaun.shoebill.auth.authorize.signer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.gtaun.shoebill.auth.authorize.Authorizer;

/**
 * 
 * 
 * @author MK124
 */
public class Md5PasswordSigner implements PasswordSigner
{
	public static byte[] getSign( String password )
	{
		try
		{
			MessageDigest messageDigest = MessageDigest.getInstance( "MD5" );
			return messageDigest.digest( password.getBytes() );
		}
		catch( NoSuchAlgorithmException e )
		{
			return null;
		}
	}
	
	
	private Authorizer authorizer;
	
	
	public Md5PasswordSigner( Authorizer authorizer )
	{
		this.authorizer = authorizer;
	}
	
	public Authorizer getAuthorizer()
	{
		return authorizer;
	}
	

	@Override
	public boolean authorize( String user, byte[] sign )
	{
		return authorizer.authorize( user, sign );
	}

	@Override
	public boolean registerAuthorization( String user, byte[] sign )
	{
		return authorizer.authorize( user, sign );
	}

	@Override
	public boolean isRegistedAuthorization( String user )
	{
		return authorizer.isRegistedAuthorization( user );
	}

	@Override
	public boolean authorize( String user, String password )
	{
		return authorize( user, getSign(password) );
	}

	@Override
	public boolean registerAuthorization( String user, String password )
	{
		return registerAuthorization( user, getSign(password) );
	}
}
