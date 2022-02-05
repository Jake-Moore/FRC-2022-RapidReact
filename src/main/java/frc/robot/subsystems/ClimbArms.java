package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@SuppressWarnings("ununsed")
public class ClimbArms extends SubsystemBase {
    //Rope Motors
    public final WPI_TalonFX mLeftStraight;
    public final WPI_TalonFX mLeftPivot;
    public final WPI_TalonFX mRightStraight;
    public final WPI_TalonFX mRightPivot;
    //Brake Solenoids
    //public final Solenoid sLeftStraight;
    //public final Solenoid sLeftPivot;
    //public final Solenoid sRightStraight;
    //public final Solenoid sRightPivot;
    //public final Servo sRightStraight;
    //Pivot Motor
    public final WPI_TalonFX mPivoter;

    //Create the motors, invert a couple, help hold them in place, and zero encoders
    public ClimbArms() {
        //Rope Motors
        mLeftStraight  = setupClimbFalcon(Constants.mLeftStraight,  false);
        mLeftPivot     = setupClimbFalcon(Constants.mLeftPivot,     false );
        mRightStraight = setupClimbFalcon(Constants.mRightStraight, true );
        mRightPivot    = setupClimbFalcon(Constants.mRightPivot,    true);

        //Brake Solenoids
        //sLeftStraight = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
        //sLeftPivot = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
        //sRightStraight = new Solenoid(PneumaticsModuleType.CTREPCM, 2);
        //sRightPivot = new Solenoid(PneumaticsModuleType.CTREPCM, 3);
        //sRightStraight = new Servo(0);

        //Pivot Motor
        mPivoter       = setupClimbFalcon(Constants.mPivoter,       false);

        /* TalonFX Position Closed Loop Example

        mPivoter.configFactoryDefault();
        mPivoter.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.PID_LOOP_IDX, Constants.TIMEOUT_MS);

        mPivoter.configNominalOutputForward(0, Constants.TIMEOUT_MS);
        mPivoter.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
        mPivoter.configPeakOutputForward(1, Constants.TIMEOUT_MS);
        mPivoter.configPeakOutputReverse(-1, Constants.TIMEOUT_MS);
        mPivoter.configAllowableClosedloopError(Constants.PID_LOOP_IDX, 0, Constants.TIMEOUT_MS);

        mPivoter.config_kP(Constants.PID_LOOP_IDX, Constants.PIVOT_P, Constants.TIMEOUT_MS);
        mPivoter.config_kI(Constants.PID_LOOP_IDX, Constants.PIVOT_I, Constants.TIMEOUT_MS);
        mPivoter.config_kD(Constants.PID_LOOP_IDX, Constants.PIVOT_D, Constants.TIMEOUT_MS);
        mPivoter.config_kF(Constants.PID_LOOP_IDX, Constants.PIVOT_F, Constants.TIMEOUT_MS);

        mPivoter.set(TalonFXControlMode.Position, 0);

        -----------------------------------------------------

        double motorOutput = mPivoter.getMotorOutputPercent();
        double targetPositionRotations = <rotations> * 2048; //2048 ticks per rotation
        mPivoter.set(TalonFXControlMode.Position, targetPositionRotations);

        -----------------------------------------------------

          Steps to Tune Position PID
        1. Set kP, kI, and kD to zero.
        2. Increase  until the output starts to oscillate around the setpoint.
        3. Increase  as much as possible without introducing jittering in the system response.

        */
    }

    //Simplifies code by calling common neutralMode and sensor methods in fewer lines
    private WPI_TalonFX setupClimbFalcon(int id, boolean invert) {
        WPI_TalonFX talon = new WPI_TalonFX(id);
        talon.setNeutralMode(NeutralMode.Coast);
        talon.setSelectedSensorPosition(0);
        talon.setInverted(invert);
        return talon;
    }

    public void setStraightArms(double power) {
        mLeftStraight.set(TalonFXControlMode.PercentOutput, power);
        mRightStraight.set(TalonFXControlMode.PercentOutput, power);
    }
    public void setStraightSolenoids(boolean on) {
        //LeftStraight.set(on);
        //sRightStraight.set(on);
        //if (on) {
        //    sRightStraight.setAngle(0);
        //}else {
        //    sRightStraight.setAngle(180);
        //}
    }

    public void setPivotArms(double power) {
        mLeftPivot.set(TalonFXControlMode.PercentOutput, power);
        mRightPivot.set(TalonFXControlMode.PercentOutput, power);
    }
    public void setPivotSolenoids(boolean on) {
        //sLeftPivot.set(on);
        //sRightPivot.set(on);
    }

    public void setPivotPivot(double power) {
        mPivoter.set(TalonFXControlMode.PercentOutput, power);
    }
}