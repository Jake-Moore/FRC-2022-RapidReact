package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbArms extends SubsystemBase {
    public final TalonFX mLeftStraight;
    public final TalonFX mLeftPivot;
    public final TalonFX mRightStraight;
    public final TalonFX mRightPivot;
    public final TalonFX mPivoter;

    //Create the motors, invert a couple, help hold them in place, and zero encoders
    public ClimbArms() {
        //Vertical Motors
        mLeftStraight  = setupClimbFalcon(Constants.mLeftStraight,  false);
        mLeftPivot     = setupClimbFalcon(Constants.mLeftPivot,     true );
        mRightStraight = setupClimbFalcon(Constants.mRightStraight, true );
        mRightPivot    = setupClimbFalcon(Constants.mRightPivot,    false);

        //Pivot Motor
        mPivoter       = setupClimbFalcon(Constants.mPivoter,       false);
    }

    //Simplifies code by calling common neutralMode and sensor methods in fewer lines
    private TalonFX setupClimbFalcon(int id, boolean invert) {
        TalonFX talon = new TalonFX(id);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.setSelectedSensorPosition(0);
        talon.setInverted(invert);
        return talon;
    }

    public void setStraightArms(double power) {
        mLeftStraight.set(TalonFXControlMode.PercentOutput, power);
        mRightStraight.set(TalonFXControlMode.PercentOutput, power);
    }

    public void setPivotArms(double power) {
        mLeftPivot.set(TalonFXControlMode.PercentOutput, power);
        mRightPivot.set(TalonFXControlMode.PercentOutput, power);
    }

    public void setPivotPivot(double power) {
        mPivoter.set(TalonFXControlMode.PercentOutput, power);
    }
}