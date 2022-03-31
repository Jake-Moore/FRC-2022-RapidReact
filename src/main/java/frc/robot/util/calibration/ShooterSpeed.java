package frc.robot.util.calibration;

public class ShooterSpeed {
    public static final double A = -20.6377;
    public static final double B = 2.48403;
    public static final double C = 20.658;
    public static final double D = 2.48384;
    public static final double E = -12.3976;
    public static final double F = -5951.08;

    public static double get(double distance) {
        return A*Math.pow(distance, B) + C*Math.pow(distance, D) + E*distance + F;
    }
}
