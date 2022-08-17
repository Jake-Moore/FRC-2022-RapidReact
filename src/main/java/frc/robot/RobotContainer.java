package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.util.AutoPaths;
import frc.robot.util.JoystickAxisToButton;
import frc.robot.util.RequireButton;

import java.time.Instant;

@SuppressWarnings("unused")
public class RobotContainer {
    //Subsystems
    public final ClimbArms climbArms = new ClimbArms();
    public final Drivetrain drivetrain = new Drivetrain();
    public final Shooter shooter = new Shooter();
    public final Limelight limelight = new Limelight();
    public final Cameras cameras = new Cameras();
    public final SendableChooser<Double> speedChooser;
    public double manualActual = -10000;
    public boolean manualSpinning = false;
    public boolean lastWasZero = false;

    public AutoPaths ap = new AutoPaths(climbArms, drivetrain, shooter, limelight);

    //Joysticks
    private final Joystick pJoy = new Joystick(Constants.pJoyID);
    private final Joystick sJoy = new Joystick(Constants.sJoyID);

    //Primary Controller Buttons//
    private final JoystickButton joyBSquare   = new JoystickButton(pJoy, 1); //Square
    private final JoystickButton joyBX        = new JoystickButton(pJoy, 2); //X
    private final JoystickButton joyBCircle   = new JoystickButton(pJoy, 3); //Circle
    private final JoystickButton joyBTriangle = new JoystickButton(pJoy, 4); //Triangle
    private final JoystickButton joyBBR        = new JoystickButton(pJoy, 6); //Right Bumper Button
    private final JoystickButton joyBTR        = new JoystickButton(pJoy, 8); //Right Trigger Button
    private final JoystickButton joyBBL        = new JoystickButton(pJoy, 5); //Left Bumper Button
    private final JoystickButton joyBTL        = new JoystickButton(pJoy, 7); //Left Trigger Button
    private final JoystickButton joyBShare    = new JoystickButton(pJoy, 9); //Share Button
    private final JoystickButton joyBOptions  = new JoystickButton(pJoy, 10);//Options Button
    private final JoystickButton joyBLeftJoystick  = new JoystickButton(pJoy, 11);//L Joystick Button
    private final JoystickButton joyBRightJoystick  = new JoystickButton(pJoy, 12);//R Joystick Button
    private final JoystickButton joyBPS       = new JoystickButton(pJoy, 13);//PlayStation Button
    private final JoystickButton joyBBig      = new JoystickButton(pJoy, 14);//Big (Center) Button
    private final POVButton joyPOVN = new POVButton(pJoy, 0);   //North
    private final POVButton joyPOVE = new POVButton(pJoy, 90);  //East
    private final POVButton joyPOVS = new POVButton(pJoy, 180); //South
    private final POVButton joyPOVW = new POVButton(pJoy, 270); //West

    private final JoystickButton sJoyBA = new JoystickButton(sJoy, 1); //A Button
    private final JoystickButton sJoyBB = new JoystickButton(sJoy, 2); //B Button
    private final JoystickButton sJoyBX = new JoystickButton(sJoy, 3); //X Button
    private final JoystickButton sJoyBY = new JoystickButton(sJoy, 4); //Y Button
    private final JoystickButton sJoyBBL = new JoystickButton(sJoy, 5); //Left Bumper Button
    private final JoystickButton sJoyBBR = new JoystickButton(sJoy, 6); //Right Bumper Button
    private final JoystickButton sJoyBBack = new JoystickButton(sJoy, 7); //Back Button
    private final JoystickButton sJoyBStart = new JoystickButton(sJoy, 8); //Back Button
    private final JoystickAxisToButton sJoyBTR = new JoystickAxisToButton(sJoy, 3);
    private final JoystickAxisToButton sJoyBTL = new JoystickAxisToButton(sJoy, 2);

    private final JoystickButton sJoyBLS = new JoystickButton(sJoy, 9); //Left Stick Button
    private final JoystickButton sJoyBRS = new JoystickButton(sJoy, 10); //Right Stick Button

    private final POVButton sJoyPOVN = new POVButton(sJoy, 0); //North
    private final POVButton sJoyPOVS = new POVButton(sJoy, 180); //South
    private final POVButton sJoyPOVW = new POVButton(sJoy, 270); //North
    private final POVButton sJoyPOVE = new POVButton(sJoy, 90); //South

    public RobotContainer(SendableChooser<Double> speedChooser) {
        configureButtonBindings();
        this.speedChooser = speedChooser;
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        cameras.enableCameras();

        //Drivetrain
        drivetrain.setDefaultCommand(new RunCommand(() -> drivetrain.driveFromJoysticks(powAxis(pJoy.getRawAxis(1), 7D/3D) * getSpeedFactor(), pJoy.getRawAxis(2)/2.25D), drivetrain //Functional, not tuned
        ));

        //-----START CLIMB-----//

        //KEEP THESE COMMANDS THE SAME AS pJoyBY !!!
        joyBShare.whileHeld(new RequireButton(new RunStraightRopePosAdj(climbArms, 20000, 0.7), joyBPS));
        joyBShare.whileHeld(new RequireButton(new ParallelCommandGroup(
                new RunShooterPivot(shooter, -6250),
                new RunBrake(climbArms, 90, 90)
            ).andThen(new RunPivotPos(climbArms, 22000, 0.15))
        , joyBPS));
        sJoyBY.whileHeld(new RequireButton(new RunStraightRopePosAdj(climbArms, 20000, 0.7), sJoyBBack));
        sJoyBY.whileHeld(new RequireButton(new ParallelCommandGroup(
                new RunShooterPivot(shooter, -6250),
                new RunBrake(climbArms, 90, 90)
            ).andThen(new RunPivotPos(climbArms, 22000, 0.15))
        , sJoyBBack));

        sJoyBA.whenPressed(new RequireButton(new RunStraightRopePosAdj(climbArms, -20000, 0.7), sJoyBBack));

        joyBBig.whenPressed(new RequireButton(
                new ParallelCommandGroup(
                        new RunTimer(0.5),
                        new RunBrake(climbArms, 90, 90),
                        new RunShooterPivot(shooter, -6250)
                ).andThen(new RunPivotRopePos(climbArms, 75000))
                .andThen(new RunPivotPos(climbArms, 22000, 0.15))
        , joyBPS));

        joyBOptions.whileHeld(new RequireButton(
            new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 0, 0.7),
                new RunPivotRopePos(climbArms, 240000)
            )
        , joyBPS));
        joyBTriangle.whenPressed(new RequireButton(new RunPivotPos(climbArms, 17000, 0.15), joyBPS));
        joyBCircle.whenPressed(new RequireButton(
            new RunPivotRopePos(climbArms, 207000)
            .andThen(new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 120000, 0.4),
                new RunPivotRopePos(climbArms, 140000),
                new RunPivotPos(climbArms, 26500, 0.15)
            )).andThen(new RunPivotRopePos(climbArms, 75000))
            .andThen(new ParallelCommandGroup(
                    new RunStraightRopePos(climbArms, 0, 0.7),
                    new RunSmoothPivotPos(climbArms, -15000, 0.5, 1000)
            )).andThen(new ParallelCommandGroup(
                    new RunPivotRopePos(climbArms, 0),
                    new RunStraightRopePos(climbArms, 75000, 0.7)
            )).andThen(new RunBrake(climbArms, 60, 115))
            .andThen(new RunPivotPos(climbArms, 0, 0.3)) //Using smooth class freaks out
            .andThen(new RunStraightRopePos(climbArms, 17000, 0.7))
            .andThen(new RunShooterPivot(shooter, 8000))
        , joyBPS));

        joyBX.whenPressed(new RequireButton(
            new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 17000, 0.7),
                new RunPivotRopePos(climbArms, 75000)
            ).andThen(new RunPivotPos(climbArms, -3000, 0.3))
            .andThen(new RunPivotRopePos(climbArms, 500))
            .andThen(new RunBrake(climbArms, 60, 115))
            .andThen(new RunShooterPivot(shooter, 8000))
        , joyBPS));

        /*
        joyBX.whenPressed(new RequireButton(
            new ParallelCommandGroup(
                    new RunStraightRopePos(climbArms, 80000, 0.4),
                    new RunPivotRopePos(climbArms, 140000),
                    new RunPivotPos(climbArms, 22000, 0.15)
            ).andThen(new RunPivotRopePos(climbArms, 75000))
            .andThen(new ParallelCommandGroup(
                    new RunStraightRopePos(climbArms, 60000, 0.7),
                    new RunPivotRopePos(climbArms, 0)
            )).andThen(new RunBrake(climbArms, 60, 115))
            .andThen(new RunPivotPos(climbArms, -6250, 0.3))
            .andThen(new RunStraightRopePos(climbArms, 20000, 0.7))
            .andThen(new RunShooterPivot(shooter, 8000))
        , joyBPS));
        */

        joyBSquare.whenPressed(new RequireButton(
                new ParallelCommandGroup(
                        new RunStraightRopePos(climbArms, 15000, 0.7),
                        new RunPivotRopePos(climbArms, 50000)
                ).andThen(new RunPivotPos(climbArms, 1000, 0.15))
                .andThen(new RunPivotRopePos(climbArms, 0))
                .andThen(new RunBrake(climbArms, 60, 115))
                .andThen(new RunShooterPivot(shooter, 8000))
        , joyBPS));
        joyBRightJoystick.whenPressed(new RequireButton(new RunBrake(climbArms, 60, 115), joyBPS));
        //-----END CLIMB-----//

        //Primary Controller Buttons//
        joyBLeftJoystick.whileHeld(new RunLights(limelight, 3, 3)); //Lights TODO make 3, 1
        joyBTL.whileHeld(new ParallelCommandGroup(
                new RunCommand(() -> manualSpinning = false),
                new RunShooterWheels(shooter, 6144, 0), //Intake Wheels
                new RunShooterRollers(shooter, 0.5, 0) //Slightly Intake Rollers
        ));
        joyBBL.whileHeld(new ParallelRaceGroup(
                new RunLights(limelight, 3, 3),
                new RunCommand(() -> drivetrain.setOverrideDrivetrain(true)),
                new RunCommand(() -> drivetrain.driveFromJoysticks(0, 0))
            ).andThen(new ParallelCommandGroup( //Auto Shot?
                new RunCenterOnLimelight(drivetrain, limelight, 3),
                new RunTargetShooter(shooter, limelight, 3)
            ).andThen(
                new RunShooterRollers(shooter, -0.5, 0)
            )
        ));
        joyBBL.whenReleased(new RunLights(limelight, 1, 1).andThen(new RunShooterWheels(shooter, 0, 0)));
        joyBTR.whileHeld(new RunShooterPivot(shooter, -55500)); //Set Pivot to intake
        joyBBR.whileHeld(new RunShooterPivot(shooter, -6250)); //Set Pivot to stow
        joyPOVW.whileHeld(new RequireButton(new RunBrake(climbArms, 90, 90).andThen( //Oh-Shit Button
                new ParallelCommandGroup(
                        new RunStraightRopePosAdj(climbArms, -20000, 0.7),
                        new RunPivotRopePosAdj(climbArms, -20000)
                )//.andThen(new RunClimbPivotPIDToggle(climbArms, true)
                //).andThen(new RunPivotPos(climbArms, 0, 0.15))
        ), joyBPS));
        joyPOVN.whileHeld(new ParallelCommandGroup(
                new RunShooterWheels(shooter, -2024, 0),
                new RunShooterRollers(shooter, -0.4, 0)
        ));
        joyPOVS.whenPressed(new RunShooterPivot(shooter, 10500));
        joyPOVE.whileHeld(new RunShooterRollers(shooter, -0.5, 0));

        //Calibration buttons
        //joyBTriangle.whenPressed(new RunShooterPivotAdj(shooter, 500, 15000, -55000));
        //joyBX.whenPressed(new RunShooterPivotAdj(shooter, -500, 15000, -55000));
        //joyBSquare.whenPressed(new RunShooterWheelsAdj(shooter, 250, 0, -10000));
        //joyBCircle.whenPressed(new RunShooterWheelsAdj(shooter, -250, 0, -10000));

        //Secondary Controller Buttons//
        sJoyBLS.whenPressed(new RunLights(limelight, 3, 1));
        sJoyBBR.whileHeld(new RunLights(limelight, 3, 3).andThen(new RunCenterOnLimelight(drivetrain, limelight, 1)));
        sJoyBTR.whileHeld(new RunLights(limelight, 3, 3).andThen(new RunTargetShooter(shooter, limelight, 1)));
        sJoyBTR.whenReleased(new ParallelRaceGroup(new RunTimer(0.25), new RunShooterWheels(shooter, 0, 0)));
        sJoyBBL.whileHeld(new RunTargetShooterSpeed(shooter, limelight));
        sJoyBBL.whenReleased(new ParallelRaceGroup(new RunTimer(0.25), new RunShooterWheels(shooter, 0, 0)));
        sJoyBTL.whileHeld(new RunShooterRollers(shooter, -0.75, 0)); //Change later to 1 ball per click (pos)
        sJoyPOVW.whileHeld(new RunShooterPivotAdj(shooter, -50, 0, -55500));
        sJoyPOVE.whileHeld(new RunShooterPivotAdj(shooter, 50, 0, -55500));
        sJoyBRS.whileHeld(new ParallelCommandGroup(
                new RunShooterWheels(shooter, -4096, 0),
                new RunShooterRollers(shooter, -0.75, 0)
        ));

        sJoyPOVN.whileHeld(new RunCommand(() -> manualActual = Math.max(-20000, manualActual - 25)));
        sJoyPOVS.whileHeld(new RunCommand(() -> manualActual = Math.min(0, manualActual + 25)));
        sJoyBStart.whenPressed(new RunToggleShooterWheels(this));
    }

    private double getSpeedFactor() {
        return speedChooser.getSelected();
    }

    public void updateSmartDashboard() {
        SmartDashboard.putNumber("LeftStraightPos", climbArms.mLeftStraight.getSelectedSensorPosition());
        SmartDashboard.putNumber("LeftPivotPos", climbArms.mLeftPivot.getSelectedSensorPosition());
        SmartDashboard.putNumber("RightStraightPos", climbArms.mRightStraight.getSelectedSensorPosition());
        SmartDashboard.putNumber("RightPivotPos", climbArms.mRightPivot.getSelectedSensorPosition());

        SmartDashboard.putNumber("ClimbPivoterPos", climbArms.mPivoter.getSelectedSensorPosition());
        SmartDashboard.putNumber("ClimbPivotAbsPos", climbArms.getPivotAbsolutePos());

        SmartDashboard.putNumber("ShooterPivoterPos", shooter.mPivoter.getSelectedSensorPosition());
        SmartDashboard.putNumber("ShooterPivotAbsPos", shooter.getPivotAbsolutePos());

        SmartDashboard.putNumber("Gyro (Rotation)", drivetrain.getGyroRot());

        double[] wheelSpeeds = shooter.getWheelSpeeds();
        double targetWheelSpeed = shooter.getTargetWheelSpeed();
        SmartDashboard.putNumber("Left Wheel Speed", wheelSpeeds[0]);
        SmartDashboard.putNumber("Right Wheel Speed", wheelSpeeds[1]);
        SmartDashboard.putNumber("Target Wheel Speed", targetWheelSpeed);
        SmartDashboard.putBoolean("Wheels At Speed", Math.abs(wheelSpeeds[0] - targetWheelSpeed) <= 50 && Math.abs(wheelSpeeds[1] - targetWheelSpeed) <= 50);

        SmartDashboard.putBoolean("Targets?", limelight.hasTarget());
        SmartDashboard.putNumber("Distance?", limelight.getDistance());

        SmartDashboard.putNumber("IdealSpeed", shooter.getIdealSpeed(limelight.getDistance()));
        SmartDashboard.putNumber("IdealAngle", shooter.getIdealAngle(limelight.getDistance()));

        SmartDashboard.putNumber("mLeftA", drivetrain.getDrivePos()[0]);
        SmartDashboard.putNumber("mRightA", drivetrain.getDrivePos()[1]);

        if (limelight.hasTarget()) {
            SmartDashboard.putNumber("Yaw", limelight.getTarget().yaw);
        }
    }

    public double powAxis(double a, double b) {
        if (a >= 0) {
            return Math.pow(a, b);
        }else {
            return -Math.pow(-a, b);
        }
    }

    public void init() {
        climbArms.setPivotPos(0);
        climbArms.setBrake(90, 90);
    }

    public void whenDisabled() {}
}
