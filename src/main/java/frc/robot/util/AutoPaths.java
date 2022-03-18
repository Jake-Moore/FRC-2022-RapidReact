package frc.robot.util;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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

        trajs.add(
            new NamedCommand(
                "Taxi Back + 1 Ball Forward",

                new RunDrivePos(drivetrain, -getTicksFromDist(45), -getTicksFromDist(45))
                .andThen(new ParallelRaceGroup(
                    new RunTimer(1.5),
                    new RunCenterOnLimelight(drivetrain, limelight, 1)
                ))
                .andThen(new RunLights(limelight, 3, 3)
                .andThen(new RunTargetShooter(shooter, limelight, 1)
                .andThen(new ParallelRaceGroup(
                    new RunTimer(2.5),
                    new RunShooterRollers(shooter, -0.75, 0)
                )).andThen(new RunShooterWheels(shooter, 0, 0))
            )))
        );

        trajs.add(
            new NamedCommand(
                "Pickup Forward + 2 Ball + Taxi",

                new ParallelCommandGroup(
                    new RunShooterPivot(shooter, -55500),
                    new RunTimer(1.5)
                ).andThen(new ParallelRaceGroup(
                    new RunDrivePos(drivetrain, getTicksFromDist(50), getTicksFromDist(50)),
                    new RunShooterWheels(shooter, 4096, 0),
                    new RunShooterRollers(shooter, 0.75, 0)
                )).andThen(new ParallelRaceGroup(
                    new RunTimer(0.25),
                    new RunShooterPivot(shooter, -15000)
                ))
                .andThen(new ParallelRaceGroup(
                    new RunTimer(3),
                    new RunRotateBot(drivetrain, 180)
                )).andThen(new ParallelRaceGroup(
                    new RunTimer(1.5),
                    new RunCenterOnLimelight(drivetrain, limelight, 1)
                ))
                .andThen(new RunLights(limelight, 3, 3))
                .andThen(new RunTargetShooter(shooter, limelight, 1))
                .andThen(new ParallelRaceGroup(
                    new RunTimer(2.5),
                    new RunShooterRollers(shooter, -0.75, 0)
                )).andThen(new RunShooterWheels(shooter, 0, 0))
            )
        );
    }

    private double getTicksFromDist(double inches) {
        return (inches/(6*Math.PI)) * (25D/3D) * 2048;
    }
}
