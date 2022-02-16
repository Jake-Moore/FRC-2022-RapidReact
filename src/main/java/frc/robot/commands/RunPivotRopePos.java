package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunPivotRopePos extends CommandBase {
    private final ClimbArms climbArms;
    private final double pos;
    public RunPivotRopePos(ClimbArms climbArms, double pos) {
        this.climbArms = climbArms;
        this.pos = pos;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climbArms.setPivotArmsPos(pos);
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(climbArms.getPivotArmsPos() - pos) <= 1000);
    }
}
