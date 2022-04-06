package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunStraightRopePosAdj extends CommandBase {
    private final ClimbArms climbArms;
    private final double change;
    private final double maxPower;

    /**
     * Finishes Immediately
     */
    public RunStraightRopePosAdj(ClimbArms climbArms, double change, double maxPower) {
        this.climbArms = climbArms;
        this.change = change;
        this.maxPower = maxPower;
    }

    @Override
    public void initialize() {
        climbArms.setStraightArmsMaxPower(maxPower);
        climbArms.setStraightArmsPos(Math.max(0, Math.min(climbArms.mLeftStraight.getSelectedSensorPosition() + change, 230000)));
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
