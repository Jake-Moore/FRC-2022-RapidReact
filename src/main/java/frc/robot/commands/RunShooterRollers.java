package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RunShooterRollers extends CommandBase {
    private final Shooter shooter;
    private final double power;
    public RunShooterRollers(Shooter shooter, double power) {
        this.shooter = shooter;
        this.power = power;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        shooter.setRollerPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setRollerPower(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
