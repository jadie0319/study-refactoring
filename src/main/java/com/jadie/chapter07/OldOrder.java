package com.jadie.chapter07;

public class OldOrder {
    private Priority priority;
    public String priorityString() {
        return priority.toString();
    }
    public void priority(String aString) {
        this.priority = new Priority(aString);
    }
    public Priority priority() {
        return priority;
    }

    public boolean isLegalPriority() {
        return priority.isLegalPriority();
    }
}
