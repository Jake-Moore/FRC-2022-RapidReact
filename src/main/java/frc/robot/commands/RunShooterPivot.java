package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RunShooterPivot extends CommandBase {
    private final Shooter shooter;
    private final double pos;

    /**
     * Finishes When Shooter Pivot is Within 128 Ticks
     */
    public RunShooterPivot(Shooter shooter, double pos) {
        this.shooter = shooter;
        this.pos = pos;
    }

    @Override
    public void initialize() {
        shooter.setPivot(pos);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return Math.abs(shooter.getPivot() - pos) <= 128;
    }
}
