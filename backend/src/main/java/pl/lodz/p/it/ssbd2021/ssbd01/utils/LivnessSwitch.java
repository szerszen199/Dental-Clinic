package pl.lodz.p.it.ssbd2021.ssbd01.utils;

public class LivnessSwitch {
    private boolean isAlive = true;

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public LivnessSwitch(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public LivnessSwitch() {
    }
}
