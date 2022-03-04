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
import frc.robot.subsystems.Shooter;
import frc.robot.util.RequireButton;

@SuppressWarnings("unused")
public class RobotContainer {
    //Subsystems
    private final ClimbArms climbArms = new ClimbArms();
    private final Drivetrain drivetrain = new Drivetrain();
    private final Shooter shooter = new Shooter();

    //Joysticks
    private final Joystick pJoy = new Joystick(Constants.pJoyID);

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

    private final JoystickButton joyBPS       = new JoystickButton(pJoy, 13);//PlayStation Button
    private final JoystickButton joyBBig      = new JoystickButton(pJoy, 14);//Big (Center) Button

    private final POVButton joyPOVN = new POVButton(pJoy, 0);   //North
    private final POVButton joyPOVE = new POVButton(pJoy, 90);  //East
    private final POVButton joyPOVS = new POVButton(pJoy, 180); //South
    private final POVButton joyPOVW = new POVButton(pJoy, 270); //West

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
                drivetrain.setPercentOutput(powAxis(pJoy.getRawAxis(1), 7D/3D) * 0.65D, pJoy.getRawAxis(2)/2.25D), drivetrain //Functional, not tuned
        ));

        joyBShare.whileHeld(new RequireButton(new RunStraightRopePosAdj(climbArms, 10000), joyBPS));
        joyBBig.whileHeld(new RequireButton(new RunPivotPos(climbArms, 7000), joyBPS));
        joyBOptions.whileHeld(new RequireButton(new RunBrake(climbArms, Constants.sLeftStart, Constants.sRightStart).andThen(new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 0),
                new RunPivotRopePos(climbArms, 240000)
        )), joyBPS));
        joyBTriangle.whileHeld(new RequireButton(new RunPivotPosAdj(climbArms, -1000), joyBPS));

        joyBCircle.whenPressed(new RequireButton(
            new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 100000),
                new RunPivotRopePos(climbArms, 0),
                new RunPivotPos(climbArms, 7000)
            ).andThen(new ParallelCommandGroup(
                    new RunPivotRopePos(climbArms, 50000),
                    new RunStraightRopePos(climbArms, 0)
            )).andThen(new RunPivotPos(climbArms, -13000/3D)
            ).andThen(new ParallelCommandGroup(
                    //new RunBrake(climbArms, 115, 60),
                    new RunStraightRopePos(climbArms, 100000),
                    new RunPivotRopePos(climbArms, 0)
            )).andThen(new RunPivotPos(climbArms, 3250)
            ).andThen(new RunStraightRopePos(climbArms, 0)
            ).andThen(new RunPivotPos(climbArms, 0))
        , joyBPS));
        joyBX.whenPressed(new RequireButton(new RunBrake(climbArms, Constants.sLeftStart, Constants.sRightStart).andThen(
            new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 0),
                new RunPivotRopePos(climbArms, 120000)
            ).andThen(new RunPivotPos(climbArms, 3500)
            ).andThen(new RunPivotRopePos(climbArms, 240000)
            ).andThen(new RunPivotPos(climbArms, 7000))
        ), joyBPS));
        joyBSquare.whenPressed(new RequireButton(new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 100000),
                new RunBrake(climbArms, 115, 60),
                new RunPivotRopePos(climbArms, 0),
                new RunPivotPos(climbArms, 7000)
            ).andThen(new RunPivotPos(climbArms, -13000/3D)
            ).andThen(new RunStraightRopePos(climbArms, 6000))
        , joyBPS));

        joyBTL.whileHeld(new RunShooterRollers(shooter, 20480));
        joyBBL.whileHeld(new RunShooterRollers(shooter, -20480));

        joyBTR.whileHeld(new RunShooterWheels(shooter, 10240));
        joyBBR.whileHeld(new RunShooterWheels(shooter, -10240));

        joyPOVN.whenPressed(new RunShooterPivot(shooter, -15000));
        joyPOVS.whenPressed(new RunShooterPivot(shooter, -58000));
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
    }

    public void whenDisabled() {}
}
