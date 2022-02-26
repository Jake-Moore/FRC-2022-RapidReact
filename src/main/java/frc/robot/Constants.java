package frc.robot;

@SuppressWarnings("unused")
public class Constants {
    //Joysticks
    public static final int pJoyID = 0;
    public static final int sJoyID = 1;

    //Motors
    public static final int mLeftStraight = 6;
    public static final int mLeftPivot = 5;
    public static final int mRightPivot = 7;
    public static final int mRightStraight = 8;
    public static final int mPivoter = 9;

    //Servos
    public static final int servoLeft = 0;
    public static final int servoRight = 1;

    public static final int sLeftStart = 85;
    public static final int sRightStart = 97;

    //  PID
    public static final int PID_LOOP_IDX = 0;
    public static final int TIMEOUT_MS = 30;
    //Rope Motors PID
    public static final double ROPE_P = 0.15;
    public static final double ROPE_I = 0.0;
    public static final double ROPE_D = 0.0;
    public static final double ROPE_F = 0.0;
    //Pivot Motor PID
    public static final double PIVOT_P = 0.15;
    public static final double PIVOT_I = 0.0;
    public static final double PIVOT_D = 0.0;
    public static final double PIVOT_F = 0.0;

}
