package by.epam.homework3.entity;

public class MilitaryPlane extends Plane {
    private boolean weapon;
    private boolean invisibility;

    public MilitaryPlane() {
        super();
    }

    public MilitaryPlane(boolean weapon, boolean invisibility) {
        this.weapon = weapon;
        this.invisibility = invisibility;
    }

    public boolean isWeapon() {
        return weapon;
    }

    public void setWeapon(boolean weapon) {
        this.weapon = weapon;
    }

    public boolean isInvisibility() {
        return invisibility;
    }

    public void setInvisibility(boolean invisibility) {
        this.invisibility = invisibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        MilitaryPlane that = (MilitaryPlane) o;

        if (weapon != that.weapon) {
            return false;
        }
        return invisibility == that.invisibility;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (weapon ? 1 : 0);
        result = 31 * result + (invisibility ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MilitaryPlane{");
        sb.append(super.toString());
        sb.append("weapon=").append(weapon);
        sb.append(", invisibility=").append(invisibility);
        sb.append('}');
        return sb.toString();
    }
}
