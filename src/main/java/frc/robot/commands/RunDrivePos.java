package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class RunDrivePos extends CommandBase {
    private final Drivetrain drivetrain;
    private final double left;
    private final double right;

    /**
     * Moves based on offset, not absolute position
     * Finishes When Position Values Are Within 100 Ticks
     */
    public RunDrivePos(Drivetrain drivetrain, double left, double right) {
        this.drivetrain = drivetrain;
        this.left = left;
        this.right = right;
    }

    double leftTarget;
    double rightTarget;
    @Override
    public void initialize() {
        drivetrain.setOutputs(0.3);
        drivetrain.setOverrideDrivetrain(true);
        leftTarget = left + drivetrain.mLeftA.getSelectedSensorPosition();
        rightTarget = right + drivetrain.mRightA.getSelectedSensorPosition();
        drivetrain.setDrivePositions(leftTarget, rightTarget);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        drivetrain.setOverrideDrivetrain(false);
        drivetrain.setOutputs(1.0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(drivetrain.getDrivePos()[0] - leftTarget) <= 100 && Math.abs(drivetrain.getDrivePos()[1] - rightTarget) <= 100;
    }
}
