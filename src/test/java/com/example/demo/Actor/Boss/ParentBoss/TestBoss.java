package com.example.demo.Actor.Boss.ParentBoss;

import com.example.demo.Actor.ActiveActorDestructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestBoss {

	private Boss boss;

	@BeforeEach
	void setUp() {
		boss = new Boss(100);
	}

	@Test
	void testInitialHealth() {
		assertEquals(100, boss.getHealth());
	}

	@Test
	void testShieldActivation() throws Exception {
		Method updateShieldMethod = Boss.class.getDeclaredMethod("updateShield");
		updateShieldMethod.setAccessible(true);

		boolean initialShieldStatus = boss.getShielded();
		updateShieldMethod.invoke(boss);
		assertNotEquals(initialShieldStatus, boss.getShielded());
	}

	@Test
	void testTakeDamageWhenShielded() throws Exception {
		// Use reflection to invoke the private activateShield method
		Method activateShieldMethod = Boss.class.getDeclaredMethod("activateShield");
		activateShieldMethod.setAccessible(true);

		activateShieldMethod.invoke(boss); // Activate the shield
		int initialHealth = boss.getHealth();
		boss.takeDamage();
		assertEquals(initialHealth, boss.getHealth());
	}

	@Test
	void testTakeDamageWhenNotShielded() {
		int initialHealth = boss.getHealth();
		boss.takeDamage();
		assertEquals(initialHealth - 1, boss.getHealth());
	}

	@Test
	void testFireProjectiles() {
		List<ActiveActorDestructible> projectiles = boss.fireProjectiles();
		assertNotNull(projectiles);
		assertTrue(projectiles.size() > 0);
	}

	@Test
	void testUpdatePosition() {
		double initialPosition = boss.getLayoutY();
		boss.updatePosition();
		assertNotEquals(initialPosition, boss.getLayoutY());
	}

	@Test
	void testGetNextMove() throws Exception {
		Method getNextMoveMethod = Boss.class.getDeclaredMethod("getNextMove");
		getNextMoveMethod.setAccessible(true);

		int initialMove = (int) getNextMoveMethod.invoke(boss);
		int newMove = (int) getNextMoveMethod.invoke(boss);
		assertNotEquals(initialMove, newMove);
	}
}