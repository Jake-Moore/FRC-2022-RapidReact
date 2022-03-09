package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RunShooterRollers extends CommandBase {
    private final Shooter shooter;
    private final double start;
    private final double end;
    public RunShooterRollers(Shooter shooter, double start, double end) {
        this.shooter = shooter;
        this.start = start;
        this.end = end;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        shooter.setRollerPower(start);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setRollerPower(end);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
