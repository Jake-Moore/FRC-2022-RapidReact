package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.PID;

@SuppressWarnings("unused")
public class Shooter extends SubsystemBase {
    //Motors
    private final WPI_TalonFX mRollerA;
    private final WPI_TalonFX mRollerB;
    private final WPI_TalonFX mWheelsA;
    private final WPI_TalonFX mWheelsB;
    public final WPI_TalonFX mPivoter;

    //Pivot Encoder
    public final AnalogInput pivotEncoder = new AnalogInput(1);

    //PIDs
    private final PID pivotPID = new PID(Constants.SHOOTER_PIVOT_P, Constants.SHOOTER_PIVOT_I, Constants.SHOOTER_PIVOT_D, Constants.SHOOTER_PIVOT_F, 128, 0.2);
    private final PID velocityPID = new PID(Constants.SHOOTER_VEL_P, Constants.SHOOTER_VEL_I, Constants.SHOOTER_VEL_D, Constants.SHOOTER_VEL_F, 0, 1);

    public Shooter() {
        mRollerA = setupShooterFalcon(Constants.rollerAID, false, velocityPID);
        mRollerB = setupShooterFalcon(Constants.rollerBID, true, velocityPID);
        mRollerB.follow(mRollerA);
        mWheelsA = setupShooterFalcon(Constants.wheelsAID, false, velocityPID);
        mWheelsB = setupShooterFalcon(Constants.wheelsBID, true, velocityPID);
        mWheelsB.follow(mWheelsA);
        mPivoter = setupShooterFalcon(Constants.shooterPivotID, false, pivotPID);

        //Pivot Encoder
        pivotEncoder.setAverageBits(10); //Not sure if this is critical but it works with it :)

        //Calibrate the falcon using the abs encoder?
        mPivoter.setSelectedSensorPosition((229D - getPivotAbsolutePos()) * (60000D/118D));
    }

    private WPI_TalonFX setupShooterFalcon(int id, boolean invert, PID pid) {
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

    //Sets the speed (velocity) of the shooter rollers in ticks per 100ms
    // Ex: vel = 204.8 will be 0.1 revolution per 100ms or 10 revolutions per second
    public void setWheelSpeed(double vel) {
        if (vel == 0D) {
            mWheelsA.config_kP(Constants.PID_LOOP_IDX, 0);
            mWheelsB.config_kP(Constants.PID_LOOP_IDX, 0);
        }else {
            mWheelsA.config_kP(Constants.PID_LOOP_IDX, velocityPID.kP);
            mWheelsB.config_kP(Constants.PID_LOOP_IDX, velocityPID.kP);
        }
        mWheelsA.set(TalonFXControlMode.Velocity, vel);
    }

    //Sets the speed (velocity) of the shooter rollers in ticks per 100ms
    // Ex: vel = 204.8 will be 0.1 revolution per 100ms or 10 revolutions per second
    public void setRollerSpeed(double vel) {
        if (vel == 0D) {
            mRollerA.config_kP(Constants.PID_LOOP_IDX, 0);
            mRollerB.config_kP(Constants.PID_LOOP_IDX, 0);
        }else {
            mRollerA.config_kP(Constants.PID_LOOP_IDX, velocityPID.kP);
            mRollerB.config_kP(Constants.PID_LOOP_IDX, velocityPID.kP);
        }
        mRollerA.set(TalonFXControlMode.Velocity, vel);
    }

    public void setPivot(double pos) {
        mPivoter.set(TalonFXControlMode.Position, pos);
    }

    //Returns the angle (to one decimal) of the shooter arm
    public double getPivotAbsolutePos() {
        return ((int) Math.round(pivotEncoder.getAverageValue() * 360D/269.4))/10D;
    }
}
