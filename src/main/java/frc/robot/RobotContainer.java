package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
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

    private final JoystickButton joyOptions    = new JoystickButton(pJoy, 10);//Options Button

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
        //joyBBR.whileHeld(new RunStraightArms(climbArms, .2));
        //joyBTR.whileHeld(new RunStraightArms(climbArms, -.2));

        //joyBBL.whileHeld(new ParallelDeadlineGroup(
        //        new RunPivotArms(climbArms, .5)
        //));
        //joyBTL.whileHeld(new ParallelDeadlineGroup(
        //        new RunPivotArms(climbArms, -.5)
        //));

        //joyBSquare.whileHeld(new RunArmPivot(climbArms, .2));
        //joyBCircle.whileHeld(new RunArmPivot(climbArms, -.2));

        //Straight arms controls
        joyBTriangle.whileHeld(new RunStraightRopePos(climbArms, 200000));
        joyBX.whileHeld(new RunStraightRopePos(climbArms, 0));
        joyBBR.whileHeld(new RunStraightRopePosAdj(climbArms, 1000));
        joyBTR.whileHeld(new RunStraightRopePosAdj(climbArms, -1000));

        //Pivot Arms Controls
        joyPOVN.whileHeld(new RunPivotRopePos(climbArms, 224000));
        joyPOVS.whileHeld(new RunPivotRopePos(climbArms, 0));
        joyBBL.whileHeld(new RunPivotRopePosAdj(climbArms, 1000));
        joyBTL.whileHeld(new RunPivotRopePosAdj(climbArms, -1000));

        //mPivoter controls
        joyBCircle.whileHeld(new RunPivotPos(climbArms, 13000));
        joyBSquare.whileHeld(new RunPivotPos(climbArms, -24500));
        joyOptions.whileHeld(new RunPivotPos(climbArms, 0));
        joyPOVE.whileHeld(new RunPivotPosAdj(climbArms, 1000));
        joyPOVW.whileHeld(new RunPivotPosAdj(climbArms, -1000));

        //joyBBR.whileHeld(new ParallelDeadlineGroup(
        //        new RunStraightArms(climbArms, .5)
        //));
        //joyBTR.whileHeld(new ParallelDeadlineGroup(
        //        new RunStraightArms(climbArms, -.5)
        //));
    }

    public void updateSmartDashboard() {
        SmartDashboard.putNumber("LeftStraightPos",  climbArms.mLeftStraight.getSelectedSensorPosition());
        SmartDashboard.putNumber("LeftPivotPos",     climbArms.mLeftPivot.getSelectedSensorPosition());
        SmartDashboard.putNumber("RightStraightPos", climbArms.mRightStraight.getSelectedSensorPosition());
        SmartDashboard.putNumber("RightPivotPos",    climbArms.mRightPivot.getSelectedSensorPosition());

        SmartDashboard.putNumber("PivoterPos",       climbArms.mPivoter.getSelectedSensorPosition());
    }

    public void whenDisabled() {}
}
