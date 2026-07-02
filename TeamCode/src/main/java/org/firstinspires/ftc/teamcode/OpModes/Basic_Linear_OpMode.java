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

        Mecanum_Drive drive = new Mecanum_Drive(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            double axial   = -gamepad1.left_stick_y;
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            drive.drive(axial, lateral, yaw);

            intake.setIntakePower(gamepad1.a ? -0.7 : 0.0);
            intake.setShooterPower(gamepad1.x ? -0.7 : 0.0);

            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.addData("Drive Motors", "FL:%.2f FR:%.2f BL:%.2f BR:%.2f",
                    drive.getFrontLeftPower(), drive.getFrontRightPower(),
                    drive.getBackLeftPower(), drive.getBackRightPower());
            telemetry.addData("Intake/Shooter", "Intake:%.2f Shooter:%.2f",
                    intake.getIntakePower(), intake.getShooterPower());
            telemetry.update();
        }

        drive.stop();
        intake.stop();
    }
}
