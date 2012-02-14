package net.gtaun.shoebill.auth.authorize.signer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.gtaun.shoebill.auth.authorize.Authorizer;

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
