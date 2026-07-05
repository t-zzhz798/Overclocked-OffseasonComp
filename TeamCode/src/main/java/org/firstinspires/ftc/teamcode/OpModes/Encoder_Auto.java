package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Encoder_Auto", group="robot")
@Disabled
public class Encoder_Auto extends LinearOpMode {
    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor intakeMotor;
    DcMotor shooterMotor;

    static final double TICKS_PER_ROTATION = 28.00;
    static final double DRIVE_GEAR_REDUCTION = 11.21;
    static final double WHEEL_DIAMETER = 4.09;
    static final double COUNTS_PER_INCH = (TICKS_PER_ROTATION * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER * Math.PI);

    @Override
    public void runOpMode() {
        initialise();
        waitForStart();
        moveVertically(-7,0.7);
        moveHorizontally(3,0.4);
        moveVertically(5,0.7);
        moveVertically(2,0.2);
        shoot(0.7,1.0);
    }

    public void initialise() {
        frontLeft = hardwareMap.get(DcMotor.class, "front_left_drive"); //1
        backLeft = hardwareMap.get(DcMotor.class, "back_left_drive"); //2
        frontRight = hardwareMap.get(DcMotor.class, "front_right_drive"); //0
        backRight = hardwareMap.get(DcMotor.class, "back_right_drive"); //3
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor"); //0
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor"); //1

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        shooterMotor.setDirection(DcMotor.Direction.FORWARD);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void reset() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void powerAllMotors(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    public void shoot(double shooterPower, double intakePower) {
        shooterMotor.setPower(shooterPower);
        intakeMotor.setPower(intakePower);
    }

    public void strafe(double power) {
        frontLeft.setPower(-power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(-power);
    }

    boolean started = false;

    public boolean moveVertically(double inches, double power) {
        if (!started) {
            started = true;
            reset();
        }

        double ticksTravelled = Math.abs(frontLeft.getCurrentPosition());
        double distanceTravelled = ticksTravelled / COUNTS_PER_INCH;

        if (distanceTravelled < Math.abs(inches)) {
            powerAllMotors(power);
            return true;
        } else {
            powerAllMotors(0);
            started = false;
            return false;
        }

    }

    public boolean moveHorizontally(double inches, double power) {
        if (!started) {
            started = true;
            reset();
        }

        double ticksTravelled = Math.abs(frontLeft.getCurrentPosition());
        double distanceTravelled = ticksTravelled / TICKS_PER_ROTATION * WHEEL_DIAMETER * Math.PI;

        if (distanceTravelled < Math.abs(inches)) {
            strafe(power);
            return true;
        } else {
            powerAllMotors(0);
            started = false;
            return false;
        }

    }

}
