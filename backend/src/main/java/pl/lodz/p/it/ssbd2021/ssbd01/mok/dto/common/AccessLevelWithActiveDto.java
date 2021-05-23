package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.common;

public abstract class AccessLevelWithActiveDto extends AccessLevelDto {

    private boolean isActive;

    public AccessLevelWithActiveDto(String level, boolean isActive) {
        super(level);
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


}
