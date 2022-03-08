package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class RunTargetShooter extends CommandBase {
    private Shooter shooter;
    private Limelight limelight;
    public RunTargetShooter(Shooter shooter, Limelight limelight) {
        this.shooter = shooter;
        this.limelight = limelight;
    }

    @Override
    public void execute() {
        limelight.setLights(3);
        double dist = limelight.getDistance();
        double angle = shooter.getIdealAngle(dist);
        double speed = shooter.getIdealSpeed(dist);
        if (dist < 0 || angle == 401) { return; }
        shooter.targetShooter(angle, speed);
    }

    @Override
    public void end(boolean interrupted) {
        limelight.setLights(1);
        shooter.setWheelSpeed(0);
    }
}
