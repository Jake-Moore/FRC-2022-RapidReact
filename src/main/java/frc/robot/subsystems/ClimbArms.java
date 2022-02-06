package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
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

    //PID Sets
    public final PID ropePID = new PID(Constants.ROPE_P, Constants.ROPE_I, Constants.ROPE_D, Constants.ROPE_F, 512, 0.5);
    public final PID pivotPID = new PID(Constants.PIVOT_P, Constants.PIVOT_I, Constants.PIVOT_D, Constants.PIVOT_F, 128, 0.25);


    //Create the motors, invert a couple, help hold them in place, and zero encoders
    public ClimbArms() {
        //Rope Motors
        mLeftStraight  = setupClimbFalcon(Constants.mLeftStraight,  false, ropePID);
        mLeftPivot     = setupClimbFalcon(Constants.mLeftPivot,     false, ropePID);
        mRightStraight = setupClimbFalcon(Constants.mRightStraight, true , ropePID);
        mRightPivot    = setupClimbFalcon(Constants.mRightPivot,    true , ropePID);

        //Brake Solenoids
        //sLeftStraight = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
        //sLeftPivot = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
        //sRightStraight = new Solenoid(PneumaticsModuleType.CTREPCM, 2);
        //sRightPivot = new Solenoid(PneumaticsModuleType.CTREPCM, 3);
        //sRightStraight = new Servo(0);

        //Pivot Motor
        mPivoter       = setupClimbFalcon(Constants.mPivoter,       false, pivotPID);

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

    public void setStraightArms(double power) {
        mLeftStraight.set(TalonFXControlMode.PercentOutput, power);
        mRightStraight.set(TalonFXControlMode.PercentOutput, power);
    }
    public void setStraightArmsPos(double pos) {
        mLeftStraight.set(TalonFXControlMode.Position, pos);
        mRightStraight.set(TalonFXControlMode.Position, pos);
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
    public void setPivotArmsPos(double pos) {
        mLeftPivot.set(TalonFXControlMode.Position, pos);
        mRightPivot.set(TalonFXControlMode.Position, pos);
    }


    public void setPivotPos(double pos) {
        mPivoter.set(TalonFXControlMode.Position, pos);
    }

    public void setPivotSolenoids(boolean on) {
        //sLeftPivot.set(on);
        //sRightPivot.set(on);
    }

    public void setPivotPivot(double power) {
        mPivoter.set(TalonFXControlMode.PercentOutput, power);
    }


    private static class PID {
        public final double kP;
        public final double kI;
        public final double kD;
        public final double kF;
        public final double allowedError;
        public final double maxPower;
        public PID(double kP, double kI, double kD, double kF, double allowedError, double maxPower) {
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
            this.kF = kF;
            this.allowedError = allowedError;
            this.maxPower = maxPower;
        }
    }
}