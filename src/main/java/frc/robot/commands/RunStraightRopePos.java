package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunStraightRopePos extends CommandBase {
    private final ClimbArms climbArms;
    private final double pos;

    /**
     * Finishes When Straight Arm Pos is Within 1,000 Ticks
     */
    public RunStraightRopePos(ClimbArms climbArms, double pos) {
        this.climbArms = climbArms;
        this.pos = pos;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climbArms.setStraightArmsPos(pos);
    }

    @Override
    public boolean isFinished() {
        return climbArms.straightArmsAtTarget();
    }
}
