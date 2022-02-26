package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

import java.time.Instant;

public class RunBrake extends CommandBase {
    private final ClimbArms climbArms;
    private final double angleL;
    private final double angleR;
    private double start = Double.MAX_VALUE;
    public RunBrake(ClimbArms climbArms, double angleL, double angleR) {
        this.climbArms = climbArms;
        this.angleL = angleL;
        this.angleR = angleR;
    }

    @Override
    public void initialize() {
        start = Instant.now().toEpochMilli();
    }

    @Override
    public void execute() {
        climbArms.setBrake(angleL, angleR);
    }

    @Override
    public boolean isFinished() {
        return (Instant.now().toEpochMilli() - start >= 500D);
    }
}
