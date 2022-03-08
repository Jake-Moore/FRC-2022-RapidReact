package frc.robot.commands;

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
        drivetrain.aimbot = true;
        limelight.setLights(3);

        Target t = limelight.getTarget();
        drivetrain.setRotation(drivetrain.getGyroRot() + t.yaw);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.aimbot = false;
        limelight.setLights(1);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
