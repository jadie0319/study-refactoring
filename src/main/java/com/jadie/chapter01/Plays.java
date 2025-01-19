package com.jadie.chapter01;

import java.util.Map;

public record Plays(
        Map<String, Play> plays
) {
    public Play getPlay(String playId) {
        return plays.get(playId);
    }
}
