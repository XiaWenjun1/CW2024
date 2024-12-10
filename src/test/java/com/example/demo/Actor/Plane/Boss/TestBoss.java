package com.example.demo.Actor.Plane.Boss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoss {

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
	void testShieldActivation() {
		// Shield is activated based on the updateShield method, so we call updateShield directly
		boolean initialShieldStatus = boss.getShielded();
		boss.activateShield();
		assertNotEquals(initialShieldStatus, boss.getShielded());
	}

	@Test
	void testTakeDamageWhenShielded() {
		// Manually activate the shield by calling the method directly
		boss.activateShield();
		int initialHealth = boss.getHealth();
		boss.takeDamage();
		assertEquals(initialHealth, boss.getHealth()); // Health should remain the same if shielded
	}

	@Test
	void testTakeDamageWhenNotShielded() {
		int initialHealth = boss.getHealth();
		boss.takeDamage();
		assertEquals(initialHealth - 1, boss.getHealth()); // Health should decrease by 1 if not shielded
	}
}