package org.example.spring.transaction.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		if (TransactionSynchronizationManager.isActualTransactionActive()
			 && !TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
			return DatabaseType.WRITE;
		}

		return DatabaseType.READ;
	}

	enum DatabaseType {
		READ,
		WRITE,
	}

}
