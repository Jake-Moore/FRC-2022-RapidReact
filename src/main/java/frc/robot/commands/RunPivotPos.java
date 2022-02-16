package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunPivotPos extends CommandBase {
    private final ClimbArms climbArms;
    private final double pos;
    public RunPivotPos(ClimbArms climbArms, double pos) {
        this.climbArms = climbArms;
        this.pos = pos;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climbArms.setPivotPos(pos);
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(climbArms.getPivotPos() - pos) <= 1000);
    }
}
