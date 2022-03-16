package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunPivotPosAdj extends CommandBase {
    private final ClimbArms climbArms;
    private final double change;

    /**
     * Finishes Immediately
     */
    public RunPivotPosAdj(ClimbArms climbArms, double change) {
        this.climbArms = climbArms;
        this.change = change;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climbArms.setPivotPos(climbArms.mPivoter.getSelectedSensorPosition() + change);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
