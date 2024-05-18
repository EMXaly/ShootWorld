package me.emxion.shootworld.Stats;

public class AbilityStats {
    private int placed;
    private int used;
    private double distance;
    private float damage;

    public AbilityStats() {
        this.placed = 0;
        this.used = 0;
        this.distance = 0;
        this.damage = 0;
    }

    public int getPlaced() {
        return placed;
    }

    public void setPlaced(int placed) {
        this.placed = placed;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
