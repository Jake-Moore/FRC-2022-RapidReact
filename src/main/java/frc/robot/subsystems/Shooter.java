package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.PID;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("unused")
public class Shooter extends SubsystemBase {
    //Motors
    private final WPI_TalonFX mRollerT; //Top Roller
    private final WPI_TalonFX mRollerB; //Bottom Roller
    private final WPI_TalonFX mWheelsL; //Left Wheel
    private final WPI_TalonFX mWheelsR; //Right Wheel
    public final WPI_TalonFX mPivoter;

    //Pivot Encoder
    public final AnalogInput pivotEncoder = new AnalogInput(1);

    //PIDs
    private final PID pivotPID = new PID(Constants.SHOOTER_PIVOT_P, Constants.SHOOTER_PIVOT_I, Constants.SHOOTER_PIVOT_D, Constants.SHOOTER_PIVOT_F, 32, 0.2);
    private final PID wheelPID = new PID(Constants.SHOOTER_VEL_P, Constants.SHOOTER_VEL_I, Constants.SHOOTER_VEL_D, Constants.SHOOTER_VEL_F, 0, 1.0);
    private final PID rollerPID = new PID(0, 0, 0 , 0, 0, 1);

    public Shooter() {
        mRollerT = setupShooterFalcon(Constants.rollerAID, false, rollerPID);
        mRollerB = setupShooterFalcon(Constants.rollerBID, true, rollerPID);
        mRollerB.follow(mRollerT);
        mWheelsL = setupShooterFalcon(Constants.wheelsAID, false, wheelPID);
        mWheelsR = setupShooterFalcon(Constants.wheelsBID, true, wheelPID);
        mPivoter = setupShooterFalcon(Constants.shooterPivotID, false, pivotPID);

        //Pivot Encoder
        pivotEncoder.setAverageBits(10); //Not sure if this is critical, but it works with it :)

        calibrate();
    }

    public void calibrate() {
        //Calibrate the falcon using the abs encoder. Delayed 1 second to allow it to find an average
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mPivoter.setSelectedSensorPosition((230.5 - getPivotAbsolutePos()) * (72706/142D));
            }
        }, 1000);
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
    private double targetWheelSpeed = 0;
    public void setWheelSpeed(double vel) {
        if (vel == 0D) {
            mWheelsL.config_kP(Constants.PID_LOOP_IDX, 0);
            mWheelsR.config_kP(Constants.PID_LOOP_IDX, 0);
            mWheelsL.config_kI(Constants.PID_LOOP_IDX, 0);
            mWheelsR.config_kI(Constants.PID_LOOP_IDX, 0);
            mWheelsL.config_kD(Constants.PID_LOOP_IDX, 0);
            mWheelsR.config_kD(Constants.PID_LOOP_IDX, 0);
            mWheelsL.config_kF(Constants.PID_LOOP_IDX, 0);
            mWheelsR.config_kF(Constants.PID_LOOP_IDX, 0);
        }else {
            mWheelsL.config_kP(Constants.PID_LOOP_IDX, wheelPID.kP);
            mWheelsR.config_kP(Constants.PID_LOOP_IDX, wheelPID.kP);
            mWheelsL.config_kI(Constants.PID_LOOP_IDX, wheelPID.kI);
            mWheelsR.config_kI(Constants.PID_LOOP_IDX, wheelPID.kI);
            mWheelsL.config_kD(Constants.PID_LOOP_IDX, wheelPID.kD);
            mWheelsR.config_kD(Constants.PID_LOOP_IDX, wheelPID.kD);
            mWheelsL.config_kF(Constants.PID_LOOP_IDX, wheelPID.kF);
            mWheelsR.config_kF(Constants.PID_LOOP_IDX, wheelPID.kF);
        }
        targetWheelSpeed = vel;
        mWheelsL.set(TalonFXControlMode.Velocity, vel);
        mWheelsR.set(TalonFXControlMode.Velocity, vel);
    }
    public double getTargetWheelSpeed() {
        return targetWheelSpeed;
    }
    public double[] getWheelSpeeds() {
        return new double[]{mWheelsL.getSelectedSensorVelocity(), mWheelsR.getSelectedSensorVelocity()};
    }

    public void setRollerPower(double power) {
        mRollerT.set(TalonFXControlMode.PercentOutput, power);
    }

    private double pivot = 0;
    public void setPivot(double pos) {
        mPivoter.set(TalonFXControlMode.Position, pos);
        pivot = pos;
    }
    public double getPivotTargetPos() {
        return pivot;
    }

    //Returns the angle (to one decimal) of the shooter arm
    public double getPivotAbsolutePos() {
        return ((int) Math.round(pivotEncoder.getAverageValue() * 360D/269.4))/10D;
    }

    // less than 45 limelight can't find target
    public double getIdealSpeed(double distance) {
        if (distance <= 45 || distance >= 250) { return -1; }
        return (1 + Math.pow(distance/200, 2)*0.11)*(-Math.pow(1.00941, distance+640) - 5793.4);
    }

    public double getIdealAngle(double distance) {
        if (distance <= 45 || distance >= 250) { return 401; }
        return -11533.2 * Math.log10(distance - 38) + 8772.51;
    }

    public void targetShooter(double angle, double speed) {
        setWheelSpeed(speed);
        setPivot(angle);
    }

    public void targetShooter(double speed) {
        setWheelSpeed(speed);
    }
}
