package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.time.Instant;

public class RunPressedTest extends CommandBase {

    private long exec = Long.MAX_VALUE;
    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Pressed", true);
        exec = Instant.now().toEpochMilli();
    }

    @Override
    public void execute() {
        SmartDashboard.putBoolean("Pressed", true);
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("Pressed", false);
    }

    @Override
    public boolean isFinished() {
        return Instant.now().toEpochMilli() - exec >= 500;
    }
}
