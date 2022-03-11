package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RunShooterWheels extends CommandBase {
    private final Shooter shooter;
    private final double start;
    private final double end;
    public RunShooterWheels(Shooter shooter, double start, double end) {
        this.shooter = shooter;
        this.start = start;
        this.end = end;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        shooter.setWheelSpeed(start);
        SmartDashboard.putBoolean("aaaaa", start == 0);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setWheelSpeed(end);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
