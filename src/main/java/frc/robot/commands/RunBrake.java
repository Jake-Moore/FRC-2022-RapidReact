package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunBrake extends CommandBase {
    private final ClimbArms climbArms;
    private final double angleL;
    private final double angleR;

    public RunBrake(ClimbArms climbArms, double angleL, double angleR) {
        this.climbArms = climbArms;
        this.angleL = angleL;
        this.angleR = angleR;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climbArms.setBrake(angleL, angleR);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
