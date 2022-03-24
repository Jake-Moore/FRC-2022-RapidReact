package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunPivotRopePosAdj extends CommandBase {
    private final ClimbArms climbArms;
    private final double adj;

    /**
     * Finishes When Pivot Arm Pos is Within 1,000 ticks
     */
    public RunPivotRopePosAdj(ClimbArms climbArms, double adj) {
        this.climbArms = climbArms;
        this.adj = adj;
    }

    double target = Double.MAX_VALUE;
    @Override
    public void initialize() {
        target = climbArms.mLeftPivot.getSelectedSensorPosition() + adj;
        climbArms.setPivotArmsPos(target);
    }

    @Override
    public void execute() { }

    @Override
    public boolean isFinished() {
        return (Math.abs(climbArms.getPivotArmsPos() - target) <= 1000);
    }
}
