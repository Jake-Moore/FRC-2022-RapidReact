package frc.robot.commands;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.util.Target;

public class RunCenterOnLimelight extends CommandBase {
    private final Drivetrain drivetrain;
    private final Limelight limelight;
    private final Notifier aimbotLoop;
    public RunCenterOnLimelight(Drivetrain drivetrain, Limelight limelight) {
        this.drivetrain = drivetrain;
        this.limelight = limelight;

        aimbotLoop = new Notifier(() -> {
            Target periodic = limelight.getTarget();
            drivetrain.setRotation(drivetrain.getGyroRot() + periodic.yaw);
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
        limelight.setLights(1);
        aimbotLoop.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
