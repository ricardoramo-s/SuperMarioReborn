package uni.ldts.listeners;

import uni.ldts.engine.GameState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventHandler {
    GameState state() default GameState.LEVEL;
    // if master=true, it listens in all game states
    boolean master() default false;
}
