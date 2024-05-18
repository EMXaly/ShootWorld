package me.emxion.shootworld.Stats;

public class WeaponStats {
    private int kills;
    private float damage;
    private int shotsFired;
    private int shotsHit;
    private double farthestKill;
    public WeaponStats() {
        this.kills = 0;
        this.damage = 0;
        this.shotsFired = 0;
        this.shotsHit = 0;
        this.farthestKill = 0;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public void setShotsFired(int shotsFired) {
        this.shotsFired = shotsFired;
    }

    public int getShotsHit() {
        return shotsHit;
    }

    public void setShotsHit(int shotsHit) {
        this.shotsHit = shotsHit;
    }

    public double getFarthestKill() {
        return farthestKill;
    }

    public void setFarthestKill(double farthestKill) {
        this.farthestKill = farthestKill;
    }
}
