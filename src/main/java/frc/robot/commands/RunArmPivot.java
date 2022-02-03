package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunArmPivot extends CommandBase {
    private final ClimbArms kClimbArms;
    private final double power;
    public RunArmPivot(ClimbArms kClimbArms, double power) {
        this.kClimbArms = kClimbArms;
        this.power = power;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        kClimbArms.setPivotPivot(power);
    }

    @Override
    public void end(boolean interrupted) {
        kClimbArms.setPivotPivot(0);
    }
}
