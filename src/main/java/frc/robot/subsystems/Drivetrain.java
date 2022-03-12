package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@SuppressWarnings("unused")
public class Drivetrain extends SubsystemBase {
    private final TalonFX mLeftA = new TalonFX(Constants.leftDriveAID);
    private final TalonFX mLeftB = new TalonFX(Constants.leftDriveBID);
    private final TalonFX mRightA = new TalonFX(Constants.rightDriveAID);
    private final TalonFX mRightB = new TalonFX(Constants.rightDriveBID);

    private final AHRS gyro = new AHRS(I2C.Port.kOnboard);

    public boolean overrideDrivetrain;
    int driveID = 0;

    public Drivetrain() {
        //Drivetrain PID
        mLeftA.configFactoryDefault();
        mLeftB.configFactoryDefault();
        mRightA.configFactoryDefault();
        mRightB.configFactoryDefault();

        setOverrideDrivetrain(false);
        mLeftA.setSelectedSensorPosition(0);
        mRightA.setSelectedSensorPosition(0);

        mLeftA.setNeutralMode(NeutralMode.Coast);
        mLeftB.setNeutralMode(NeutralMode.Coast);
        mLeftB.setSelectedSensorPosition(0);
        mRightA.setNeutralMode(NeutralMode.Coast);
        mRightB.setNeutralMode(NeutralMode.Coast);
        mRightB.setSelectedSensorPosition(0);

        mLeftA.setInverted(true);
        mLeftB.setInverted(true);
        mRightA.setInverted(false);
        mRightB.setInverted(false);

        mLeftB.follow(mLeftA);
        mRightB.follow(mRightA);

        zeroGyro();
    }
    public void setOverrideDrivetrain(boolean overrideDrivetrain) {
        this.overrideDrivetrain = overrideDrivetrain;
        if (overrideDrivetrain) {
            mLeftA.config_kP(driveID, Constants.driveP);
            mLeftA.config_kI(driveID, Constants.driveI);
            mLeftA.config_kD(driveID, Constants.driveD);
            mLeftA.config_kF(driveID, Constants.driveF);
            mRightA.config_kP(driveID, Constants.driveP);
            mRightA.config_kI(driveID, Constants.driveI);
            mRightA.config_kD(driveID, Constants.driveD);
            mRightA.config_kF(driveID, Constants.driveF);
        }else {
            mLeftA.config_kP(driveID, 0);
            mLeftA.config_kI(driveID, 0);
            mLeftA.config_kD(driveID, 0);
            mLeftA.config_kF(driveID, 0);
            mRightA.config_kP(driveID, 0);
            mRightA.config_kI(driveID, 0);
            mRightA.config_kD(driveID, 0);
            mRightA.config_kF(driveID, 0);
        }
    }
    public boolean getOverrideDrivetrain() {
        return overrideDrivetrain;
    }

    public void setRotation(double setRotation) {
        //this.setRotation = setRotation;
        double delta = getDeltaTargetPos(setRotation);
        mLeftA.set(ControlMode.Position, mLeftA.getSelectedSensorPosition()-delta);
        mRightA.set(ControlMode.Position, mRightA.getSelectedSensorPosition()+delta);
    }

    //Drive Modes
    public void driveFromJoysticks(double zoom, double nyoom) {
        if (overrideDrivetrain) { return; }
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


    public double getDeltaTargetPos(double targetAngle) {
        double currAngle = getGyroRot();
        double deltaAngle = currAngle - targetAngle; //Positive = Clockwise Rotation

        double wheelCirc = 6 * Math.PI; //~18.85
        double wheelSeparation = 24.5;
        double gearRatio = 25D/3D; //~8.33 rotations of the Falcon per wheel rotation

        double sectorLength = (wheelSeparation/2D) * Math.toRadians(deltaAngle); //+Distance to travel
        double rotations = sectorLength/wheelCirc;
        return rotations*gearRatio*2048;
    }

    public void setDrivePositions(double left, double right) {
        mLeftA.set(ControlMode.Position, left);
        mRightA.set(ControlMode.Position, right);
    }

    public void setDriveMaxPower(double driveMaxPower) {
        mLeftA.configPeakOutputForward(driveMaxPower); mLeftA.configPeakOutputReverse(-driveMaxPower);
        mRightA.configPeakOutputForward(driveMaxPower); mRightA.configPeakOutputReverse(-driveMaxPower);
    }

    public void setPower(double power) {
        mLeftA.set(TalonFXControlMode.PercentOutput, power);
        mRightA.set(TalonFXControlMode.PercentOutput, power);
    }
}
