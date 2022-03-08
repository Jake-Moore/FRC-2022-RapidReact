package frc.robot.commands;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.util.Target;

public class RunCenterOnLimelight extends CommandBase {
    private final Drivetrain drivetrain;
    private final Limelight limelight;
    public RunCenterOnLimelight(Drivetrain drivetrain, Limelight limelight) {
        this.drivetrain = drivetrain;
        this.limelight = limelight;
    }

    @Override
    public void initialize() {
        drivetrain.overrideDrivetrain = true;
        limelight.setLights(3);


        Target initial = limelight.getTarget();
        drivetrain.setRotation(drivetrain.getGyroRot() + initial.yaw);

        Notifier aimbotLoop = new Notifier(() -> {
            if (drivetrain.overrideDrivetrain) {
                Target periodic = limelight.getTarget();
                drivetrain.setRotation(drivetrain.getGyroRot() + periodic.yaw);
            }
        });
        aimbotLoop.startPeriodic(1);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.overrideDrivetrain = false;
        limelight.setLights(1);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
