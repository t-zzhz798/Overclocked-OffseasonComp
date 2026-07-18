package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {
    public Intake_System Intake;
    public Mecanum_Drive MecanumDrive;

    public Robot(HardwareMap hardwareMap) {
        this.Intake = new Intake_System(hardwareMap);
        this.MecanumDrive = new Mecanum_Drive(hardwareMap);
    }

    public void update(Telemetry telemetry, Gamepad gamepad) {
        this.Intake.update(telemetry, gamepad);
        this.MecanumDrive.update(telemetry, gamepad);
        telemetry.update();
    }
    
    public void stop() {
        Intake.stop();
        MecanumDrive.stop();
    }
}
