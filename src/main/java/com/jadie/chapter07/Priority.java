package com.jadie.chapter07;

public class Priority {
    private String priority;
    public Priority(String priority) {
        this.priority = priority;
    }
    @Override
    public String toString() {
        return priority;
    }


    //    public boolean higherThan(Priority normal) {
//        return "high".equals(priority) || "rush".equals(o.priority);
//    }
}
