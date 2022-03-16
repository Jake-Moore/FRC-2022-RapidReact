package frc.robot.commands;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class RunRotateBot extends CommandBase {
    private final Drivetrain drivetrain;
    private final double deltaDegrees;
    private final Notifier aimbotLoop;
    private double target;

    /**
     * Finishes When Rotation is Within 0.5 Degrees
     */
    public RunRotateBot(Drivetrain drivetrain, double deltaDegrees) {
        this.drivetrain = drivetrain;
        this.deltaDegrees = deltaDegrees;

        aimbotLoop = new Notifier(() -> drivetrain.setRotation(target));
    }

    @Override
    public void initialize() {
        drivetrain.setOverrideDrivetrain(true);

        target = drivetrain.getGyroRot() + deltaDegrees;
        drivetrain.setRotation(target);
        aimbotLoop.startPeriodic(0.5);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.setOverrideDrivetrain(false);
        aimbotLoop.stop();
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(drivetrain.getGyroRot() - target) <= 0.5);
    }
}
