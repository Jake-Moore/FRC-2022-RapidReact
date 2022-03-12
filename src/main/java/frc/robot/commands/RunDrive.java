package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class RunDrive extends CommandBase {
    private final Drivetrain drivetrain;
    private final double power;
    public RunDrive(Drivetrain drivetrain, double power) {
        this.drivetrain = drivetrain;
        this.power = power;
    }

    @Override
    public void initialize() {
        drivetrain.overrideDrivetrain = true;
        drivetrain.setPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.setPower(0);
        drivetrain.overrideDrivetrain = false;
    }
}
