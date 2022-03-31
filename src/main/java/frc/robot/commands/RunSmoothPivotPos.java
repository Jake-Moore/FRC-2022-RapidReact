package frc.robot.commands;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbArms;

public class RunSmoothPivotPos extends CommandBase {
    private final ClimbArms climbArms;
    private final double pos;
    private final double maxPower;
    private final double timeMS;

    double initPos;
    double add;
    double target;
    boolean finished;

    private Notifier notifier;
    /**
     * Finishes When Pivot Pos is Within 1,000 ticks
     */
    public RunSmoothPivotPos(ClimbArms climbArms, double pos, double maxPower, double timeMS) {
        this.climbArms = climbArms;
        this.pos = pos;
        this.maxPower = maxPower;
        this.timeMS = timeMS;
        this.finished = false;
    }

    @Override
    public void initialize() {
        finished = false;
        climbArms.setPivotMaxPower(maxPower);
        initPos = climbArms.getPivotPos();

        add = getSign(pos-initPos) * 100;
        //SmartDashboard.putNumber("Add", add);
        target = initPos;
        notifier = new Notifier(() -> {
            target += add;
            if (pos >= 0) {
                if (target > pos) { target = pos; }
            }else {
                if (target < pos) { target = pos; }
            }

            climbArms.setPivotPos(target);
            if (Math.abs(target) == Math.abs(pos)) {
                finished = true;
            }
        });
        notifier.startPeriodic((timeMS/1000D) / ((pos - initPos) / add));
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        //SmartDashboard.putBoolean("Finished", finished);
        //SmartDashboard.putBoolean("Done", done);
        //SmartDashboard.putNumber("Error", Math.abs(climbArms.getPivotPos() - pos));
        return finished && (Math.abs(climbArms.getPivotPos() - pos) <= 1000);
    }

    @Override
    public void end(boolean interrupted) {
        notifier.stop();
    }

    private int getSign(double num) {
        if (num >= 0D) {
            return 1;
        }
        return -1;
    }
}
