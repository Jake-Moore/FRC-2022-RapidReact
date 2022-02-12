package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunStraightRopePosAdj extends CommandBase {
    private final ClimbArms climbArms;
    private final double change;
    public RunStraightRopePosAdj(ClimbArms climbArms, double change) {
        this.climbArms = climbArms;
        this.change = change;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climbArms.setStraightArmsPos(Math.max(0, climbArms.mLeftStraight.getSelectedSensorPosition() + change));
    }

    @Override
    public void end(boolean interrupted) {}
}
