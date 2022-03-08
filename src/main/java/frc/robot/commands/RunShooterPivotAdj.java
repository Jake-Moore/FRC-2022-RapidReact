package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RunShooterPivotAdj extends CommandBase {
    private final Shooter shooter;
    private final double adj;
    private final double max;
    private final double min;
    public RunShooterPivotAdj(Shooter shooter, double adj, double max, double min) {
        this.shooter = shooter;
        this.adj = adj;
        this.max = max;
        this.min = min;
    }

    @Override
    public void initialize() {
        shooter.setPivot(Math.min(Math.max(shooter.getPivot()+adj, min), max));
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
