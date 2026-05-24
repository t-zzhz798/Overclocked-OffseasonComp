package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Mecanum_Drive;

@TeleOp(name = "Basic_Opmode", group = "Linear OpMode")
public class Basic_Linear_OpMode extends LinearOpMode {

    private Mecanum_Drive drive;

    @Override
    public void runOpMode() {

        drive = new Mecanum_Drive(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            double axial = -gamepad1.left_stick_y;
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;

            drive.drive(axial, lateral, yaw);

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}