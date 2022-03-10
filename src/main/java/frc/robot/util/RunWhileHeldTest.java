package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RunWhileHeldTest extends CommandBase {

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Held", true);
    }

    @Override
    public void execute() {
        SmartDashboard.putBoolean("Held", true);
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("Held", false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
