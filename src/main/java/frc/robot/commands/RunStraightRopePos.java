package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunStraightRopePos extends CommandBase {
    private final ClimbArms climbArms;
    private final double pos;
    private final double maxPower;

    /**
     * Finishes When Straight Arm Pos is Within 1,000 Ticks
     */
    public RunStraightRopePos(ClimbArms climbArms, double pos, double maxPower) {
        this.climbArms = climbArms;
        this.pos = pos;
        this.maxPower = maxPower;
    }

    @Override
    public void initialize() {
        climbArms.setStraightArmsMaxPower(maxPower);
    }

    @Override
    public void execute() {
        climbArms.setStraightArmsPos(pos);
    }

    @Override
    public boolean isFinished() {
        return climbArms.straightArmsAtTarget();
    }
}
