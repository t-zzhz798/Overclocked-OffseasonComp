package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Mecanum_Drive {

    // Drive motors
    private final DcMotor flDrive; //this is our encoder motor
    private final DcMotor frDrive;
    private final DcMotor blDrive;
    private final DcMotor brDrive;

    public Mecanum_Drive(HardwareMap hardwareMap) {
        flDrive = hardwareMap.get(DcMotor.class, "front_left_drive"); //1
        frDrive = hardwareMap.get(DcMotor.class, "front_right_drive"); //0
        blDrive = hardwareMap.get(DcMotor.class, "back_left_drive"); //2
        brDrive = hardwareMap.get(DcMotor.class, "back_right_drive"); //3

        flDrive.setDirection(DcMotor.Direction.FORWARD);
        blDrive.setDirection(DcMotor.Direction.FORWARD);
        frDrive.setDirection(DcMotor.Direction.REVERSE);
        brDrive.setDirection(DcMotor.Direction.REVERSE);
    }

    //TeleOp
    public void drive(double axial, double lateral, double yaw) {
        double FLpower  = axial + lateral + yaw;
        double FRpower = axial - lateral - yaw;
        double BLpower   = axial - lateral + yaw;
        double BRpower  = axial + lateral - yaw;

        double max = Math.max(Math.abs(FLpower),  Math.abs(FRpower));
        max        = Math.max(max, Math.abs(BLpower));
        max        = Math.max(max, Math.abs(BRpower));

        if (max > 0.7) {
            FLpower  /= max;
            FRpower /= max;
            BLpower   /= max;
            BRpower  /= max;
        }

        flDrive.setPower(FLpower);
        frDrive.setPower(FRpower);
        blDrive.setPower(BLpower);
        brDrive.setPower(BRpower);
    }
    
    public void drive(Gamepad gamepad) {
        double axial = -gamepad.left_stick_y;
        double lateral = gamepad.left_stick_x;
        double yaw = gamepad.right_stick_x;
        drive(axial, lateral, yaw);
    }

    public void stop() {
        drive(0, 0, 0);
    }

    public void update (Telemetry telemetry, Gamepad gamepad1){
        drive(gamepad1);
        telemetry.addData("Drive Motors", "FL:%.2f FR:%.2f BL:%.2f BR:%.2f",
                flDrive.getPower(), frDrive.getPower(), blDrive.getPower(), brDrive.getPower());
    }

    //Autonomous
    private void powerMotors(double flPower, double frPower, double blPower, double brPower){
        this.flDrive.setPower(flPower);
        this.frDrive.setPower(frPower);
        this.blDrive.setPower(blPower);
        this.brDrive.setPower(brPower);
    }

    private void resetEncoder() {
        flDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    private void powerStraight(double power) {
        powerMotors(power, power, power, power);
    }

    private void powerStrafe(double power){
        powerMotors(power, -power, -power, power);
    }

    private void stopMotors() {
        powerMotors(0,0,0,0);
    }

    private double getDistance() {
        double ticksTravelled = Math.abs(flDrive.getCurrentPosition());
        double WHEEL_CIRCUMFERENCE = Math.PI * 4.09;
        double TICK_PER_ROTATION = 28.00 * 11.21;
        return (WHEEL_CIRCUMFERENCE / TICK_PER_ROTATION)*ticksTravelled;
    }

    private double powerMotorRatio(double target_distance, double scale) {
        if (target_distance == 0) return 0;
        double a = (scale-1)/target_distance;
        double b = scale*target_distance/(scale-1);
        double x = getDistance();
        double val = a*(-x+b);
        if (val <= 0.1) return 0.1;
        return (Math.log10(val))/Math.log10(scale);
    }

    private boolean hasRun = false;
    public boolean moveForwardDistance(double inches, double power, double scale){
        double powerRatio = powerMotorRatio(Math.abs(inches), scale);
        //Run this once when trajectory is first called -
        if (!hasRun) {
            hasRun = true;
            resetEncoder();
        }
        //Run this when trajectory is in action - UPDATE
        if (hasRun && getDistance()<Math.abs(inches)) {
            powerStraight(power*powerRatio);
            return true;
        }
        //Run this once when trajectory has ended -END
        stopMotors();
        hasRun = false;
        return false;
    }

    public boolean moveHorizontalDistance(double inches, double power, double scale) {
        double powerRatio = powerMotorRatio(Math.abs(inches), scale);
        if (!hasRun) {
            hasRun = true;
            resetEncoder();
        }
        //Run this when trajectory is in action - UPDATE
        if (hasRun && getDistance()<Math.abs(inches)) {
            powerStrafe(power*powerRatio);
            return true;
        }
        //Run this once when trajectory has ended -END
        stopMotors();
        hasRun = false;
        return false;
    }
}
