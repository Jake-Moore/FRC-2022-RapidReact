package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunStraightSolenoid extends CommandBase {
    ClimbArms climbArms;
    boolean on;

    public RunStraightSolenoid(ClimbArms climbArms, boolean on) {
        this.climbArms = climbArms;
        this.on = on;
    }

    @Override
    public void initialize() {
        climbArms.setStraightSolenoids(on);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
