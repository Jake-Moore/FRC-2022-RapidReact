package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class RunDrivePos extends CommandBase {
    private final Drivetrain drivetrain;
    private final double left;
    private final double right;

    /**
     * Finishes When Position Values Are Within 100 Ticks
     */
    public RunDrivePos(Drivetrain drivetrain, double left, double right) {
        this.drivetrain = drivetrain;
        this.left = left;
        this.right = right;
    }

    @Override
    public void initialize() {
        drivetrain.setOverrideDrivetrain(true);
    }

    @Override
    public void execute() {
        drivetrain.setDrivePositions(left, right);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.setOverrideDrivetrain(false);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(drivetrain.getDrivePos()[0] - left) <= 100 && Math.abs(drivetrain.getDrivePos()[1] - right) <= 100;
    }
}
