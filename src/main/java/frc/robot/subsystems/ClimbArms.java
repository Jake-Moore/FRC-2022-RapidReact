package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.PID;

@SuppressWarnings("ununsed")
public class ClimbArms extends SubsystemBase {
    //Rope Motors
    public final WPI_TalonFX mLeftStraight;
    public final WPI_TalonFX mLeftPivot;
    public final WPI_TalonFX mRightStraight;
    public final WPI_TalonFX mRightPivot;
    //Pivot Motor
    public final WPI_TalonFX mPivoter;
    //Pivot Encoder
    public final AnalogInput pivotEncoder = new AnalogInput(0);

    //Brake Servos
    public final Servo brakeL;
    public final Servo brakeR;

    //PID Sets
    public final PID ropePID = new PID(Constants.ROPE_P, Constants.ROPE_I, Constants.ROPE_D, Constants.ROPE_F, 64, 0.5);
    public final PID pivotPID = new PID(Constants.CLIMB_PIVOT_P, Constants.CLIMB_PIVOT_I, Constants.CLIMB_PIVOT_D, Constants.CLIMB_PIVOT_F, 128, 0.5);

    //Create the motors, invert a couple, help hold them in place, and zero encoders
    public ClimbArms() {
        //Rope Motors
        mLeftStraight  = setupClimbFalcon(Constants.climbLeftStraightID,  false, ropePID);
        mLeftPivot     = setupClimbFalcon(Constants.climbLeftPivotID,     true, ropePID);
        mRightStraight = setupClimbFalcon(Constants.climbRightStraightID, true , ropePID);
        mRightPivot    = setupClimbFalcon(Constants.climbRightPivotID,    false , ropePID);

        //Pivot Motor
        mPivoter       = setupClimbFalcon(Constants.climbPivotID,       false, pivotPID);
        //Pivot Encoder
        pivotEncoder.setAverageBits(10); //Not sure if this is critical, but it works with it :)

        //Brakes
        brakeL = new Servo(Constants.servoLeft);
        brakeR = new Servo(Constants.servoRight);
        brakeL.setAngle(Constants.sLeftStart); //115
        brakeR.setAngle(Constants.sRightStart);  //60

        /* TalonFX Position Closed Loop Example

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
    private WPI_TalonFX setupClimbFalcon(int id, boolean invert, PID pid) {
        WPI_TalonFX talon = new WPI_TalonFX(id);
        //Configure PID
        talon.configFactoryDefault();
        talon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.PID_LOOP_IDX, Constants.TIMEOUT_MS);

        talon.configNominalOutputForward(0, Constants.TIMEOUT_MS);
        talon.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
        talon.configPeakOutputForward(pid.maxPower, Constants.TIMEOUT_MS);
        talon.configPeakOutputReverse(-pid.maxPower, Constants.TIMEOUT_MS);
        talon.configAllowableClosedloopError(Constants.PID_LOOP_IDX, pid.allowedError, Constants.TIMEOUT_MS);

        talon.config_kP(Constants.PID_LOOP_IDX, pid.kP, Constants.TIMEOUT_MS);
        talon.config_kI(Constants.PID_LOOP_IDX, pid.kI, Constants.TIMEOUT_MS);
        talon.config_kD(Constants.PID_LOOP_IDX, pid.kD, Constants.TIMEOUT_MS);
        talon.config_kF(Constants.PID_LOOP_IDX, pid.kF, Constants.TIMEOUT_MS);
        //Configure Motor
        talon.setNeutralMode(NeutralMode.Coast);
        talon.setSelectedSensorPosition(0);
        talon.setInverted(invert);
        return talon;
    }

    public void setStraightArmsPos(double pos) {
        mLeftStraight.set(TalonFXControlMode.Position, pos);
        mRightStraight.set(TalonFXControlMode.Position, pos);
    }
    public double getStraightArmsPos() {
        return Math.min(mLeftStraight.getSelectedSensorPosition(), mRightStraight.getSelectedSensorPosition());
    }

    public void setPivotArmsPos(double pos) {
        mLeftPivot.set(TalonFXControlMode.Position, pos);
        mRightPivot.set(TalonFXControlMode.Position, pos);
    }
    //Returns the internal falcon position for the pivoter
    public double getPivotArmsPos() {
        return Math.min(mLeftPivot.getSelectedSensorPosition(), mRightPivot.getSelectedSensorPosition());
    }
    //Returns the angle (to one decimal) of the pivot arm
    public double getPivotAbsolutePos() {
        return ((int) Math.round(pivotEncoder.getAverageValue() * 360D/269.4))/10D;
    }


    public void setPivotPos(double pos) {
        mPivoter.set(TalonFXControlMode.Position, pos);
    }
    public double getPivotPos() {
        return mPivoter.getSelectedSensorPosition();
    }

    public void setBrake(double angleL, double angleR) {
        brakeL.setAngle(angleL);
        brakeR.setAngle(angleR);
    }
}