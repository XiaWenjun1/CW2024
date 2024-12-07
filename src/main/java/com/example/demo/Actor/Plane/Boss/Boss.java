package com.example.demo.Actor.Plane.Boss;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Plane.FighterPlane;

import java.util.*;

/**
 * The {@code Boss} class represents a boss enemy in the game, extending the {@code FighterPlane} class.
 * It manages the boss's health, movement patterns, projectile firing, and shield mechanics.
 * The boss can move vertically, fire projectiles in different patterns, and activate a shield randomly.
 * <p>
 * This class is responsible for updating the boss's state, including its position, shield status,
 * and firing projectiles. It also handles the boss's movement pattern and the mechanics of shield
 * activation and exhaustion.
 * </p>
 */
public class Boss extends FighterPlane {

	/**
	 * The image file name for the boss.
	 * This image is used to represent the boss visually in the game.
	 */
	private static final String IMAGE_NAME = "bossplane.png";

	/**
	 * The initial X position of the boss.
	 * This position defines where the boss will start in the game world.
	 */
	private static final double INITIAL_X_POSITION = 1000.0;

	/**
	 * The initial Y position of the boss.
	 * This defines the starting vertical position of the boss.
	 */
	private static final double INITIAL_Y_POSITION = 400.0;

	/**
	 * The probability of the boss firing a projectile in each frame.
	 * This value determines how likely it is for the boss to fire a projectile during each game frame.
	 */
	private static final double BOSS_FIRE_RATE = 0.08;

	/**
	 * The probability of the boss activating its shield.
	 * This value defines the chance that the boss will activate its shield in any given frame.
	 */
	private static final double BOSS_SHIELD_PROBABILITY = 0.0025;

	/**
	 * The width of the boss image.
	 * This value defines the horizontal size of the boss image when rendered on screen.
	 */
	private static final int IMAGE_WIDTH = 300;

	/**
	 * The height of the boss image.
	 * This value defines the vertical size of the boss image when rendered on screen.
	 */
	private static final int IMAGE_HEIGHT = 300;

	/**
	 * The vertical velocity for the boss's movement.
	 * This value determines how much the boss moves vertically in each update frame.
	 */
	private static final int VERTICAL_VELOCITY = 5;

	/**
	 * The maximum number of frames the boss can move in the same direction.
	 * This is the upper limit for consecutive movements in the same direction before the boss changes direction.
	 */
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;

	/**
	 * The upper bound for the boss's vertical position (Y).
	 * This defines the highest Y-coordinate the boss can move to before stopping.
	 */
	private static final int Y_UPPER_BOUND = 60;

	/**
	 * The lower bound for the boss's vertical position (Y).
	 * This defines the lowest Y-coordinate the boss can move to before stopping.
	 */
	private static final int Y_LOWER_BOUND = 650;

	/**
	 * The maximum number of frames the shield can stay active.
	 * This defines how long the boss's shield can remain activated before it is deactivated.
	 */
	private static final int MAX_FRAMES_WITH_SHIELD = 200;

	/**
	 * The frequency of movement changes in a cycle.
	 * This value defines how often the boss's movement pattern changes during a cycle of movements.
	 */
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;

	/**
	 * A constant representing zero movement.
	 * This value is used to signify that the boss should remain stationary for a particular frame.
	 */
	private static final int ZERO = 0;

	/**
	 * The list defining the movement pattern for the boss.
	 * This list contains alternating vertical movements and some stationary moves.
	 * The boss's movement is randomized in each cycle.
	 */
	private final List<Integer> movePattern = new ArrayList<>();

	/**
	 * The boss's fire pattern manager.
	 * This object manages the boss's projectile firing patterns and types.
	 */
	private final BossFirePattern firePattern;

	/**
	 * Whether the boss is currently shielded.
	 * This boolean flag tracks the shield status of the boss.
	 */
	private boolean isShielded;

	/**
	 * The counter for consecutive moves the boss has made in the same direction.
	 * This value helps to determine when to change the direction of the boss's movement.
	 */
	private int consecutiveMovesInSameDirection;

	/**
	 * The index for the current move in the boss's movement pattern.
	 * This index is used to track the boss's current move in the predefined movement pattern.
	 */
	private int indexOfCurrentMove;

	/**
	 * The counter for frames that the shield has been activated.
	 * This value tracks how long the shield has been active, so it can be deactivated after a certain period.
	 */
	private int framesWithShieldActivated;

	/**
	 * Constructs a {@code Boss} object with the specified initial health.
	 * <p>
	 * This constructor initializes the boss with its starting position, health, movement pattern, and other attributes.
	 * The boss's fire pattern manager is also initialized in this constructor.
	 * </p>
	 *
	 * @param initialHealth The initial health of the boss.
	 */
	public Boss(int initialHealth) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.firePattern = new BossFirePattern(this);
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.2);
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		initializeMovePattern();
	}

	/**
	 * Initializes the movement pattern for the boss.
	 * <p>
	 * The pattern contains alternating vertical movements and some stationary moves.
	 * The pattern is shuffled to randomize the boss's movement behavior during each cycle.
	 * </p>
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the boss's state, including position, shield, health bar, and hitbox.
	 * <p>
	 * This method is called every frame to update the boss's state and ensure proper behavior
	 * during the game loop.
	 * </p>
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
		updateShield();
	}

	/**
	 * Updates the boss's position based on its movement pattern.
	 * <p>
	 * This method ensures the boss stays within vertical bounds, preventing it from moving
	 * off-screen or outside the allowed area.
	 * </p>
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPositionY = getLayoutY() + getTranslateY();
		if (currentPositionY < Y_UPPER_BOUND || currentPositionY > Y_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the shield status, activating or deactivating it based on probabilities and usage time.
	 * <p>
	 * This method checks whether the shield should be activated and whether it has been active for
	 * too long, in which case it will be deactivated.
	 * </p>
	 */
	private void updateShield() {
		if (isShielded) framesWithShieldActivated++;
		else if (shieldShouldBeActivated()) activateShield();
		if (shieldExhausted()) deactivateShield();
	}

	/**
	 * Fires projectiles based on the boss's current attack type.
	 * <p>
	 * This method selects an attack type using the fire pattern manager and returns the list of
	 * projectiles to be fired by the boss.
	 * </p>
	 *
	 * @return A list of projectiles fired by the boss.
	 */
	@Override
	public List<ActiveActorDestructible> fireProjectiles() {
		if (!bossFiresInCurrentFrame()) {
			return new ArrayList<>();
		}

		int attackType = firePattern.selectAttackType();
		return switch (attackType) {
			case 1 -> firePattern.createStraightProjectile();
			default -> new ArrayList<>();
		};
	}

	/**
	 * Reduces the boss's health by 1 if not shielded and updates the health bar.
	 * <p>
	 * This method is called when the boss takes damage from the player or other game entities.
	 * </p>
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Determines if the boss fires a projectile in the current frame.
	 * <p>
	 * This method checks if the boss should fire a projectile based on the defined fire rate.
	 * </p>
	 *
	 * @return True if the boss fires a projectile, false otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Determines if the shield should be activated.
	 * <p>
	 * This method checks if the boss's shield should be activated based on the defined shield probability.
	 * </p>
	 *
	 * @return True if the shield should be activated, false otherwise.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Determines if the shield has been activated for too long.
	 * <p>
	 * This method checks if the shield has been active for longer than the maximum allowed duration.
	 * </p>
	 *
	 * @return True if the shield is exhausted, false otherwise.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the boss's shield.
	 * <p>
	 * This method sets the boss's shield to active and starts tracking its duration.
	 * </p>
	 */
	public void activateShield() {
		isShielded = true;
	}

	/**
	 * Deactivates the boss's shield.
	 * <p>
	 * This method deactivates the boss's shield and resets the shield duration counter.
	 * </p>
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	/**
	 * Gets whether the boss is currently shielded.
	 * <p>
	 * This method returns the current shield status of the boss.
	 * </p>
	 *
	 * @return True if the boss is shielded, false otherwise.
	 */
	public boolean getShielded() {
		return isShielded;
	}

	/**
	 * Gets the next move in the movement pattern for the boss.
	 * <p>
	 * This method returns the next vertical movement value for the boss from its movement pattern,
	 * ensuring that the boss moves according to its defined behavior.
	 * </p>
	 *
	 * @return The next move for the boss in the pattern.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}
}