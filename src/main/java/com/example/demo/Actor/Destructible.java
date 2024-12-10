package com.example.demo.Actor;

/**
 * Interface for objects that can be destroyed or damaged.
 * Any class implementing this interface should provide the logic for taking damage and being destroyed.
 */
public interface Destructible {

    /**
     * Applies damage to the object.
     * The implementation should define how the object responds to taking damage (e.g., reducing health).
     */
    void takeDamage();

    /**
     * Destroys the object.
     * The implementation should define what happens when the object is destroyed (e.g., removal from the game, triggering effects).
     */
    void destroy();

}