package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Mecanum_Drive;

@TeleOp(name = "MainTeleOp", group = "Linear OpMode")
public class Basic_Linear_OpMode extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        // Initialize the subsystems.
        Mecanum_Drive drive = new Mecanum_Drive(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the driver to press START.
        waitForStart();
        runtime.reset();

        // Main loop — runs until the driver presses STOP.
        while (opModeIsActive()) {

            // --- Drive control ---
            // Pushing the left stick forward produces a negative Y value, so negate it.
            double axial   = -gamepad1.left_stick_y;
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            drive.drive(axial, lateral, yaw);

            // --- Intake and Shooter control ---
            // Use gamepad buttons to control the intake subsystem.
            intake.setIntakePower(gamepad1.a ? 1.0 : 0.0);
            intake.setShooterPower(gamepad1.x ? 1.0 : 0.0);

            // --- Telemetry ---
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.addData("Drive Motors", "FL:%.2f FR:%.2f BL:%.2f BR:%.2f",
                    drive.getFrontLeftPower(), drive.getFrontRightPower(),
                    drive.getBackLeftPower(), drive.getBackRightPower());
            telemetry.addData("Intake/Shooter", "Intake:%.2f Shooter:%.2f",
                    intake.getIntakePower(), intake.getShooterPower());
            telemetry.update();
        }

        // Ensure motors are stopped when the OpMode ends.
        drive.stop();
        intake.stop();
    }
}
