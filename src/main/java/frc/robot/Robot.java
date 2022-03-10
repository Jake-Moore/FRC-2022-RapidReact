// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.util.NamedCommand;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private RobotContainer kRobotContainer;
    private final SendableChooser<String> kChooser = new SendableChooser<>();
    private final HashMap<String, @Nullable NamedCommand> paths = new HashMap<>();

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        //House Keeping
        kRobotContainer = new RobotContainer();
        kRobotContainer.limelight.setLights(1);
        kRobotContainer.shooter.calibrate();

        //Auto Paths
        kChooser.setDefaultOption("None", null);
        paths.put("None", null);

        for (NamedCommand nc : kRobotContainer.ap.trajs) {
            paths.put(nc.getName(), nc);
            kChooser.addOption(nc.getName(), nc.getName());
        }

        SmartDashboard.putData(kChooser);
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */

    @Override
    public void robotPeriodic() {
        // Runs the Scheduler. This is responsible for polling buttons, adding
        // newly-scheduled
        // commands, running already-scheduled commands, removing finished or
        // interrupted commands,
        // and running subsystem periodic() methods. This must be called from the
        // robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
        kRobotContainer.updateSmartDashboard();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select between different
     * autonomous modes using the dashboard. The sendable chooser code works with the Java
     * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all the chooser code and
     * uncomment the getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to the switch structure
     * below with additional strings. If using the SendableChooser make sure to add them to the
     * chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        kRobotContainer.drivetrain.setDriveMaxPower(Constants.driveMaxPowerAuto);
        NamedCommand command = paths.get(kChooser.getSelected());
        if (command != null) {
            command.getCommand().schedule();
        }
        kRobotContainer.init();
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    /** This function is called once when teleop is enabled. */
    @Override
    public void teleopInit() {
        kRobotContainer.drivetrain.setDriveMaxPower(Constants.driveMaxPowerTeleop);
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        CommandScheduler.getInstance().cancelAll();
        kRobotContainer.init();
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }

    /** This function is called once when the robot is disabled. */
    @Override
    public void disabledInit() {}

    /** This function is called periodically when disabled. */
    @Override
    public void disabledPeriodic() {
        kRobotContainer.whenDisabled();
    }

    /** This function is called once when test mode is enabled. */
    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {}
}
