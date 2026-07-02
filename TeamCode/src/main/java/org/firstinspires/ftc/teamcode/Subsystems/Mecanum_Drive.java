package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Mecanum_Drive {

    // Drive motors
    private final DcMotor frontLeftDrive;
    private final DcMotor backLeftDrive;
    private final DcMotor frontRightDrive;
    private final DcMotor backRightDrive;

    public Mecanum_Drive(HardwareMap hardwareMap) {
        frontLeftDrive  = hardwareMap.get(DcMotor.class, "front_left_drive"); //1
        backLeftDrive   = hardwareMap.get(DcMotor.class, "back_left_drive"); //2
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive"); //0
        backRightDrive  = hardwareMap.get(DcMotor.class, "back_right_drive"); //3

        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);
    }
    public void drive(double axial, double lateral, double yaw) {
        double frontLeftPower  = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower   = axial - lateral + yaw;
        double backRightPower  = axial + lateral - yaw;

        double max = Math.max(Math.abs(frontLeftPower),  Math.abs(frontRightPower));
        max        = Math.max(max, Math.abs(backLeftPower));
        max        = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower  /= max;
            frontRightPower /= max;
            backLeftPower   /= max;
            backRightPower  /= max;
        }

        frontLeftDrive.setPower(frontLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backLeftDrive.setPower(backLeftPower);
        backRightDrive.setPower(backRightPower);
    }
    public void stop() {
        drive(0, 0, 0);
    }
    public double getFrontLeftPower()  { return frontLeftDrive.getPower();  }
    public double getFrontRightPower() { return frontRightDrive.getPower(); }
    public double getBackLeftPower()   { return backLeftDrive.getPower();   }
    public double getBackRightPower()  { return backRightDrive.getPower();  }
}