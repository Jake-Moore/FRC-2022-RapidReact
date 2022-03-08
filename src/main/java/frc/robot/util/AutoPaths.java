package frc.robot.util;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.commands.*;
import frc.robot.subsystems.ClimbArms;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class AutoPaths {
    private final ClimbArms climbArms;
    private final Drivetrain drivetrain;
    private final Shooter shooter;
    private final Limelight limelight;

    public ArrayList<NamedCommand> trajs = new ArrayList<>();

    public AutoPaths(ClimbArms climbArms, Drivetrain drivetrain, Shooter shooter, Limelight limelight) {
        this.climbArms = climbArms;
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.limelight = limelight;

        double inchesBackup = 20; //How many inches to back up in order to achieve a taxi
        double ticks = inchesBackup/(6*Math.PI) * (25D/3D) * 2048;
        trajs.add(
            new NamedCommand(
                "Taxi Back + 1 Ball Forward",

                new RunDrivePos(drivetrain, -ticks, -ticks)
                .andThen(new RunLights(limelight, 3, 3)
                .andThen(new RunTargetShooter(shooter, limelight)
                .andThen(new ParallelRaceGroup(
                    new RunTimer(5),
                    new RunShooterRollers(shooter, 0.75)
                )).andThen(new RunShooterWheels(shooter, 0))))
            )
        );
    }
}
