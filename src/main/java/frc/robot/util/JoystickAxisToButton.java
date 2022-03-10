package frc.robot.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JoystickAxisToButton {
    private final Joystick joystick;
    private final int axis;
    private final Notifier loop;
    private boolean wasHeld = false;
    public JoystickAxisToButton(Joystick joystick, int axis) {
        this.joystick = joystick;
        this.axis = axis;

        loop = new Notifier(() -> {
            boolean pressed = joystick.getRawAxis(axis) >= 0.1;

            if (!wasHeld && pressed) { //If newly pressed
                runHeldCommands();
                runPressedCommands();
            }else if (wasHeld && pressed) { //If continued press
                runHeldCommands();
            }else if (wasHeld) { //If was pressed but no longer (released)
                runReleasedCommands();
            }

            wasHeld = pressed;
        });
        loop.startPeriodic(0.01);
    }

    List<Command> heldCommands = new ArrayList<>();
    public void whileHeld(Command command) {
        heldCommands.add(command);
    }
    private void runHeldCommands() {
        for (Command held : heldCommands) {
            held.schedule(true);
        }
    }

    List<Command> pressedCommands = new ArrayList<>();
    public void whenPressed(Command command) {
        pressedCommands.add(command);
    }
    private void runPressedCommands() {
        for (Command pressed : pressedCommands) {
            pressed.schedule();
        }
    }

    List<Command> releasedCommands = new ArrayList<>();
    public void whenReleased(Command command) {
        releasedCommands.add(command);
    }
    public void runReleasedCommands() {
        for (Command released : releasedCommands) {
            released.schedule();
        }
    }

    public boolean get() {
        return joystick.getRawAxis(axis) >= 0.1;
    }
}