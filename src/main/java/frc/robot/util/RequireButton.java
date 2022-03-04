package frc.robot.util;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RequireButton extends CommandBase {
    private final CommandBase command;
    private final JoystickButton button;
    public RequireButton(CommandBase command, JoystickButton button) {
        this.command = command;
        this.button = button;
    }

    @Override
    public void initialize() {
        if (button.get()) command.initialize();
    }

    @Override
    public void execute() {
        if (button.get()) command.execute();
    }

    @Override
    public void end(boolean interrupted) {
        if (button.get()) command.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        if (!button.get()) { return true; }
        return command.isFinished();
    }
}
