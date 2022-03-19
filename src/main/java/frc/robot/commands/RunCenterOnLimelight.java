package frc.robot.commands;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.util.Target;

public class RunCenterOnLimelight extends CommandBase {
    private final Drivetrain drivetrain;
    private final Limelight limelight;
    private final int endLights;
    private final Notifier aimbotLoop;

    private final double allowedYawError = 0.75;

    /**
     * Finishes When Rotation is Within 0.75 Yaw
     */
    public RunCenterOnLimelight(Drivetrain drivetrain, Limelight limelight, int endLights) {
        this.drivetrain = drivetrain;
        this.limelight = limelight;
        this.endLights = endLights;

        aimbotLoop = new Notifier(() -> {
            Target periodic = limelight.getTarget();
            if (Math.abs(periodic.yaw) > allowedYawError) {
                drivetrain.setRotation(drivetrain.getGyroRot() + periodic.yaw);
            }
        });
    }

    int successes = 0;
    int failures = 0;

    @Override
    public void initialize() {
        successes = 0; failures = 0;

        drivetrain.setOverrideDrivetrain(true);
        limelight.setLights(3);

        Target initial = limelight.getTarget();
        if (Math.abs(initial.yaw) > allowedYawError) {
            drivetrain.setRotation(drivetrain.getGyroRot() + initial.yaw);
        }

        aimbotLoop.startPeriodic(0.5);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.setOverrideDrivetrain(false);
        limelight.setLights(endLights);
        aimbotLoop.stop();
    }

    @Override
    public boolean isFinished() {
        if (limelight.hasTarget() && Math.abs(limelight.getTarget().yaw) <= allowedYawError) {
            successes++;
        }else {
            failures++;
        }
        if (failures >= 10) {
            successes = 0; failures = 0;
        }
        double percent = (successes + failures == 0) ? 0 : (successes / (double) (successes + failures));
        SmartDashboard.putString("CenterRatio", successes + "s : " + failures + "f : " + percent + "%");
        return successes >= 50 && (percent >= 0.8);

        //Basically keep track of how many times we were successful and how many times we were not
        // If we get too many failures then reset, and if we have enough with a good enough success rate, end
    }
}
