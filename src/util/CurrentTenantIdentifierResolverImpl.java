package util;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	public static ThreadLocal<String> _tenantIdentifier = new ThreadLocal<String>();
	public static String DEFAULT_TENANT_ID = "sapo_master";

	@Override
	public String resolveCurrentTenantIdentifier() {
		
		String tenantId = null;
		try {
			System.out.println("from inside resolveCurrentTenantIdentifier....");
			tenantId = _tenantIdentifier.get();

			if(tenantId == null)
				tenantId = DEFAULT_TENANT_ID;

			System.out.println("threadlocal tenant id ="+tenantId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tenantId;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

}