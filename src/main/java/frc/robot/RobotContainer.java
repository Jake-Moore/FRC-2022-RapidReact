package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.*;
import frc.robot.subsystems.ClimbArms;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.util.RequireButton;

@SuppressWarnings("unused")
public class RobotContainer {
    //Subsystems
    private final ClimbArms climbArms = new ClimbArms();
    private final Drivetrain drivetrain = new Drivetrain();
    private final Shooter shooter = new Shooter();
    private final Limelight limelight = new Limelight();

    //Joysticks
    private final Joystick pJoy = new Joystick(Constants.pJoyID);
    private final Joystick sJoy = new Joystick(Constants.sJoyID);

    //Joystick Buttons
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
    private final JoystickButton joyBPS       = new JoystickButton(pJoy, 13);//PlayStation Button
    private final JoystickButton joyBBig      = new JoystickButton(pJoy, 14);//Big (Center) Button
    private final POVButton joyPOVN = new POVButton(pJoy, 0);   //North
    private final POVButton joyPOVE = new POVButton(pJoy, 90);  //East
    private final POVButton joyPOVS = new POVButton(pJoy, 180); //South
    private final POVButton joyPOVW = new POVButton(pJoy, 270); //West

    private final POVButton sJoyPOVN = new POVButton(sJoy, 0); //North
    private final JoystickButton sJoyY = new JoystickButton(sJoy, 4); //Y Button


    public RobotContainer() {
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        //Drivetrain
        drivetrain.setDefaultCommand(new RunCommand(() ->
                drivetrain.driveFromJoysticks(powAxis(pJoy.getRawAxis(1), 7D/3D) * 0.65D, pJoy.getRawAxis(2)/2.25D), drivetrain //Functional, not tuned
        ));


        //-----START CLIMB-----//
        joyBShare.whileHeld(new RequireButton(new ParallelCommandGroup(
                new RunStraightRopePosAdj(climbArms, 20000),
                new RunShooterPivot(shooter, -4000)
        ), joyBPS));
        joyBBig.whileHeld(new RequireButton(new RunPivotPos(climbArms, -4000), joyBPS));
        joyBOptions.whileHeld(new RequireButton(new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 0),
                new RunPivotRopePos(climbArms, 240000)
        ), joyBPS));
        joyBTriangle.whileHeld(new RequireButton(new RunPivotPosAdj(climbArms, -500), joyBPS));
        joyBCircle.whenPressed(new RequireButton(
                new ParallelCommandGroup(
                        new RunStraightRopePos(climbArms, 100000),
                        new RunPivotRopePos(climbArms, 0)
                ).andThen(new RunPivotPos(climbArms, -2000)
                ).andThen(new ParallelCommandGroup(
                        new RunPivotRopePos(climbArms, 50000),
                        new RunStraightRopePos(climbArms, 0)
                )).andThen(new RunPivotPos(climbArms, 9000)
                ).andThen(new ParallelCommandGroup(
                        new RunStraightRopePos(climbArms, 100000),
                        new RunPivotRopePos(climbArms, 0)
                )).andThen(new RunPivotPos(climbArms, 3250)
                ).andThen(new RunStraightRopePos(climbArms, 0)
                ).andThen(new RunPivotRopePos(climbArms, 50000)
                ).andThen(new RunPivotPos(climbArms, 7000)
                ).andThen(new ParallelCommandGroup(
                        new RunStraightRopePos(climbArms, 50000),
                        new RunPivotRopePos(climbArms, 0)
                )).andThen(new RunPivotPos(climbArms, -3000))
                , joyBPS));
        joyBX.whenPressed(new RequireButton(
                new ParallelCommandGroup(
                        new RunStraightRopePos(climbArms, 100000),
                        new RunPivotRopePos(climbArms, 0)
                ).andThen(new RunBrake(climbArms, 60, 115)
                ).andThen(new RunPivotPos(climbArms, 5500)
                ).andThen(new RunStraightRopePos(climbArms, 5000)
                ).andThen(new RunShooterPivot(shooter, 8000))
                , joyBPS));
        //-----END CLIMB-----//


        //joyBTL.whileHeld(new RunShooterWheelsAdj(shooter, 512));
        //joyBBL.whileHeld(new RunShooterWheelsAdj(shooter, -512));
        joyBTL.whileHeld(new RunShooterRollers(shooter, 0.75));
        joyBBL.whileHeld(new RunShooterRollers(shooter, -0.75));

        joyBTR.whenPressed(new RunShooterWheelsAdj(shooter, 512, 15360, -15360));
        joyBBR.whenPressed(new RunShooterWheelsAdj(shooter, -512, 15360, -15360));

        joyPOVN.whenPressed(new RunShooterPivot(shooter, -15000));
        joyPOVS.whenPressed(new RunShooterPivot(shooter, -55500)); //-58000

        joyPOVW.whenPressed(new RequireButton(new RunBrake(climbArms, 90, 90).andThen(
            new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 0),
                new RunPivotRopePos(climbArms, 0)
            ).andThen(new RunPivotPos(climbArms, 0))
        ), joyBPS));


        joyBLeftJoystick.whileHeld(new RunLights(limelight, 3, 1));


        sJoyPOVN.whileHeld(new RunLights(limelight, 3, 3).andThen(new RunCenterOnLimelight(drivetrain, limelight)));
        sJoyY.whileHeld(new RunLights(limelight, 3, 3).andThen(new RunTargetShooter(shooter, limelight)));
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
        SmartDashboard.putBoolean("Wheels At Speed", Math.abs(wheelSpeeds[0] - targetWheelSpeed) <= 128 && Math.abs(wheelSpeeds[1] - targetWheelSpeed) <= 128);

        SmartDashboard.putBoolean("Targets?", limelight.hasTarget());
        SmartDashboard.putNumber("Distance?", limelight.getDistance());

        SmartDashboard.putNumber("IdealSpeed", shooter.getIdealSpeed(limelight.getDistance()));
        SmartDashboard.putNumber("IdealAngle", shooter.getIdealAngle(limelight.getDistance()));
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
