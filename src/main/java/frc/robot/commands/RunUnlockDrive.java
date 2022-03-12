package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class RunUnlockDrive extends CommandBase {
    private final Drivetrain drivetrain;
    public RunUnlockDrive(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {
        drivetrain.setOverrideDrivetrain(false);
    }
}
