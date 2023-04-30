package org.example.spring.transaction;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
@Transactional
public class TransactionDetectTest {

	@Test
	public void givenTransactional_whenCheckingForIsActualTransactionActive_thenTrue(){
		assertTrue(TransactionSynchronizationManager.isActualTransactionActive());
	}
}
