package frc.robot;

@SuppressWarnings("unused")
public class Constants {
    //Joysticks
    public static final int pJoyID = 0;
    public static final int sJoyID = 1;

    //Motors
    public static final int rightDriveAID = 1;
    public static final int rightDriveBID = 2;
    public static final int leftDriveAID = 3;
    public static final int leftDriveBID = 4;

    public static final int shooterPivotID = 10;
    public static final int wheelsAID = 11;
    public static final int wheelsBID = 12;
    public static final int rollerAID = 13;
    public static final int rollerBID = 14;


    public static final int climbLeftStraightID = 8;
    public static final int climbLeftPivotID = 7;
    public static final int climbRightPivotID = 5;
    public static final int climbRightStraightID = 6;
    public static final int climbPivotID = 9;

    //Servos
    public static final int servoLeft = 0;
    public static final int servoRight = 1;

    public static final int sLeftStart = 85;
    public static final int sRightStart = 97;

    //  PID
    public static final int PID_LOOP_IDX = 0;
    public static final int TIMEOUT_MS = 30;
    //Rope Motors PID
    public static final double ROPE_P = 0.3;
    public static final double ROPE_I = 0.0;
    public static final double ROPE_D = 0.0;
    public static final double ROPE_F = 0.0;
    //Climb Pivot Motor PID
    public static final double CLIMB_PIVOT_P = 0.3;
    public static final double CLIMB_PIVOT_I = 0.0;
    public static final double CLIMB_PIVOT_D = 0.0;
    public static final double CLIMB_PIVOT_F = 0.0;
    //Shooter Pivot Motor PID
    public static final double SHOOTER_PIVOT_P = 0.6;
    public static final double SHOOTER_PIVOT_I = 0.0;
    public static final double SHOOTER_PIVOT_D = 0.0;
    public static final double SHOOTER_PIVOT_F = 0.0;
    //Shooter Velocity Based Motors PID
    public static final double SHOOTER_VEL_P = 0.45; //0.4;
    public static final double SHOOTER_VEL_I = 0.0;
    public static final double SHOOTER_VEL_D = 0.05; //0.05;
    public static final double SHOOTER_VEL_F = 0.05; //0.05;
    //Drive PID (Position???)
    public static final double driveP = 0.5;
    public static final double driveI = 0.0;
    public static final double driveD = 0.0;
    public static final double driveF = 0.0;
    public static final double driveMaxPowerAuto = 0.225;
    public static final double driveMaxPowerTeleop = 1;

    public static final double yawOffset = 1.75; //OOF (Positive will shoot more right, and vice versa)
    public static final double leftPivotRopeMotorScalar = 1D;
    public static final double leftStraightRopeMotorScalar = 0.966D; //1.025, 0.966

    public static final double allowedYawError = 1.0;
}
