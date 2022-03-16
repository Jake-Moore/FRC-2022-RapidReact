package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunStraightRopePosAdj extends CommandBase {
    private final ClimbArms climbArms;
    private final double change;

    /**
     * Finishes Immediately
     */
    public RunStraightRopePosAdj(ClimbArms climbArms, double change) {
        this.climbArms = climbArms;
        this.change = change;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climbArms.setStraightArmsPos(Math.max(0, Math.min(climbArms.mLeftStraight.getSelectedSensorPosition() + change, 230000)));
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
