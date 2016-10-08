package com.matthewcasperson.security;

import com.matthewcasperson.domain.MicroserviceKeyValue;
import com.yahoo.elide.security.ChangeSpec;
import com.yahoo.elide.security.RequestScope;
import com.yahoo.elide.security.User;
import com.yahoo.elide.security.checks.InlineCheck;

import java.util.Optional;

/**
 * This security check verifies that the quote id, dob and postcode that the client submitted
 * matches the details that were retrieved from DISC. These must match or the result is not returned.
 */
public class ValidateSecretDetails extends InlineCheck<MicroserviceKeyValue> {

	@Override
	public boolean ok(final MicroserviceKeyValue object, final RequestScope requestScope, final Optional<ChangeSpec> changeSpec) {
		final OpaqueUser user = (OpaqueUser)requestScope.getUser().getOpaqueUser();
        return user.getSecret().equals(object.getSecret());
	}

	@Override
	public boolean ok(final User user) {
		return true;
	}
}
