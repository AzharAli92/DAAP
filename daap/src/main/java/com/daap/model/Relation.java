package com.daap.model;



public class Relation {
    public enum Type {
        calls, creates, references, uses, inherits, has, relates
    }

    protected LegacyClass from;
    protected LegacyClass to;
    protected Type type;

    public Relation(LegacyClass A, LegacyClass B, Type type) {
        from = A;
        to = B;
        this.type = type;
    }

    /**
     * Returns a LegacyClass representing the first part of this Relation.
     */
    public LegacyClass getFrom() {
        return from;
    }

    /**
     * Returns a LegacyClass representing the second part of this Relation.
     */
    public LegacyClass getTo() {
        return to;
    }

    /**
     * Returns an enum type representing the type of this Relation.
     */
    public Type getType() {
        return type;
    }

    public boolean hasType(Type type) {
        return this.type == type;
    }

    /**
     * Overriding toString() to print the appropriate results.
     */
//    @Override
//    public String toString() {
//        return from + " " + to + " " + type;
//    }

    public String printRelation() {
        return from.getName() + " " + type + " " + to.getName();
    }
}