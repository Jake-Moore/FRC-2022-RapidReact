package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RunShooterWheels extends CommandBase {
    private final Shooter shooter;
    private final double vel;
    public RunShooterWheels(Shooter shooter, double vel) {
        this.shooter = shooter;
        this.vel = vel;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        shooter.setWheelSpeed(vel);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setWheelSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
