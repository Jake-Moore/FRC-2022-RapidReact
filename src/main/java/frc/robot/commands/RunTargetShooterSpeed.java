package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class RunTargetShooterSpeed extends CommandBase {
    private final Shooter shooter;
    private final Limelight limelight;

    /**
     * Finishes When Speeds are Within 25 ticks/100ms
     */
    public RunTargetShooterSpeed(Shooter shooter, Limelight limelight) {
        this.shooter = shooter;
        this.limelight = limelight;
    }

    boolean finished = false;
    @Override
    public void execute() {
        limelight.setLights(3);
        double dist = limelight.getDistance();
        double speed = shooter.getIdealSpeed(dist);
        if (shooter.getIdealAngle(dist) < 0 || shooter.getIdealAngle(dist) == 401) { return; }
        shooter.targetShooter(speed);

        double[] speeds = shooter.getWheelSpeeds();
        //If wheelA and wheelB are within acceptable speed, and the angle is within accepted range, we are finished
        finished = Math.abs(speeds[0] - speed) <= 25 && Math.abs(speeds[1] - speed) <= 25;
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
