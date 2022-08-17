package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

import java.time.Instant;

public class RunToggleShooterWheels extends CommandBase {
    private final RobotContainer container;

    /**
     * Finishes NEVER
     */
    public RunToggleShooterWheels(RobotContainer container) {
        this.container = container;
    }

    @Override
    public void initialize() {
        container.manualSpinning = !container.manualSpinning;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
