package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.ClimbArms;

@SuppressWarnings("unused")
public class RobotContainer {
    //Subsystems
    private final ClimbArms climbArms = new ClimbArms();

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
        //joyBBR.whileHeld(new RunStraightArms(climbArms, .2));
        //joyBTR.whileHeld(new RunStraightArms(climbArms, -.2));

        joyBBL.whileHeld(new RunPivotArms(climbArms, .2));
        joyBTL.whileHeld(new RunPivotArms(climbArms, -.2));

        joyBSquare.whileHeld(new RunArmPivot(climbArms, .2));
        joyBCircle.whileHeld(new RunArmPivot(climbArms, -.2));

        joyBBR.whileHeld(new ParallelDeadlineGroup(
                new RunStraightSolenoid(climbArms, true),
                new RunTimer(0.5),
                new RunStraightArms(climbArms, .2)
        ));
        joyBTR.whileHeld(new ParallelDeadlineGroup(
                new RunStraightArms(climbArms, 0),
                new RunTimer(0.5),
                new RunStraightArms(climbArms, -.2)
        ));

    }

    public void updateSmartDashboard() {
        //SmartDashboard.putNumber("LeftStraightPos",  climbArms.mLeftStraight.getSelectedSensorPosition());
        //SmartDashboard.putNumber("LeftPivotPos",     climbArms.mLeftPivot.getSelectedSensorPosition());
        SmartDashboard.putNumber("RightStraightPos", climbArms.mRightStraight.getSelectedSensorPosition());
        //SmartDashboard.putNumber("RightPivotPos",    climbArms.mRightPivot.getSelectedSensorPosition());

        SmartDashboard.putNumber("PivoterPos",       climbArms.mPivoter.getSelectedSensorPosition());
    }

    public void whenDisabled() {}
}
