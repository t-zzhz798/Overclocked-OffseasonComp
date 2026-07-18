package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Mecanum_Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake_System;

@Autonomous(name="Basic_Auto", group="robot")
public class Basic_Auto extends LinearOpMode {

    @Override
    public void runOpMode() {
        Mecanum_Drive drive = new Mecanum_Drive(hardwareMap);
        Intake_System intake = new Intake_System(hardwareMap);

        waitForStart();

        if (opModeIsActive()) {
            // Move forward 3 inches
            while (opModeIsActive() && drive.moveForwardDistance(3, 0.8, 14)) {
                telemetry.addData("Path", "Step 1: Forward");
                telemetry.update();
            }
            
            // Move horizontally 3 inches
            while (opModeIsActive() && drive.moveHorizontalDistance(3, 0.8, 14)) {
                telemetry.addData("Path", "Step 2: Strafe");
                telemetry.update();
            }
            
            // Intake the balls
            intake.autoIntake();
            
            // Move forward another 3 inches while intaking
            while (opModeIsActive() && drive.moveForwardDistance(3, 0.4, 14)) {
                telemetry.addData("Path", "Step 3: Forward + Intake");
                telemetry.update();
            }
            
            // Stop intaking
            intake.stop();
            
            // Shoot the balls (this has an internal 3s delay for shooter ramp up)
            intake.autoShoot(0.8);
            
            // Wait for shot to clear
            sleep(2000);
            intake.stop();
            drive.stop();
        }
    }
}
