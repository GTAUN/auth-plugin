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

import net.gtaun.shoebill.auth.authorize.Authorizer;

/**
 * @author MK124
 *
 */

public interface PasswordSigner extends Authorizer
{
	boolean authorize( String user, String password );
	boolean registerAuthorization( String user, String password );
	boolean isRegistedAuthorization( String user );
}
