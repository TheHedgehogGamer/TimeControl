package de.setlex.timecontrol;

import org.bukkit.entity.Player;

import java.util.function.*;

public class Mode {

    public enum Type {
        DEFAULT(0),
        FROZEN(1),
        CUSTOM(2);

        final int id;

        Type(int id) {
            this.id = id;
        }
    }

    public static class Time {

        private final long WorldAge;
        private final long TimeOfDay;

        /**
         * @param WorldAge In ticks; not changed by server commands.
         * @param TimeOfDay The world (or region) time, in ticks.
         */
        public Time(long WorldAge, long TimeOfDay) {
            this.WorldAge = WorldAge;
            this.TimeOfDay = TimeOfDay;
        }

        /**
         * @return The World Age in ticks; not changed by server commands.
         */
        public long getWorldAge() {
            return WorldAge;
        }

        /**
         * @return The time of the day in ticks; The world (or region) time, in ticks.
         */
        public long getTimeOfDay() {
            return TimeOfDay;
        }
    }

    private final Type type;
    private Time time;
    private Function<Player, Time> function;

    private Mode(Type type) {
        this.type = type;
    }

    private Mode(Type type, long WorldAge, long TimeOfDay) {
        this.type = type;
        this.time = new Time(WorldAge, TimeOfDay);
    }

    private Mode(Type type, Function<Player, Time> function) {
        this.type = type;
        this.function = function;
    }

    /**
     * @return a default mode object.
     */
    public static Mode DEFAULT() {
        return new Mode(Type.DEFAULT);
    }

    /**
     * @param time WorldAge and TimeOfDay in one class.
     * @return a frozen mode object.
     */
    public static Mode FROZEN(Time time) {
        return new Mode(Type.FROZEN, time.getWorldAge(), time.getTimeOfDay());
    }

    /**
     * @param WorldAge In ticks; not changed by server commands.
     * @param TimeOfDay The world (or region) time, in ticks.
     * @return a frozen mode object.
     */
    public static Mode FROZEN(long WorldAge, long TimeOfDay) {
        return new Mode(Type.FROZEN, WorldAge, TimeOfDay);
    }

    /**
     * @param function your function.
     * @return a custom mode object.
     */
    public static Mode CUSTOM(Function<Player, Time> function) {
        return new Mode(Type.CUSTOM, function);
    }

    /**
     * @return The type (DEFAULT, FROZEN, ...)
     */
    public Type getType() {
        return type;
    }

    /**
     * @return The World Age in ticks; not changed by server commands.
     */
    public long getWorldAge() {
        return time.getWorldAge();
    }

    /**
     * @return The time of the day in ticks; The world (or region) time, in ticks.
     */
    public long getTimeOfDay() {
        return time.getTimeOfDay();
    }

    protected Function<Player, Time> getFunction() {
        return function;
    }
}
