package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class RunTargetShooter extends CommandBase {
    private final Shooter shooter;
    private final Limelight limelight;

    /**
     * Finishes When Speeds are Within 25 ticks/100ms and Rotation is Within 100 Ticks
     */
    public RunTargetShooter(Shooter shooter, Limelight limelight) {
        this.shooter = shooter;
        this.limelight = limelight;
    }

    boolean finished = false;
    double prevSpeed = 0;
    @Override
    public void execute() {
        limelight.setLights(3);
        double dist = limelight.getDistance();
        double angle = shooter.getIdealAngle(dist);
        double speed = shooter.getIdealSpeed(dist);
        double useSpeed = (Math.abs(speed - prevSpeed) >= 10) ? speed : prevSpeed;
        if (dist < 0 || angle == 401) { return; }
        shooter.targetShooter(angle, useSpeed);

        double[] speeds = shooter.getWheelSpeeds();
        //If wheelA and wheelB are within acceptable speed, and the angle is within accepted range, we are finished
        finished = Math.abs(speeds[0] - useSpeed) <= 25 && Math.abs(speeds[1] - useSpeed) <= 25 && Math.abs(shooter.getPivotTargetPos() - angle) <= 100;
    }

    @Override
    public void end(boolean interrupted) {
        limelight.setLights(1);
        //shooter.setWheelSpeed(0); //Finished will be called once this is ready, any implementation of this must
        // run the RunShooterWheels command to stop the wheels
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
