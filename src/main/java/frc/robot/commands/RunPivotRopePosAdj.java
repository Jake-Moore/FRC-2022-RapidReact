package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunPivotRopePosAdj extends CommandBase {
    private final ClimbArms climbArms;
    private final double adj;

    /**
     * Finishes Immediately
     */
    public RunPivotRopePosAdj(ClimbArms climbArms, double adj) {
        this.climbArms = climbArms;
        this.adj = adj;
    }

    @Override
    public void initialize() {
        climbArms.setPivotArmsPos(Math.max(0, Math.min(climbArms.mLeftPivot.getSelectedSensorPosition() + adj, 230000)));
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return true;//climbArms.pivotArmsAtTarget();
    }
}
