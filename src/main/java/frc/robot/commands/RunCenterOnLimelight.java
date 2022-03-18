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

    /**
     * Finishes When Rotation is Within 2.5 Yaw
     */
    public RunCenterOnLimelight(Drivetrain drivetrain, Limelight limelight, int endLights) {
        this.drivetrain = drivetrain;
        this.limelight = limelight;
        this.endLights = endLights;

        aimbotLoop = new Notifier(() -> {
            Target periodic = limelight.getTarget();
            drivetrain.setRotation(drivetrain.getGyroRot()+3D + periodic.yaw);

            SmartDashboard.putNumber("error%", Math.abs(periodic.yaw/drivetrain.getGyroRot()) * 100D);
        });
    }

    @Override
    public void initialize() {
        drivetrain.setOverrideDrivetrain(true);
        limelight.setLights(3);

        Target initial = limelight.getTarget();
        drivetrain.setRotation(drivetrain.getGyroRot() + initial.yaw);

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
        return (limelight.hasTarget() && Math.abs(limelight.getTarget().yaw) <= 2.5);
    }
}
