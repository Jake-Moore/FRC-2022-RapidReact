package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunPivotPos extends CommandBase {
    private final ClimbArms climbArms;
    private final double pos;
    private final double maxPower;

    /**
     * Finishes When Pivot Pos is Within 1,000 ticks
     */
    public RunPivotPos(ClimbArms climbArms, double pos, double maxPower) {
        this.climbArms = climbArms;
        this.pos = pos;
        this.maxPower = maxPower;
    }

    @Override
    public void initialize() {
        climbArms.setPivotMaxPower(maxPower);
    }

    @Override
    public void execute() {
        climbArms.setPivotPos(pos);
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(climbArms.getPivotPos() - pos) <= 1000);
    }
}
