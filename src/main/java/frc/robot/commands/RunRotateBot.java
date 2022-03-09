package frc.robot.commands;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Target;

public class RunRotateBot extends CommandBase {
    private final Drivetrain drivetrain;
    private final double deltaDegrees;
    public RunRotateBot(Drivetrain drivetrain, double deltaDegrees) {
        this.drivetrain = drivetrain;
        this.deltaDegrees = deltaDegrees;
    }

    @Override
    public void initialize() {
        drivetrain.overrideDrivetrain = true;

        double target = drivetrain.getGyroRot() + deltaDegrees;
        drivetrain.setRotation(target);

        Notifier aimbotLoop = new Notifier(() -> {
            if (drivetrain.overrideDrivetrain) {
                drivetrain.setRotation(target);
            }
        });
        aimbotLoop.startPeriodic(0.5);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.overrideDrivetrain = false;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
