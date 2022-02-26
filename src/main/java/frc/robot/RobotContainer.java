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

@SuppressWarnings("unused")
public class RobotContainer {
    //Subsystems
    private final ClimbArms climbArms = new ClimbArms();
    private final Drivetrain drivetrain = new Drivetrain();

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

        joyBTL.whileHeld(new RunStraightRopePosAdj(climbArms, 10000));
        joyBBL.whileHeld(new RunPivotPos(climbArms, 7000));
        joyBShare.whileHeld(new RunBrake(climbArms, Constants.sLeftStart, Constants.sRightStart).andThen(new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 0),
                new RunPivotRopePos(climbArms, 240000)
        )));
        joyBBig.whileHeld(new RunPivotPosAdj(climbArms, -1000));

        joyBOptions.whenPressed(
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
        );
        joyBBR.whenPressed(new RunBrake(climbArms, Constants.sLeftStart, Constants.sRightStart).andThen(
            new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 0),
                new RunPivotRopePos(climbArms, 120000)
            ).andThen(new RunPivotPos(climbArms, 3500)
            ).andThen(new RunPivotRopePos(climbArms, 240000)
            ).andThen(new RunPivotPos(climbArms, 7000))
        ));
        joyBTR.whenPressed(new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 100000),
                new RunBrake(climbArms, 115, 60),
                new RunPivotRopePos(climbArms, 0),
                new RunPivotPos(climbArms, 7000)
            ).andThen(new RunPivotPos(climbArms, -13000/3D)
            ).andThen(new RunStraightRopePos(climbArms, 6000))
        );

        joyBPS.whileHeld(new ParallelCommandGroup(
                new RunStraightRopePos(climbArms, 0),
                new RunPivotRopePos(climbArms, 0),
                new RunPivotPos(climbArms, 0)
        ));
    }

    public void updateSmartDashboard() {
        SmartDashboard.putNumber("LeftStraightPos",  climbArms.mLeftStraight.getSelectedSensorPosition());
        SmartDashboard.putNumber("LeftPivotPos",     climbArms.mLeftPivot.getSelectedSensorPosition());
        SmartDashboard.putNumber("RightStraightPos", climbArms.mRightStraight.getSelectedSensorPosition());
        SmartDashboard.putNumber("RightPivotPos",    climbArms.mRightPivot.getSelectedSensorPosition());

        SmartDashboard.putNumber("PivoterPos",       climbArms.mPivoter.getSelectedSensorPosition());
    }

    public double powAxis(double a, double b) {
        if (a >= 0) {
            return Math.pow(a, b);
        }else {
            return -Math.pow(-a, b);
        }
    }

    public void whenDisabled() {}
}
