package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class RunTargetShooter extends CommandBase {
    private final Shooter shooter;
    private final Limelight limelight;
    public RunTargetShooter(Shooter shooter, Limelight limelight) {
        this.shooter = shooter;
        this.limelight = limelight;
    }

    boolean finished = false;
    @Override
    public void execute() {
        limelight.setLights(3);
        double dist = limelight.getDistance();
        double angle = shooter.getIdealAngle(dist);
        double speed = shooter.getIdealSpeed(dist);
        if (dist < 0 || angle == 401) { return; }
        shooter.targetShooter(angle, speed);

        double[] speeds = shooter.getWheelSpeeds();
        //If wheelA and wheelB are within acceptable speed, and the angle is within accepted range, we are finished
        finished = Math.abs(speeds[0] - speed) <= 25 && Math.abs(speeds[1] - speed) <= 25 && Math.abs(shooter.getPivotTargetPos() - angle) <= 100;
    }

    @Override
    public void end(boolean interrupted) {
        limelight.setLights(1);
        shooter.setWheelSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
