package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@SuppressWarnings("unused")
public class Drivetrain extends SubsystemBase {
    private final TalonFX mLeftA = new TalonFX(Constants.leftDriveAID);
    private final TalonFX mLeftB = new TalonFX(Constants.leftDriveBID);
    private final TalonFX mRightA = new TalonFX(Constants.rightDriveAID);
    private final TalonFX mRightB = new TalonFX(Constants.rightDriveBID);

    private final AHRS gyro = new AHRS(I2C.Port.kOnboard);

    private double lastPos, currentPos, dPos, x, y, theta;

    public Drivetrain() {
        /* Drive Train PID
        int driveID = 0;
        mLeftA.getPIDController().setP(Constants.driveP, driveID);
        mLeftA.getPIDController().setI(Constants.driveI, driveID);
        mLeftA.getPIDController().setD(Constants.driveD, driveID);
        mLeftA.getPIDController().setFF(Constants.driveFF, driveID);

        mRightA.getPIDController().setP(Constants.driveP, driveID);
        mRightA.getPIDController().setI(Constants.driveI, driveID);
        mRightA.getPIDController().setD(Constants.driveD, driveID);
        mRightA.getPIDController().setFF(Constants.driveFF, driveID);
        */

        mLeftA.setNeutralMode(NeutralMode.Coast);
        mLeftB.setNeutralMode(NeutralMode.Coast);
        mRightA.setNeutralMode(NeutralMode.Coast);
        mRightB.setNeutralMode(NeutralMode.Coast);

        mLeftA.setInverted(true);
        mLeftB.setInverted(true);
        mRightA.setInverted(false);
        mRightB.setInverted(false);

        mLeftB.follow(mLeftA);
        mRightB.follow(mRightA);

        zeroGyro();
    }

    //Drive Modes
    public void setPercentOutput(double zoom, double nyoom) {
        mLeftA.set(ControlMode.PercentOutput, nyoom - zoom);
        mRightA.set(ControlMode.PercentOutput, -nyoom - zoom);
    }

    public double[] getDrivePos() {
        return new double[]{ mLeftA.getSelectedSensorPosition(), mRightA.getSelectedSensorPosition() };
    }

    public double getAveragePos() {
        return ( mLeftA.getSelectedSensorPosition() + mRightA.getSelectedSensorPosition() ) / 2D;
    }

    public double[] getDriveVel() {
        return new double[]{ mLeftA.getSelectedSensorVelocity(), mRightA.getSelectedSensorVelocity() };
    }

    public double getPos() {
        return (mLeftA.getSelectedSensorPosition() + mRightA.getSelectedSensorVelocity()) / 2D;
    }

    //Gyroscope
    public double getGyroRot() {
        return -gyro.getAngle();
    }

    public void zeroGyro() {
        gyro.reset();
    }
}
