package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class RunDrivePos extends CommandBase {
    private final Drivetrain drivetrain;
    private final double deltaLeft;
    private final double deltaRight;

    public RunDrivePos(Drivetrain drivetrain, double deltaLeft, double deltaRight) {
        this.drivetrain = drivetrain;
        this.deltaLeft = deltaLeft;
        this.deltaRight = deltaRight;
    }

    @Override
    public void initialize() {
        drivetrain.setDeltaPositions(deltaLeft, deltaRight);
    }
}
