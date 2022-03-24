package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunClimbPivotPIDToggle extends CommandBase {
    private final ClimbArms climbArms;
    private final boolean enabled;
    public RunClimbPivotPIDToggle(ClimbArms climbArms, boolean enabled) {
        this.climbArms = climbArms;
        this.enabled = enabled;
    }

    @Override
    public void initialize() {
        climbArms.setPivotPIDEnabled(enabled);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
