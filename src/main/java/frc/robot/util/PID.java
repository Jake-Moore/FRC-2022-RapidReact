package frc.robot.util;

public class PID {
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
