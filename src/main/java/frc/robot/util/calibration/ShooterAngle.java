package frc.robot.util.calibration;

public class ShooterAngle {
    public static final double A = -748.445;
    public static final double B = 0.0756292;
    public static final double C = 2306.27;
    public static final double D = 0.025386;
    public static final double E = -1.0691*Math.pow(10, -11);
    public static final double F = 6.21989;
    public static final double G = -24.136;
    public static final double H = -6473.55;

    public static double get(double distance) {
        return A*Math.cos(B*distance) + C*Math.sin(D*distance) + E*Math.pow(distance, F) + G*distance + H;
    }
}
