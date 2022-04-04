package frc.robot.commands;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class RunRotateBot extends CommandBase {
    private final Drivetrain drivetrain;
    private final double deltaDegrees;
    private final boolean preciseIsFinished;
    private final Notifier aimbotLoop;
    private double target;

    /**
     * Finishes When Rotation is Within 0.5 Degrees
     */
    public RunRotateBot(Drivetrain drivetrain, double deltaDegrees, boolean preciseIsFinished) {
        this.drivetrain = drivetrain;
        this.deltaDegrees = deltaDegrees;
        this.preciseIsFinished = preciseIsFinished;

        aimbotLoop = new Notifier(() -> {
            if (gyroBroke) { return; }
            drivetrain.setRotation(target);
            gyroBroke = drivetrain.getGyroRot() == -0D;
        });
    }

    int successes = 0;
    int failures = 0;

    private boolean gyroBroke = false;
    @Override
    public void initialize() {
        successes = 0; failures = 0;
        drivetrain.setOverrideDrivetrain(true);
        drivetrain.setOutputs(0.1);

        target = drivetrain.getGyroRot() + deltaDegrees;
        drivetrain.setRotation(target);
        aimbotLoop.startPeriodic(1);
        gyroBroke = drivetrain.getGyroRot() == -0D;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.setOverrideDrivetrain(false);
        aimbotLoop.stop();
        drivetrain.setOutputs(1.0);
    }

    @Override
    public boolean isFinished() {
        if (!preciseIsFinished) {
            return Math.abs(drivetrain.getGyroRot() - target) <= Constants.allowedYawError;
        }

        if (Math.abs(drivetrain.getGyroRot() - target) <= Constants.allowedYawError) {
            successes++;
        }else {
            failures++;
        }
        if (failures >= 5) {
            successes = 0; failures = 0;
        }

        return successes >= 10 && ((successes + failures == 0) ? 0 : (successes / (double) (successes + failures))) >= 0.9;
    }
}
