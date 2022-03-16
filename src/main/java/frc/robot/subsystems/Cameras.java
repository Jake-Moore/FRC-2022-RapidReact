package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@SuppressWarnings("unused")
public class Cameras extends SubsystemBase {

    public void enableCameras() {
        CameraServer.startAutomaticCapture(0);
        //CameraServer.startAutomaticCapture(1);
    }
}