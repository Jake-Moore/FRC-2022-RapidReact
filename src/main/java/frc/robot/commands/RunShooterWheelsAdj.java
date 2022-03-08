package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RunShooterWheelsAdj extends CommandBase {
    private final Shooter shooter;
    private final double adj;
    private final double max;
    private final double min;
    public RunShooterWheelsAdj(Shooter shooter, double adj, double max, double min) {
        this.shooter = shooter;
        this.adj = adj;
        this.max = max;
        this.min = min;
    }

    @Override
    public void initialize() {
        shooter.setWheelSpeed(Math.max(Math.min(shooter.getTargetWheelSpeed() + adj, max), min));
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
