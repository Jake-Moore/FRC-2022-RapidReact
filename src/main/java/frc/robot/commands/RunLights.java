package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;

import java.time.Instant;

public class RunLights extends CommandBase {
    private final Limelight limelight;
    private final int start;
    private final int end;

    /**
     * Finishes After 100ms
     */
    public RunLights(Limelight limelight, int start, int end) {
        this.limelight = limelight;
        this.start = start;
        this.end = end;
    }

    private long time = Long.MAX_VALUE;
    @Override
    public void initialize() {
        limelight.setLights(start);
        time = Instant.now().toEpochMilli();
    }

    @Override
    public void end(boolean interrupted) {
        limelight.setLights(end);
    }

    @Override
    public boolean isFinished() {
        return (Instant.now().toEpochMilli() - time) >= 100;
    }
}
